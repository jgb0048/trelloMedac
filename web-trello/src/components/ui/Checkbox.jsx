export default function Checkbox({ label, name, register, defaultChecked }) {
  return (
    <label className="inline-flex items-center gap-2 text-sm cursor-pointer select-none">
      <input
        type="checkbox"
        className="h-4 w-4 rounded border-gray-300 text-violet-600 focus:ring-violet-500"
        defaultChecked={defaultChecked}
        {...(register ? register(name) : {})}
      />
      {label}
    </label>
  );
}
