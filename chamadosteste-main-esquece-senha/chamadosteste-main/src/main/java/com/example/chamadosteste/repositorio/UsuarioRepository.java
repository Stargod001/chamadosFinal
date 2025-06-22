package com.example.chamadosteste.repositorio;

import com.example.chamadosteste.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmail(String email);

    List<Usuario> findAll();

    Usuario findByNomeAndEmail(String nome, String email);

    // Busca usuários por tipo (ex: "AGENTE", "ADMIN", "USUARIO")
    List<Usuario> findByTipo(String tipo);
}
