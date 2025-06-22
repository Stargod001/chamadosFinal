package com.example.chamadosteste.repositorio;

import com.example.chamadosteste.modelo.LogAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogAtividade, Integer> {

}
