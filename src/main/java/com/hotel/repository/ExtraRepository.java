package com.hotel.repository;

import com.hotel.model.Extra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExtraRepository extends JpaRepository<Extra, Long> {
    List<Extra> findByDisponible(boolean disponible);
    List<Extra> findByNombreContaining(String nombre);
    List<Extra> findByPrecioBetween(Double precioMin, Double precioMax);
} 