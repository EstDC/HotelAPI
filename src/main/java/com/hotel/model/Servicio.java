package com.hotel.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToMany(mappedBy = "servicios")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private List<Hotel> hoteles;
} 