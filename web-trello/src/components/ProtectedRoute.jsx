import React from 'react';
import { Navigate } from 'react-router-dom';

// ⚠️ DEBES IMPLEMENTAR TU LÓGICA DE AUTENTICACIÓN AQUÍ
// Por ahora, asumiremos que usas un hook o contexto para el usuario.
// Reemplaza esta línea con la forma en que obtienes el estado de autenticación.
const useAuth = () => {
    // Ejemplo: Lee un valor del localStorage, un estado global, o un contexto.
    const isAuthenticated = localStorage.getItem('userToken') ? true : false;
    return { isAuthenticated };
};

const ProtectedRoute = ({ children }) => {
    // 1. Obtiene el estado de autenticación
    const { isAuthenticated } = useAuth();

    // 2. Si el usuario NO está autenticado, redirige a /login
    if (!isAuthenticated) {
        return <Navigate to="/login" replace />;
    }

    // 3. Si el usuario SÍ está autenticado, muestra los componentes hijos (Dashboard, Perfil, etc.)
    return children;
};

export default ProtectedRoute;