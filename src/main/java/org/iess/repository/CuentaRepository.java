package org.iess.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.iess.entity.Cuenta;

@ApplicationScoped
public class CuentaRepository implements PanacheRepository<Cuenta> {
}
