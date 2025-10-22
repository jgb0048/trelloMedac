export default function Input({
  label,
  name,
  type = "text",
  register,
  error,
  className = "",
  ...props
}) {
  const registration = register ? register(name) : {};
  return (
    <label className={`flex flex-col gap-2 text-sm ${className}`.trim()}>
      {label ? <span className="font-semibold text-neutral-900">{label}</span> : null}
      <input
        name={name}
        type={type}
        className="rounded-xl border border-brand-100 bg-white/90 px-4 py-3 text-sm text-neutral-900 shadow-inner shadow-brand-700/5 placeholder:text-neutral-400 transition focus:border-brand-200 focus:outline-none focus:ring-[3px] focus:ring-brand-200/70 focus:ring-offset-1 focus:ring-offset-white"
        {...props}
        {...registration}
      />
      {error ? <span className="text-xs text-brand-600">{error}</span> : null}
    </label>
  );
}
