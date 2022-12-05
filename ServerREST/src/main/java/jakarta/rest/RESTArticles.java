package jakarta.rest;

import common.ConstantesAPI;
import domain.services.ServicesArticles;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.*;

import java.util.List;

@Path(ConstantesAPI.PATH_ARTICLES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RESTArticles {

    private final ServicesArticles servicesArticles;

    @Inject
    public RESTArticles(ServicesArticles servicesArticles) {
        this.servicesArticles = servicesArticles;
    }

    @GET
    public List<Article> getAllArticles() {
        return servicesArticles.getArticles();
    }

    @GET
    @Path(ConstantesAPI.ID_PATH_PARAM)
    public ArticleQuery1 getArticleById(@PathParam(ConstantesAPI.ID) String id) {
        return servicesArticles.getArticleInfo(id);
    }

    @GET
    @Path(ConstantesAPI.READER_PATH + ConstantesAPI.ID_PATH_PARAM)
    public List<Article> getArticlesAvailableForReader(@PathParam(ConstantesAPI.ID) String id) {
        return servicesArticles.getArticlesAvailableForReader(id);
    }

    @GET
    @Path(ConstantesAPI.NEWSPAPER_PATH + ConstantesAPI.ID_PATH_PARAM)
    public List<Article> getArticlesByNewspaper(@PathParam(ConstantesAPI.ID) String id) {
        return servicesArticles.getArticlesByNewspaper(id);
    }

    @GET
    @Path(ConstantesAPI.NEWSPAPER_PATH + ConstantesAPI.ID_PATH_PARAM + ConstantesAPI.BAD_RATINGS_PATH)
    public List<ArticleQuery3> getArticlesByNewspaperWithBadRatings(@PathParam(ConstantesAPI.ID) String id) {
        return servicesArticles.getArticlesByNewspaperWithBadRatings(id);
    }

    @GET
    @Path(ConstantesAPI.TYPE_PATH + ConstantesAPI.ID_PATH_PARAM)
    public List<Article> getArticlesByTypeIdWithoutNewspaperName(@PathParam(ConstantesAPI.ID) String id) {
        return servicesArticles.getArticlesByType(id);
    }

    @GET
    @Path(ConstantesAPI.TYPE_PATH + ConstantesAPI.ID_PATH_PARAM + ConstantesAPI.NEWSPAPER_PATH)
    public List<ArticleQuery2> getArticlesByTypeIdWithNewspaperName(@PathParam(ConstantesAPI.ID) String id) {
        return servicesArticles.getArticlesByTypeWithNewspaper(id);
    }

    @GET
    @Path(ConstantesAPI.ARTICLE_TYPES_PATH)
    public List<ArticleType> getArticlesTypes() {
        return servicesArticles.getArticleTypes();
    }

    @GET
    @Path(ConstantesAPI.ARTICLE_TYPES_PATH + ConstantesAPI.ID_PATH_PARAM)
    public ArticleType getArticleTypeById(@PathParam(ConstantesAPI.ID) String id) {
        return servicesArticles.getArticleType(id);
    }

    @POST
    public Response saveArticle(Article article) {
        return Response.status(Response.Status.CREATED)
                .entity(servicesArticles.saveArticle(article))
                .build();
    }

    @POST
    @Path(ConstantesAPI.ARTICLE_TYPES_PATH)
    public Response saveArticleType(ArticleType articleType) {
        return Response.status(Response.Status.CREATED)
                .entity(servicesArticles.saveArticleType(articleType))
                .build();
    }

    @PUT
    public Article updateArticle(Article article) {
        return servicesArticles.updateArticle(article);
    }

    @PUT
    @Path(ConstantesAPI.ARTICLE_TYPES_PATH)
    public ArticleType updateArticleType(ArticleType articleType) {
        return servicesArticles.updateArticleType(articleType);
    }

    @DELETE
    @Path(ConstantesAPI.ID_PATH_PARAM)
    public Response deleteArticle(@PathParam(ConstantesAPI.ID) String id) {
        servicesArticles.deleteArticle(servicesArticles.getArticle(id));
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path(ConstantesAPI.ARTICLE_TYPES_PATH + ConstantesAPI.ID_PATH_PARAM)
    public Response deleteArticleType(@PathParam(ConstantesAPI.ID) String id) {
        servicesArticles.deleteArticleType(servicesArticles.getArticleType(id));
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}