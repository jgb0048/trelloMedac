package com.medac.trello.api.service; // Asume tu paquete de servicio

import com.medac.trello.api.model.Lista;
import com.medac.trello.api.model.repository.ListaRepository; // O ListaRepository, asegúrate del nombre
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List; 
import java.util.Optional;

@Service
public class ListaService {

    @Autowired
    private ListaRepository listaRepository;

    // C - Crear / Actualizar
    public Lista guardarLista(Lista lista) {
        return listaRepository.save(lista);
    }

    // Listar (Todos)
    public List<Lista> obtenerTodasLasListas() {
        return listaRepository.findAll();
    }

    //Listar (Por ID)
    public Optional<Lista> obtenerListaPorId(Long idLista) {
        // CORREGIDO: Usar el parámetro idLista
        return listaRepository.findById(idLista);
    }

    // Actualizar
    public Lista actualizarLista(Long idLista, Lista listaDetalles) {
        Optional<Lista> listaOptional = listaRepository.findById(idLista);

        if (listaOptional.isPresent()) {
            Lista listaExistente = listaOptional.get();

            listaExistente.setNombre(listaDetalles.getNombre());
            listaExistente.setOrden(listaDetalles.getOrden());
            listaExistente.setIdTablero(listaDetalles.getIdTablero());

            return listaRepository.save(listaExistente);
        } else {
            // Esto se mapeará a un HTTP 404 en el controlador
            throw new RuntimeException("Lista no encontrada con id: " + idLista);
        }
    }

    // D - Eliminar
    public void eliminarLista(Long idLista) {
        listaRepository.deleteById(idLista);
    }
}
