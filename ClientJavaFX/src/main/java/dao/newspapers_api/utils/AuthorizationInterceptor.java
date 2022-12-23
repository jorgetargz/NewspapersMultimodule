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
        Request original = chain.request();
        Request request;

        // Send request with bearer auth if we have a token cached or with basic auth if we don't have a token cached
        if (ca.getJwtAuth() == null) {
            request = original.newBuilder()
                    .header(Constantes.AUTHORIZATION, Credentials.basic(ca.getUser(), ca.getPassword())).build();
        } else {
            request = original.newBuilder()
                    .header(Constantes.AUTHORIZATION, ca.getJwtAuth()).build();
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
