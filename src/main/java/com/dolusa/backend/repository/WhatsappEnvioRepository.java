package com.dolusa.backend.repository;

import com.dolusa.backend.model.WhatsappEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhatsappEnvioRepository extends JpaRepository<WhatsappEnvio, Long> {}
