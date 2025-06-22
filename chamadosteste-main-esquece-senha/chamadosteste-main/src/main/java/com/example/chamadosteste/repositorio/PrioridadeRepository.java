package com.example.chamadosteste.repositorio;

import com.example.chamadosteste.modelo.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrioridadeRepository extends JpaRepository<Prioridade, Integer> {
}