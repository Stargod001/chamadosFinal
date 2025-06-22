package com.example.chamadosteste.repositorio;

import com.example.chamadosteste.modelo.StatusChamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusChamadoRepository extends JpaRepository<StatusChamado, Integer> {
}