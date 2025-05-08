package com.hotel.repository;

import com.hotel.model.Servicio;
import com.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findByActivo(Boolean activo);
    
    @Query("SELECT s FROM Servicio s JOIN s.hoteles h WHERE h = :hotel")
    List<Servicio> findByHotel(@Param("hotel") Hotel hotel);
    
    @Query("SELECT s FROM Servicio s JOIN s.hoteles h WHERE h = :hotel AND s.activo = true")
    List<Servicio> findByHotelAndActivo(@Param("hotel") Hotel hotel);
} 