package com.medac.trello.api.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tarjeta")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column (name ="descripcion")
    private String descripcion;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate expiresOn;

    @Column(name = "orden", nullable = false)
    private Integer orden;

    @Column(name = "id_lista", nullable = false)
    private Long owningListId;

    //CONSTRUCTOS SIN ARGUMENTOS
    public Card(){
        //Establecer la fecha de creación automáticamente si la DB no lo hace
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
    }

    //CONSTRUCTOR
    public Card(String titulo, LocalDateTime fechaCreacion, LocalDate expiresOn, Integer orden, Long owningListId){
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.expiresOn = expiresOn;
        this.orden = orden;
        this.owningListId = owningListId;

    }

    //GETTERS
    public Long getId() { return id; }
    public String getTitulo() { return titulo; } // CORREGIDO: getTitulo
    public String getDescripcion() { return descripcion; } // Añadido
    public LocalDateTime getFechaCreacion() { return fechaCreacion; } // Añadido
    public LocalDate getExpiresOn() { return expiresOn; }
    public Integer getOrden() { return orden; } // CORREGIDO: getOrden
    public Long getOwningListId() { return owningListId; }

    //SETTERS
    public void setTitulo(String titulo) { this.titulo = titulo; } // CORREGIDO: setTitulo
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; } // Añadido
    public void setExpiresOn(LocalDate expiresOn) { this.expiresOn = expiresOn; }
    public void setOrden(Integer orden) { this.orden = orden; } // CORREGIDO: setOrden
    public void setOwningListId(Long owningListId) { this.owningListId = owningListId; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id); // Usar Objects.equals con Long
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0; // Manejar posible null de Long
    }
}