package com.medac.trello.api.service; // Asume tu paquete de servicio

import com.medac.trello.api.model.Lista;
import com.medac.trello.api.model.repository.ListaRepository; // O ListaRepository, asegúrate del nombre
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List; // <<-- ¡IMPORTANTE! Añade esta importación para java.util.List
import java.util.Optional;

@Service
public class ListaService {

    @Autowired
    private ListaRepository listaRepository; // Asumo ListRepository existe

    // C - Crear / Actualizar
    public Lista guardarLista(Lista lista) {
        return listaRepository.save(lista);
    }

    // R - Listar (Todos)
    // CORREGIDO: El tipo de retorno debe ser java.util.List<Lista>
    public List<Lista> obtenerTodasLasListas() {
        return listaRepository.findAll();
    }

    // R - Listar (Por ID)
    public Optional<Lista> obtenerListaPorId(Long idLista) {
        // CORREGIDO: Usar el parámetro idLista
        return listaRepository.findById(idLista);
    }

    // U - Actualizar
    public Lista actualizarLista(Long idLista, Lista listaDetalles) {
        Optional<Lista> listaOptional = listaRepository.findById(idLista);

        if (listaOptional.isPresent()) {
            Lista listaExistente = listaOptional.get();

            // Usando los nombres de variables de tu última entidad (nombre, orden, idTablero)
            listaExistente.setNombre(listaDetalles.getNombre());
            listaExistente.setOrden(listaDetalles.getOrden());
            // CORREGIDO: Asumo que usas el ID, no el objeto Tablero, según tu última entidad.
            listaExistente.setIdTablero(listaDetalles.getIdTablero());

            return listaRepository.save(listaExistente);
        } else {
            // Esto se mapeará a un HTTP 404 en tu controlador
            throw new RuntimeException("Lista no encontrada con id: " + idLista);
        }
    }

    // D - Eliminar
    public void eliminarLista(Long idLista) {
        listaRepository.deleteById(idLista);
    }
}
