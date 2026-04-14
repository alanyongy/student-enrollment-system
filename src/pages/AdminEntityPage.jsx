import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import { adminEntities } from "../config/adminEntities";
import api from "../api/api";
import Button from "../components/Button";
import Input from "../components/Input";

export default function AdminEntityPage() {
  const { entity } = useParams();
  const config = adminEntities[entity];

  const [rows, setRows] = useState([]);
  const [form, setForm] = useState({});
  const [editingId, setEditingId] = useState(null);

  // Load data
  useEffect(() => {
    if (config) fetchData();
  }, [entity]);

  const fetchData = async () => {
    const res = await api.get(config.endpoint);
    setRows(res.data);
  };

  // Create / Update
  const handleSubmit = async () => {
    if (editingId) {
      await api.put(`${config.endpoint}/${editingId}`, form);
    } else {
      await api.post(config.endpoint, form);
    }

    setForm({});
    setEditingId(null);
    fetchData();
  };

  // Edit
  const handleEdit = (row) => {
    setForm(row);
    setEditingId(row.id);
  };

  // Delete
  const handleDelete = async (row) => {
    const entityID = Object.values(row)[0]; // first column value is id
    await api.delete(`${config.endpoint}/${entityID}`);
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
    <div className="p-6">
      {/* Title */}
      <h1 className="text-2xl font-bold mb-6">
        {config.title}
      </h1>

      {/* FORM */}
      <div className="flex flex-wrap gap-2 mb-6">
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
          {editingId ? "Update" : "Create"}
        </Button>
      </div>

      {/* TABLE */}
      <div className="overflow-x-auto">
        <table className="w-full bg-white/5 border border-white/10 rounded-xl overflow-hidden">
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
                key={row.id}
                className="border-b border-white/5 hover:bg-white/5"
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
  );
}