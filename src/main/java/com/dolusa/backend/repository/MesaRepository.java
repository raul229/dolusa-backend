package com.dolusa.backend.repository;

import com.dolusa.backend.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    List<Mesa> findByZonaId(Long zonaId);
    List<Mesa> findByEstado(String estado);
}
