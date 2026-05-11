import { useCallback, useEffect, useMemo, useState } from "react";
import api from "../api/api";
import Navbar from "../components/Navbar";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

// ---------------------------------------------------------------------------
// Time-conflict helpers (pure)
// ---------------------------------------------------------------------------

function parseClockTime(clockTime) {
  const match = clockTime?.match(/^(\d{1,2}):(\d{2})\s([AP]M)$/);
  if (!match) return null;
  let hours = Number(match[1]);
  const minutes = Number(match[2]);
  const isPm = match[3] === "PM";
  if (hours === 12) hours = 0;
  if (isPm) hours += 12;
  return hours * 60 + minutes;
}

function parseScheduleTime(scheduleTime) {
  const match = scheduleTime?.match(
    /^(Mon|Tue|Wed|Thu|Fri|Sat|Sun)\s+(\d{1,2}:\d{2}\s[AP]M)\s*-\s*(\d{1,2}:\d{2}\s[AP]M)$/,
  );
  if (!match) return null;
  const start = parseClockTime(match[2]);
  const end = parseClockTime(match[3]);
  if (start === null || end === null) return null;
  return { day: match[1], start, end };
}

function hasTimeConflict(candidateSection, enrolledSections) {
  const slot = parseScheduleTime(candidateSection.scheduleTime);
  if (!slot) return false;
  return enrolledSections.some((enrolled) => {
    const es = parseScheduleTime(enrolled.scheduleTime);
    if (!es) return false;
    if (slot.day !== es.day) return false;
    return slot.start < es.end && es.start < slot.end;
  });
}

// ---------------------------------------------------------------------------
// Status chip (pure)
// ---------------------------------------------------------------------------

function buildStatusChip(section, semesterClosed, activeSchedule) {
  if (section.isEnrolled) return { label: "Added", tone: "neutral" };
  if (semesterClosed) return { label: "Closed", tone: "danger" };
  if (section.enrolledCount >= section.capacity)
    return { label: "Full", tone: "danger" };
  if (hasTimeConflict(section, activeSchedule))
    return { label: "Time conflict", tone: "warning" };
  return {
    label: `${section.capacity - section.enrolledCount} seats left`,
    tone: "success",
  };
}

// ---------------------------------------------------------------------------
// Semester helpers (pure)
// ---------------------------------------------------------------------------

function parseSemesterDates(sem) {
  return {
    startDate: sem.startDate ? new Date(sem.startDate) : new Date(0),
    endDate: sem.endDate ? new Date(sem.endDate) : new Date(0),
  };
}

function semesterLabel(sem) {
  return (
    sem.termName ??
    sem.term_name ??
    sem.label ??
    sem.name ??
    String(sem.termId ?? sem.semesterId ?? sem.id ?? "")
  );
}

function semesterId(sem) {
  return sem.semesterId ?? sem.termId ?? sem.id ?? null;
}

function semesterIsClosed(sem, now) {
  const { endDate } = parseSemesterDates(sem);
  return now > endDate;
}

function getDefaultSemester(semesters, now) {
  const current = semesters.find((s) => {
    const { startDate, endDate } = parseSemesterDates(s);
    return now >= startDate && now <= endDate;
  });
  if (current) return current;
  const upcoming = semesters.find((s) => now < parseSemesterDates(s).startDate);
  return upcoming ?? semesters[0] ?? null;
}

// ---------------------------------------------------------------------------
// Component
// ---------------------------------------------------------------------------

