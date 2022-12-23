package dao.newspapers_api;

import common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import modelo.Newspaper;
import modelo.Reader;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface NewspapersAPI {

    //ENDPOINTS_LOGIN
    @GET(ConstantesAPI.ENDPOINT_LOGIN)
    Single<Reader> getReaderByLogin();

    @POST(ConstantesAPI.ENDPOINT_LOGIN)
    Single<Reader> registerReader(@Body Reader reader);

    //ENDPOINTS_READERS
    @GET(ConstantesAPI.ENDPOINT_READERS)
    Single<List<Reader>> getReaders();

    @POST(ConstantesAPI.ENDPOINT_READERS)
    Single<Reader> saveReader(@Body Reader reader);

    @PUT(ConstantesAPI.ENDPOINT_READERS)
    Single<Reader> updateReader(@Body Reader reader);

    @DELETE(ConstantesAPI.ENDPOINT_READER_BY_ID)
    Single<Response<Object>> deleteReader(@Path(ConstantesAPI.ID) int id);

    //ENDPOINTS_NEWSPAPERS
    @GET(ConstantesAPI.ENDPOINT_NEWSPAPERS)
    Single<List<Newspaper>> getNewspapers();

    @POST(ConstantesAPI.ENDPOINT_NEWSPAPERS)
    Single<Newspaper> saveNewspaper(@Body Newspaper newspaper);

    @PUT(ConstantesAPI.ENDPOINT_NEWSPAPERS)
    Single<Newspaper> updateNewspaper(@Body Newspaper newspaper);

    @DELETE(ConstantesAPI.ENDPOINT_NEWSPAPER_BY_ID)
    Single<Response<Object>> deleteNewspaper(@Path(ConstantesAPI.ID) int id);

}
