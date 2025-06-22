package com.example.chamadosteste.repositorio;

import com.example.chamadosteste.modelo.Chamado;
import com.example.chamadosteste.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

    @Query("SELECT c FROM Chamado c WHERE c.status.idStatus = :idStatus")
    List<Chamado> buscarPorStatusId(@Param("idStatus") Integer idStatus);

    // Busca chamados abertos por um usuário específico
    List<Chamado> findByUsuario(Usuario usuario);

    // Busca chamados atribuídos a um responsável específico
    List<Chamado> findByResponsavel(Usuario responsavel);
}
