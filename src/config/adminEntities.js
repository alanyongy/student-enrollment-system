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
      { key: "yearsOfExperience", label: "Experience (Years)"},
      { key: "expertiseTopics", label: "Expertise Topics"},
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
      {
        key: "department",
        label: "Department",
        type: "relation",
        entity: "departments",
        displayKey: "department.deptName",
      },
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
      {
        key: "department",
        label: "Department",
        type: "relation",
        entity: "departments",
        displayKey: "department.deptName",
      }
    ],
  },

  sections: {
    title: "Sections",
    endpoint: "api/admin/sections",
    idKey: "sectionId",
    columns: [
      { key: "sectionId", label: "Section ID" },
      {
        key: "course",
        label: "Course",
        type: "relation",
        entity: "courses",
        displayKey: "course.courseNumber",
      },
      { key: "scheduleTime", label: "Scheduled Time" },
      { key: "location", label: "Location" },
      { key: "capacity", label: "Capacity" },
      {
        key: "instructor",
        label: "Instructor",
        type: "relation",
        entity: "instructors",
        displayKey: "instructor.firstName",
      },
      {
        key: "semester",
        label: "Semester",
        type: "relation",
        entity: "semesters",
        displayKey: "semester.termName",
      },
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
        displayKey: "firstName",
      },
  
      {
        key: "section",
        label: "Section",
        type: "relation",
        entity: "sections",
        displayKey: "sectionId",
      },
  
      {
        key: "course_display",
        label: "Course",
        type: "relation",
        entity: "courses",
        displayKey: "section.course.courseNumber",
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
    endpoint: "api/admin/courses/course-prerequisites",
    idKey: "prerequisiteId",
    columns: [
      { key: "prerequisiteId", label: "Prerequisite ID" },

      {
        key: "course",
        label: "Course",
        type: "relation",
        entity: "courses",
        displayKey: "course.courseNumber",
      },

      {
        key: "prerequisiteCourse",
        label: "Requires",
        type: "relation",
        entity: "courses",
        displayKey: "prerequisiteCourse.courseNumber",
      },
    ],
  },

  "program-course-access": {
    title: "Program Course Access",
    endpoint: "api/admin/courses/access",
    idKey: "accessId",
    columns: [
      { key: "accessId", label: "Access ID" },

      {
        key: "program",
        label: "Program",
        type: "relation",
        entity: "programs",
        displayKey: "program.programName",
      },

      {
        key: "course",
        label: "Course",
        type: "relation",
        entity: "courses",
        displayKey: "course.courseNumber",
      },
    ],
  },

  "course-completions": {
    title: "Course Completions",
    endpoint: "api/admin/completions",
    idKey: "completedCourseId",
    columns: [
      { key: "completedCourseId", label: "Completion ID" },
  
      {
        key: "student",
        label: "Student",
        type: "relation",
        entity: "students",
        displayKey: "firstName",
      },

      {
        key: "course",
        label: "Course",
        type: "relation",
        entity: "courses",
        displayKey: "course.courseNumber",
      },
    ],
  },
};