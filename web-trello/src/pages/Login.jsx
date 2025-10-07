import { useForm } from "react-hook-form";
import { Link, useLocation, useNavigate } from "react-router-dom";
import AuthCard from "../components/ui/AuthCard.jsx";
import Input from "../components/ui/Input.jsx";
import Checkbox from "../components/ui/Checkbox.jsx";
import Button from "../components/ui/Button.jsx";
import { useAuth } from "../modules/auth/AuthContext.jsx";

export default function Login() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const { state } = useLocation();
  const { register, handleSubmit, formState: { isSubmitting } } = useForm({
    defaultValues: { remember: true },
  });

  async function onSubmit({ email, password, remember }) {
    try {
      await login(email, password, remember);
      navigate(state?.from?.pathname || "/dashboard", { replace: true });
    } catch (err) {
      alert(err.message);
    }
  }

  return (
    <AuthCard
      title="Bienvenido"
      subtitle="Inicia sesión para continuar."
      footer={<p>¿No tienes una cuenta? ! <Link to="/register" className="text-violet-600 hover:underline">Crea una cuenta</Link></p>}
    >
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <Input label="Email" type="email" name="email" placeholder="Introduce tu correo electrónico" register={register} required />
        <Input label="Contraseña" type="password" name="password" placeholder="Introduce tu contraseña" register={register} required />

        <div className="flex items-center justify-between">
          <Checkbox label="Recordarme" name="remember" register={register} defaultChecked />
          <Link to="/forgot" className="text-sm text-violet-600 hover:underline">He olvidado la contraseña</Link>
        </div>

        <Button type="submit" variant="primary" full disabled={isSubmitting}>
          {isSubmitting ? "Signing in..." : "Iniciar sesión"}
        </Button>
      </form>

      
            {/* separador ----o---- */}
            <div className="relative my-6">
              <div className="border-t" />
              <span className="absolute left-1/2 -translate-x-1/2 -top-3 bg-white px-2 text-sm text-gray-500">
                o
              </span>
            </div>
      
            {/* boton de Google (sin funcionalidad) */}
            <Button type="button" variant="secondary" full aria-label="Iniciar sesión con Google">
              <span className="inline-flex items-center gap-2">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="18"
                  height="18"
                  viewBox="0 0 48 48"
                  aria-hidden="true"
                >
                  <path
                    fill="#FFC107"
                    d="M43.6 20.5H42V20H24v8h11.3C33.7 32.9 29.3 36 24 36c-6.6 0-12-5.4-12-12s5.4-12 12-12c3 0 5.7 1.1 7.7 3l5.7-5.7C33.6 6.1 29.1 4 24 4 12.9 4 4 12.9 4 24s8.9 20 20 20c10.4 0 19-7.5 19-20 0-1.3-.1-2.7-.4-3.5z"
                  />
                  <path
                    fill="#FF3D00"
                    d="M6.3 14.7l6.6 4.8C14.6 16.2 18.9 12 24 12c3 0 5.7 1.1 7.7 3l5.7-5.7C33.6 6.1 29.1 4 24 4 16.1 4 9.3 8.5 6.3 14.7z"
                  />
                  <path
                    fill="#4CAF50"
                    d="M24 44c5.2 0 9.9-1.7 13.4-4.7l-6.2-5.1C29.3 36 26.8 37 24 37c-5.3 0-9.7-3.1-11.6-7.6l-6.6 5.1C9.3 39.5 16.1 44 24 44z"
                  />
                  <path
                    fill="#1976D2"
                    d="M43.6 20.5H42V20H24v8h11.3c-1.1 3.3-3.5 5.9-6.6 7.2l6.2 5.1C37.9 37.6 43 32.4 43.6 24c.1-1.2 0-2.5 0-3.5z"
                  />
                </svg>
                Iniciar sesión con Google
              </span>
            </Button>
    </AuthCard>
  );
}
