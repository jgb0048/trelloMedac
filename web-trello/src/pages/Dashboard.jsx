import Button from "../components/ui/Button.jsx";
import { useAuth } from "../modules/auth/AuthContext.jsx";

export default function Dashboard() {
  const { user, signOut } = useAuth();
  return (
    <section className="flex min-h-screen items-center justify-center px-4 py-12">
      <div className="w-full max-w-md rounded-2xl border border-white/60 bg-white/85 p-10 text-center shadow-lg shadow-brand-700/20 backdrop-blur">
        <h1 className="text-3xl font-bold tracking-tight text-neutral-950">Dashboard</h1>
        <p className="mt-3 text-neutral-600">
          Signed in as <span className="font-semibold text-neutral-900">{user?.email}</span>
        </p>
        <Button onClick={() => signOut()} variant="secondary" className="mt-8">
          Sign out
        </Button>
      </div>
    </section>
  );
}
