import { useEffect, useState } from "react";
import api from "../api/api";
import Navbar from "../components/Navbar";

function getInitials(student) {
  const firstName = student?.firstName?.trim();
  const lastName = student?.lastName?.trim();

  if (firstName || lastName) {
    return `${firstName?.[0] || ""}${lastName?.[0] || ""}`.toUpperCase();
  }

  const fallbackName = student?.name?.trim();

  if (!fallbackName) {
    return "S";
  }

  return fallbackName
    .split(/\s+/)
    .slice(0, 2)
    .map((part) => part[0])
    .join("")
    .toUpperCase();
}

export default function StudentProfile() {
  const [student, setStudent] = useState(null);

  useEffect(() => {
    async function fetchProfile() {
      try {
        const res = await api.get("/api/me/profile");
        setStudent(res.data);
      } catch (err) {
        console.error("Failed to fetch profile:", err);
      }
    }

    fetchProfile();
  }, []);

  const displayName = student
    ? [student.firstName, student.lastName].filter(Boolean).join(" ") ||
      student.name ||
      "Student"
    : "Student";
  const initials = getInitials(student);
  const currentYear = new Date().getFullYear();
  const enrollmentYear = Number(student?.enrollmentYear || currentYear);
  const creditsEarned = Number(student?.creditsEarned || 0);
  const yearOfStudy = Number(student?.yearOfStudy || 0);

  return (
    <div className="min-h-screen bg-[radial-gradient(circle_at_top,rgba(56,189,248,0.18),transparent_38%),linear-gradient(135deg,#020617_0%,#0f172a_50%,#020617_100%)]">
      <Navbar studentName={displayName} activePage="profile" />

      <main className="mx-auto w-full max-w-7xl space-y-8 px-6 py-8 text-white">
        {!student ? (
          <div className="rounded-3xl border border-white/10 bg-white/5 p-6 text-white/70 shadow-2xl shadow-sky-950/30 backdrop-blur-xl">
            Loading profile...
          </div>
        ) : (
          <>
            <section className="grid gap-4 lg:grid-cols-[1.25fr_0.75fr]">
              <div className="overflow-hidden rounded-3xl border border-white/10 bg-white/5 shadow-2xl shadow-sky-950/30 backdrop-blur-xl">
                <div className="border-b border-white/10 px-6 py-6 sm:px-8 sm:py-8">
                  <div className="flex flex-col gap-5 sm:flex-row sm:items-center sm:justify-between">
                    <div className="flex items-center gap-4">
                      <div className="flex h-16 w-16 items-center justify-center rounded-2xl border border-sky-300/20 bg-sky-400/15 text-2xl font-bold text-sky-100 shadow-lg shadow-sky-900/30">
                        {initials}
                      </div>

                      <div>
                        <p className="text-sm uppercase tracking-[0.35em] text-sky-200/70">
                          Student Profile
                        </p>
                        <h1 className="mt-2 text-3xl font-bold text-white">
                          {displayName}
                        </h1>
                        <p className="mt-2 max-w-2xl text-sm text-white/65 sm:text-base">
                          Review your personal and academic details in one place
                          before heading back to registration.
                        </p>
                      </div>
                    </div>

                    <span className="w-fit rounded-full bg-emerald-500/15 px-4 py-2 text-xs font-semibold uppercase tracking-[0.3em] text-emerald-300">
                      Active student
                    </span>
                  </div>
                </div>

                <div className="grid gap-4 px-6 py-6 sm:grid-cols-3 sm:px-8">
                  <div className="rounded-2xl border border-white/10 bg-black/20 p-4">
                    <div className="text-sm text-white/50">Enrollment Year</div>
                    <div className="mt-2 text-2xl font-bold text-white">
                      {enrollmentYear || "N/A"}
                    </div>
                  </div>

                  <div className="rounded-2xl border border-white/10 bg-black/20 p-4">
                    <div className="text-sm text-white/50">Credits Earned</div>
                    <div className="mt-2 text-2xl font-bold text-white">
                      {creditsEarned}
                    </div>
                  </div>

                  <div className="rounded-2xl border border-white/10 bg-black/20 p-4">
                    <div className="text-sm text-white/50">Year of Study</div>
                    <div className="mt-2 text-2xl font-bold text-white">
                      {yearOfStudy || "N/A"}
                    </div>
                  </div>
                </div>
              </div>

              <div className="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-2xl shadow-sky-950/30 backdrop-blur-xl">
                <div className="flex items-center justify-between">
                  <h2 className="text-lg font-semibold text-white">
                    Profile Summary
                  </h2>
                  <span className="rounded-full bg-white/10 px-3 py-1 text-xs text-white/70">
                    Updated live
                  </span>
                </div>

                <div className="mt-5 space-y-3">
                  <div className="rounded-2xl bg-black/20 p-4">
                    <div className="text-sm text-white/50">Name</div>
                    <div className="mt-1 text-base font-semibold text-white">
                      {displayName}
                    </div>
                  </div>

                  <div className="rounded-2xl bg-black/20 p-4">
                    <div className="text-sm text-white/50">Email</div>
                    <div className="mt-1 break-all text-base font-semibold text-white">
                      {student.email || "N/A"}
                    </div>
                  </div>

                  <div className="rounded-2xl bg-black/20 p-4">
                    <div className="text-sm text-white/50">Status</div>
                    <div className="mt-1 text-base font-semibold text-white">
                      {student.academicStatus || "N/A"}
                    </div>
                  </div>
                </div>
              </div>
            </section>

            <section className="grid gap-6 lg:grid-cols-2">
              <div className="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-2xl shadow-sky-950/30 backdrop-blur-xl">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm text-white/60">
                      Personal Information
                    </p>
                    <h2 className="text-xl font-semibold text-white">
                      Identity and contact details
                    </h2>
                  </div>
                  <span className="rounded-full bg-white/10 px-3 py-1 text-xs text-white/70">
                    Profile
                  </span>
                </div>

                <div className="mt-5 grid gap-4 sm:grid-cols-2">
                  <div className="rounded-2xl border border-white/10 bg-black/20 p-4">
                    <div className="text-sm text-white/50">First Name</div>
                    <div className="mt-1 text-base font-semibold text-white">
                      {student.firstName || "N/A"}
                    </div>
                  </div>

                  <div className="rounded-2xl border border-white/10 bg-black/20 p-4">
                    <div className="text-sm text-white/50">Last Name</div>
                    <div className="mt-1 text-base font-semibold text-white">
                      {student.lastName || "N/A"}
                    </div>
                  </div>

                  <div className="rounded-2xl border border-white/10 bg-black/20 p-4 sm:col-span-2">
                    <div className="text-sm text-white/50">Email</div>
                    <div className="mt-1 break-all text-base font-semibold text-white">
                      {student.email || "N/A"}
                    </div>
                  </div>

                  <div className="rounded-2xl border border-white/10 bg-black/20 p-4 sm:col-span-2">
                    <div className="text-sm text-white/50">Phone</div>
                    <div className="mt-1 text-base font-semibold text-white">
                      {student.phoneNumber || "N/A"}
                    </div>
                  </div>
                </div>
              </div>

              <div className="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-2xl shadow-sky-950/30 backdrop-blur-xl">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm text-white/60">
                      Academic Information
                    </p>
                    <h2 className="text-xl font-semibold text-white">
                      Enrollment overview
                    </h2>
                  </div>
                  <span className="rounded-full bg-sky-500/15 px-3 py-1 text-xs text-sky-200">
                    Academic
                  </span>
                </div>

                <div className="mt-5 grid gap-4 sm:grid-cols-2">
                  <div className="rounded-2xl border border-white/10 bg-black/20 p-4">
                    <div className="text-sm text-white/50">Student ID</div>
                    <div className="mt-1 text-base font-semibold text-white">
                      {student.id || "N/A"}
                    </div>
                  </div>

                  <div className="rounded-2xl border border-white/10 bg-black/20 p-4">
                    <div className="text-sm text-white/50">Enrollment Year</div>
                    <div className="mt-1 text-base font-semibold text-white">
                      {student.enrollmentYear || "N/A"}
                    </div>
                  </div>

                  <div className="rounded-2xl border border-white/10 bg-black/20 p-4">
                    <div className="text-sm text-white/50">Academic Status</div>
                    <div className="mt-1 text-base font-semibold text-white">
                      {student.academicStatus || "N/A"}
                    </div>
                  </div>

                  <div className="rounded-2xl border border-white/10 bg-black/20 p-4">
                    <div className="text-sm text-white/50">Credits Earned</div>
                    <div className="mt-1 text-base font-semibold text-white">
                      {student.creditsEarned ?? "N/A"}
                    </div>
                  </div>

                  <div className="rounded-2xl border border-white/10 bg-black/20 p-4 sm:col-span-2">
                    <div className="text-sm text-white/50">Year of Study</div>
                    <div className="mt-1 text-base font-semibold text-white">
                      {student.yearOfStudy || "N/A"}
                    </div>
                  </div>
                </div>
              </div>
            </section>

            <div className="flex justify-end">
              <button className="rounded-xl bg-sky-500 px-5 py-3 text-sm font-semibold text-white shadow-lg shadow-sky-950/30 transition-all duration-200 hover:bg-sky-400 active:scale-95">
                Edit Profile
              </button>
            </div>
          </>
        )}
      </main>
    </div>
  );
}
