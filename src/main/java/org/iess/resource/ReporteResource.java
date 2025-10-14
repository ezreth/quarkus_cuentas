package org.iess.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.iess.entity.Cliente;
import org.iess.entity.Cuenta;
import org.iess.entity.Movimiento;
import org.iess.repository.ClienteRepository;
import org.iess.repository.CuentaRepository;
import org.iess.repository.MovimientoRepository;

import java.time.LocalDate;
import java.util.*;

@Path("/reportes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReporteResource {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    CuentaRepository cuentaRepository;

    @Inject
    MovimientoRepository movimientoRepository;

    @GET
    public Response generarReporte(
            @QueryParam("identificacion") String identificacion,
            @QueryParam("fechaInicio") String fechaInicio,
            @QueryParam("fechaFin") String fechaFin) {

        if (identificacion == null || fechaInicio == null || fechaFin == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Debe enviar los parámetros: identificacion, fechaInicio y fechaFin (yyyy-MM-dd)")
                    .build();
        }

        LocalDate inicio;
        LocalDate fin;
        try {
            inicio = LocalDate.parse(fechaInicio);
            fin = LocalDate.parse(fechaFin);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Formato de fecha inválido, use yyyy-MM-dd")
                    .build();
        }

        Cliente cliente = clienteRepository.find("identificacion", identificacion).firstResult();
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente no encontrado con identificación: " + identificacion)
                    .build();
        }

        List<Cuenta> cuentas = cuentaRepository.list("cliente", cliente);

        List<Map<String, Object>> cuentasReporte = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            // Movimientos dentro del rango
            List<Movimiento> movimientos = movimientoRepository.list(
                    "cuenta = ?1 and fecha between ?2 and ?3",
                    cuenta, inicio.atStartOfDay(), fin.atTime(23, 59, 59)
            );

            double totalCreditos = movimientos.stream()
                    .filter(m -> m.getValor() > 0)
                    .mapToDouble(Movimiento::getValor)
                    .sum();

            double totalDebitos = movimientos.stream()
                    .filter(m -> m.getValor() < 0)
                    .mapToDouble(m -> Math.abs(m.getValor()))
                    .sum();

            Map<String, Object> cuentaInfo = new LinkedHashMap<>();
            cuentaInfo.put("numeroCuenta", cuenta.getNumeroCuenta());
            cuentaInfo.put("tipoCuenta", cuenta.getTipoCuenta());
            cuentaInfo.put("saldoInicial", cuenta.getSaldoInicial());
            cuentaInfo.put("totalCreditos", totalCreditos);
            cuentaInfo.put("totalDebitos", totalDebitos);
            cuentaInfo.put("saldoDisponible", cuenta.getSaldoInicial());
            cuentasReporte.add(cuentaInfo);
        }

        Map<String, Object> reporte = new LinkedHashMap<>();
        reporte.put("cliente", cliente.getNombre());
        reporte.put("fechaInicio", inicio);
        reporte.put("fechaFin", fin);
        reporte.put("cuentas", cuentasReporte);

        return Response.ok(reporte).build();
    }
}

