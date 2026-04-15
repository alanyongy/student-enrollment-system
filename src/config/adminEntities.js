export const adminEntities = {
  students: {
    title: "Students",
    endpoint: "api/admin/students",
    idKey: "personId",
    columns: [
      { key: "personId", label: "ID" },
      { key: "firstName", label: "First Name" },
      { key: "lastName", label: "Last Name" },
      { key: "email", label: "Email" },
    ],
  },

  instructors: {
    title: "Instructors",
    endpoint: "api/admin/instructors",
    idKey: "personId",
    columns: [
      { key: "personId", label: "ID" },
      { key: "firstName", label: "First Name" },
      { key: "lastName", label: "Last Name" },
      { key: "email", label: "Email" },
    ],
  },

  administrators: {
    title: "Administrators",
    endpoint: "api/admin/administrators",
    idKey: "personId",
    columns: [
      { key: "personId", label: "ID" },
      { key: "firstName", label: "First Name" },
      { key: "lastName", label: "Last Name" },
      { key: "email", label: "Email" },
    ],
  },

  departments: {
    title: "Departments",
    endpoint: "api/admin/departments",
    idKey: "deptId",
    columns: [
      { key: "deptId", label: "Department ID" },
      { key: "deptName", label: "Department Name" },
      { key: "deptEmail", label: "Department Email" },
      { key: "phoneNumber", label: "Phone Number" },
      { key: "officeLocation", label: "Office Location" },
    ],
  },

  programs: {
    title: "Programs",
    endpoint: "api/admin/programs",
    idKey: "programId",
    columns: [
      { key: "programId", label: "Program ID" },
      { key: "programName", label: "Program Name" },
      { key: "description", label: "Description" },
      { key: "programType", label: "Program Type" },
    ],
  },

  courses: {
    title: "Courses",
    endpoint: "api/admin/courses",
    idKey: "courseId",
    columns: [
      { key: "courseId", label: "Course ID" },
      { key: "courseNumber", label: "Course Number" },
      { key: "description", label: "Description" },
      { key: "credits", label: "Credits" },
    ],
  },

  sections: {
    title: "Sections",
    endpoint: "api/admin/sections",
    idKey: "sectionId",
    columns: [
      { key: "sectionId", label: "Section ID" },
      { key: "scheduleTime", label: "Scheduled Time" },
      { key: "location", label: "Location" },
      { key: "capacity", label: "Capacity" },
    ],
  },

  semesters: {
    title: "Semesters",
    endpoint: "api/admin/semesters",
    idKey: "semesterId",
    columns: [
      { key: "semesterId", label: "Semester ID" },
      { key: "termName", label: "Term Name" },
      { key: "startDate", label: "Start Date" },
      { key: "endDate", label: "End Date" },
    ],
  },

  enrollments: {
    title: "Enrollments",
    endpoint: "api/admin/enrollments",
    idKey: "enrollmentId",
    columns: [
      { key: "enrollmentId", label: "Enrollment ID" },
  
      {
        key: "student",
        label: "Student",
        type: "relation",
        entity: "students",
        display: (s) => `${s.firstName} ${s.lastName}`,
      },
  
      {
        key: "section",
        label: "Section",
        type: "relation",
        entity: "sections",
        displayKey: "sectionId",
      },
  
      {
        key: "section",
        label: "Course",
        type: "relation",
        entity: "courses",
        displayKey: "course.courseName",
      },
  
      { key: "status", label: "Enrollment Status" },
    ],
  },

  admissions: {
    title: "Admissions",
    endpoint: "/api/admin/admissions",
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
      { key: "prerequisiteId", label: "Prerequisite ID" },
    ],
  },

  "program-course-access": {
    title: "Program Course Access",
    endpoint: "api/admin/program-course-access",
    idKey: "accessId",
    columns: [
      { key: "accessId", label: "Access ID" },
    ],
  },
};