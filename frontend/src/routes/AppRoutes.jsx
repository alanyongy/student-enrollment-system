import { Routes, Route } from "react-router-dom";
import Login from "../pages/Login";
import AdminDashboard from "../pages/AdminDashboard";
import StudentDashboard from "../pages/StudentDashboard";
import AdminEntityPage from "../pages/AdminEntityPage";
import StudentProfile from "../pages/StudentProfile";
import AdminLogin from "../pages/AdminLogin";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/login" element={<Login />} />
      <Route path="/admin-dashboard" element={<AdminDashboard />} />
      <Route path="/student-dashboard" element={<StudentDashboard />} />
      <Route path="/admin/:entity" element={<AdminEntityPage />} />
      <Route path="/profile" element={<StudentProfile />} />
      <Route path="*" element={<Login />} />
      <Route path="/admin-login" element={<AdminLogin />} />
    </Routes>
  );
}
