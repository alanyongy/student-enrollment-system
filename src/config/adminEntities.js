export const adminEntities = {
  students: {
    title: "Students",
    endpoint: "api/admin/students",
    idKey: "personId", 
    columns: [
      { key: "personId", label: "ID" },
      { key: "firstName", label: "First name" },
      { key: "lastName", label: "Last name" },
      { key: "email", label: "Email" },
    ],
  },

  instructors: {
    title: "Instructors",
    endpoint: "api/admin/instructors",
    idKey: "personId",
    columns: [
      { key: "id", label: "ID" },
      { key: "name", label: "Name" },
      { key: "email", label: "Email" },
    ],
  },

  departments: {
    title: "Departments",
    endpoint: "api/admin/departments",
    idKey: "deptId",
    columns: [
      { key: "id", label: "ID" },
      { key: "name", label: "Name" },
    ],
  },

  programs: {
    title: "Programs",
    endpoint: "api/admin/programs",
    idKey: "programId",
    columns: [
      { key: "id", label: "ID" }, 
      { key: "name", label: "Name" },
    ],
  },

  courses: {
    title: "Courses",
    endpoint: "api/admin/courses",
    idKey: "courseId",
    columns: [
      { key: "id", label: "ID" }, // ⚠️ may not match backend
      { key: "code", label: "Code" },
      { key: "name", label: "Name" },
    ],
  },

  sections: {
    title: "Sections",
    endpoint: "api/admin/sections",
    idKey: "sectionId",
    columns: [
      { key: "id", label: "ID" },
      { key: "sectionNumber", label: "Section #" },
    ],
  },

  semesters: {
    title: "Semesters",
    endpoint: "api/admin/semesters",
    idKey: "semesterId",
    columns: [
      { key: "id", label: "ID" },
      { key: "name", label: "Name" },
    ],
  },

  enrollments: {
    title: "Enrollments",
    endpoint: "api/admin/enrollments",
    idKey: "enrollmentId",
    columns: [
      { key: "id", label: "ID" },
      { key: "studentId", label: "Student ID" },
      { key: "sectionId", label: "Section ID" },
    ],
  },

  admissions: {
    title: "Admissions",
    endpoint: "/api/admin/admission",
    idKey: "admissionId",
    columns: [
      { key: "admissionId", label: "ID", type: "id" },

      {
        key: "student",
        label: "Student",
        type: "relation",
        entity: "students",
        displayKey: "firstName",
      },

      {
        key: "program",
        label: "Program",
        type: "relation",
        entity: "programs",
        displayKey: "programName",
      },
    ],
  },

  "course-prerequisites": {
    title: "Course Prerequisites",
    endpoint: "api/admin/course-prerequisites",
    idKey: "prerequisiteId",
    columns: [
      { key: "id", label: "ID" },
      { key: "courseId", label: "Course" },
      { key: "prerequisiteId", label: "Prerequisite" },
    ],
  },

  "program-course-access": {
    title: "Program Course Access",
    endpoint: "api/admin/program-course-access",
    idKey: "accessId",
    columns: [
      { key: "id", label: "ID" },
      { key: "programId", label: "Program" },
      { key: "courseId", label: "Course" },
    ],
  },

  "semester-sections": {
    title: "Semester Sections",
    endpoint: "api/admin/semester-sections",
    idKey: "semesterSectionId",
    columns: [
      { key: "id", label: "ID" },
      { key: "semesterId", label: "Semester" },
      { key: "sectionId", label: "Section" },
    ],
  },
};