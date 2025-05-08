package com.hotel.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "datos_bancarios")
public class DatosBancarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tarjeta", nullable = false)
    private TipoTarjeta tipoTarjeta;

    @Column(name = "numero_tarjeta", nullable = false)
    private String numeroTarjeta;

    @Column(name = "titular", nullable = false)
    private String titular;

    @Column(name = "fecha_expiracion", nullable = false)
    private String fechaExpiracion;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    @Column(nullable = false)
    private boolean activa = true;

    @Column(name = "predeterminada", nullable = false)
    private boolean predeterminada = false;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "ultima_modificacion")
    private LocalDateTime ultimaModificacion;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        ultimaModificacion = LocalDateTime.now();
    }
} 