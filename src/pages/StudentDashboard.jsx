import { useEffect, useMemo, useState } from "react";
import api from "../api/api";
import Navbar from "../components/Navbar";

const SEMESTER_LIBRARY = [
  {
    label: "2024 Fall",
    year: 2024,
    term: "Fall",
    startDate: new Date(2024, 7, 16),
    endDate: new Date(2024, 11, 20, 23, 59, 59, 999),
    schedule: [
      {
        id: "cpsc-101-a",
        courseId: "cpsc-101",
        courseCode: "CPSC 101",
        courseTitle: "Introduction to Programming",
        credits: 3,
        sectionLabel: "A",
        instructor: "Dr. Malik",
        scheduleTime: "Mon 9:00 AM - 10:15 AM",
        location: "Lab A",
        capacity: 30,
        enrolledCount: 28,
        isEnrolled: true,
      },
    ],
    courses: [
      {
        id: "cpsc-101",
        code: "CPSC 101",
        title: "Introduction to Programming",
        credits: 3,
        sections: [
          {
            id: "cpsc-101-a",
            sectionLabel: "A",
            instructor: "Dr. Malik",
            scheduleTime: "Mon 9:00 AM - 10:15 AM",
            location: "Lab A",
            capacity: 30,
            enrolledCount: 28,
            isEnrolled: true,
          },
          {
            id: "cpsc-101-b",
            sectionLabel: "B",
            instructor: "Dr. Malik",
            scheduleTime: "Wed 1:00 PM - 2:15 PM",
            location: "Lab B",
            capacity: 30,
            enrolledCount: 22,
            isEnrolled: false,
          },
        ],
      },
      {
        id: "math-120",
        code: "MATH 120",
        title: "Calculus I",
        credits: 4,
        sections: [
          {
            id: "math-120-a",
            sectionLabel: "A",
            instructor: "Prof. Lin",
            scheduleTime: "Tue 11:30 AM - 12:45 PM",
            location: "Room 204",
            capacity: 32,
            enrolledCount: 30,
            isEnrolled: false,
          },
          {
            id: "math-120-b",
            sectionLabel: "B",
            instructor: "Prof. Lin",
            scheduleTime: "Thu 2:00 PM - 3:15 PM",
            location: "Room 206",
            capacity: 32,
            enrolledCount: 31,
            isEnrolled: false,
          },
        ],
      },
    ],
  },
  {
    label: "2025 Winter",
    year: 2025,
    term: "Winter",
    startDate: new Date(2025, 0, 6),
    endDate: new Date(2025, 3, 25, 23, 59, 59, 999),
    schedule: [],
    courses: [
      {
        id: "se-210",
        code: "SE 210",
        title: "Software Engineering",
        credits: 3,
        sections: [
          {
            id: "se-210-a",
            sectionLabel: "A",
            instructor: "Dr. Patel",
            scheduleTime: "Tue 1:00 PM - 2:15 PM",
            location: "Room 118",
            capacity: 28,
            enrolledCount: 25,
            isEnrolled: false,
          },
          {
            id: "se-210-b",
            sectionLabel: "B",
            instructor: "Dr. Patel",
            scheduleTime: "Fri 9:00 AM - 10:15 AM",
            location: "Room 119",
            capacity: 28,
            enrolledCount: 27,
            isEnrolled: false,
          },
        ],
      },
      {
        id: "stat-200",
        code: "STAT 200",
        title: "Statistics for Computing",
        credits: 3,
        sections: [
          {
            id: "stat-200-a",
            sectionLabel: "A",
            instructor: "Dr. Brown",
            scheduleTime: "Mon 3:00 PM - 4:15 PM",
            location: "Room 310",
            capacity: 24,
            enrolledCount: 24,
            isEnrolled: false,
          },
          {
            id: "stat-200-b",
            sectionLabel: "B",
            instructor: "Dr. Brown",
            scheduleTime: "Wed 10:00 AM - 11:15 AM",
            location: "Room 312",
            capacity: 24,
            enrolledCount: 18,
            isEnrolled: false,
          },
        ],
      },
    ],
  },
  {
    label: "2025 Fall",
    year: 2025,
    term: "Fall",
    startDate: new Date(2025, 7, 18),
    endDate: new Date(2025, 11, 19, 23, 59, 59, 999),
    schedule: [
      {
        id: "cpsc-231-b",
        courseId: "cpsc-231",
        courseCode: "CPSC 231",
        courseTitle: "Data Structures",
        credits: 3,
        sectionLabel: "B",
        instructor: "Dr. Saha",
        scheduleTime: "Tue 10:30 AM - 11:45 AM",
        location: "Lab C",
        capacity: 30,
        enrolledCount: 29,
        isEnrolled: true,
      },
    ],
    courses: [
      {
        id: "cpsc-231",
        code: "CPSC 231",
        title: "Data Structures",
        credits: 3,
        sections: [
          {
            id: "cpsc-231-a",
            sectionLabel: "A",
            instructor: "Dr. Saha",
            scheduleTime: "Mon 8:30 AM - 9:45 AM",
            location: "Lab B",
            capacity: 30,
            enrolledCount: 27,
            isEnrolled: false,
          },
          {
            id: "cpsc-231-b",
            sectionLabel: "B",
            instructor: "Dr. Saha",
            scheduleTime: "Tue 10:30 AM - 11:45 AM",
            location: "Lab C",
            capacity: 30,
            enrolledCount: 29,
            isEnrolled: true,
          },
        ],
      },
      {
        id: "hum-150",
        code: "HUM 150",
        title: "Communication Skills",
        credits: 2,
        sections: [
          {
            id: "hum-150-a",
            sectionLabel: "A",
            instructor: "Dr. Ali",
            scheduleTime: "Thu 1:00 PM - 2:15 PM",
            location: "Room 115",
            capacity: 20,
            enrolledCount: 19,
            isEnrolled: false,
          },
          {
            id: "hum-150-b",
            sectionLabel: "B",
            instructor: "Dr. Ali",
            scheduleTime: "Fri 3:00 PM - 4:15 PM",
            location: "Room 117",
            capacity: 20,
            enrolledCount: 18,
            isEnrolled: false,
          },
        ],
      },
    ],
  },
  {
    label: "2026 Winter",
    year: 2026,
    term: "Winter",
    startDate: new Date(2026, 0, 5),
    endDate: new Date(2026, 3, 30, 23, 59, 59, 999),
    schedule: [
      {
        id: "cpsc-320-b",
        courseId: "cpsc-320",
        courseCode: "CPSC 320",
        courseTitle: "Operating Systems",
        credits: 4,
        sectionLabel: "B",
        instructor: "Dr. Khan",
        scheduleTime: "Mon 10:00 AM - 11:15 AM",
        location: "Lab B",
        capacity: 28,
        enrolledCount: 28,
        isEnrolled: true,
      },
    ],
    courses: [
      {
        id: "cpsc-320",
        code: "CPSC 320",
        title: "Operating Systems",
        credits: 4,
        sections: [
          {
            id: "cpsc-320-a",
            sectionLabel: "A",
            instructor: "Dr. Khan",
            scheduleTime: "Mon 9:00 AM - 10:15 AM",
            location: "Lab A",
            capacity: 28,
            enrolledCount: 26,
            isEnrolled: false,
          },
          {
            id: "cpsc-320-b",
            sectionLabel: "B",
            instructor: "Dr. Khan",
            scheduleTime: "Mon 10:00 AM - 11:15 AM",
            location: "Lab B",
            capacity: 28,
            enrolledCount: 28,
            isEnrolled: true,
          },
          {
            id: "cpsc-320-c",
            sectionLabel: "C",
            instructor: "Dr. Khan",
            scheduleTime: "Wed 1:00 PM - 2:15 PM",
            location: "Lab C",
            capacity: 28,
            enrolledCount: 12,
            isEnrolled: false,
          },
        ],
      },
      {
        id: "math-350",
        code: "MATH 350",
        title: "Probability and Statistics",
        credits: 3,
        sections: [
          {
            id: "math-350-a",
            sectionLabel: "A",
            instructor: "Prof. Lin",
            scheduleTime: "Mon 10:30 AM - 11:45 AM",
            location: "Room 220",
            capacity: 24,
            enrolledCount: 24,
            isEnrolled: false,
          },
          {
            id: "math-350-b",
            sectionLabel: "B",
            instructor: "Prof. Lin",
            scheduleTime: "Fri 1:00 PM - 2:15 PM",
            location: "Room 221",
            capacity: 24,
            enrolledCount: 20,
            isEnrolled: false,
          },
        ],
      },
    ],
  },
  {
    label: "2026 Summer",
    year: 2026,
    term: "Summer",
    startDate: new Date(2026, 4, 1),
    endDate: new Date(2026, 7, 15, 23, 59, 59, 999),
    schedule: [],
    courses: [
      {
        id: "cpsc-360",
        code: "CPSC 360",
        title: "UI/UX Design",
        credits: 3,
        sections: [
          {
            id: "cpsc-360-a",
            sectionLabel: "A",
            instructor: "Ms. Chen",
            scheduleTime: "Tue 2:00 PM - 3:15 PM",
            location: "Design Lab",
            capacity: 18,
            enrolledCount: 16,
            isEnrolled: false,
          },
          {
            id: "cpsc-360-b",
            sectionLabel: "B",
            instructor: "Ms. Chen",
            scheduleTime: "Thu 9:00 AM - 10:15 AM",
            location: "Design Lab",
            capacity: 18,
            enrolledCount: 18,
            isEnrolled: false,
          },
        ],
      },
      {
        id: "cpsc-370",
        code: "CPSC 370",
        title: "Cloud Fundamentals",
        credits: 3,
        sections: [
          {
            id: "cpsc-370-a",
            sectionLabel: "A",
            instructor: "Dr. Morgan",
            scheduleTime: "Mon 1:00 PM - 2:15 PM",
            location: "Lab E",
            capacity: 24,
            enrolledCount: 15,
            isEnrolled: false,
          },
          {
            id: "cpsc-370-b",
            sectionLabel: "B",
            instructor: "Dr. Morgan",
            scheduleTime: "Wed 3:00 PM - 4:15 PM",
            location: "Lab F",
            capacity: 24,
            enrolledCount: 17,
            isEnrolled: false,
          },
        ],
      },
    ],
  },
  {
    label: "2026 Fall",
    year: 2026,
    term: "Fall",
    startDate: new Date(2026, 7, 18),
    endDate: new Date(2026, 11, 18, 23, 59, 59, 999),
    schedule: [],
    courses: [
      {
        id: "cpsc-410",
        code: "CPSC 410",
        title: "Computer Networks",
        credits: 3,
        sections: [
          {
            id: "cpsc-410-a",
            sectionLabel: "A",
            instructor: "Prof. Silva",
            scheduleTime: "Tue 11:30 AM - 12:45 PM",
            location: "Room 210",
            capacity: 26,
            enrolledCount: 19,
            isEnrolled: false,
          },
          {
            id: "cpsc-410-b",
            sectionLabel: "B",
            instructor: "Prof. Silva",
            scheduleTime: "Thu 3:00 PM - 4:15 PM",
            location: "Room 212",
            capacity: 26,
            enrolledCount: 25,
            isEnrolled: false,
          },
        ],
      },
      {
        id: "bus-101",
        code: "BUS 101",
        title: "Startup Basics",
        credits: 2,
        sections: [
          {
            id: "bus-101-a",
            sectionLabel: "A",
            instructor: "Prof. Hassan",
            scheduleTime: "Mon 3:00 PM - 4:15 PM",
            location: "Room 106",
            capacity: 20,
            enrolledCount: 14,
            isEnrolled: false,
          },
          {
            id: "bus-101-b",
            sectionLabel: "B",
            instructor: "Prof. Hassan",
            scheduleTime: "Fri 9:00 AM - 10:15 AM",
            location: "Room 108",
            capacity: 20,
            enrolledCount: 20,
            isEnrolled: false,
          },
        ],
      },
    ],
  },
  {
    label: "2027 Winter",
    year: 2027,
    term: "Winter",
    startDate: new Date(2027, 0, 4),
    endDate: new Date(2027, 3, 23, 23, 59, 59, 999),
    schedule: [],
    courses: [
      {
        id: "cpsc-420",
        code: "CPSC 420",
        title: "Machine Learning",
        credits: 4,
        sections: [
          {
            id: "cpsc-420-a",
            sectionLabel: "A",
            instructor: "Dr. Noor",
            scheduleTime: "Tue 9:00 AM - 10:15 AM",
            location: "Lab G",
            capacity: 22,
            enrolledCount: 18,
            isEnrolled: false,
          },
          {
            id: "cpsc-420-b",
            sectionLabel: "B",
            instructor: "Dr. Noor",
            scheduleTime: "Thu 1:00 PM - 2:15 PM",
            location: "Lab H",
            capacity: 22,
            enrolledCount: 21,
            isEnrolled: false,
          },
        ],
      },
      {
        id: "math-410",
        code: "MATH 410",
        title: "Numerical Methods",
        credits: 3,
        sections: [
          {
            id: "math-410-a",
            sectionLabel: "A",
            instructor: "Prof. Ortega",
            scheduleTime: "Mon 11:30 AM - 12:45 PM",
            location: "Room 301",
            capacity: 24,
            enrolledCount: 23,
            isEnrolled: false,
          },
          {
            id: "math-410-b",
            sectionLabel: "B",
            instructor: "Prof. Ortega",
            scheduleTime: "Wed 2:00 PM - 3:15 PM",
            location: "Room 302",
            capacity: 24,
            enrolledCount: 19,
            isEnrolled: false,
          },
        ],
      },
    ],
  },
];

