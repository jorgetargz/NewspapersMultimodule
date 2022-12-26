package dao.newspapers_api.utils;

import dao.common.Constantes;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthorizationInterceptor implements Interceptor {


    private final CacheAuthorization ca;


    public AuthorizationInterceptor(CacheAuthorization ca) {
        this.ca = ca;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request().newBuilder()
                .addHeader(Constantes.PROTOCOL_REQUEST, Constantes.HTTP_2_0)
                .addHeader(Constantes.ACCEPT, Constantes.APPLICATION_JSON)
                .method(chain.request().method(), chain.request().body())
                .build();
        Request request;

        // Send request with the cached credentials if present
        if (ca.getJwtAuth() == null) {
            if (ca.getUser() == null || ca.getPassword() == null) {
                request = original;
            } else {
                String basicAuth = Credentials.basic(ca.getUser(), ca.getPassword());
                request = original.newBuilder()
                        .header(Constantes.AUTHORIZATION, basicAuth)
                        .build();
            }
        } else {
            request = original.newBuilder()
                    .header(Constantes.AUTHORIZATION, ca.getJwtAuth())
                    .build();
        }

        // Save the JWT in the cache
        Response response = chain.proceed(request);
        if (response.header(Constantes.AUTHORIZATION) != null)
            ca.setJwtAuth(response.header(Constantes.AUTHORIZATION));

        // Re-authenticate if the token is expired
        String tokenExpiredHeader = response.header(Constantes.TOKEN_EXPIRED);
        if (tokenExpiredHeader != null && tokenExpiredHeader.equals(Constantes.TRUE)) {
            response.close();
            request = original.newBuilder()
                    .header(Constantes.AUTHORIZATION, Credentials.basic(ca.getUser(), ca.getPassword())).build();
            response = chain.proceed(request);
            if (response.header(Constantes.AUTHORIZATION) != null)
                ca.setJwtAuth(response.header(Constantes.AUTHORIZATION));
            return response;
        }

        return response;
    }
}
