package org.iess.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "CUENTA")
public class Cuenta extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUENTA_ID")
    private Long cuentaId;

    @Column(name = "NUMERO_CUENTA", unique = true, nullable = false)
    private String numeroCuenta;

    @Column(name = "TIPO_CUENTA")
    private String tipoCuenta;

    @Column(name = "SALDO_INICIAL")
    private Double saldoInicial;

    @Column(name = "ESTADO")
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimiento> movimientos;

    // ======== Getters y Setters ========

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
}
