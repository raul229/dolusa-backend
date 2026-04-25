package com.dolusa.backend.repository;

import com.dolusa.backend.model.AforoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AforoLogRepository extends JpaRepository<AforoLog, Long> {}
