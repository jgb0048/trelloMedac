<<<<<<< HEAD
package com.medac.trello.api.resources;

import com.medac.trello.api.model.Board; // Necesitas importar la entidad Board
import com.medac.trello.api.model.repository.BoardRepository;
import com.medac.trello.api.view.BoardRequest; // Necesitas crear esta clase (para recibir el JSON)
import com.medac.trello.api.view.BoardView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // ¡Importante para el POST!
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.CREATED; // Importar el código 201
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = TrelloApi.BASE_API_PATH + "/tableros", produces = APPLICATION_JSON_VALUE) // Ruta en español
public class BoardResource implements TrelloApi {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardResource(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // --- 1. ENDPOINT GET (Obtener tableros por usuario) ---
    @GetMapping
    public ResponseEntity<Set<BoardView>> boardsOwnedBy(@RequestParam Long ownerId) {
        // ... (Tu lógica existente para obtener tableros)
        return ResponseEntity.ok(boardRepository.findAllByCreatedBy(ownerId).stream()
                .map(b -> new BoardView(
                        b.getId(),
                        b.getName(),
                        b.getDescription(),
                        b.getCreatedOn()
                ))
                .collect(toSet()));
    }

    // --- 2. ENDPOINT POST (Crear un nuevo tablero) ---
    @PostMapping // Maneja las peticiones POST a /api/v1/tableros
    public ResponseEntity<BoardView> createBoard(@RequestBody BoardRequest boardRequest) {
        // Creamos la entidad Board a partir de la solicitud
        Board newBoard = new Board(
                boardRequest.getName(),
                boardRequest.getDescription(),
                LocalDateTime.now(), // El backend genera el timestamp
                boardRequest.getCreatedBy()
        );

        // Guardamos el tablero en la base de datos
        Board savedBoard = boardRepository.save(newBoard);

        // Devolvemos la vista del tablero creado con estado 201 Created
        return ResponseEntity.status(CREATED).body(new BoardView(
                savedBoard.getId(),
                savedBoard.getName(),
                savedBoard.getDescription(),
                savedBoard.getCreatedOn()
        ));
    }
}
=======
package com.medac.trello.api.resources;

import com.medac.trello.api.model.Board; // Necesitas importar la entidad Board
import com.medac.trello.api.model.repository.BoardRepository;
import com.medac.trello.api.view.BoardRequest; // Necesitas crear esta clase (para recibir el JSON)
import com.medac.trello.api.view.BoardView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // ¡Importante para el POST!
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.CREATED; // Importar el código 201
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = TrelloApi.BASE_API_PATH + "/tableros", produces = APPLICATION_JSON_VALUE) // Ruta en español
public class BoardResource implements TrelloApi {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardResource(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // --- 1. ENDPOINT GET (Obtener tableros por usuario) ---
    @GetMapping
    public ResponseEntity<Set<BoardView>> boardsOwnedBy(@RequestParam Long ownerId) {
        // ... (Tu lógica existente para obtener tableros)
        return ResponseEntity.ok(boardRepository.findAllByCreatedBy(ownerId).stream()
                .map(b -> new BoardView(
                        b.getId(),
                        b.getName(),
                        b.getDescription(),
                        b.getCreatedOn()
                ))
                .collect(toSet()));
    }

    // --- 2. ENDPOINT POST (Crear un nuevo tablero) ---
    @PostMapping // Maneja las peticiones POST a /api/v1/tableros
    public ResponseEntity<BoardView> createBoard(@RequestBody BoardRequest boardRequest) {
        // Creamos la entidad Board a partir de la solicitud
        Board newBoard = new Board(
                boardRequest.getName(),
                boardRequest.getDescription(),
                LocalDateTime.now(), // El backend genera el timestamp
                boardRequest.getCreatedBy()
        );

        // Guardamos el tablero en la base de datos
        Board savedBoard = boardRepository.save(newBoard);

        // Devolvemos la vista del tablero creado con estado 201 Created
        return ResponseEntity.status(CREATED).body(new BoardView(
                savedBoard.getId(),
                savedBoard.getName(),
                savedBoard.getDescription(),
                savedBoard.getCreatedOn()
        ));
    }
}
>>>>>>> 600b2cd6d0d7ecea5dff2ccc610d9a00a957caec
