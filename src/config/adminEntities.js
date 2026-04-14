export const adminEntities = {
    students: {
      title: "Students",
      endpoint: "api/admin/students",
      columns: [
        { key: "personId", label: "ID" },
        { key: "firstName", label: "Name" },
        { key: "email", label: "Email" },
      ],
    },
  
    instructors: {
      title: "Instructors",
      endpoint: "api/admin/instructors",
      columns: [
        { key: "id", label: "ID" },
        { key: "name", label: "Name" },
        { key: "email", label: "Email" },
      ],
    },
  
    administrators: {
      title: "Administrators",
      endpoint: "api/admin/administrators",
      columns: [
        { key: "id", label: "ID" },
        { key: "name", label: "Name" },
        { key: "email", label: "Email" },
      ],
    },
  
    departments: {
      title: "Departments",
      endpoint: "api/admin/departments",
      columns: [
        { key: "id", label: "ID" },
        { key: "name", label: "Name" },
      ],
    },
  
    programs: {
      title: "Programs",
      endpoint: "api/admin/programs",
      columns: [
        { key: "id", label: "ID" },
        { key: "name", label: "Name" },
      ],
    },
  
    courses: {
      title: "Courses",
      endpoint: "api/admin/courses",
      columns: [
        { key: "id", label: "ID" },
        { key: "code", label: "Code" },
        { key: "name", label: "Name" },
      ],
    },
  
    sections: {
      title: "Sections",
      endpoint: "api/admin/sections",
      columns: [
        { key: "id", label: "ID" },
        { key: "sectionNumber", label: "Section #" },
      ],
    },
  
    semesters: {
      title: "Semesters",
      endpoint: "api/admin/semesters",
      columns: [
        { key: "id", label: "ID" },
        { key: "name", label: "Name" },
      ],
    },
  
    enrollments: {
      title: "Enrollments",
      endpoint: "api/admin/enrollments",
      columns: [
        { key: "id", label: "ID" },
        { key: "studentId", label: "Student ID" },
        { key: "sectionId", label: "Section ID" },
      ],
    },
  
    admissions: {
      title: "Admissions",
      endpoint: "api/admin/admissions",
      columns: [
        { key: "id", label: "ID" },
        { key: "studentId", label: "Student ID" },
        { key: "status", label: "Status" },
      ],
    },
  
    "course-prerequisites": {
      title: "Course Prerequisites",
      endpoint: "api/admin/course-prerequisites",
      columns: [
        { key: "id", label: "ID" },
        { key: "courseId", label: "Course" },
        { key: "prerequisiteId", label: "Prerequisite" },
      ],
    },
  
    "program-course-access": {
      title: "Program Course Access",
      endpoint: "api/admin/program-course-access",
      columns: [
        { key: "id", label: "ID" },
        { key: "programId", label: "Program" },
        { key: "courseId", label: "Course" },
      ],
    },
  
    "semester-sections": {
      title: "Semester Sections",
      endpoint: "api/admin/semester-sections",
      columns: [
        { key: "id", label: "ID" },
        { key: "semesterId", label: "Semester" },
        { key: "sectionId", label: "Section" },
      ],
    },
  };