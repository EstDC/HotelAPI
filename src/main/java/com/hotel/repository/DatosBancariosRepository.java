package com.hotel.repository;

import com.hotel.model.DatosBancarios;
import com.hotel.model.Usuario;
import com.hotel.model.TipoTarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DatosBancariosRepository extends JpaRepository<DatosBancarios, Long> {
    List<DatosBancarios> findByUsuario(Usuario usuario);
    List<DatosBancarios> findByUsuarioAndActiva(Usuario usuario, boolean activa);
    Optional<DatosBancarios> findByUsuarioAndPredeterminada(Usuario usuario, boolean predeterminada);
    List<DatosBancarios> findByTipoTarjeta(TipoTarjeta tipoTarjeta);
    List<DatosBancarios> findByUsuarioAndTipoTarjeta(Usuario usuario, TipoTarjeta tipoTarjeta);
} 