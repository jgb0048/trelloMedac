// ¡Solo para demo! No uses esto en producción.
const USERS_KEY = "demo_users";
const LOCAL_SESSION_KEY = "demo_auth_local";
const SESSION_SESSION_KEY = "demo_auth_session";

function loadUsers() {
  try {
    return JSON.parse(localStorage.getItem(USERS_KEY)) || [];
  } catch {
    return [];
  }
}
function saveUsers(users) {
  localStorage.setItem(USERS_KEY, JSON.stringify(users));
}

// Hash cutre para demo (NO seguro)
function hash(str) {
  let h = 2166136261;
  for (let i = 0; i < str.length; i++) {
    h ^= str.charCodeAt(i);
    h += (h << 1) + (h << 4) + (h << 7) + (h << 8) + (h << 24);
  }
  return (h >>> 0).toString(16);
}

export function register(email, password) {
  const users = loadUsers();
  if (users.some(u => u.email.toLowerCase() === email.toLowerCase())) {
    throw new Error("Email is already registered.");
  }
  users.push({
    email,
    passwordHash: hash(password),
    createdAt: Date.now(),
  });
  saveUsers(users);
  return { email };
}

export function login(email, password, remember) {
  const users = loadUsers();
  const user = users.find(u => u.email.toLowerCase() === email.toLowerCase());
  if (!user || user.passwordHash !== hash(password)) {
    throw new Error("Invalid email or password.");
  }
  const payload = JSON.stringify({ email: user.email, t: Date.now() });
  if (remember) {
    localStorage.setItem(LOCAL_SESSION_KEY, payload);
    sessionStorage.removeItem(SESSION_SESSION_KEY);
  } else {
    sessionStorage.setItem(SESSION_SESSION_KEY, payload);
    localStorage.removeItem(LOCAL_SESSION_KEY);
  }
  return { email: user.email };
}

export function getCurrentUser() {
  const raw =
    localStorage.getItem(LOCAL_SESSION_KEY) ||
    sessionStorage.getItem(SESSION_SESSION_KEY);
  if (!raw) return null;
  try {
    const { email } = JSON.parse(raw);
    return { email };
  } catch {
    return null;
  }
}

export function signOut() {
  localStorage.removeItem(LOCAL_SESSION_KEY);
  sessionStorage.removeItem(SESSION_SESSION_KEY);
}

export function resetPassword(email, newPassword) {
  const users = loadUsers();
  const idx = users.findIndex(u => u.email.toLowerCase() === email.toLowerCase());
  if (idx === -1) throw new Error("Email not found.");
  users[idx].passwordHash = hash(newPassword);
  saveUsers(users);
  return true;
}
