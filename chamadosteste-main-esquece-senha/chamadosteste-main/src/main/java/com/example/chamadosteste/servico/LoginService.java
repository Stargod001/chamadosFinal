package com.example.chamadosteste.servico;

import com.example.chamadosteste.modelo.Usuario;
import com.example.chamadosteste.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuarioLogado;

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public boolean validarLogin(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario != null && usuario.getSenha().equals(senha);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            this.usuarioLogado = usuario;
            return usuario;
        }
        return null;
    }
}
