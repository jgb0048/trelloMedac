/*package com.medac.trello.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tablero")
public class Board {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_tablero")
    private long id;

    @Column(name = "nombre", nullable = false)
    private String name;
    @Column(name = "descripcion")
    private String description;
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "id_usuario_creador", nullable = false)
    private long createdBy;

    public Board(String name, LocalDateTime createdOn, long createdBy) {
        this.name = name;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
    }

    public Board(String name, String description, LocalDateTime createdOn, long createdBy) {
        this.name = name;
        this.description = description;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return id == board.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

 */


/*package com.medac.trello.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tablero")
public class Board {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_tablero")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;
    @Column(name = "descripcion")
    private String description;
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "id_usuario_creador", nullable = false)
    private Long createdBy;

    //CONSTRUCTOR SIN ARGUMENTOS (obligatorio)
    public Board() {
    }

    public Board(String name, LocalDateTime createdOn, Long createdBy) {
        this.name = name;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
    }

    public Board(String name, String description, LocalDateTime createdOn, Long createdBy) {
        this.name = name;
        this.description = description;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public long getCreatedBy() {
        return createdBy;
    }
    //SETTERS
    // Necesarios para la actualización de datos (PUT)
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // id, createdOn, y createdBy no necesitan setters ya que no deben cambiarse.
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        //return id == board.id;
        return Objects.equals(id, board.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}


 */

package com.medac.trello.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tablero")
public class Board {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_tablero")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;
    @Column(name = "descripcion")
    private String description;
    @Column(name = "fecha_creacion", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdOn;
    @Column(name = "id_usuario_creador", nullable = false)
    private Long createdBy;

    // Aplicamos @JsonIgnore para evitar que el Board intente serializar la lista de Listas,
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    //@JsonIgnore
    @JsonIgnoreProperties("board")
    private Set<Lista> listas;


    // CONSTRUCTOR SIN ARGUMENTOS (obligatorio)
    public Board() {
    }

    // CONSTRUCTORES CON ARGUMENTOS
    public Board(String name, LocalDateTime createdOn, Long createdBy) {
        this.name = name;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
    }

    public Board(String name, String description, LocalDateTime createdOn, Long createdBy) {
        this.name = name;
        this.description = description;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
    }

    // GETTERS
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    // Getter de la colección (Importante para JPA, pero ignorado por JSON)
    public Set<Lista> getListas() {
        return listas;
    }


    // SETTERS

    // Necesarios para la actualización (PUT) y, ahora, la deserialización (POST)
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ⭐ CORRECCIÓN APLICADA: Setter necesario para que Jackson inyecte el valor 'createdOn'
    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    // ⭐ CORRECCIÓN APLICADA: Setter necesario para que Jackson inyecte el valor 'createdBy'
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public void setListas(Set<Lista> listas) {
        this.listas = listas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(id, board.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
