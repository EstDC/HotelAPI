package com.hotel.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reserva_extras")
public class ReservaExtra {
    @EmbeddedId
    private ReservaExtraId id = new ReservaExtraId();

    @ManyToOne
    @MapsId("reservaId")
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    @ManyToOne
    @MapsId("extraId")
    @JoinColumn(name = "extra_id")
    private Extra extra;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;
} 