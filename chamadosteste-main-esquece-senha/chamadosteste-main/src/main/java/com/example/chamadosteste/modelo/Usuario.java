package com.example.chamadosteste.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;

    private String nome;
    private String email;
    private String senha;
    private String tipo;

    public String toString(){
        return "ID: " + id_usuario + "  -   Nome: " + nome + "  -   email: " + email + "  -   Senha: " + senha + "  -   Tipo: " + tipo;
    }
}
