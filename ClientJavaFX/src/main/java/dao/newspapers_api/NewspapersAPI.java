package dao.newspapers_api;

import common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import modelo.Newspaper;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface NewspapersAPI {

    @GET(ConstantesAPI.ENDPOINT_NEWSPAPERS)
    Single<List<Newspaper>> getNewspapers();

    @POST(ConstantesAPI.ENDPOINT_NEWSPAPERS)
    Single<Newspaper> saveNewspaper(@Body Newspaper newspaper);

    @PUT(ConstantesAPI.ENDPOINT_NEWSPAPERS)
    Single<Newspaper> updateNewspaper(@Body Newspaper newspaper);

    @DELETE(ConstantesAPI.ENDPOINT_NEWSPAPER_BY_ID)
    Single<Response<Void>> deleteNewspaper(@Path(ConstantesAPI.ID) int id);

}
