package jakarta.rest;

import common.ConstantesAPI;
import domain.services.ServicesReaders;
import jakarta.beans.VerifyEmailBean;
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
    private final VerifyEmailBean verifyEmailBean;

    @Context
    private HttpServletRequest httpRequest;

    @Context
    SecurityContext securityContext;

    @Inject
    public RESTLogin(ServicesReaders servicesReaders, VerifyEmailBean verifyEmailBean) {
        this.servicesReaders = servicesReaders;
        this.verifyEmailBean = verifyEmailBean;
    }

    @POST
    public Response register(Reader reader) {
        String password = reader.getLogin().getPassword();
        Reader newReader = servicesReaders.saveReader(reader);
        verifyEmailBean.setEmail(reader.getLogin().getEmail());
        verifyEmailBean.setUsername(reader.getLogin().getUsername());
        verifyEmailBean.setPassword(password);
        verifyEmailBean.sendVerificationMail();
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
    public Reader logout() {
        httpRequest.getSession().removeAttribute(Constantes.CREDENTIAL);
        return new Reader();
    }
}