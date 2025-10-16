package com.medac.trello.api.controller; // Asumo el paquete

import com.medac.trello.api.model.Lista; // Importa tu entidad renombrada
import com.medac.trello.api.service.ListaService; // Asumo el servicio renombrado
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List; // Importar java.util.List explícitamente

@RestController
@RequestMapping("/api/listas")
// ¡También renombra la clase de ListController a ListaController!
public class ListaController {

    @Autowired
    private ListaService listaService; // Inyecta el servicio renombrado

    // 1. CREAR (C)
    @PostMapping
    public ResponseEntity<Lista> crearLista(@RequestBody Lista lista) {
        Lista nuevaLista = listaService.guardarLista(lista);
        return new ResponseEntity<>(nuevaLista, HttpStatus.CREATED);
    }

    // 2. LISTAR (R) - Ahora usa java.util.List y tu modelo Lista
    @GetMapping
    public List<Lista> listarTodasLasListas() {
        return listaService.obtenerTodasLasListas();
    }

    // 3. ACTUALIZAR (U)
    @PutMapping("/{idLista}")
    public ResponseEntity<Lista> actualizarLista(@PathVariable Long idLista, @RequestBody Lista listaDetalles) {
        try {
            Lista listaActualizada = listaService.actualizarLista(idLista, listaDetalles);
            return ResponseEntity.ok(listaActualizada);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 4. ELIMINAR (D) - DELETE http://localhost:8080/api/listas/{idLista}
    @DeleteMapping("/{idLista}")
    public ResponseEntity<HttpStatus> eliminarLista(@PathVariable Long idLista) {
        try {
            listaService.eliminarLista(idLista);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
