package dao.newspapers_api.di;

import com.google.gson.Gson;
import dao.common.Constantes;
import dao.newspapers_api.NewspapersAPI;
import dao.newspapers_api.config.ConfigNewspapersAPI;
import dao.newspapers_api.utils.JavaNetCookieJar;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;


public class ProducerNewspapersAPI {

    @Produces
    @Singleton
    public Retrofit getRetrofit(ConfigNewspapersAPI configNewspapersAPI, Gson gson) {

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient clientOK = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(1, 1, TimeUnit.SECONDS))
                .addNetworkInterceptor(chain -> {
                            Request original = chain.request();
                            Request request = original.newBuilder()
                                    .header(Constantes.PROTOCOL_REQUEST, Constantes.HTTP_2_0)
                                    .header(Constantes.ACCEPT, Constantes.APPLICATION_JSON)
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                )
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        return new Retrofit.Builder()
                .baseUrl(configNewspapersAPI.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(clientOK)
                .build();

    }

    @Produces
    @Singleton
    public NewspapersAPI getNewspapersAPI(Retrofit retrofit) {
        return retrofit.create(NewspapersAPI.class);
    }
}
