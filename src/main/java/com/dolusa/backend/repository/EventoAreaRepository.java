package com.dolusa.backend.repository;

import com.dolusa.backend.model.EventoArea;
import com.dolusa.backend.model.EventoAreaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoAreaRepository extends JpaRepository<EventoArea, EventoAreaId> {
    List<EventoArea> findByEvento_IdEvento(Integer idEvento);
}
