export default function Section({ title, description, right, children }) {
  return (
    <section className="mt-8">
      <div className="flex items-end justify-between gap-4">
        <div>
          <h2 className="text-xl font-bold">{title}</h2>
          {description && <p className="text-sm text-neutral-600 mt-1">{description}</p>}
        </div>
        {right}
      </div>
      <div className="mt-4">{children}</div>
    </section>
  );
}
