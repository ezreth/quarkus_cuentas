package org.iess.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.iess.entity.Cliente;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
}
