import { useEffect, useState } from "react";
import api from "../api/api";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";

export default function StudentDashboard() {
  const [student, setStudent] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    async function fetchProfile() {
      try {
        const res = await api.get("/api/me/profile"); // ✅ FIXED
        setStudent(res.data);
      } catch (err) {
        console.error("Failed to fetch profile:", err);
      }
    }

    fetchProfile();
  }, []);

  function handleLogout() {
    localStorage.removeItem("token");
    navigate("/login");
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950">
      <div className="flex justify-between items-center px-6 py-4 border-b border-white/10 backdrop-blur-xl bg-white/5">
        <h1 className="text-xl font-bold text-white">
          Welcome {student?.firstName || student?.name || "Student"}
        </h1>

        <Button
          onClick={handleLogout}
          className="!w-min bg-red-700 px-3 !py-2 hover:bg-red-500"
        >
          Logout
        </Button>
      </div>
    </div>
  );
}
