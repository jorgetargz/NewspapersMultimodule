package dao.newspapers_api;

import common.ConstantesAPI;
import dao.common.Constantes;
import io.reactivex.rxjava3.core.Single;
import modelo.Reader;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginAPI {

    @GET(ConstantesAPI.ENDPOINT_LOGIN)
    Single<Reader> getReaderByLogin(@Header(Constantes.AUTHORIZATION) String authorization);

    @GET(ConstantesAPI.ENDPOINT_LOGOUT)
    Single<Response<Void>> logout(@Header(Constantes.AUTHORIZATION) String authorization);

    @POST(ConstantesAPI.ENDPOINT_LOGIN)
    Single<Reader> registerReader(@Body Reader reader);
}
