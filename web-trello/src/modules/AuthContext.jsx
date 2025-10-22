import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const savedUser = localStorage.getItem("user");
    if (savedUser) {
      setUser(JSON.parse(savedUser));
    }
  }, []);

  // Login normal
  async function login(email, password, remember) {
    const fakeUser = { email, name: "Usuario Ejemplo" };
    setUser(fakeUser);
    if (remember) localStorage.setItem("user", JSON.stringify(fakeUser));
  }

  // Login Google
  function loginWithGoogle(googleUser) {
    console.log("Usuario guardado desde Google:", googleUser);
    setUser(googleUser);
    localStorage.setItem("user", JSON.stringify(googleUser));
  }

  function logout() {
    setUser(null);
    localStorage.removeItem("user");
  }

  return (
    <AuthContext.Provider value={{ user, login, loginWithGoogle, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);