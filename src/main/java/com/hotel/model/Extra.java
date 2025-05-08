package com.hotel.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "extras")
public class Extra {
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
    private Boolean disponible = true;
} 