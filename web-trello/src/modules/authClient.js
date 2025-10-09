const API_URL = "http://localhost:8080/trello/v1";

// Completamos los campos de userName y name con la parte del email antes de la @
export async function register(email, password) {
  if (!email || !password) {
    throw new Error("Email and password are required.");
  }
  if (password.length < 8) throw new Error("La contraseña debe tener al menos 8 caracteres.");

  const base = (email.split("@")[0] || "user").trim();

  const body = {
    userName: base,
    name: base.charAt(0).toUpperCase() + base.slice(1),
    email,
    password,
  };

  try {
    const res = await fetch(`${API_URL}/user/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });

    if (!res.ok) {
      const text = await res.text();
      throw new Error(text || `HTTP ${res.status}`);
    }

    return await res.json();
  } catch (err) {
    console.error("REGISTER ERROR:", err);
    throw err;
  }
}

// A la espera de que el backend lo implemente.
export async function login(email, password) {
  if (!email || !password) {
    throw new Error("Email and password are required.");
  }

  // Placeholder local
  const payload = JSON.stringify({ email, t: Date.now() });
  localStorage.setItem("demo_auth_session", payload);
  return { email };
}

// A la espera de que el backend lo implemente.
export function getCurrentUser() {
  const raw = localStorage.getItem("demo_auth_session");
  if (!raw) return null;
  try {
    const { email } = JSON.parse(raw);
    return { email };
  } catch {
    return null;
  }
}

// A la espera de que el backend lo implemente.
export function signOut() {
  localStorage.removeItem("demo_auth_session");
}

// A la espera de que el backend lo implemente.
export async function resetPassword(email, newPassword) {
  console.warn("resetPassword called but not implemented on server yet.");
  throw new Error("Password reset not available yet.");
}
