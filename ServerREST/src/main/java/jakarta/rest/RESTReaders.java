package jakarta.rest;

import common.ConstantesAPI;
import domain.services.ServicesReaders;
import jakarta.annotation.security.RolesAllowed;
import jakarta.beans.VerifyEmailBean;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Reader;

import java.util.List;

@Path(ConstantesAPI.PATH_READERS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RESTReaders {

    private final ServicesReaders servicesReaders;
    private final VerifyEmailBean verifyEmailBean;

    @Inject
    public RESTReaders(ServicesReaders servicesReaders, VerifyEmailBean verifyEmailBean) {
        this.servicesReaders = servicesReaders;
        this.verifyEmailBean = verifyEmailBean;
    }

    @GET
    @RolesAllowed({ConstantesAPI.ROLE_READER})
    public List<Reader> getAllReaders() {
        return servicesReaders.getReaders();
    }

    @GET
    @Path(ConstantesAPI.NEWSPAPER_PATH + ConstantesAPI.ID_PATH_PARAM)
    @RolesAllowed({ConstantesAPI.ROLE_READER})
    public List<Reader> getReadersByNewspaper(@PathParam(ConstantesAPI.ID) String id) {
        return servicesReaders.getReadersByNewspaper(id);
    }

    @GET
    @Path(ConstantesAPI.ARTICLE_TYPE_PATH + ConstantesAPI.ID_PATH_PARAM)
    @RolesAllowed({ConstantesAPI.ROLE_READER})
    public List<Reader> getReadersByArticleType(@PathParam(ConstantesAPI.ID) String id) {
        return servicesReaders.getReadersByArticleType(id);
    }

    @GET
    @Path(ConstantesAPI.ID_PATH_PARAM)
    @RolesAllowed({ConstantesAPI.ROLE_READER})
    public Reader getReader(@PathParam(ConstantesAPI.ID) String id) {
        return servicesReaders.getReader(id);
    }

    @POST
    @RolesAllowed({ConstantesAPI.ROLE_ADMIN})
    public Response saveReader(Reader reader) {
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

    @PUT
    @RolesAllowed({ConstantesAPI.ROLE_ADMIN})
    public Reader updateReader(Reader reader) {
        return servicesReaders.updateReader(reader);
    }


    @DELETE
    @Path(ConstantesAPI.ID_PATH_PARAM)
    @RolesAllowed(ConstantesAPI.ROLE_ADMIN)
    public Response deleteReader(@PathParam(ConstantesAPI.ID) String id) {
        servicesReaders.deleteReader(servicesReaders.getReader(id));
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}