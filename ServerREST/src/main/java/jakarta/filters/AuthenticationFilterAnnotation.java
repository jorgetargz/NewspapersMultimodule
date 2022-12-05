package jakarta.filters;


import jakarta.common.Constantes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import modelo.BaseError;

import java.time.LocalDateTime;

@Provider
@Private
public class AuthenticationFilterAnnotation implements ContainerRequestFilter {

    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {

        if (request.getSession().getAttribute(Constantes.LOGIN) == null) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new BaseError(Constantes.THIS_ENDPOINT_IS_PRIVATE, LocalDateTime.now()))
                    .type(MediaType.APPLICATION_JSON_TYPE).build());
        }
    }
}
