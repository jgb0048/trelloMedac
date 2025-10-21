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

    //CORREGIDO
    @Column(name = "fecha_creacion", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaCreacion;

    //CORREGIDO
    @Column(name = "fecha_vencimiento", nullable = true, columnDefinition = "DATETIME") // Lo hice nullable true, ya que muchos campos de fecha/hora son opcionales
    private LocalDateTime expiresOn;

    @Column(name = "orden", nullable = false)
    private Integer orden;

    //CORREGIDO
    @Column(name = "id_lista", nullable = false, insertable = false, updatable = false)
    private Long owningListId;

    //CONSTRUCTOS SIN ARGUMENTOS
    public Card(){
        //Establecer la fecha de creación automáticamente si la DB no lo hace
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
    }

    //CONSTRUCTOR
    public Card(String titulo, LocalDateTime fechaCreacion, LocalDateTime expiresOn, Integer orden, Long owningListId){
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
    //public LocalDate getExpiresOn() { return expiresOn; }
    public LocalDateTime getExpiresOn() { return expiresOn; } // CORREGIDO: Retorna LocalDateTime
    public Integer getOrden() { return orden; } // CORREGIDO: getOrden
    public Long getOwningListId() { return owningListId; }

    //SETTERS
    public void setTitulo(String titulo) { this.titulo = titulo; } // CORREGIDO: setTitulo
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; } // Añadido
    //public void setExpiresOn(LocalDate expiresOn) { this.expiresOn = expiresOn; }
    public void setExpiresOn(LocalDateTime expiresOn) { this.expiresOn = expiresOn; } // CORREGIDO: Recibe LocalDateTime
    public void setOrden(Integer orden) { this.orden = orden; } // CORREGIDO: setOrden
    //public void setOwningListId(Long owningListId) { this.owningListId = owningListId; }


    // Setter necesario para Jackson si la creamos o actualizamos (aunque no deberíamos usarlo aquí)
    public void setOwningListId(Long owningListId) {
        this.owningListId = owningListId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0; // Manejar posible null de Long
    }
}