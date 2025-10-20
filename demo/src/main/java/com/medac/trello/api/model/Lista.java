/*package com.medac.trello.api.model;

import jakarta.persistence.*;
import lombok.Data;

// Marca la clase como una tabla de DB
@Entity
@Table(name="Lista")
// Genera getters y setters
@Data
public class Lista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_lista")
    private Long idLista;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "orden", nullable = false)
    private Integer orden;

    // Relación Many-to-One con Tablero
    // Muchas Listas tienen un Tablero
    @ManyToOne
    @JoinColumn(name = "id_tablero", nullable = false)//COLUMNA FOREIGN KEY
    private Tablero tablero;



}


 */

package com.medac.trello.api.model;

import jakarta.persistence.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "lista")
public class Lista {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_lista")
    private Long idLista;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "orden", nullable = false)
    private int orden;
    @Column(name = "id_tablero", nullable = false)
    private Long idTablero;

    // 1. CONSTRUCTOR SIN ARGUMENTOS (¡AÑADIDO!)
    public Lista() {
    }

    // Constructor con argumentos (opcional, pero útil)
    public Lista(String nombre, int orden, long idTablero) {
        this.nombre = nombre;
        this.orden = orden;
        this.idTablero = idTablero;
    }

    // --- Getters ---
    public Long getIdLista() {
        return idLista;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getOrden() {
        return orden;
    }

    public Long getIdTablero() {
        return idTablero;
    }

    // --- Setters (¡AÑADIDOS!) ---
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public void setIdTablero(Long idTablero) {
        this.idTablero = idTablero;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Lista lista = (Lista) o;
        return Objects.equals(idLista, lista.idLista);
    }

    @Override
    public int hashCode() {
        return idLista != null ? idLista.hashCode() : 0;
    }
}