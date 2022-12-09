package jakarta.rest;

import common.ConstantesAPI;
import domain.services.ServicesLogin;
import domain.services.ServicesReaders;
import jakarta.beans.VerifyEmailBean;
import jakarta.common.Constantes;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Reader;

@Path(ConstantesAPI.PATH_LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RESTLogin {

    private final ServicesLogin servicesLogin;
    private final ServicesReaders servicesReaders;
    private final VerifyEmailBean verifyEmailBean;

    @Context
    private HttpServletRequest httpRequest;

    @Inject
    public RESTLogin(ServicesLogin servicesLogin, ServicesReaders servicesReaders, VerifyEmailBean verifyEmailBean) {
        this.servicesLogin = servicesLogin;
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
    public Reader login(@QueryParam(ConstantesAPI.USERNAME) String username, @QueryParam(ConstantesAPI.PASSWORD) String password) {
        Reader reader = servicesLogin.login(username, password);
        httpRequest.getSession().setAttribute(Constantes.LOGIN, true);
        return reader;
    }

    @GET
    @Path(ConstantesAPI.LOGOUT_PATH)
    public Reader logout() {
        httpRequest.getSession().removeAttribute(Constantes.LOGIN);
        return new Reader();
    }
}