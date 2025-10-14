package org.iess.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<BusinessException> {
    @Override
    public Response toResponse(BusinessException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"" + ex.getMessage() + "\"}")
                .type("application/json")
                .build();
    }
}
