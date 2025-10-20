export default function Textarea(props) {
  return (
    <textarea
      {...props}
      className="w-full rounded-md border border-neutral-300 px-3 py-2 text-sm focus:border-blue-500 focus:ring-blue-500"
    />
  );
}