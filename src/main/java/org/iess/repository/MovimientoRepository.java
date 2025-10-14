package org.iess.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.iess.entity.Movimiento;

@ApplicationScoped
public class MovimientoRepository implements PanacheRepository<Movimiento> {
}
