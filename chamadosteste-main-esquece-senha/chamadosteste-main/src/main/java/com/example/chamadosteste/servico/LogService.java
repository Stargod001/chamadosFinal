package com.example.chamadosteste.servico;

import com.example.chamadosteste.modelo.LogAtividade;
import com.example.chamadosteste.repositorio.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    // Método principal para registrar atividade no log
    public void registrar(Integer idUsuario, String acao, String detalhes) {
        LogAtividade log = new LogAtividade();
        log.setIdUsuario(idUsuario);
        log.setAcao(acao);
        log.setDetalhes(detalhes);
        logRepository.save(log);
    }

    // Método alternativo com nome diferente, mas mesma funcionalidade
    public void logAtividade(int idUsuario, String acao, String detalhes) {
        registrar(idUsuario, acao, detalhes);
    }
}
