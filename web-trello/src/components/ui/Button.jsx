export default function Button({ children, type="button", variant="primary", full, ...props }) {
  const variants = {
    primary: "bg-violet-600 hover:bg-violet-700 text-white",
    secondary: "bg-gray-100 hover:bg-gray-200 text-gray-900",
    ghost: "bg-transparent hover:bg-gray-100 text-gray-900",
  };
  return (
    <button
      type={type}
      className={`rounded-xl px-4 py-3 font-medium transition ${variants[variant]} ${full ? "w-full" : ""}`}
      {...props}
    >
      {children}
    </button>
  );
}
