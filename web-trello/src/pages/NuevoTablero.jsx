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
        <section className="min-h-screen bg-neutral-50 p-8">
            <div className="mx-auto w-full max-w-lg bg-white p-8 rounded-xl shadow-2xl">
                <h1 className="text-3xl font-bold text-neutral-800 mb-6 border-b pb-2">
                    Crear Nuevo Tablero
                </h1>
                
                {error && (
                    <div className="p-3 mb-4 bg-red-100 border border-red-400 text-red-700 rounded">
                        <p>{error}</p>
                    </div>
                )}

                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label htmlFor="name" className="block text-sm font-medium text-neutral-700">
                            Nombre del Tablero <span className="text-red-500">*</span>
                        </label>
                        <input
                            type="text"
                            id="name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                            className="mt-1 block w-full border border-neutral-300 rounded-md shadow-sm p-2"
                            disabled={loading}
                        />
                    </div>
                    
                    <div>
                        <label htmlFor="description" className="block text-sm font-medium text-neutral-700">
                            Descripción (Opcional)
                        </label>
                        <textarea
                            id="description"
                            rows="3"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            className="mt-1 block w-full border border-neutral-300 rounded-md shadow-sm p-2"
                            disabled={loading}
                        />
                    </div>

                    <div className="flex justify-end space-x-3 pt-4">
                        <Button 
                            variant="secondary" 
                            type="button" 
                            onClick={() => navigate(-1)}
                            disabled={loading}
                        >
                            Cancelar
                        </Button>
                        <Button 
                            type="submit" 
                            disabled={loading}
                            className="bg-blue-600 hover:bg-blue-700 text-white"
                        >
                            {loading ? 'Creando...' : 'Crear Tablero'}
                        </Button>
                    </div>
                </form>
            </div>
        </section>
    );
}