import { Routes, Route } from "react-router-dom";
import AdminLogin from "../pages/AdminLogin";
import StudentLogin from "../pages/StudentLogin";
import AdminDashboard from "../pages/AdminDashboard";
import StudentDashboard from "../pages/StudentDashboard";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/admin-login" element={<AdminLogin />} />
      <Route path="/admin-dashboard" element={<AdminDashboard />} />
      <Route path="/student-dashboard" element={<StudentDashboard />} />
      <Route path="*" element={<StudentLogin />} />
    </Routes>
  );
}