export default function StudentDashboard() {
  const now = useMemo(() => new Date(), []);
  const [student, setStudent] = useState(null);
  const [semesters, setSemesters] = useState([]);
  const [activeSemester, setActiveSemester] = useState(null);
  const [sectionCoursePairs, setSectionCoursePairs] = useState([]);
  const [sectionsLoading, setSectionsLoading] = useState(false);
  const [enrollments, setEnrollments] = useState([]);
  const [enrollmentsLoading, setEnrollmentsLoading] = useState(false);
  const [pendingSection, setPendingSection] = useState(null);
  const [enrolling, setEnrolling] = useState(false);
  const [droppingId, setDroppingId] = useState(null);
  const [notice, setNotice] = useState(null);

  // auto-dismiss notice
  useEffect(() => {
    if (!notice) return undefined;
    const id = window.setTimeout(() => setNotice(null), 4500);
    return () => window.clearTimeout(id);
  }, [notice]);

  // ── fetch profile ───────────────────────────────────────────────────────────
  useEffect(() => {
    api
      .get("/api/me/profile")
      .then((res) => setStudent(res.data))
      .catch((err) => console.error("Failed to fetch profile:", err));
  }, []);

  // ── fetch semesters ─────────────────────────────────────────────────────────
  useEffect(() => {
    api
      .get("/api/student/semesters")
      .then((res) => {
        const list = Array.isArray(res.data)
          ? res.data
          : (res.data?.data ?? []);
        setSemesters(list);
        const defaultSem = getDefaultSemester(list, now);
        setActiveSemester(defaultSem);
      })
      .catch((err) => console.error("Failed to fetch semesters:", err));
  }, [now]);

  // ── fetch enrollments ───────────────────────────────────────────────────────
  const fetchEnrollments = useCallback(() => {
    setEnrollmentsLoading(true);
    return api
      .get("/api/student/enrollments")
      .then((res) => {
        const raw = Array.isArray(res.data) ? res.data : (res.data?.data ?? []);
        setEnrollments(raw);
        return raw;
      })
      .catch((err) => {
        console.error("Failed to fetch enrollments:", err);
        return [];
      })
      .finally(() => setEnrollmentsLoading(false));
  }, []);

  useEffect(() => {
    fetchEnrollments();
  }, [fetchEnrollments]);

  // ── fetch sections for active semester ─────────────────────────────────────
  const fetchSectionsForSemester = useCallback(async (semId) => {
    if (!semId) return;
    setSectionsLoading(true);
    try {
      const res = await api.get(
        `/api/student/semesters/${semId}/sections/details`,
      );
      const list = Array.isArray(res.data?.data)
        ? res.data.data
        : Array.isArray(res.data)
          ? res.data
          : [];
      setSectionCoursePairs(list);
    } catch (err) {
      console.error("Failed to fetch semester sections:", err);
      if (err.response) {
        console.error(
          "Status:",
          err.response.status,
          "Data:",
          err.response.data,
        );
      }
      setSectionCoursePairs([]);
    } finally {
      setSectionsLoading(false);
    }
  }, []);

  useEffect(() => {
    if (!activeSemester) {
      setSectionCoursePairs([]);
      return;
    }
    const sid = semesterId(activeSemester);
    if (sid) fetchSectionsForSemester(sid);
  }, [activeSemester, fetchSectionsForSemester]);

  // ── derived state ───────────────────────────────────────────────────────────

  // Set of section ids the student is currently enrolled in
  const enrolledSectionIds = useMemo(
    () => new Set(enrollments.map((e) => String(e.sectionId))),
    [enrollments],
  );

  const activeIsClosed = activeSemester
    ? semesterIsClosed(activeSemester, now)
    : true;

  // Lookup map: sectionId -> details from the loaded course/section list
  // for the active semester. We use this so an enrollment row (which may
  // not include credits/scheduleTime) can be enriched with the data the
  // course API does return.
  const sectionDetailsMap = useMemo(() => {
    const map = new Map();
    if (!Array.isArray(sectionCoursePairs)) return map;
    for (const item of sectionCoursePairs) {
      const sid = String(item.sectionId ?? item.id ?? "");
      if (sid) map.set(sid, item);
    }
    return map;
  }, [sectionCoursePairs]);

  const activeSchedule = useMemo(() => {
    if (!activeSemester) return [];
    const activeLabel = semesterLabel(activeSemester);

    return enrollments
      .filter((e) => {
        const eLabel = e.semester ?? e.termName ?? "";
        return !eLabel || eLabel === activeLabel;
      })
      .map((e) => {
        const sid = String(e.sectionId);
        const details = sectionDetailsMap.get(sid) ?? {};
        return {
          id: sid,
          enrollmentId: e.enrollmentId,
          sectionId: sid,
          courseCode: e.courseCode ?? details.courseCode ?? "",
          courseTitle:
            e.courseName ??
            e.courseTitle ??
            details.courseName ??
            details.courseTitle ??
            "",
          credits: e.credits ?? details.credits ?? 0,
          sectionLabel: e.sectionLabel ?? details.sectionLabel ?? "",
          instructor: e.instructor ?? details.instructor ?? "",
          scheduleTime: e.scheduleTime ?? details.scheduleTime ?? "TBA",
          location: e.location ?? details.location ?? "",
          capacity: e.capacity ?? details.capacity ?? 0,
          enrolledCount: e.enrolledCount ?? details.enrolledCount ?? 0,
          semesterLabel: e.semester ?? "",
        };
      });
  }, [enrollments, activeSemester, sectionDetailsMap]);

  const resolvedLabel = activeSemester
    ? semesterLabel(activeSemester)
    : "Select a semester";

  // Each entry is a flat object with everything the UI needs.
  // No more nested course/section — we keep one shape end-to-end.
  const sectionList = useMemo(() => {
    if (!Array.isArray(sectionCoursePairs)) return [];

    return sectionCoursePairs.map((item) => {
      const sid = String(item.sectionId ?? item.id ?? "");
      return {
        id: sid,
        sectionId: sid,
        courseCode: item.courseCode ?? "N/A",
        courseTitle: item.courseName ?? item.courseTitle ?? "Untitled",
        credits: item.credits ?? 0,
        sectionLabel: item.sectionLabel ?? "N/A",
        instructor: item.instructor ?? "TBA",
        scheduleTime: item.scheduleTime ?? "TBA",
        location: item.location ?? "TBA",
        capacity: item.capacity ?? 0,
        enrolledCount: item.enrolledCount ?? 0,
        isEnrolled: enrolledSectionIds.has(sid),
      };
    });
  }, [sectionCoursePairs, enrolledSectionIds]);

  // ── enroll: pre-flight checks, then open confirmation modal ────────────────
  // entry is one row from sectionList — flat shape, has everything we need.
  function handleRequestAddSection(entry) {
    if (!activeSemester || activeIsClosed) {
      setNotice({
        type: "error",
        message: "Enrollment is closed for this semester.",
      });
      return;
    }
    if (entry.isEnrolled) {
      setNotice({
        type: "error",
        message: "You are already enrolled in this section.",
      });
      return;
    }
    if (entry.enrolledCount >= entry.capacity) {
      setNotice({ type: "error", message: "This section is full." });
      return;
    }
    if (hasTimeConflict(entry, activeSchedule)) {
      setNotice({
        type: "error",
        message: "This section conflicts with your current schedule.",
      });
      return;
    }
    setPendingSection(entry);
    setNotice(null);
  }

  // ── enroll: confirmed ───────────────────────────────────────────────────────
  async function handleConfirmAddSection() {
    if (!pendingSection || enrolling) return;

    setEnrolling(true);
    try {
      const sectionIdNum = Number(pendingSection.sectionId);
      if (!sectionIdNum || Number.isNaN(sectionIdNum)) {
        setNotice({ type: "error", message: "Invalid section ID." });
        setPendingSection(null);
        return;
      }

      const res = await api.post("/api/student/enrollments", {
        sectionId: sectionIdNum,
      });
      const body = res.data;

      if (body?.success === false) {
        const msg =
          (body.errors ?? []).join(", ") ||
          body.message ||
          "Enrollment failed.";
        setNotice({ type: "error", message: msg });
      } else {
        setNotice({
          type: "success",
          message: body?.message || "Section added to your schedule.",
        });
        // Refresh enrollments + section list (enrolledCount updates)
        await fetchEnrollments();
        const sid = activeSemester ? semesterId(activeSemester) : null;
        if (sid) fetchSectionsForSemester(sid);
      }
    } catch (err) {
      console.error("Enroll error:", err);
      const errData = err?.response?.data;
      const msg =
        (errData?.errors ?? []).join(", ") ||
        errData?.message ||
        err?.message ||
        "Failed to enroll. Please try again.";
      setNotice({ type: "error", message: msg });
    } finally {
      setEnrolling(false);
      setPendingSection(null);
    }
  }

  // ── drop enrollment ─────────────────────────────────────────────────────────
  async function handleDropSection(scheduleEntry) {
    const sectionIdToDrop = scheduleEntry.sectionId || scheduleEntry.id;
    setDroppingId(sectionIdToDrop);
    try {
      await api.delete(`/api/student/enrollments/${sectionIdToDrop}`);
      setNotice({
        type: "success",
        message: "Section removed from your schedule.",
      });
      await fetchEnrollments();
      const sid = activeSemester ? semesterId(activeSemester) : null;
      if (sid) fetchSectionsForSemester(sid);
    } catch (err) {
      console.error("Drop error:", err);
      const errData = err?.response?.data;
      const msg =
        errData?.message ||
        err?.message ||
        "Failed to drop section. Please try again.";
      toast.error(msg, {
        style: {
          background: "#111827",
          color: "#fff",
        },
      });
      setNotice({ type: "error", message: msg });
    } finally {
      setDroppingId(null);
    }
  }

  // ---------------------------------------------------------------------------
  // Render
  // ---------------------------------------------------------------------------

  return (
    <div className="min-h-screen bg-[radial-gradient(circle_at_top,rgba(56,189,248,0.18),transparent_38%),linear-gradient(135deg,#020617_0%,#0f172a_50%,#020617_100%)]">
      <Navbar studentName={student?.firstName || student?.name} />

      <ToastContainer
        position="top-right"
        autoClose={4500}
        theme="dark"
        newestOnTop
        pauseOnFocusLoss={false}
      />
      <main className="space-y-8 px-6 py-8 text-white">
        {/* ── Hero ── */}
        <section className="grid gap-4 items-stretch lg:grid-cols-[1.35fr_0.65fr]">
          <div className="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-2xl shadow-sky-950/30 backdrop-blur-xl">
            <p className="mb-3 text-sm uppercase tracking-[0.35em] text-sky-200/70">
              Course Registration System
            </p>
            <h2 className="text-3xl font-bold text-white"></h2>
            <p className="mt-3 max-w-2xl text-white/70">
              Pick a semester from your enrollment year onward, review your
              schedule, and add individual sections with conflict, capacity, and
              deadline checks.
            </p>
            {student?.enrollmentYear ? (
              <p className="mt-4 text-sm text-sky-100/70">
                Enrollment year: {student.enrollmentYear}
              </p>
            ) : null}
          </div>

          <div className="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-2xl shadow-sky-950/30 backdrop-blur-xl">
            <div className="flex items-center justify-between">
              <span className="text-sm text-white/60">Selected semester</span>
              <span
                className={`rounded-full px-3 py-1 text-xs font-semibold ${
                  activeIsClosed
                    ? "bg-rose-500/15 text-rose-300"
                    : "bg-emerald-500/15 text-emerald-300"
                }`}
              >
                {activeIsClosed ? "Enrollment closed" : "Enrollment open"}
              </span>
            </div>
            <div className="mt-4">
              <div className="rounded-2xl bg-black/20 p-6">
                <div className="text-white/50">Sections</div>
                <div className="mt-2 text-4xl font-bold">
                  {enrollmentsLoading ? "…" : activeSchedule.length}
                </div>
              </div>
            </div>
          </div>
        </section>

        {/* ── Notice ── */}
        {notice ? (
          <div
            className={`rounded-2xl border px-4 py-3 text-sm ${
              notice.type === "success"
                ? "border-emerald-400/30 bg-emerald-500/15 text-emerald-100"
                : "border-rose-400/30 bg-rose-500/15 text-rose-100"
            }`}
          >
            {notice.message}
          </div>
        ) : null}

        {/* ── Semester tabs ── */}
        <section className="flex flex-wrap gap-3">
          {semesters.map((sem) => {
            const label = semesterLabel(sem);
            const isActive =
              activeSemester && semesterLabel(activeSemester) === label;
            const closed = semesterIsClosed(sem, now);

            return (
              <button
                key={semesterId(sem) ?? label}
                type="button"
                onClick={() => setActiveSemester(sem)}
                className={`rounded-full border px-4 py-2 text-sm font-semibold transition-all duration-200 ${
                  isActive
                    ? "border-sky-300 bg-sky-300/20 text-white"
                    : "border-white/10 bg-white/5 text-white/70 hover:bg-white/10 hover:text-white"
                }`}
              >
                {label}
                <span className="ml-2 text-[11px] uppercase tracking-[0.25em] text-white/45">
                  {closed ? "Closed" : "Open"}
                </span>
              </button>
            );
          })}
        </section>

        {/* ── My Schedule + Available Sections ── */}
        <section className="grid gap-6 lg:grid-cols-2">
          {/* My Schedule */}
          <div className="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-2xl shadow-sky-950/30 backdrop-blur-xl">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-white/60">My Schedule</p>
                <h3 className="text-xl font-semibold">{resolvedLabel}</h3>
              </div>
              <span className="rounded-full bg-white/10 px-3 py-1 text-xs text-white/70">
                {activeSchedule.length} sections
              </span>
            </div>

            <div className="mt-5 space-y-4">
              {enrollmentsLoading ? (
                <div className="rounded-2xl border border-dashed border-white/15 bg-black/10 p-6 text-sm text-white/60">
                  Loading schedule…
                </div>
              ) : !activeSemester || activeSchedule.length === 0 ? (
                <div className="rounded-2xl border border-dashed border-white/15 bg-black/10 p-6 text-sm text-white/60">
                  {activeSemester
                    ? "Your schedule is empty for this semester. Add a section to start building it."
                    : "Select a semester to view your schedule."}
                </div>
              ) : (
                activeSchedule.map((entry) => (
                  <article
                    key={entry.id}
                    className="rounded-2xl border border-white/10 bg-black/20 p-4"
                  >
                    <div className="flex items-start justify-between gap-4">
                      <div>
                        <p className="text-sm uppercase tracking-[0.2em] text-sky-200/70">
                          {entry.courseCode}
                        </p>
                        <h4 className="mt-1 text-lg font-semibold">
                          {entry.courseTitle}
                        </h4>
                      </div>
                      <span className="rounded-full bg-white/10 px-3 py-1 text-xs text-white/70">
                        Section {entry.sectionLabel}
                      </span>
                    </div>

                    <div className="mt-4 grid gap-2 text-sm text-white/65 sm:grid-cols-3">
                      <span>{entry.instructor}</span>
                      <span>{entry.scheduleTime}</span>
                      <span>{entry.location}</span>
                    </div>

                    {!activeIsClosed && (
                      <div className="mt-4 flex justify-end border-t border-white/10 pt-4">
                        <button
                          type="button"
                          onClick={() => handleDropSection(entry)}
                          disabled={
                            droppingId === (entry.sectionId || entry.id)
                          }
                          className="rounded-xl border border-rose-400/30 bg-rose-500/10 px-4 py-2 text-sm font-semibold text-rose-300 transition-all duration-200 hover:bg-rose-500/20 active:scale-95 disabled:cursor-not-allowed disabled:opacity-50"
                        >
                          {droppingId === (entry.sectionId || entry.id)
                            ? "Removing…"
                            : "Remove"}
                        </button>
                      </div>
                    )}
                  </article>
                ))
              )}
            </div>
          </div>

          {/* Available Sections */}
          <div className="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-2xl shadow-sky-950/30 backdrop-blur-xl">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-white/60">Available Sections</p>
                <h3 className="text-xl font-semibold">
                  Choose your next section
                </h3>
              </div>
              <span className="rounded-full bg-white/10 px-3 py-1 text-xs text-white/70">
                {sectionList.length} sections
              </span>
            </div>

            <div className="mt-5 space-y-4">
              {sectionsLoading ? (
                <div className="rounded-2xl border border-dashed border-white/15 bg-black/10 p-6 text-sm text-white/60">
                  Loading sections…
                </div>
              ) : !activeSemester ? null : sectionList.length === 0 ? (
                <div className="rounded-2xl border border-dashed border-white/15 bg-black/10 p-6 text-sm text-white/60">
                  No sections available for this semester.
                </div>
              ) : (
                sectionList.map((entry) => {
                  const status = buildStatusChip(
                    entry,
                    activeIsClosed,
                    activeSchedule,
                  );
                  const buttonDisabled =
                    status.label === "Added" ||
                    status.label === "Closed" ||
                    status.label === "Full" ||
                    activeIsClosed;

                  return (
                    <article
                      key={entry.id}
                      className="rounded-2xl border border-white/10 bg-black/20 p-4"
                    >
                      <div className="flex items-start justify-between gap-4">
                        <div>
                          <p className="text-sm uppercase tracking-[0.2em] text-sky-200/70">
                            {entry.courseCode}
                          </p>
                          <h4 className="mt-1 text-lg font-semibold text-white">
                            {entry.courseTitle}
                          </h4>
                          <p className="mt-1 text-sm text-white/55">
                            Section {entry.sectionLabel}
                          </p>
                        </div>
                        <span
                          className={`rounded-full px-3 py-1 text-xs font-semibold ${
                            status.tone === "success"
                              ? "bg-emerald-500/15 text-emerald-300"
                              : status.tone === "warning"
                                ? "bg-amber-500/15 text-amber-300"
                                : "bg-white/10 text-white/70"
                          }`}
                        >
                          {status.label}
                        </span>
                      </div>

                      <div className="mt-4 grid gap-2 text-sm text-white/65 sm:grid-cols-3">
                        <span>{entry.instructor}</span>
                        <span>{entry.location}</span>
                        <span>
                          {entry.enrolledCount}/{entry.capacity} capacity
                        </span>
                      </div>

                      <div className="mt-4 flex justify-end border-t border-white/10 pt-4">
                        <button
                          type="button"
                          onClick={() => handleRequestAddSection(entry)}
                          disabled={buttonDisabled}
                          className="rounded-xl bg-sky-500 px-4 py-2 text-sm font-semibold text-white transition-all duration-200 hover:bg-sky-400 active:scale-95 disabled:cursor-not-allowed disabled:bg-white/10 disabled:text-white/40"
                        >
                          Add Section
                        </button>
                      </div>
                    </article>
                  );
                })
              )}
            </div>
          </div>
        </section>

        {/* ── Confirm enroll modal ── */}
        {pendingSection ? (
          <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-950/70 px-4 py-8 backdrop-blur-sm">
            <div className="w-full max-w-lg rounded-3xl border border-white/10 bg-slate-950 p-6 shadow-2xl shadow-sky-950/40">
              <p className="text-sm uppercase tracking-[0.3em] text-sky-200/70">
                Confirm section
              </p>
              <h3 className="mt-2 text-2xl font-semibold text-white">
                Add {pendingSection.courseCode} – Section{" "}
                {pendingSection.sectionLabel}?
              </h3>
              <div className="mt-4 space-y-3 rounded-2xl border border-white/10 bg-white/5 p-4 text-sm text-white/70">
                <p>{pendingSection.courseTitle}</p>
                <p>{pendingSection.scheduleTime}</p>
                <p>{pendingSection.location}</p>
                <p>
                  {pendingSection.enrolledCount}/{pendingSection.capacity}{" "}
                  capacity
                </p>
              </div>
              <div className="mt-6 flex flex-wrap justify-end gap-3">
                <button
                  type="button"
                  onClick={() => setPendingSection(null)}
                  disabled={enrolling}
                  className="rounded-xl border border-white/10 bg-white/5 px-4 py-2 text-sm font-semibold text-white/80 transition-all duration-200 hover:bg-white/10 disabled:cursor-not-allowed disabled:opacity-50"
                >
                  Cancel
                </button>
                <button
                  type="button"
                  onClick={handleConfirmAddSection}
                  disabled={enrolling}
                  className="rounded-xl bg-sky-500 px-4 py-2 text-sm font-semibold text-white transition-all duration-200 hover:bg-sky-400 active:scale-95 disabled:cursor-not-allowed disabled:opacity-60"
                >
                  {enrolling ? "Adding…" : "Confirm Add"}
                </button>
              </div>
            </div>
          </div>
        ) : null}
      </main>
    </div>
  );
}
