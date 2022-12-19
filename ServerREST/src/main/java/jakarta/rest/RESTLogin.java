package jakarta.rest;

import common.ConstantesAPI;
import domain.services.ServicesLogin;
import domain.services.ServicesReaders;
import jakarta.common.Constantes;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
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
        servicesLogin.sendVerificationEmail(reader);
        Reader newReader = servicesReaders.saveReader(reader);
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
                    .entity(new BaseError(Constantes.NO_SE_HA_PODIDO_AUTENTICAR_AL_USUARIO, LocalDateTime.now()))
                    .build();
        }
    }

    @GET
    @Path(ConstantesAPI.LOGOUT_PATH)
    public Response logout() {
        httpRequest.getSession().removeAttribute(Constantes.CREDENTIAL);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}