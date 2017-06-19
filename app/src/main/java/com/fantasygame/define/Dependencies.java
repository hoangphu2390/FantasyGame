package com.fantasygame.define;

import android.support.annotation.NonNull;

import com.fantasygame.BuildConfig;
import com.fantasygame.api.ServerAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.lang.reflect.Modifier;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import timber.log.Timber;

import static android.text.TextUtils.isEmpty;
import static com.squareup.okhttp.logging.HttpLoggingInterceptor.Level.BODY;
import static com.squareup.okhttp.logging.HttpLoggingInterceptor.Level.NONE;
import static java.util.concurrent.TimeUnit.SECONDS;

@SuppressWarnings("SpellCheckingInspection")
public final class Dependencies {
    private static final int TIMEOUT_MAXIMUM = 30;
    static ServerAPI serverAPI;

    public static void init() {
        HttpLoggingInterceptor interceptor = provideHttpLoggingInterceptor();
        OkHttpClient client = provideOkHttpClientDefault(interceptor);
        serverAPI = provideRestApi(client);
    }

    public static ServerAPI getServerAPI() {
        return serverAPI;
    }

    static ServerAPI provideRestApi(@NonNull OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create();
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ServerAPI.SERVER_ADDRESS)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return builder.build().create(ServerAPI.class);
    }

    static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Timber.d(message));
        interceptor.setLevel(BuildConfig.DEBUG ? BODY : NONE);
        return interceptor;
    }

    static OkHttpClient provideOkHttpClientDefault(HttpLoggingInterceptor interceptor) {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(interceptor);
        client.interceptors().add(chain -> {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();
//            if (Constants.ACCESS_TOKEN != null && !isEmpty(Constants.ACCESS_TOKEN)) {
//                builder.addHeader("Authorization", String.format("Bearer %s", Constants.ACCESS_TOKEN));
//            }
            return chain.proceed(builder.build());
        });


        client.setConnectTimeout(TIMEOUT_MAXIMUM, SECONDS);
        client.setReadTimeout(TIMEOUT_MAXIMUM, SECONDS);
        client.setWriteTimeout(TIMEOUT_MAXIMUM, SECONDS);

        return client;
    }
}
