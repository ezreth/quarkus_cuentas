package org.iess.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.iess.entity.Cliente;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes del sistema")
public class ClienteResource {

    @GET
    @Operation(summary = "Listar clientes", description = "Obtiene todos los clientes registrados en el sistema.")
    public List<Cliente> listarClientes() {
        return Cliente.listAll();
    }

    @POST
    @Transactional
    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente con sus datos personales.")
    public Response crearCliente(Cliente cliente) {
        cliente.persist();
        return Response.status(Response.Status.CREATED).entity(cliente).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Actualizar cliente", description = "Actualiza la información de un cliente existente.")
    public Response actualizarCliente(@PathParam("id") Long id, Cliente cliente) {
        Cliente existente = Cliente.findById(id);
        if (existente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        existente.setNombre(cliente.getNombre());
        existente.setGenero(cliente.getGenero());
        existente.setEdad(cliente.getEdad());
        existente.setIdentificacion(cliente.getIdentificacion());
        existente.setDireccion(cliente.getDireccion());
        existente.setTelefono(cliente.getTelefono());
        existente.setContrasena(cliente.getContrasena());
        existente.setEstado(cliente.getEstado());

        return Response.ok(existente).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente por su identificador único.")
    public Response eliminarCliente(@PathParam("id") Long id) {
        boolean eliminado = Cliente.deleteById(id);
        return eliminado ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
