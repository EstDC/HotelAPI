package com.hotel.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "habitacion_id", nullable = false)
    private Habitacion habitacion;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDateTime fechaEntrada;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDateTime fechaSalida;

    @Column(name = "numero_huespedes", nullable = false)
    private Integer numeroHuespedes;

    @ManyToMany
    @JoinTable(
        name = "reserva_extras",
        joinColumns = @JoinColumn(name = "reserva_id"),
        inverseJoinColumns = @JoinColumn(name = "extra_id")
    )
    private Set<Extra> extras;

    @Column(name = "precio_total", nullable = false)
    private Double precioTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
} 