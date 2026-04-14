import { Routes, Route } from "react-router-dom";
import Login from "../pages/Login";
import AdminDashboard from "../pages/AdminDashboard";
import StudentDashboard from "../pages/StudentDashboard";
import AdminEntityPage from "../pages/AdminEntityPage";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/admin-dashboard" element={<AdminDashboard />} />
      <Route path="/student-dashboard" element={<StudentDashboard />} />
      <Route path="*" element={<Login />} />
      <Route path="/admin/:entity" element={<AdminEntityPage />} />
    </Routes>
  );
}
