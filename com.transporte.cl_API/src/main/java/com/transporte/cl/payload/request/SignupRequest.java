package com.transporte.cl.payload.request;

import java.util.Set;

import jakarta.validation.constraints.*;

public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 50)
  private String nombres;

  @NotBlank
  private String username;
  private Boolean notificacion;
  @Email
  private String email;
  private Set<String> role;

  @Size(min = 6, max = 80)
  private String password;

  private String empresa;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRole() {
    return this.role;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getEmpresa() {
    return empresa;
  }

  public void setEmpresa(String empresa) {
    this.empresa = empresa;
  }

  public Boolean getNotificacion() {
    return notificacion;
  }

  public void setNotificacion(Boolean notificacion) {
    this.notificacion = notificacion;
  }
}
