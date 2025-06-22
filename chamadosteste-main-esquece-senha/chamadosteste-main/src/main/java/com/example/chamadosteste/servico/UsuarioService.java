package com.example.chamadosteste.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chamadosteste.modelo.Usuario;
import com.example.chamadosteste.repositorio.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public String recuperarSenha(String nome, String email) {
         Usuario usuario = usuarioRepository.findByNomeAndEmail(nome, email);
        if (usuario != null) {
            return usuario.getSenha();
        }
        return null;
    }
}
