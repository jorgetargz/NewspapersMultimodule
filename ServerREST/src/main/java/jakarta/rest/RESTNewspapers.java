package jakarta.rest;

import common.ConstantesAPI;
import domain.services.ServicesNewspapers;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Newspaper;

import java.util.List;

@Path(ConstantesAPI.PATH_NEWSPAPERS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RESTNewspapers {

    private final ServicesNewspapers servicesNewspapers;

    @Inject
    public RESTNewspapers(ServicesNewspapers servicesNewspapers) {
        this.servicesNewspapers = servicesNewspapers;
    }


    @GET
    public List<Newspaper> getAllNewspapers() {
        return servicesNewspapers.getNewspapers();
    }

    @GET
    @Path(ConstantesAPI.ID_PATH_PARAM)
    public Newspaper getNewspaper(@PathParam(ConstantesAPI.ID) String id) {
        return servicesNewspapers.get(Integer.parseInt(id));
    }

    @POST
    @RolesAllowed({ConstantesAPI.ROLE_ADMIN})
    public Response saveNewspaper(Newspaper newspaper) {
        return Response.status(Response.Status.CREATED)
                .entity(servicesNewspapers.saveNewspaper(newspaper))
                .build();
    }

    @PUT
    @RolesAllowed({ConstantesAPI.ROLE_ADMIN})
    public Newspaper updateNewspaper(Newspaper newspaper) {
        return servicesNewspapers.updateNewspaper(newspaper);
    }

    @DELETE
    @Path(ConstantesAPI.ID_PATH_PARAM)
    @RolesAllowed({ConstantesAPI.ROLE_ADMIN})
    public Response deleteNewspaper(@PathParam(ConstantesAPI.ID) String id) {
        servicesNewspapers.deleteNewspaper(servicesNewspapers.get(Integer.parseInt(id)));
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
