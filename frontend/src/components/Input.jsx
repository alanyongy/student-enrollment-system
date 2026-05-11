export default function Input({ type = "text", placeholder, value, onChange }) {
  return (
    <input
      type={type}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      className="
        w-full px-4 py-3 rounded-xl
        bg-white/5 backdrop-blur
        border border-white/10
        text-white placeholder-gray-400
        focus:outline-none
        focus:ring-2 focus:ring-indigo-500
        transition-all duration-300
      "
    />
  );
}
