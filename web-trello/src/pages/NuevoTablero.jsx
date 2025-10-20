/*import Button from "../components/ui/Button.jsx";
import { useNavigate } from "react-router-dom";

export default function NuevoTablero() {
  const navigate = useNavigate();

  return (
    <section className="min-h-screen bg-gradient-to-b from-neutral-50 to-white px-4 py-8">
      <div className="mx-auto w-full max-w-3xl">
        <div className="mb-4">
          <Button variant="secondary" onClick={() => navigate(-1)}>← Volver</Button>
        </div>

        <div className="rounded-2xl border bg-white p-6 shadow-sm">
          <h1 className="text-xl font-bold">Creación de tableros</h1>
          <p className="mt-2 text-sm text-neutral-600">
            (Aqui iria la creacion de tableros)
          </p>
        </div>
      </div>
    </section>
  );
}
  */

 // ... importaciones
import React, { useState } from "react"; 
import Button from "../components/ui/Button.jsx";
import Input from "../components/ui/Input.jsx"; 
import Textarea from "../components/ui/Textarea.jsx"; 
import { useNavigate } from "react-router-dom";

export default function NuevoTablero() {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        name: "", // Renombrado a 'name' para coincidir con la entidad Board
        description: "",
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

  
        // Simulación: Obtener el ID del usuario actual (DEBES REEMPLAZAR 1L por el ID REAL del usuario logueado)
        const userId = 1; 

        const boardData = {
            name: formData.name, // Coincide con el campo 'name' de Board.java
            description: formData.description, // Coincide con el campo 'description'
            // Campo requerido por la BBDD
            createdBy: userId, 
            // Campo requerido por la BBDD (ISO 8601 string)
            createdOn: new Date().toISOString(), 
        };

        try {
            const res = await fetch("/api/tableros", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
            
                },
                body: JSON.stringify(boardData),
            });

            if (!res.ok) {
                // Si la BBDD falla (ej. versión MySQL incompatible o error de campo), 
                // Spring devolverá un 500. Capturamos el error aquí.
                const errorData = await res.json();
                throw new Error(errorData.message || "Error desconocido al crear el tablero.");
            }

            const nuevoBoard = await res.json();
            
            // Éxito:
            console.log("Tablero creado con éxito:", nuevoBoard);
            alert(`Tablero "${nuevoBoard.name}" creado con éxito.`); 
            navigate(`/tableros/${nuevoBoard.id}`, { replace: true });

        } catch (e) {
            setError(e.message);
            console.error("Error al guardar el tablero:", e);
        } finally {
            setLoading(false);
        }
    };

    return (
        <section className="min-h-screen bg-gradient-to-b from-neutral-50 to-white px-4 py-8">
            <div className="mx-auto w-full max-w-3xl">
                {/* ... Botón Volver */}
                <div className="mb-4">
                    <Button variant="secondary" onClick={() => navigate(-1)}>
                        ← Volver
                    </Button>
                </div>

                <div className="rounded-2xl border bg-white p-6 shadow-sm">
                    <h1 className="text-xl font-bold mb-6">Creación de tableros</h1>

                    {error && (
                        <div className="rounded-xl border border-red-300 bg-red-50 p-3 text-sm text-red-700 mb-4">
                            Error: {error}
                        </div>
                    )}
                    
                    <form onSubmit={handleSubmit} className="space-y-6">
                        
                        {/* Campo Nombre del Tablero */}
                        <div>
                            <label htmlFor="name" className="block text-sm font-medium text-neutral-700 mb-1">
                                Nombre del Tablero
                            </label>
                            <Input
                                type="text"
                                id="name"
                                // CAMBIO: Usamos 'name' para coincidir con el estado y la entidad Java
                                name="name" 
                                placeholder="Ej. Proyecto Alpha"
                                value={formData.name}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        {/* Campo Descripción del Tablero */}
                        <div>
                            <label htmlFor="description" className="block text-sm font-medium text-neutral-700 mb-1">
                                Descripción (Opcional)
                            </label>
                            <Textarea
                                id="description"
                                // CAMBIO: Usamos 'description' para coincidir con el estado y la entidad Java
                                name="description" 
                                placeholder="Una breve descripción de lo que se gestionará aquí."
                                value={formData.description}
                                onChange={handleChange}
                                rows="4"
                            />
                        </div>

                        {/* Botón de Envío */}
                        <Button type="submit" className="w-full" disabled={loading}>
                            {loading ? "Creando..." : "Crear Tablero"}
                        </Button>
                    </form>
                </div>
            </div>
        </section>
    );
}