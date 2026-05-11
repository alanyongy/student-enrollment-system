import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";

import Button from "../components/Button";
import { adminCards } from "../config/adminCards";

export default function AdminDashboard() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/admin-login");
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950">
      {/* Top Bar */}
      <div className="flex justify-between items-center px-6 py-4 border-b border-white/10 backdrop-blur-xl bg-white/5">
        <h1 className="text-white text-xl font-bold">
          Admin Dashboard
        </h1>

        <Button
          onClick={handleLogout}
          className="!w-min bg-red-700 px-3 !py-2 hover:bg-red-500"
        >
          Logout
        </Button>
      </div>

      {/* Cards Grid */}
      <div className="p-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        {adminCards.map((card, index) => (
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