const INITIAL_DASHBOARD_STATE = SEMESTER_LIBRARY.reduce((state, semester) => {
  state[semester.label] = {
    label: semester.label,
    year: semester.year,
    term: semester.term,
    startDate: semester.startDate,
    endDate: semester.endDate,
    schedule: semester.schedule,
    courses: semester.courses,
  };

  return state;
}, {});

function createEnrolledSection(course, section) {
  return {
    id: section.id,
    courseId: course.id,
    courseCode: course.code,
    courseTitle: course.title,
    credits: course.credits,
    sectionLabel: section.sectionLabel,
    instructor: section.instructor,
    scheduleTime: section.scheduleTime,
    location: section.location,
    capacity: section.capacity,
    enrolledCount: section.enrolledCount,
    isEnrolled: true,
  };
}

function buildSemesterList(enrollmentYear) {
  return SEMESTER_LIBRARY.filter((semester) => semester.year >= enrollmentYear);
}

function getDefaultSemesterLabel(semesterList, now) {
  const currentSemester = semesterList.find(
    (semester) => now >= semester.startDate && now <= semester.endDate,
  );

  if (currentSemester) {
    return currentSemester.label;
  }

  const upcomingSemester = semesterList.find(
    (semester) => now < semester.startDate,
  );

  return upcomingSemester?.label || semesterList[0]?.label || "";
}

