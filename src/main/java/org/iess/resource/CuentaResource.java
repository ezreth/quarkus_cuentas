package org.iess.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.iess.entity.Cuenta;
import org.iess.entity.Cliente;

import java.util.List;

@Path("/cuentas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Cuentas", description = "Gestión de cuentas asociadas a los clientes")
public class CuentaResource {

    @GET
    @Operation(summary = "Listar cuentas", description = "Obtiene todas las cuentas registradas.")
    public List<Cuenta> listarCuentas() {
        return Cuenta.listAll();
    }

    @POST
    @Transactional
    @Operation(summary = "Crear cuenta", description = "Crea una nueva cuenta bancaria asociada a un cliente existente.")
    public Response crearCuenta(Cuenta cuenta) {
        // Validar que el cliente asociado exista
        Cliente cliente = Cliente.findById(cuenta.getCliente().getClienteId());
        if (cliente == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El cliente asociado no existe.")
                    .build();
        }

        cuenta.setCliente(cliente);
        cuenta.persist();
        return Response.status(Response.Status.CREATED).entity(cuenta).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Actualizar cuenta", description = "Actualiza los datos de una cuenta existente.")
    public Response actualizarCuenta(@PathParam("id") Long id, Cuenta cuenta) {
        Cuenta existente = Cuenta.findById(id);
        if (existente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        existente.setTipoCuenta(cuenta.getTipoCuenta());
        existente.setSaldoInicial(cuenta.getSaldoInicial());
        existente.setEstado(cuenta.getEstado());
        existente.setCliente(cuenta.getCliente());

        return Response.ok(existente).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Eliminar cuenta", description = "Elimina una cuenta bancaria por su identificador.")
    public Response eliminarCuenta(@PathParam("id") Long id) {
        boolean eliminado = Cuenta.deleteById(id);
        return eliminado ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
