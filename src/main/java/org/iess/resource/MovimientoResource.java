package org.iess.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.iess.entity.Cuenta;
import org.iess.entity.Movimiento;
import org.iess.exception.BusinessException;
import java.time.LocalDateTime;
import java.util.List;

@Path("/movimientos")
@Tag(name = "Movimientos", description = "Operaciones sobre los movimientos bancarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovimientoResource {

    @GET
    @Operation(summary = "Listar movimientos", description = "Obtiene todos los movimientos registrados")
    public List<Movimiento> listAll() {
        return Movimiento.listAll();
    }

    @POST
    @Transactional
    @Operation(summary = "Registrar movimiento", description = "Registra una transacción de crédito o débito")
    public Response registrarMovimiento(Movimiento movimiento) {
        Cuenta cuenta = Cuenta.findById(movimiento.getCuenta().getCuentaId());
        if (cuenta == null) {
            throw new BusinessException("La cuenta no existe");
        }

        double saldoActual = cuenta.getSaldoInicial();
        double valor = movimiento.getValor();

        if ("DEBITO".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            if (saldoActual <= 0 || saldoActual < valor) {
                throw new BusinessException("Saldo no disponible");
            }
            movimiento.setValor(-Math.abs(valor)); // siempre negativo
            cuenta.setSaldoInicial(saldoActual - valor);
        } else if ("CREDITO".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            movimiento.setValor(Math.abs(valor)); // siempre positivo
            cuenta.setSaldoInicial(saldoActual + valor);
        } else {
            throw new BusinessException("Tipo de movimiento inválido (use 'DEBITO' o 'CREDITO')");
        }

        movimiento.setFecha(LocalDateTime.now());
        movimiento.setCuenta(cuenta);
        movimiento.persist();

        return Response.ok(movimiento).build();
    }
}
