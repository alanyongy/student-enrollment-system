import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { motion } from "framer-motion";

import { adminEntities } from "../config/adminEntities";
import api from "../api/api";
import Button from "../components/Button";
import Input from "../components/Input";

export default function AdminEntityPage() {
  const { entity } = useParams();
  const navigate = useNavigate();

  const config = adminEntities?.[entity];

  const [rows, setRows] = useState([]);
  const [form, setForm] = useState({});
  const [editingId, setEditingId] = useState(null);

  // Load data when entity/config changes
  useEffect(() => {
    if (!config) return;
    fetchData();
  }, [entity, config]);

  const fetchData = async () => {
    const res = await api.get(config.endpoint);
    setRows(res.data);
  };

  // Create / Update
  const handleSubmit = async () => {
    if (!config) return;
    if (editingId !== null) {
      await api.put(`${config.endpoint}/${editingId}`, form);
      setEditingId(null); // exit edit mode
    } else {
      await api.post(config.endpoint, form);
    }

    setForm({});
    fetchData();
  };

  // Edit
  const handleEdit = (row) => {
    setForm(row);
    setEditingId(Object.values(row)[0]);
  };

  // Delete
  const handleDelete = async (row) => {
    await api.delete(`${config.endpoint}/${Object.values(row)[0]}`);
    fetchData();
  };

  if (!config) {
    return (
      <div className="p-6 text-red-500">
        Unknown entity: {entity}
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950 p-6">
      <motion.div
        key={entity}
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
        className="
          max-w-6xl mx-auto
          p-6 rounded-2xl
          bg-white/5 backdrop-blur-xl
          border border-white/10
          shadow-2xl
        "
      >
        {/* Header */}
        <div className="flex items-center justify-between mb-6">
          <h1 className="text-2xl font-bold text-white">
            {config.title}
          </h1>

          <button
            onClick={() => navigate("/admin-dashboard")}
            className="
              px-4 py-2
              rounded-lg
              bg-white/10 hover:bg-white/20
              text-white
              border border-white/10
              transition
            "
          >
            ← Back
          </button>
        </div>

        {/* FORM */}
        <div className="mb-6 p-4 rounded-xl bg-white/5 border border-white/10">
          <div className="flex flex-wrap gap-2 items-center">
            {config.columns.map((col) =>
              col.key !== "id" ? (
                <Input
                  key={col.key}
                  placeholder={col.label}
                  value={form[col.key] || ""}
                  onChange={(e) =>
                    setForm({
                      ...form,
                      [col.key]: e.target.value,
                    })
                  }
                />
              ) : null
            )}

            <Button onClick={handleSubmit}>
              {editingId !== null ? "Update" : "Create"}
            </Button>

            {/* Cancel button */}
            {editingId !== null && (
            <Button
                className="!bg-red-600 hover:!bg-red-500"
                onClick={() => {
                setForm({});
                setEditingId(null);
                }}
            >
                Cancel
            </Button>
            )}
          </div>
        </div>

        {/* TABLE */}
        <div className="rounded-xl bg-white/5 border border-white/10 overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="text-left text-gray-300 border-b border-white/10">
                  {config.columns.map((col) => (
                    <th key={col.key} className="p-3">
                      {col.label}
                    </th>
                  ))}
                  <th className="p-3">Actions</th>
                </tr>
              </thead>

              <tbody>
                {rows.map((row) => (
                  <tr
                    key={Object.values(row)[0]}
                    className="border-b border-white/5 hover:bg-white/10 transition"
                  >
                    {config.columns.map((col) => (
                      <td key={col.key} className="p-3 text-gray-200">
                        {row[col.key]}
                      </td>
                    ))}

                    <td className="p-3 flex gap-2">
                      <Button onClick={() => handleEdit(row)}>
                        Edit
                      </Button>

                      <Button
                        className="bg-red-600 hover:bg-red-500"
                        onClick={() => handleDelete(row)}
                      >
                        Delete
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </motion.div>
    </div>
  );
}