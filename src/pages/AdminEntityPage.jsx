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
  const [relationOptions, setRelationOptions] = useState({});

  const getValue = (obj, path) => {
    if (!obj || !path) return undefined;
    return path.split(".").reduce((acc, key) => acc?.[key], obj);
  };

  // -----------------------------
  // LOAD DATA
  // -----------------------------
  useEffect(() => {
    if (!config) return;

    console.log("CONFIG:", config);

    fetchData();
    loadRelations();
  }, [entity, config]);

  const fetchData = async () => {
    const res = await api.get(config.endpoint);

    console.log("FETCH DATA RAW:", res.data);

    const data = Array.isArray(res.data)
      ? res.data
      : res.data?.data || [];

    console.log("NORMALIZED ROWS:", data);

    setRows(data);
  };

  // -----------------------------
  // LOAD RELATIONS
  // -----------------------------
  const loadRelations = async () => {
    if (!config) return;

    const relFields = config.columns.filter(
      (col) => col.type === "relation"
    );

    const results = {};

    for (const field of relFields) {
      const relConfig = adminEntities[field.entity];
      if (!relConfig) continue;

      const res = await api.get(relConfig.endpoint);

      console.log(
        `RELATION LOAD [${field.entity}]`,
        res.data
      );

      results[field.entity] = Array.isArray(res.data)
        ? res.data
        : res.data?.data || [];
    }

    setRelationOptions(results);
  };

  // -----------------------------
  // CREATE / UPDATE
  // -----------------------------
  const handleSubmit = async () => {
    const payload = { ...form };

    config.columns.forEach((col) => {
      if (col.type === "relation") {
        const value = payload[col.key];
    
        const parsed = parseInt(value, 10);
    
        const relConfig = adminEntities[col.entity];
        const idKey = relConfig?.idKey || "id";
    
        console.log(`RELATION FIX [${col.key}]`, {
          raw: value,
          parsed,
          idKey,
        });
    
        if (!value || Number.isNaN(parsed)) {
          payload[col.key] = null;
        } else {
          payload[col.key] = {
            [idKey]: parsed,
          };
        }
      }
    });

    console.log("FINAL PAYLOAD:", payload);

    if (editingId !== null) {
      await api.put(`${config.endpoint}/${editingId}`, payload);
      setEditingId(null);
    } else {
      await api.post(config.endpoint, payload);
    }

    setForm({});
    fetchData();
  };

  // -----------------------------
  // EDIT
  // -----------------------------
  const handleEdit = (row) => {
    const cleaned = {};

    config.columns.forEach((col) => {
      if (col.type === "relation") {
        const relIdKey = adminEntities[col.entity]?.idKey || "id";
        cleaned[col.key] = 
          row[col.key]?.[relIdKey] || 
          getValue(row, col.displayKey.replace(/\.[^.]+$/, `.${relIdKey}`)) ||
          row[col.key] || 
          "";
      } else {
        cleaned[col.key] = row[col.key];
      }
    });

    setForm(cleaned);
    setEditingId(row[config.idKey]);
  };

  // -----------------------------
  // DELETE
  // -----------------------------
  const handleDelete = async (row) => {
    await api.delete(
      `${config.endpoint}/${row[config.idKey]}`
    );

    fetchData();
  };

  // -----------------------------
  // GUARD
  // -----------------------------
  if (!config) {
    return (
      <div className="p-6 text-red-500">
        Unknown entity: {entity}
      </div>
    );
  }

  // -----------------------------
  // FIELD RENDERER
  // -----------------------------
  const renderField = (col) => {
    if (col.key === "id") return null;
  
    if (col.type === "relation") {
      const relIdKey = adminEntities[col.entity]?.idKey || "id";
  
      return (
        <div key={col.key} className="flex flex-col gap-1 w-full md:w-96">
          <label className="text-sm text-gray-400">{col.label}</label>
          <select
            value={form[col.key] ?? ""}
            onChange={(e) =>
              setForm((prev) => ({
                ...prev,
                [col.key]: e.target.value,
              }))
            }
            className="bg-slate-900 text-white p-2 rounded border border-white/20 appearance-none cursor-pointer hover:border-white/40 transition-colors"
          >
            <option value="" className="bg-slate-900">Select {col.label}</option>
  
            {relationOptions[col.entity]?.map((item) => {
              // Logic to parse the last field out of the original display key
              const keys = col.displayKey.split(".");
              const lastKey = keys[keys.length - 1];
  
              return (
                <option key={item[relIdKey]} value={item[relIdKey]} className="bg-slate-900">
                  {getValue(item, col.displayKey) || item[lastKey] || item[relIdKey]}
                </option>
              );
            })}
          </select>
        </div>
      );
    }
  
    return (
      <div key={col.key} className="flex flex-col gap-1 w-full md:w-96">
        <label className="text-sm text-gray-400">{col.label}</label>
        <Input
          value={form[col.key] || ""}
          onChange={(e) =>
            setForm((prev) => ({
              ...prev,
              [col.key]: e.target.value,
            }))
          }
        />
      </div>
    );
  };

  // -----------------------------
  // UI
  // -----------------------------
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-950 via-slate-900 to-slate-950 p-6">
      <motion.div
        key={entity}
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        className="max-w-6xl mx-auto p-6 rounded-2xl bg-white/5 border border-white/10"
      >
        {/* HEADER */}
        <div className="flex justify-between mb-6">
          <h1 className="text-white text-2xl font-bold">
            {config.title}
          </h1>

          <button
            onClick={() =>
              navigate("/admin-dashboard")
            }
            className="px-4 py-2 bg-white/10 text-white rounded"
          >
            Back
          </button>
        </div>

        {/* FORM */}
        <div className="flex flex-col gap-4 mb-6">
          {config.columns.map(renderField)}

          <div className="flex gap-2">
            <Button onClick={handleSubmit}>
              {editingId ? "Update" : "Create"}
            </Button>

            {editingId && (
              <Button
                className="bg-red-600"
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
        <table className="w-full text-white">
          <thead>
            <tr>
              {config.columns.map((c) => (
                <th key={c.key} className="p-3 text-left align-middle">
                {c.label}
                </th>
              ))}
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {rows.map((row) => (
              <tr
                key={row[config.idKey]}
              >
                {config.columns.map((col) => (
                  <td key={col.key} className="p-3 text-left align-middle">
                    {col.type === "relation"
                      ? (getValue(row[col.key], col.displayKey) || // Standard: row.student -> firstName
                        getValue(row, col.displayKey) ||           // Nested: row -> section.course.courseNumber
                        row[col.key]?.id)
                      : row[col.key]}
                  </td>
                ))}

                <td className="p-3">
                  <div className="flex items-center gap-2">
                    <Button onClick={() => handleEdit(row)}>
                      Edit
                    </Button>

                    <Button
                      onClick={() => handleDelete(row)}
                      className="bg-red-600"
                    >
                      Delete
                    </Button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </motion.div>
    </div>
  );
}