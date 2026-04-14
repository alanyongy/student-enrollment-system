import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";

import Input from "../components/Input";
import Button from "../components/Button";

export default function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("STUDENT");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email,
          password,
          role,
        }),
      });

      const data = await res.json();

      if (!res.ok || !data.success) {
        throw new Error(data.message || "Login failed");
      }

      // extract token from message string
      let token = null;

      if (data.message && data.message.includes("Token:")) {
        token = data.message.split("Token: ")[1].trim();
      }

      if (token) {
        localStorage.setItem("token", token);
      }

      // store role for UI logic later
      localStorage.setItem("role", role);

      // 👇 ROLE-BASED ROUTING
      if (role === "ADMIN") {
        navigate("/admin-dashboard");
      } else {
        navigate("/student-dashboard");
      }

    } catch (err) {
      setError(err.message || "Login failed");
    }

    setLoading(false);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950">
      <motion.div
        initial={{ opacity: 0, y: 40 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
        className="
          w-full max-w-md
          p-8 rounded-2xl
          bg-white/5 backdrop-blur-xl
          border border-white/10
          shadow-2xl
        "
      >
        <h2 className="text-3xl font-bold text-white text-center mb-2">
          Login
        </h2>

        <p className="text-gray-400 text-center mb-6">
          Course Registration System
        </p>

        {error && (
          <p className="text-red-400 text-sm mb-4 text-center">
            {error}
          </p>
        )}

        <form onSubmit={handleLogin} className="space-y-4">

          <Input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <Input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          {/* 👇 ROLE SELECTOR */}
          <select
            value={role}
            onChange={(e) => setRole(e.target.value)}
            className="w-full p-3 rounded bg-white/10 text-white border border-white/20"
          >
            <option value="STUDENT">Student</option>
            <option value="ADMIN">Admin</option>
          </select>

          <Button type="submit" loading={loading}>
            Sign In
          </Button>
        </form>
      </motion.div>
    </div>
  );
}