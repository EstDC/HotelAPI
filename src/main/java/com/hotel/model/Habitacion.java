package com.hotel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "habitaciones")
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Hotel hotel;

    @Column(nullable = false)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoHabitacion tipo;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false)
    private Double precioPorNoche;

    @ElementCollection
    @CollectionTable(name = "habitacion_extras", joinColumns = @JoinColumn(name = "habitacion_id"))
    @Column(name = "extra")
    private Set<String> extras;

    @Column(nullable = false)
    private boolean activa = true;
} 