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

  // -----------------------------
  // LOAD DATA
  // -----------------------------
  useEffect(() => {
    if (!config) return;

    console.log("📦 CONFIG:", config);

    fetchData();
    loadRelations();
  }, [entity, config]);

  const fetchData = async () => {
    const res = await api.get(config.endpoint);

    console.log("📥 FETCH DATA:", res.data);

    setRows(res.data);
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
        `🔗 RELATION LOAD [${field.entity}]`,
        res.data
      );

      results[field.entity] = res.data;
    }

    setRelationOptions(results);
  };

  // -----------------------------
  // CREATE / UPDATE
  // -----------------------------
  const handleSubmit = async () => {
    console.log("🟡 FORM BEFORE SUBMIT:", form);
  
    const payload = { ...form };
  
    config.columns.forEach((col) => {
      if (col.type === "relation") {
        const value = payload[col.key];
    
        const parsed = parseInt(value, 10);
        const relConfig = adminEntities[col.entity];
        const idKey = relConfig?.idKey;
    
        console.log(`🔍 RELATION FIX [${col.key}]`, {
          raw: value,
          parsed,
          idKey
        });
    
        if (
          value === "" ||
          value === null ||
          value === undefined ||
          Number.isNaN(parsed)
        ) {
          payload[col.key] = null;
        } else {
          payload[col.key] = {
            [idKey]: parsed,   // ✅ BACK TO NESTED OBJECTS
          };
        }
      }
    });
  
    console.log("🚀 FINAL PAYLOAD:", payload);
    console.log(JSON.stringify(payload, null, 2));
  
    if (editingId !== null) {
      console.log("✏️ PUT:", editingId);
      await api.put(`${config.endpoint}/${editingId}`, payload);
      setEditingId(null);
    } else {
      console.log("➕ POST");
      console.log("📡 API POST URL:", config.endpoint);
      await api.post(config.endpoint, payload);
    }
  
    setForm({});
    fetchData();
  };

  // -----------------------------
  // EDIT
  // -----------------------------
  const handleEdit = (row) => {
    console.log("✏️ RAW ROW:", row);

    const cleaned = {};

    const idKey = config.idKey;

    config.columns.forEach((col) => {
      if (col.type === "relation") {
        const relIdKey =
          adminEntities[col.entity]?.idKey || "id";

        const extracted =
          row[col.key]?.[relIdKey] ??
          row[col.key]?.id ??
          "";

        console.log(
          `🔄 EDIT RELATION [${col.key}] ->`,
          extracted
        );

        cleaned[col.key] = extracted;
      } else {
        cleaned[col.key] = row[col.key];
      }
    });

    setForm(cleaned);

    setEditingId(row[idKey]);

    console.log("🧼 CLEANED FORM:", cleaned);
  };

  // -----------------------------
  // DELETE
  // -----------------------------
  const handleDelete = async (row) => {
    const idKey = config.idKey;

    console.log("🗑 DELETE:", row);

    await api.delete(`${config.endpoint}/${row[idKey]}`);
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
      const relIdKey =
        adminEntities[col.entity]?.idKey || "id";

      return (
        <div key={col.key} className="flex flex-col gap-1 w-full md:w-96">
          <label className="text-sm text-gray-300">
            {col.label}
          </label>

          <select
            value={form[col.key] ?? ""}
            onChange={(e) => {
              const value = e.target.value;

              console.log(
                `🟢 SELECT [${col.key}] ->`,
                value
              );

              setForm((prev) => ({
                ...prev,
                [col.key]: value === "" ? "" : value,
              }));
            }}
            className="bg-white/10 text-white p-2 rounded border border-white/10"
          >
            <option value="">
              Select {col.label}
            </option>

            {relationOptions[col.entity]?.map((item) => {
              const value = item[relIdKey];

              console.log(
                `🔵 OPTION [${col.key}]`,
                item
              );

              return (
                <option
                  key={value}
                  value={String(value)}
                >
                  {item[col.displayKey] || value}
                </option>
              );
            })}
          </select>
        </div>
      );
    }

    return (
      <div key={col.key} className="flex flex-col gap-1 w-full md:w-96">
        <label className="text-sm text-gray-300">
          {col.label}
        </label>

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
        <div className="flex justify-between mb-6">
          <h1 className="text-white text-2xl font-bold">
            {config.title}
          </h1>

          <button
            onClick={() => navigate("/admin-dashboard")}
            className="px-4 py-2 bg-white/10 text-white rounded"
          >
            Back
          </button>
        </div>

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

        <table className="w-full text-white">
          <thead>
            <tr>
              {config.columns.map((c) => (
                <th key={c.key}>{c.label}</th>
              ))}
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {rows.map((row) => (
              <tr key={row[config.idKey]}>
                {config.columns.map((col) => (
                  <td key={col.key}>
                    {col.type === "relation"
                      ? row[col.key]?.[
                          adminEntities[col.entity]?.displayKey
                        ] || row[col.key]?.id
                      : row[col.key]}
                  </td>
                ))}

                <td>
                  <Button onClick={() => handleEdit(row)}>
                    Edit
                  </Button>

                  <Button
                    onClick={() => handleDelete(row)}
                    className="bg-red-600"
                  >
                    Delete
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </motion.div>
    </div>
  );
}