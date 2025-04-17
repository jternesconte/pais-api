package com.paisapi.pais_api.model;

import com.paisapi.pais_api.dto.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"login"})
        })
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O login é obrigatório")
    @Column(length = 50, nullable = false, unique = true)
    private String login;

    @NotBlank(message = "A senha é obrigatória")
    @Column(length = 255, nullable = false)
    private String senha;

    @NotBlank(message = "O nome é obrigatório")
    @Column(length = 100, nullable = false)
    private String nome;

    @Column(nullable = false)
    private boolean administrador;

    @Transient
    private RoleEnum role;

    public Usuario() {

    }

    public Usuario(String login, String senha, String nome, boolean administrador) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.administrador = administrador;
        this.role = administrador ? RoleEnum.ADMIN : RoleEnum.USER;
    }

    public RoleEnum getRole() {
        return this.administrador ? RoleEnum.ADMIN : RoleEnum.USER;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
        this.administrador = role == RoleEnum.ADMIN;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.getRole() == RoleEnum.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}