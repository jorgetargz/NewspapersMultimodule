package jakarta.rest;

import common.ConstantesAPI;
import domain.services.ServicesLogin;
import domain.services.ServicesReaders;
import jakarta.common.Constantes;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import modelo.BaseError;
import modelo.Reader;

import java.time.LocalDateTime;

@Path(ConstantesAPI.PATH_LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RESTLogin {

    private final ServicesReaders servicesReaders;
    private final ServicesLogin servicesLogin;

    @Context
    private HttpServletRequest httpRequest;

    @Context
    SecurityContext securityContext;

    @Inject
    public RESTLogin(ServicesReaders servicesReaders, ServicesLogin servicesLogin) {
        this.servicesReaders = servicesReaders;
        this.servicesLogin = servicesLogin;
    }

    @POST
    public Response register(Reader reader) {
        Reader newReader = servicesReaders.saveReader(reader);
        servicesLogin.sendVerificationEmail(newReader);
        return Response.status(Response.Status.CREATED)
                .entity(newReader)
                .build();
    }

    @GET
    public Response login() {
        if (securityContext.getUserPrincipal() != null) {
            return Response.ok()
                    .entity(servicesReaders.getReaderByUsername(securityContext.getUserPrincipal().getName()))
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new BaseError((String) httpRequest.getAttribute(Constantes.ERROR_LOGIN), LocalDateTime.now()))
                    .build();
        }
    }

    @GET
    @Path(ConstantesAPI.LOGOUT_PATH)
    public Response logout(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization) {
        if (securityContext.getUserPrincipal() != null) {
            servicesLogin.logout(authorization);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new BaseError((String) httpRequest.getAttribute(Constantes.ERROR_LOGIN), LocalDateTime.now()))
                    .build();
        }
    }

}