package com.hotel.repository;

import com.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByCiudad(String ciudad);
    List<Hotel> findByPais(String pais);
    List<Hotel> findByEstrellas(Integer estrellas);
    List<Hotel> findByActivo(boolean activo);
    List<Hotel> findByCiudadAndPais(String ciudad, String pais);
    List<Hotel> findByActivoTrue();
    List<Hotel> findByCiudadAndActivoTrue(String ciudad);
    List<Hotel> findByPaisAndActivoTrue(String pais);
    List<Hotel> findByNombreContainingOrCiudadContainingOrPaisContainingAndActivoTrue(
        String nombre, String ciudad, String pais);
    List<Hotel> findByEstrellasGreaterThanEqualAndEstrellasLessThanEqualAndActivoTrue(
        Integer min, Integer max);
    List<Hotel> findByNombreContainingAndCiudadAndEstrellasAndActivoTrue(String nombre, String ciudad, Integer estrellas);
    List<Hotel> findByNombreContainingAndCiudadAndActivoTrue(String nombre, String ciudad);
    List<Hotel> findByNombreContainingAndEstrellasAndActivoTrue(String nombre, Integer estrellas);
    List<Hotel> findByCiudadAndEstrellasAndActivoTrue(String ciudad, Integer estrellas);
    List<Hotel> findByNombreContainingAndActivoTrue(String nombre);
    List<Hotel> findByEstrellasAndActivoTrue(Integer estrellas);
} 