function parseClockTime(clockTime) {
  const match = clockTime.match(/^(\d{1,2}):(\d{2})\s([AP]M)$/);

  if (!match) {
    return null;
  }

  let hours = Number(match[1]);
  const minutes = Number(match[2]);
  const isPm = match[3] === "PM";

  if (hours === 12) {
    hours = 0;
  }

  if (isPm) {
    hours += 12;
  }

  return hours * 60 + minutes;
}

function parseScheduleTime(scheduleTime) {
  const match = scheduleTime.match(
    /^(Mon|Tue|Wed|Thu|Fri|Sat|Sun)\s+(\d{1,2}:\d{2}\s[AP]M)\s*-\s*(\d{1,2}:\d{2}\s[AP]M)$/,
  );

  if (!match) {
    return null;
  }

  const start = parseClockTime(match[2]);
  const end = parseClockTime(match[3]);

  if (start === null || end === null) {
    return null;
  }

  return {
    day: match[1],
    start,
    end,
  };
}

function hasTimeConflict(candidateSection, schedule) {
  const candidateSlot = parseScheduleTime(candidateSection.scheduleTime);

  if (!candidateSlot) {
    return false;
  }

  return schedule.some((enrolledSection) => {
    const enrolledSlot = parseScheduleTime(enrolledSection.scheduleTime);

    if (!enrolledSlot) {
      return false;
    }

    if (candidateSlot.day !== enrolledSlot.day) {
      return false;
    }

    return (
      candidateSlot.start < enrolledSlot.end &&
      enrolledSlot.start < candidateSlot.end
    );
  });
}

