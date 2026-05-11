export default function Button({ children, loading, className = "", ...props }) {
  return (
    <button
      {...props}
      disabled={loading}
      className={`
        w-full py-3 rounded-xl
        bg-indigo-600 hover:bg-indigo-500
        active:scale-95
        transition-all duration-200
        text-white font-semibold
        flex items-center justify-center gap-2
        disabled:opacity-70
        ${className}
      `}
    >
      {loading ? (
        <span className="h-5 w-5 border-2 border-white border-t-transparent rounded-full animate-spin" />
      ) : (
        children
      )}
    </button>
  );
}
