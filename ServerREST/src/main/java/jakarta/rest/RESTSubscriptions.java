package jakarta.rest;


import common.ConstantesAPI;
import domain.services.ServicesSubscriptions;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Subscription;

import java.util.List;

@Path(ConstantesAPI.PATH_SUBSCRIPTIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RESTSubscriptions {

    private final ServicesSubscriptions servicesSubscriptions;

    @Inject
    public RESTSubscriptions(ServicesSubscriptions servicesSubscriptions) {
        this.servicesSubscriptions = servicesSubscriptions;
    }

    @GET
    public List<Subscription> getAllSubscriptions() {
        return servicesSubscriptions.getSubscriptions();
    }

    @GET
    @Path(ConstantesAPI.NEWSPAPER_PATH + ConstantesAPI.ID_PATH_PARAM)
    public List<Subscription> getSubscriptionsByNewspaper(@PathParam(ConstantesAPI.ID) String id) {
        return servicesSubscriptions.getSubscriptionsByNewspaper(id);
    }

    @GET
    @Path(ConstantesAPI.NEWSPAPER_PATH + ConstantesAPI.ID_PATH_PARAM + ConstantesAPI.OLDEST_PATH)
    public List<Subscription> getOldestSubscriptionsByNewspaper(@PathParam(ConstantesAPI.ID) String id) {
        return servicesSubscriptions.getOldestSubscriptionsByNewspaper(id);
    }

    @GET
    @Path(ConstantesAPI.READER_PATH + ConstantesAPI.ID_PATH_PARAM)
    public List<Subscription> getSubscriptionsByReader(@PathParam(ConstantesAPI.ID) String id) {
        return servicesSubscriptions.getSubscriptionsByReader(id);
    }

    @GET
    @Path(ConstantesAPI.ID_PATH)
    public Subscription getSubscription(@QueryParam(ConstantesAPI.ID_READER) String idReader, @QueryParam(ConstantesAPI.ID_NEWSPAPER) String idNewspaper) {
        return servicesSubscriptions.getSubscription(idReader, idNewspaper);
    }

    @POST
    public Response saveSubscription(Subscription subscription) {
        return Response.status(Response.Status.CREATED)
                .entity(servicesSubscriptions.addSubscription(subscription))
                .build();
    }

    @PUT
    public Subscription cancelSubscription(Subscription subscription) {
        return servicesSubscriptions.cancelSubscription(subscription);
    }

    @DELETE
    @Path(ConstantesAPI.ID_PATH)
    public Response deleteSubscription(@QueryParam(ConstantesAPI.ID_READER) String idReader, @QueryParam(ConstantesAPI.ID_NEWSPAPER) String idNewspaper) {
        servicesSubscriptions.deleteSubscription(servicesSubscriptions.getSubscription(idReader, idNewspaper));
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
