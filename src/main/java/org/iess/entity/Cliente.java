package org.iess.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado;

    // Getters y Setters específicos de Cliente
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}
