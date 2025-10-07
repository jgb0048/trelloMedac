// src/modules/authClient.js

// URL ABSOLUTA del backend (la leemos del .env.local)
const API_BASE = import.meta.env.VITE_API_URL || "http://localhost:8080/trello/v1";

const LOCAL_SESSION_KEY = "demo_auth_local";
const SESSION_SESSION_KEY = "demo_auth_session";

// helper para peticiones JSON
async function request(path, options = {}) {
  const res = await fetch(`${API_BASE}${path}`, {
    headers: { "Content-Type": "application/json", ...(options.headers || {}) },
    ...options,
  });
  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(text || res.statusText || "API error");
  }
  if (res.status === 204) return null;
  return res.json();
}

// Guarda sesión igual que tu versión demo (compatible con tu AuthContext)
function saveSession({ email, name }, remember) {
  const payload = JSON.stringify({ email, name, t: Date.now() });
  if (remember) {
    localStorage.setItem(LOCAL_SESSION_KEY, payload);
    sessionStorage.removeItem(SESSION_SESSION_KEY);
  } else {
    sessionStorage.setItem(SESSION_SESSION_KEY, payload);
    localStorage.removeItem(LOCAL_SESSION_KEY);
  }
}

export function getCurrentUser() {
  const raw =
    localStorage.getItem(LOCAL_SESSION_KEY) ||
    sessionStorage.getItem(SESSION_SESSION_KEY);
  if (!raw) return null;
  try {
    const { email, name } = JSON.parse(raw);
    return { email, name };
  } catch {
    return null;
  }
}

export function signOut() {
  localStorage.removeItem(LOCAL_SESSION_KEY);
  sessionStorage.removeItem(SESSION_SESSION_KEY);
}

// ---- llamadas reales al backend ----

// El backend de registro pide: userName, name, email, password
// Si tu UI solo pide email+password, derivamos name/userName del email:
export async function register(email, password) {
  const userName = email.split("@")[0] || "user";
  const name = userName;

  // POST /trello/v1/user/register -> { name: "..." }
  const res = await request("/user/register", {
    method: "POST",
    body: JSON.stringify({ userName, name, email, password }),
  });

  // guardamos sesión (email + name)
  saveSession({ email, name: res?.name || name }, true);
  return { email, name: res?.name || name };
}

export async function login(email, password, remember) {
  // POST /trello/v1/user/login -> { name: "..." }
  const res = await request("/user/login", {
    method: "POST",
    body: JSON.stringify({ email, password }),
  });

  saveSession({ email, name: res?.name || email }, remember);
  return { email, name: res?.name || email };
}

// No tienes endpoint de reset; mantenemos el error como antes
export function resetPassword() {
  throw new Error("Password reset is not implemented on the backend yet.");
}
