package com.medac.trello.api.model.controller;

import com.medac.trello.api.model.Lista;
import com.medac.trello.api.service.ListasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/listas")
public class ListaController {

    @Autowired
    private ListasService listasService;

    //CREAR
    @PostMapping
    public ResponseEntity<Lista> crearLista(@RequestBody Lista lista) {
        Lista nuevaLista = listasService.guardarLista(lista);
        return new ResponseEntity<>(nuevaLista, HttpStatus.CREATED);
    }

    //LISTAR
    @GetMapping
    public List<Lista> listarTodasLasListas() {
        return listasService.obtenerTodasLasListas();
    }

    //ACTUALIZAR
    /*@PutMapping("/{idLista}")
    public ResponseEntity<Lista> actualizarLista(@PathVariable Long idLista, @RequestBody Lista listaDetalles) {
        try {
            Lista listaActualizada = listasService.actualizarLista(idLista, listaDetalles);
            return ResponseEntity.ok(listaActualizada);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     */
    //ACTUALIZAR
    @PutMapping("/{idLista}")
    public ResponseEntity<Lista> actualizarLista(@PathVariable Long idLista, @RequestBody Lista listaDetalles) {
        // Si falla, el Handler Global se encarga de devolver 404
        Lista listaActualizada = listasService.actualizarLista(idLista, listaDetalles);
        return ResponseEntity.ok(listaActualizada);
    }


    // ELIMINAR http://localhost:8080/api/listas/{idLista}
    @DeleteMapping("/{idLista}")
    public ResponseEntity<HttpStatus> eliminarLista(@PathVariable Long idLista) {
        try {
            listasService.eliminarLista(idLista);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}