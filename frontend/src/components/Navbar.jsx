import { useNavigate } from "react-router-dom";

export default function Navbar({ studentName, activePage = "dashboard" }) {
  const navigate = useNavigate();

  function handleLogout() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    navigate("/login");
  }

  function handleProfileClick() {
    if (activePage === "profile") {
      navigate("/student-dashboard");
      return;
    }

    navigate("/profile");
  }

  return (
    <div className="flex justify-between items-center px-6 py-4 border-b border-white/10 backdrop-blur-xl bg-white/5">
      <button
        type="button"
        onClick={() => navigate("/student-dashboard")}
        className="text-left"
      >
        <h1 className="text-xl font-bold text-white">
          Welcome {studentName || "Student"}
        </h1>
      </button>

      <div className="flex items-center gap-3">
        <button
          type="button"
          onClick={handleProfileClick}
          className={`
            rounded-xl px-3 py-2 text-white font-semibold transition-all duration-200
            active:scale-95
            ${
              activePage === "profile"
                ? "bg-white/20 hover:bg-white/20"
                : "bg-white/10 hover:bg-white/20"
            }
          `}
        >
          Profile
        </button>

        <button
          type="button"
          onClick={handleLogout}
          className="rounded-xl bg-red-700 px-3 py-2 text-white font-semibold transition-all duration-200 active:scale-95 hover:bg-red-500"
        >
          Logout
        </button>
      </div>
    </div>
  );
}
