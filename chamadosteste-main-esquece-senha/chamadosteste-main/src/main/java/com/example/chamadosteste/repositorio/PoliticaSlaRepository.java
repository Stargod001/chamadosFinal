package com.example.chamadosteste.repositorio;

import com.example.chamadosteste.modelo.PoliticaSla;
import com.example.chamadosteste.modelo.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PoliticaSlaRepository extends JpaRepository<PoliticaSla, Integer> {
    Optional<PoliticaSla> findByPrioridade(Prioridade prioridade);
}
