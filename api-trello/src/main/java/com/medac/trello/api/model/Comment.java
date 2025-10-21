package com.medac.trello.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "comentario")
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_comentario")
    private Long id;
    @Column(name = "contenido", nullable = false)
    private String content;
    //@Column(name = "fecha", nullable = false)
    //private LocalDateTime createdOn;
    @Column(name = "fecha", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdOn;
    //@Column(name = "id_usuario", nullable = false)
    //private Long addedById;
    //@Column(name = "id_tarjeta", nullable = false)
    //private Long owningCardId;
    @Column(name = "id_usuario", nullable = false, insertable = false, updatable = false)
    private Long addedById;
    @Column(name = "id_tarjeta", nullable = false, insertable = false, updatable = false)
    private Long owningCardId;

    // CONSTRUCTOR SIN ARGUMENTOS REQUERIDO POR JPA
    public Comment() {
    }
    public Comment(String content, LocalDateTime createdOn, Long addedById, Long owningCardId) {
        this.content = content;
        this.createdOn = createdOn;
        this.addedById = addedById;
        this.owningCardId = owningCardId;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Long getAddedById() {
        return addedById;
    }

    public Long getOwningCardId() {
        return owningCardId;
    }


    //SETTERS
    // Setters para las claves foráneas si se van a inyectar en un DTO
    public void setAddedById(Long addedById) {
        this.addedById = addedById;
    }

    public void setOwningCardId(Long owningCardId) {
        this.owningCardId = owningCardId;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        //return id == comment.id;
        //Para comprobaciónn segira de Long
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
