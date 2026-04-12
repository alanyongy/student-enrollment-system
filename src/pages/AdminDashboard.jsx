import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";

import Button from "../components/Button";

export default function AdminDashboard() {
  const navigate = useNavigate();

  // Logout
  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/admin-login");
  };

  // Dashboard cards
  const cards = [
    {
      title: "Students",
      description: "Manage student accounts and profiles",
      path: "/admin-students",
    },
    {
      title: "Instructors",
      description: "Manage instructor profiles",
      path: "/admin-instructors",
    },
    {
      title: "Administrators",
      description: "Manage admin users",
      path: "/admin-administrators",
    },
  
    //Academic Structure
    {
      title: "Departments",
      description: "Manage academic departments",
      path: "/admin-departments",
    },
    {
      title: "Programs",
      description: "Manage degree programs",
      path: "/admin-programs",
    },
    {
      title: "Courses",
      description: "Manage course catalog",
      path: "/admin-courses",
    },
    {
      title: "Sections",
      description: "Manage course sections",
      path: "/admin-sections",
    },
    {
      title: "Semesters",
      description: "Manage academic semesters",
      path: "/admin-semesters",
    },
  
    //Enrollment & Academic Activity
    {
      title: "Enrollments",
      description: "Manage student course enrollments",
      path: "/admin-enrollments",
    },
    {
      title: "Admissions",
      description: "Manage student admissions",
      path: "/admin-admissions",
    },
  
    //Relationship / Constraint Tables
    {
      title: "Course Prerequisites",
      description: "Manage course prerequisite rules",
      path: "/admin-course-prerequisites",
    },
    {
      title: "Program Course Access",
      description: "Control program access to courses",
      path: "/admin-program-course-access",
    },
    {
      title: "Semester Sections",
      description: "Link sections to semesters",
      path: "/admin-semester-sections",
    },
  
    //Students by type
    {
      title: "Undergraduate Students",
      description: "Manage undergraduate students",
      path: "/admin-undergraduates",
    },
    {
      title: "Postgraduate Students",
      description: "Manage postgraduate students",
      path: "/admin-postgraduates",
    },

    {
      title: "Departments & Programs Map",
      description: "View program-to-department structure",
      path: "/admin-org-structure",
    },
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950">
      {/* Top Bar */}
      <div className="flex justify-between items-center px-6 py-4 border-b border-white/10 backdrop-blur-xl bg-white/5">
        <h1 className="text-white text-xl font-bold">
          Admin Dashboard
        </h1>

        <div className="w-fit">
            <Button
              onClick={handleLogout}
              className="bg-red-700 px-3 !py-2 hover:bg-red-500"
            >
                Logout
            </Button>
          </div>
      </div>

      {/* Cards Grid */}
      <div className="p-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        {cards.map((card, index) => (
          <motion.div
            key={card.title}
            onClick={() => navigate(card.path)}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.4, delay: index * 0.05 }}
            className="
              cursor-pointer
              rounded-2xl
              p-6
              bg-white/5 backdrop-blur-xl
              border border-white/10
              shadow-xl
              hover:bg-white/10
              hover:scale-[1.03]
              transition
            "
          >
            <h2 className="text-white text-lg font-semibold mb-2">
              {card.title}
            </h2>

            <p className="text-gray-400 text-sm">
              {card.description}
            </p>
          </motion.div>
        ))}
      </div>
    </div>
  );
}