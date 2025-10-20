package com.medac.trello.api.controller;

import com.medac.trello.api.model.Board;
import com.medac.trello.api.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tableros")
public class BoardController {

    @Autowired
    private BoardService boardService;

    //CREAR: POST /api/tableros
    @PostMapping
    public ResponseEntity<Board> crearTablero(@RequestBody Board board) {
        Board nuevoBoard = boardService.guardarBoard(board);
        return new ResponseEntity<>(nuevoBoard, HttpStatus.CREATED); // HTTP 201
    }

    //LEER TODOS: GET /api/tableros
    @GetMapping
    public List<Board> listarTodosLosTableros() {
        return boardService.obtenerTodosLosBoards();
    }

    //LEER UNO: GET /api/tableros/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Board> obtenerTableroPorId(@PathVariable Long id) {
        // La excepción 404 es manejada por el GlobalExceptionHandler si no lo encuentra.
        Board board = boardService.obtenerBoardPorId(id);
        return ResponseEntity.ok(board);
    }

    //ACTUALIZAR: PUT /api/tableros/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Board> actualizarTablero(@PathVariable Long id, @RequestBody Board boardDetalles) {
        // La excepción 404 es manejada si el ID no existe.
        Board boardActualizado = boardService.actualizarBoard(id, boardDetalles);
        return ResponseEntity.ok(boardActualizado);
    }

    //ELIMINAR: DELETE /api/tableros/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarTablero(@PathVariable Long id) {
        // La excepción 404 es manejada si el ID no existe.
        boardService.eliminarBoard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}