package jakarta.rest;

import common.ConstantesAPI;
import domain.services.ServicesReaders;
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

    @Inject
    public RESTReaders(ServicesReaders servicesReaders) {
        this.servicesReaders = servicesReaders;
    }

    @GET
    public List<Reader> getAllReaders() {
        return servicesReaders.getReaders();
    }

    @GET
    @Path(ConstantesAPI.NEWSPAPER_PATH + ConstantesAPI.ID_PATH_PARAM)
    public List<Reader> getReadersByNewspaper(@PathParam(ConstantesAPI.ID) String id) {
        return servicesReaders.getReadersByNewspaper(id);
    }

    @GET
    @Path(ConstantesAPI.ARTICLE_TYPE_PATH + ConstantesAPI.ID_PATH_PARAM)
    public List<Reader> getReadersByArticleType(@PathParam(ConstantesAPI.ID) String id) {
        return servicesReaders.getReadersByArticleType(id);
    }

    @GET
    @Path(ConstantesAPI.ID_PATH_PARAM)
    public Reader getReader(@PathParam(ConstantesAPI.ID) String id) {
        return servicesReaders.getReader(id);
    }

    @POST
    public Response saveReader(Reader reader) {
        return Response.status(Response.Status.CREATED)
                .entity(servicesReaders.saveReader(reader))
                .build();
    }

    @PUT
    public Reader updateReader(Reader reader) {
        return servicesReaders.updateReader(reader);
    }

    @DELETE
    @Path(ConstantesAPI.ID_PATH_PARAM)
    public Response deleteReader(@PathParam(ConstantesAPI.ID) String id) {
        servicesReaders.deleteReader(servicesReaders.getReader(id));
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}