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

  // pagination + sorting
  const [page, setPage] = useState(0);
  const [size] = useState(5);
  const [sortBy, setSortBy] = useState(null);
  const [sortDir, setSortDir] = useState("asc");
  const [totalPages, setTotalPages] = useState(null);
  const [hasNextPage, setHasNextPage] = useState(true);

  const getValue = (obj, path) => {
    if (!obj || !path) return undefined;
    return path.split(".").reduce((acc, key) => acc?.[key], obj);
  };

  //load data
  useEffect(() => {
    if (!config) return;
  
    fetchData();
    loadRelations();
  }, [entity, config, page, sortBy, sortDir]);

  const fetchData = async () => {
    const res = await api.get(config.endpoint, {
      params: {
        page,
        size,
        sortBy,
        direction: sortDir,
      },
    });
  
    const data = Array.isArray(res.data)
      ? res.data
      : res.data?.data || [];
  
    setRows(data);
  
    //check next page
    const nextRes = await api.get(config.endpoint, {
      params: {
        page: page + 1,
        size,
        sortBy,
        direction: sortDir,
      },
    });
  
    const nextData = Array.isArray(nextRes.data)
      ? nextRes.data
      : nextRes.data?.data || [];
  
    setHasNextPage(nextData.length > 0);
  };


  //relations
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

      results[field.entity] = Array.isArray(res.data)
        ? res.data
        : res.data?.data || [];
    }

    setRelationOptions(results);
  };

  //create/update
  const handleSubmit = async () => {
    const payload = { ...form };

    config.columns.forEach((col) => {
      if (col.type === "relation") {
        const value = payload[col.key];
        const parsed = parseInt(value, 10);

        const relConfig = adminEntities[col.entity];
        const idKey = relConfig?.idKey || "id";

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

  //edit
  const handleEdit = (row) => {
    const cleaned = {};

    config.columns.forEach((col) => {
      if (col.type === "relation") {
        const relIdKey = adminEntities[col.entity]?.idKey || "id";

        cleaned[col.key] =
          row[col.key]?.[relIdKey] ||
          getValue(
            row,
            col.displayKey.replace(/\.[^.]+$/, `.${relIdKey}`)
          ) ||
          row[col.key] ||
          "";
      } else {
        cleaned[col.key] = row[col.key];
      }
    });

    setForm(cleaned);
    setEditingId(row[config.idKey]);
  };

  //delete
  const handleDelete = async (row) => {
    await api.delete(`${config.endpoint}/${row[config.idKey]}`);
    fetchData();
  };

  //sort
  const handleSort = (key) => {
    if (sortBy === key) {
      setSortDir((prev) => (prev === "asc" ? "desc" : "asc"));
    } else {
      setSortBy(key);
      setSortDir("asc");
    }
  };

  if (!config) {
    return (
      <div className="p-6 text-red-500">
        Unknown entity: {entity}
      </div>
    );
  }

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
            className="bg-slate-900 text-white p-2 rounded border border-white/20"
          >
            <option value="">Select {col.label}</option>

            {relationOptions[col.entity]?.map((item) => {
              const keys = col.displayKey.split(".");
              const lastKey = keys[keys.length - 1];

              return (
                <option key={item[relIdKey]} value={item[relIdKey]}>
                  {getValue(item, col.displayKey) ||
                    item[lastKey] ||
                    item[relIdKey]}
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
            onClick={() => navigate("/admin-dashboard")}
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
                <th
                  key={c.key}
                  className="p-3 text-left cursor-pointer"
                  onClick={() => handleSort(c.key)}
                >
                  {c.label}
                  {sortBy === c.key
                    ? sortDir === "asc"
                      ? " ▲"
                      : " ▼"
                    : ""}
                </th>
              ))}
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {rows.map((row) => (
              <tr key={row[config.idKey]}>
                {config.columns.map((col) => (
                  <td key={col.key} className="p-3">
                    {col.type === "relation"
                      ? getValue(row[col.key], col.displayKey) ||
                        getValue(row, col.displayKey) ||
                        row[col.key]?.id
                      : row[col.key]}
                  </td>
                ))}

                <td className="p-3">
                  <div className="flex gap-2">
                    <Button onClick={() => handleEdit(row)}>
                      Edit
                    </Button>
                    <Button
                      className="bg-red-600"
                      onClick={() => handleDelete(row)}
                    >
                      Delete
                    </Button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* PAGINATION */}
        <div className="flex justify-center items-center gap-4 mt-6 text-white">
          <button
            disabled={page === 0}
            className="px-3 py-1 bg-white/10 rounded disabled:opacity-40"
            onClick={() => {
              setPage((prev) => prev - 1)
            }}
          >
            Prev
          </button>

          <span>
            Page {page + 1}
            {totalPages ? ` / ${totalPages}` : ""}
          </span>

          <button
            disabled={!hasNextPage}
            className="px-3 py-1 bg-white/10 rounded disabled:opacity-40"
            onClick={() => {
              if (!hasNextPage) return;
              setPage((prev) => prev + 1)
            }}
          >
            Next
          </button>
        </div>
      </motion.div>
    </div>
  );
}