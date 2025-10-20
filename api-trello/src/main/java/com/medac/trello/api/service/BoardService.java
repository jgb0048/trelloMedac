package com.medac.trello.api.service;

import com.medac.trello.api.model.Board;
import com.medac.trello.api.model.repository.BoardRepository;
import com.medac.trello.api.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //CREAR/GUARDAR
    public Board guardarBoard(Board board) {
        return boardRepository.save(board);
    }

    // LISTAR TODOS
    public List<Board> obtenerTodosLosBoards() {
        return boardRepository.findAll();
    }

    // OBTENER POR ID
    public Board obtenerBoardPorId(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tablero no encontrado con id: " + id));
    }

    //  ACTUALIZAR
    public Board actualizarBoard(Long id, Board boardDetalles) {
        Board boardExistente = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tablero no encontrado con id: " + id));

        boardExistente.setName(boardDetalles.getName());
        boardExistente.setDescription(boardDetalles.getDescription());


        return boardRepository.save(boardExistente);
    }

    //ELIMINAR
    /*public void eliminarBoard(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tablero no encontrado con id: " + id);
        }
        boardRepository.deleteById(id);
    }

     */

    //ELIMINAR - Versión optimizada (Recomendada)
    public void eliminarBoard(Long id) {
        // 1. Obtener la entidad para verificar su existencia.
        Board boardExistente = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tablero no encontrado con id: " + id));

        // 2. Eliminar la entidad existente. (Solo una llamada DELETE)
        boardRepository.delete(boardExistente);
    }
    public Set<Board> obtenerTablerosPorUsuario(Long userId) {
        // Aquí usas el método que definiste en el repositorio:
        return boardRepository.findAllByCreatedBy(userId);
    }
}
