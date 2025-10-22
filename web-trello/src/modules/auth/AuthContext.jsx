import { createContext, useCallback, useContext, useMemo, useState } from "react";
import {
  getCurrentUser,
  login as authLogin,
  register as authRegister,
  signOut as authSignOut,
  resetPassword as authResetPassword,
} from "../authClient.js";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => getCurrentUser());

  const login = useCallback(async (email, password, remember) => {
    const logged = await Promise.resolve(authLogin(email, password, remember));
    setUser(logged);
    return logged;
  }, []);

  const register = useCallback(async (email, password, remember) => {
    await Promise.resolve(authRegister(email, password));
    return login(email, password, remember);
  }, [login]);

  const signOut = useCallback(() => {
    authSignOut();
    setUser(null);
  }, []);

  const resetPassword = useCallback(async (email, newPassword) => {
    await Promise.resolve(authResetPassword(email, newPassword));
  }, []);

  const value = useMemo(
    () => ({ user, login, register, signOut, resetPassword }),
    [user, login, register, signOut, resetPassword],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error("useAuth must be used within an AuthProvider.");
  }
  return ctx;
}
