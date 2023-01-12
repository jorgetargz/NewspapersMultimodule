package dao.newspapers_api;

import common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import modelo.Reader;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface ReadersAPI {

    @GET(ConstantesAPI.ENDPOINT_READERS)
    Single<List<Reader>> getReaders();

    @POST(ConstantesAPI.ENDPOINT_READERS)
    Single<Reader> saveReader(@Body Reader reader);

    @PUT(ConstantesAPI.ENDPOINT_READERS)
    Single<Reader> updateReader(@Body Reader reader);

    @DELETE(ConstantesAPI.ENDPOINT_READER_BY_ID)
    Single<Response<Void>> deleteReader(@Path(ConstantesAPI.ID) int id);
}
