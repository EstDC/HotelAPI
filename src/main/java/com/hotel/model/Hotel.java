package com.hotel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hoteles")
public class Hotel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String direccion;
    
    @Column(nullable = false)
    private String ciudad;
    
    @Column(nullable = false)
    private String pais;
    
    @Column(nullable = false)
    private Integer estrellas;
    
    @Column(length = 1000)
    private String descripcion;
    
    @Column(nullable = false)
    private boolean activo = true;
    
    @Column
    private String telefono;
    
    @Column
    private String email;
    
    @Column
    private String sitioWeb;
    
    @Column
    private Double latitud;
    
    @Column
    private Double longitud;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("hotel")
    private List<Habitacion> habitaciones = new ArrayList<>();

    //@OneToMany(mappedBy = "hotel")
    //@com.fasterxml.jackson.annotation.JsonManagedReference
    //private List<Habitacion> habitaciones;

    //@ManyToMany
    //@JoinTable(
    //    name = "hotel_servicios",
    //    joinColumns = @JoinColumn(name = "hotel_id"),
    //    inverseJoinColumns = @JoinColumn(name = "servicio_id")
    //)
    //@com.fasterxml.jackson.annotation.JsonManagedReference
    //private List<Servicio> servicios;
    @ManyToMany
    @JoinTable(
        name = "hotel_servicios",
        joinColumns = @JoinColumn(name = "hotel_id"),
        inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    @JsonIgnoreProperties("hoteles")
    private List<Servicio> servicios;
} 