function getSectionEnrollmentStatus(semester, section, schedule, now) {
  if (now > semester.endDate) {
    return {
      available: false,
      reason: "Enrollment closed for this semester.",
    };
  }

  if (section.isEnrolled) {
    return {
      available: false,
      reason: "You are already enrolled in this section.",
    };
  }

  if (section.enrolledCount >= section.capacity) {
    return {
      available: false,
      reason: "This section is full.",
    };
  }

  if (hasTimeConflict(section, schedule)) {
    return {
      available: false,
      reason: "This section conflicts with your current schedule.",
    };
  }

  return {
    available: true,
    reason: null,
  };
}

function buildStatusChip(section, semester, schedule, now) {
  if (section.isEnrolled) {
    return {
      label: "Added",
      tone: "neutral",
    };
  }

  if (now > semester.endDate) {
    return {
      label: "Closed",
      tone: "danger",
    };
  }

  if (section.enrolledCount >= section.capacity) {
    return {
      label: "Full",
      tone: "danger",
    };
  }

  if (hasTimeConflict(section, schedule)) {
    return {
      label: "Time conflict",
      tone: "warning",
    };
  }

  return {
    label: `${section.capacity - section.enrolledCount} seats left`,
    tone: "success",
  };
}

export default function StudentDashboard() {
  const [student, setStudent] = useState(null);
  const [selectedSemester, setSelectedSemester] = useState("");
  const [dashboardState, setDashboardState] = useState(
    () => INITIAL_DASHBOARD_STATE,
  );
  const [pendingSection, setPendingSection] = useState(null);
  const [notice, setNotice] = useState(null);

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

  const now = new Date();
  const enrollmentYear = Number(student?.enrollmentYear || now.getFullYear());

  const semesterList = useMemo(
    () => buildSemesterList(enrollmentYear),
    [enrollmentYear],
  );
  const resolvedSelectedSemester = semesterList.some(
    (semester) => semester.label === selectedSemester,
  )
    ? selectedSemester
    : getDefaultSemesterLabel(semesterList, now);

  useEffect(() => {
    if (!notice) {
      return undefined;
    }

    const timeoutId = window.setTimeout(() => setNotice(null), 4500);

    return () => window.clearTimeout(timeoutId);
  }, [notice]);

  const activeSemester = resolvedSelectedSemester
    ? dashboardState[resolvedSelectedSemester]
    : null;
  const activeSchedule = activeSemester?.schedule || [];
  const activeCourses = activeSemester?.courses || [];
  const semesterIsClosed = activeSemester ? now > activeSemester.endDate : true;
  const totalCredits = activeSchedule.reduce(
    (sum, section) => sum + section.credits,
    0,
  );

  function handleRequestAddSection(courseId, sectionId) {
    if (!resolvedSelectedSemester || !activeSemester) {
      return;
    }

    const course = activeSemester.courses.find((item) => item.id === courseId);
    const section = course?.sections.find((item) => item.id === sectionId);

    if (!course || !section) {
      return;
    }

    const status = getSectionEnrollmentStatus(
      activeSemester,
      section,
      activeSchedule,
      now,
    );

    if (!status.available) {
      setNotice({ type: "error", message: status.reason });
      return;
    }

    setPendingSection({
      semesterLabel: resolvedSelectedSemester,
      courseId,
      sectionId,
    });
    setNotice(null);
  }

  function handleConfirmAddSection() {
    if (!pendingSection) {
      return;
    }

    let didEnroll = false;

    setDashboardState((currentState) => {
      const semester = currentState[pendingSection.semesterLabel];

      if (!semester) {
        return currentState;
      }

      const course = semester.courses.find(
        (item) => item.id === pendingSection.courseId,
      );
      const section = course?.sections.find(
        (item) => item.id === pendingSection.sectionId,
      );

      if (!course || !section) {
        return currentState;
      }

      const validation = getSectionEnrollmentStatus(
        semester,
        section,
        semester.schedule,
        now,
      );

      if (!validation.available) {
        setNotice({ type: "error", message: validation.reason });
        return currentState;
      }

      didEnroll = true;

      const updatedSection = {
        ...section,
        enrolledCount: section.enrolledCount + 1,
        isEnrolled: true,
      };

      return {
        ...currentState,
        [pendingSection.semesterLabel]: {
          ...semester,
          schedule: [
            ...semester.schedule,
            createEnrolledSection(course, updatedSection),
          ],
          courses: semester.courses.map((item) => {
            if (item.id !== course.id) {
              return item;
            }

            return {
              ...item,
              sections: item.sections.map((currentSection) => {
                if (currentSection.id !== section.id) {
                  return currentSection;
                }

                return updatedSection;
              }),
            };
          }),
        },
      };
    });

    setPendingSection(null);

    if (didEnroll) {
      setNotice({
        type: "success",
        message: "Section added to your schedule.",
      });
    }
  }

  const pendingDetails = pendingSection
    ? (() => {
        const semester = dashboardState[pendingSection.semesterLabel];
        const course = semester?.courses.find(
          (item) => item.id === pendingSection.courseId,
        );
        const section = course?.sections.find(
          (item) => item.id === pendingSection.sectionId,
        );

        if (!semester || !course || !section) {
          return null;
        }

        return {
          semester,
          course,
          section,
        };
      })()
    : null;

  return (
    <div className="min-h-screen bg-[radial-gradient(circle_at_top,rgba(56,189,248,0.18),transparent_38%),linear-gradient(135deg,#020617_0%,#0f172a_50%,#020617_100%)]">
      <Navbar studentName={student?.firstName || student?.name} />

      <main className="space-y-8 px-6 py-8 text-white">
        <section className="grid gap-4 items-stretch lg:grid-cols-[1.35fr_0.65fr]">
          <div className="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-2xl shadow-sky-950/30 backdrop-blur-xl">
            <p className="mb-3 text-sm uppercase tracking-[0.35em] text-sky-200/70">
              Course Registration System
            </p>
            <h2 className="text-3xl font-bold text-white">

            </h2>
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
                  semesterIsClosed
                    ? "bg-rose-500/15 text-rose-300"
                    : "bg-emerald-500/15 text-emerald-300"
                }`}
              >
                {semesterIsClosed ? "Enrollment closed" : "Enrollment open"}
              </span>
            </div>
            <div className="mt-4 grid grid-cols-2 gap-3 text-sm">
              <div className="rounded-2xl bg-black/20 p-4">
                <div className="text-white/50">Sections</div>
                <div className="mt-1 text-2xl font-bold">
                  {activeSchedule.length}
                </div>
              </div>
              <div className="rounded-2xl bg-black/20 p-4">
                <div className="text-white/50">Credits</div>
                <div className="mt-1 text-2xl font-bold">{totalCredits}</div>
              </div>
            </div>
          </div>
        </section>

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

        <section className="flex flex-wrap gap-3">
          {semesterList.map((semester) => {
            const isActive = semester.label === resolvedSelectedSemester;
            const isClosed = now > semester.endDate;

            return (
              <button
                key={semester.label}
                type="button"
                onClick={() => setSelectedSemester(semester.label)}
                className={`rounded-full border px-4 py-2 text-sm font-semibold transition-all duration-200 ${
                  isActive
                    ? "border-sky-300 bg-sky-300/20 text-white"
                    : "border-white/10 bg-white/5 text-white/70 hover:bg-white/10 hover:text-white"
                }`}
              >
                {semester.label}
                <span className="ml-2 text-[11px] uppercase tracking-[0.25em] text-white/45">
                  {isClosed ? "Closed" : "Open"}
                </span>
              </button>
            );
          })}
        </section>

        <section className="grid gap-6 lg:grid-cols-2">
          <div className="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-2xl shadow-sky-950/30 backdrop-blur-xl">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-white/60">My Schedule</p>
                <h3 className="text-xl font-semibold">
                  {resolvedSelectedSemester || "Select a semester"}
                </h3>
              </div>
              <span className="rounded-full bg-white/10 px-3 py-1 text-xs text-white/70">
                {activeSchedule.length} sections
              </span>
            </div>

            <div className="mt-5 space-y-4">
              {!activeSemester || activeSchedule.length === 0 ? (
                <div className="rounded-2xl border border-dashed border-white/15 bg-black/10 p-6 text-sm text-white/60">
                  {activeSemester
                    ? "Your schedule is empty for this semester. Add a section to start building it."
                    : "Select a semester to view your schedule."}
                </div>
              ) : (
                activeSchedule.map((section) => (
                  <article
                    key={section.id}
                    className="rounded-2xl border border-white/10 bg-black/20 p-4"
                  >
                    <div className="flex items-start justify-between gap-4">
                      <div>
                        <p className="text-sm uppercase tracking-[0.2em] text-sky-200/70">
                          {section.courseCode}
                        </p>
                        <h4 className="mt-1 text-lg font-semibold">
                          {section.courseTitle}
                        </h4>
                      </div>
                      <span className="rounded-full bg-white/10 px-3 py-1 text-xs text-white/70">
                        Section {section.sectionLabel}
                      </span>
                    </div>

                    <div className="mt-4 grid gap-2 text-sm text-white/65 sm:grid-cols-3">
                      <span>{section.instructor}</span>
                      <span>{section.scheduleTime}</span>
                      <span>{section.location}</span>
                    </div>
                  </article>
                ))
              )}
            </div>
          </div>

          <div className="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-2xl shadow-sky-950/30 backdrop-blur-xl">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-white/60">Available Sections</p>
                <h3 className="text-xl font-semibold">
                  Choose your next section
                </h3>
              </div>
              <span className="rounded-full bg-white/10 px-3 py-1 text-xs text-white/70">
                {activeCourses.length} courses
              </span>
            </div>

            <div className="mt-5 space-y-4">
              {!activeSemester
                ? null
                : activeCourses.map((course) => (
                    <article
                      key={course.id}
                      className="rounded-2xl border border-white/10 bg-black/20 p-4"
                    >
                      <div className="flex items-start justify-between gap-4">
                        <div>
                          <p className="text-sm uppercase tracking-[0.2em] text-sky-200/70">
                            {course.code}
                          </p>
                          <h4 className="mt-1 text-lg font-semibold">
                            {course.title}
                          </h4>
                        </div>
                        <span className="rounded-full bg-emerald-500/15 px-3 py-1 text-xs text-emerald-300">
                          {course.credits} credits
                        </span>
                      </div>

                      <div className="mt-4 space-y-3">
                        {course.sections.map((section) => {
                          const status = buildStatusChip(
                            section,
                            activeSemester,
                            activeSchedule,
                            now,
                          );
                          const buttonDisabled =
                            status.label === "Added" ||
                            status.label === "Closed" ||
                            status.label === "Full" ||
                            semesterIsClosed;

                          return (
                            <div
                              key={section.id}
                              className="rounded-2xl border border-white/10 bg-white/5 p-4"
                            >
                              <div className="flex items-start justify-between gap-4">
                                <div>
                                  <p className="text-sm uppercase tracking-[0.2em] text-sky-200/70">
                                    Section {section.sectionLabel}
                                  </p>
                                  <h5 className="mt-1 font-semibold text-white">
                                    {section.scheduleTime}
                                  </h5>
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
                                <span>{section.instructor}</span>
                                <span>{section.location}</span>
                                <span>
                                  {section.enrolledCount}/{section.capacity}{" "}
                                  capacity
                                </span>
                              </div>

                              <div className="mt-4 flex justify-end border-t border-white/10 pt-4">
                                <button
                                  type="button"
                                  onClick={() =>
                                    handleRequestAddSection(
                                      course.id,
                                      section.id,
                                    )
                                  }
                                  disabled={buttonDisabled}
                                  className="rounded-xl bg-sky-500 px-4 py-2 text-sm font-semibold text-white transition-all duration-200 hover:bg-sky-400 active:scale-95 disabled:cursor-not-allowed disabled:bg-white/10 disabled:text-white/40"
                                >
                                  Add Section
                                </button>
                              </div>
                            </div>
                          );
                        })}
                      </div>
                    </article>
                  ))}
            </div>
          </div>
        </section>

        {pendingDetails ? (
          <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-950/70 px-4 py-8 backdrop-blur-sm">
            <div className="w-full max-w-lg rounded-3xl border border-white/10 bg-slate-950 p-6 shadow-2xl shadow-sky-950/40">
              <p className="text-sm uppercase tracking-[0.3em] text-sky-200/70">
                Confirm section
              </p>
              <h3 className="mt-2 text-2xl font-semibold text-white">
                Add {pendingDetails.course.code} - Section{" "}
                {pendingDetails.section.sectionLabel}?
              </h3>
              <div className="mt-4 space-y-3 rounded-2xl border border-white/10 bg-white/5 p-4 text-sm text-white/70">
                <p>{pendingDetails.course.title}</p>
                <p>{pendingDetails.section.scheduleTime}</p>
                <p>{pendingDetails.section.location}</p>
                <p>
                  {pendingDetails.section.enrolledCount}/
                  {pendingDetails.section.capacity} capacity
                </p>
              </div>

              <div className="mt-6 flex flex-wrap justify-end gap-3">
                <button
                  type="button"
                  onClick={() => setPendingSection(null)}
                  className="rounded-xl border border-white/10 bg-white/5 px-4 py-2 text-sm font-semibold text-white/80 transition-all duration-200 hover:bg-white/10"
                >
                  Cancel
                </button>
                <button
                  type="button"
                  onClick={handleConfirmAddSection}
                  className="rounded-xl bg-sky-500 px-4 py-2 text-sm font-semibold text-white transition-all duration-200 hover:bg-sky-400 active:scale-95"
                >
                  Confirm Add
                </button>
              </div>
            </div>
          </div>
        ) : null}
      </main>
    </div>
  );
}
