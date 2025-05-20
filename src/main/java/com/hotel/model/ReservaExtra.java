package com.hotel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Data
@EqualsAndHashCode(exclude = {"reserva"})
@Entity
@Table(name = "reserva_extras")
public class ReservaExtra {
    @EmbeddedId
    private ReservaExtraId id = new ReservaExtraId();

    @ManyToOne
    @MapsId("reservaId")
    @JoinColumn(name = "reserva_id")
    @JsonIgnore
    private Reserva reserva;

    @ManyToOne
    @MapsId("extraId")
    @JoinColumn(name = "extra_id")
    private Extra extra;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @Column(name = "precio_total", nullable = false)
    private Double precioTotal;
} 