package dao.newspapers_api.di;

import com.google.gson.Gson;
import dao.newspapers_api.NewspapersAPI;
import dao.newspapers_api.config.ConfigNewspapersAPI;
import dao.newspapers_api.utils.AuthorizationInterceptor;
import dao.newspapers_api.utils.CacheAuthorization;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;


public class ProducerNewspapersAPI {

    @Produces
    @Singleton
    public Retrofit getRetrofit(ConfigNewspapersAPI configNewspapersAPI, CacheAuthorization cache, Gson gson) {

        OkHttpClient clientOK = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(1, 1, TimeUnit.SECONDS))
                .addInterceptor(new AuthorizationInterceptor(cache))
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
