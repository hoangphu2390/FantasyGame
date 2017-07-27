package com.fantasygame.define;

import android.app.Application;
import android.support.annotation.NonNull;

import com.fantasygame.BuildConfig;
import com.fantasygame.api.ServerAPI;
import com.fantasygame.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.File;
import java.io.IOException;
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
        client.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
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

        //setup cache
        File httpCacheDirectory = new File(FantatsyGame.getAppContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        client.setCache(cache);

        return client;
    }

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (Utils.isNetworkAvailable(FantatsyGame.getAppContext())) {
                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
}
