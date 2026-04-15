export const adminEntities = {
    students: {
      title: "Students",
      endpoint: "api/admin/students",
      columns: [
        { key: "personId", label: "ID" },
        { key: "firstName", label: "First Name" },
        { key: "lastName", label: "Last Name"},
        { key: "email", label: "Email" },
        /* extra
        { key: "phoneNumber", label: "Phone Number"},
        { key: "enrollmentYear", label: "Enrollment Year"},
        { key: "academicStatus", label: "Academic Status"},
        { key: "creditsEarned", label: "Credits Earned"}, */
      ],
    },
  
    instructors: {
      title: "Instructors",
      endpoint: "api/admin/instructors",
      columns: [
        { key: "personId", label: "ID" },
        { key: "firstName", label: "First Name" },
        { key: "lastName", label: "Last Name"},
        { key: "email", label: "Email" },
        /* extra
        { key: "phoneNumber", label: "Phone Number"},
        { key: "yearsOfExperience", label: "Years of Experience"},
        { key: "expertiseTopics", label: "Expertise Topics"},*/
      ],
    },
  
    // no endpoint
    administrators: {
      title: "Administrators",
      endpoint: "api/admin/administrators",
      columns: [
        { key: "personId", label: "ID" },
        { key: "firstName", label: "First Name" },
        { key: "lastName", label: "Last Name"},
        { key: "email", label: "Email" },
      ],
    }, 
  
    departments: {
      title: "Departments",
      endpoint: "api/admin/departments",
      columns: [
        { key: "deptId", label: "Department ID" },
        { key: "deptName", label: "Department Name" },
        { key: "deptEmail", label: "Department Email" },
        { key: "phoneNumber", label: "Phone Number"},
        { key: "officeLocation", label: "Office Location" },
      ],
    },
  
    programs: {
      title: "Programs",
      endpoint: "api/admin/programs",
      columns: [
        { key: "programId", label: "Program ID" },
        { key: "programName", label: "Program Name" },
        { key: "description", label: "Description"},
        // { key: "department", label: "Department ID"},
        { key: "programType", label: "Program Type"},
      ],
    },
  
    courses: {
      title: "Courses",
      endpoint: "api/admin/courses",
      columns: [
        { key: "courseId", label: "Course ID" },
        { key: "courseNumber", label: "Course Number" },
        { key: "description", label: "Description" },
        { key: "credits", label: "Credits"},
        // { key: "department", label: "Department ID"},
      ],
    },
  
    sections: {
      title: "Sections",
      endpoint: "api/admin/sections",
      columns: [
        { key: "sectionId", label: "Section ID" },
        { key: "scheduleTime", label: "Scheduled Time" },
        { key: "location", label: "Location"},
        { key: "capacity", label: "Capacity"},
        // { key: "course", label: "Course ID"},
        // { key: "instructor", label: "Instructor ID"},
        // { key: "semester", label: "Semester ID"},
      ],
    },
  
    semesters: {
      title: "Semesters",
      endpoint: "api/admin/semesters",
      columns: [
        { key: "semesterId", label: "Semester ID" },
        { key: "termName", label: "Term Name" },
        { key: "startDate", label: "Start Date"},
        { key: "endDate", label: "End Date"},
      ],
    },
  
    // no endpoint
    /*
      ### Enroll Student in Section (ENROLLS)
      - POST   /api/admin/students/{studentId}/enroll/{sectionId}
      - DELETE /api/admin/students/{studentId}/drop/{sectionId}
    
      just use students? add button for "enroll student in section"
    */
    enrollments: {
      title: "Enrollments",
      endpoint: "api/admin/enrollments",
      columns: [
        { key: "enrollmentId", label: "Enrollment ID" },
        { key: "status", label: "Enrollment Status"},
        // { key: "student", label: "Student ID" },
        // { key: "section", label: "Section ID" },
      ],
    }, 
  
    // no endpoint
    /*
      ### Admit Student to Program (ADMITTED)
      - POST /api/admin/students/{studentId}/admit/{programId}
    
      just use students? add button for "admit student to program"
      */
    admissions: {
      title: "Admissions",
      endpoint: "api/admin/admissions",
      columns: [
        { key: "admissionId", label: "Admission ID" },
        // { key: "student", label: "Student ID" },
        { key: "status", label: "Status" },
      ],
    }, 
  
    // no endpoint
    /*
      ### Add Course Prerequisite (PRE_REQ_OF)
      - POST   /api/admin/courses/{courseId}/prerequisites/{prereqId}
      - DELETE /api/admin/courses/{courseId}/prerequisites/{prereqId}
    
      just use course? add button for "add course prerequisite"
      */
    "course-prerequisites": {
      title: "Course Prerequisites",
      endpoint: "api/admin/course-prerequisites",
      columns: [
        { key: "prerequisiteId", label: "Prerequisite ID" },
        // { key: "course", label: "Course ID" },
        // { key: "prerequisiteCourse", label: "Prerequisite Course ID" },
      ],
    },

    // no endpoint
    /*
      ### Mark Course Completed (COMPLETED)
      - POST /api/admin/students/{studentId}/completed/{courseId}
      just use students? add button for "add completed course"
    */
    "completed-courses": {
      title: "Completed Courses",
      endpoint: "api/admin/completed-courses",
      columns: [
        { key: "completedCourseId", label: "Completed Course ID"},
        // { key: "student", label: "Student ID"},
        // { key: "course", label: "Course ID"},
      ],
    },

    // no endpoint
    // no entity even
    /*
      ### Set Course Required for Program (GIVES_ACCESS / is_required)
      - POST   /api/admin/programs/{programId}/required-courses/{courseId}
      - DELETE /api/admin/programs/{programId}/required-courses/{courseId}
      just use programs? add button for "set course required"
    */
    "required-courses": {
      title: "Required Courses",
      endpoint: "api/admin/required-courses",
      columns: [
        //{},
      ],
    },




    // don't need? (no endpoint planned)

    // no endpoint
    /*
    "program-course-access": {
      title: "Program Course Access",
      endpoint: "api/admin/program-course-access",
      columns: [
        { key: "accessId", label: "Access ID" },
        // { key: "program", label: "Program ID" },
        // { key: "course", label: "Course ID" },
      ],
    }, */
  
    // no endpoint
    /*
    "semester-sections": {
      title: "Semester Sections",
      endpoint: "api/admin/semester-sections",
      columns: [
        { key: "semesterSectionId", label: "Semester Section ID" },
        // { key: "semester", label: "Semester ID" },
        // { key: "section", label: "Section ID" },
      ],
    }, */

  };