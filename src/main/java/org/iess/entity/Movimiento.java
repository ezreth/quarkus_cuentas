package org.iess.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.time.LocalDateTime;

@Entity
@Table(name = "MOVIMIENTO") // en mayúsculas, para Oracle
public class Movimiento extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MOVIMIENTO_ID")
    private Long movimientoId;

    @Column(name = "TIPO_MOVIMIENTO", nullable = false)
    private String tipoMovimiento;

    @Column(name = "VALOR", nullable = false)
    private Double valor;

    @Column(name = "FECHA", nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUENTA_ID")
    private Cuenta cuenta;

    // Getters y Setters
    public Long getMovimientoId() { return movimientoId; }
    public void setMovimientoId(Long movimientoId) { this.movimientoId = movimientoId; }

    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public Cuenta getCuenta() { return cuenta; }
    public void setCuenta(Cuenta cuenta) { this.cuenta = cuenta; }
}
