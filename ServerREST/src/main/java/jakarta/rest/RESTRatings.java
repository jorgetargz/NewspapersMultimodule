package jakarta.rest;

import common.ConstantesAPI;
import domain.services.ServicesRatings;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.ArticleRating;

import java.util.List;

@Path(ConstantesAPI.PATH_RATINGS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RESTRatings {

    private final ServicesRatings servicesRatings;

    @Inject
    public RESTRatings(ServicesRatings servicesRatings) {
        this.servicesRatings = servicesRatings;
    }

    @GET
    public List<ArticleRating> getAllRatings() {
        return servicesRatings.getRatings();
    }

    @GET
    @Path(ConstantesAPI.READER_PATH + ConstantesAPI.ID_PATH_PARAM)
    public List<ArticleRating> getRatingsByReaderId(@PathParam(ConstantesAPI.ID) String id) {
        return servicesRatings.getRatingsByReaderId(id);
    }

    @GET
    @Path(ConstantesAPI.ID_PATH_PARAM)
    public ArticleRating getRating(@PathParam(ConstantesAPI.ID) String id) {
        return servicesRatings.getRating(id);
    }

    @POST
    public Response saveRating(ArticleRating articleRating) {
        return Response.status(Response.Status.CREATED)
                .entity(servicesRatings.saveRating(articleRating))
                .build();
    }

    @PUT
    public ArticleRating updateRating(ArticleRating articleRating) {
        return servicesRatings.updateRating(articleRating);
    }

    @DELETE
    @Path(ConstantesAPI.ID_PATH_PARAM)
    public Response deleteRating(@PathParam(ConstantesAPI.ID) String id) {
        servicesRatings.deleteRating(servicesRatings.getRating(id));
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path(ConstantesAPI.NEWSPAPER_PATH + ConstantesAPI.ID_PATH_PARAM)
    public Response deleteAllRatingsByNewspaper(@PathParam(ConstantesAPI.ID) String id) {
        servicesRatings.deleteAllRatingsByNewspaper(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
