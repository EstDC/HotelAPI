package com.hotel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @Column(nullable = false)
    private Double monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodoPago;

    @Column(nullable = false)
    private String referenciaPago;

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    @Column
    private String observaciones;

    @PrePersist
    protected void onCreate() {
        fechaPago = LocalDateTime.now();
    }
} 