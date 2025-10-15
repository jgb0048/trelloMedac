import { useMemo } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../modules/auth/AuthContext.jsx";
import Button from "../components/ui/Button.jsx";

export default function Profile() {
  const { user } = useAuth();
  const navigate = useNavigate();


  const displayName = useMemo(() => {
    if (user?.name && user.name.trim()) return user.name;
    const email = user?.email || "";
    const base = email.split("@")[0] || "usuario";
    return base.charAt(0).toUpperCase() + base.slice(1);
  }, [user]);


  const initial = (user?.email || "U").charAt(0).toUpperCase();

  return (
    <section className="min-h-screen bg-gradient-to-b from-neutral-50 to-white px-4 py-8">
      <div className="mx-auto w-full max-w-3xl">
        <div className="mb-4">
          <Button variant="secondary" onClick={() => navigate(-1)}>
            ← Volver
          </Button>
        </div>

        <div className="rounded-2xl border bg-white p-6 shadow-sm">
          <div className="flex items-center gap-4">
            <div className="flex h-14 w-14 items-center justify-center rounded-full bg-violet-100 text-violet-700 text-xl font-semibold">
              {initial}
            </div>
            <div>
              <h1 className="text-xl font-bold">Perfil</h1>
              <p className="text-sm text-neutral-600">Información de tu cuenta</p>
            </div>
          </div>

          <div className="mt-6 grid grid-cols-1 gap-4 sm:grid-cols-2">
            <div className="rounded-xl border bg-white p-4">
              <div className="text-xs uppercase tracking-wide text-neutral-500">Nombre</div>
              <div className="mt-1 text-sm font-medium text-neutral-900">{displayName}</div>
            </div>

            <div className="rounded-xl border bg-white p-4">
              <div className="text-xs uppercase tracking-wide text-neutral-500">Correo</div>
              <div className="mt-1 text-sm font-medium text-neutral-900">{user?.email || "—"}</div>
            </div>

            <div className="rounded-xl border bg-white p-4">
              <div className="text-xs uppercase tracking-wide text-neutral-500">Inicial</div>
              <div className="mt-1 text-sm font-medium text-neutral-900">{initial}</div>
            </div>

            {/* hueco por si queremos poner mas cosas */}
          </div>
        </div>
      </div>
    </section>
  );
}
