import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";

import Input from "../components/Input";
import Button from "../components/Button";
import { loginStudent } from "../api/api";

export default function StudentLogin() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
  
    try {
      const data = await loginStudent(email, password);
  
      localStorage.setItem("token", data.token);
  
      navigate("/student-dashboard");
    } catch (err) {
      setError(err.message);
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
          Student Login
        </h2>
        <br />
        {error && (
          <motion.p
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="text-red-400 text-sm mb-4 text-center"
          >
            {error}
          </motion.p>
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

          <Button type="submit" loading={loading}>
            Sign In
          </Button>
        </form>

        <p className="mt-6 text-center text-gray-500 text-sm">
          Students cannot self-register. Please contact IT support.
        </p>
      </motion.div>
    </div>
  );
}
