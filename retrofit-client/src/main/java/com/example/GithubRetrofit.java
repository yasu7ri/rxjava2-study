package com.example;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yasu7ri on 2018/01/28.
 */
public class GithubRetrofit {
    public Retrofit build(){
        // ログ出力設定したokhttpのクライアント
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        httpClient.addInterceptor(logging);
        OkHttpClient client = httpClient.build();

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .client(client)//カスタマイズしたokhttpのクライアントの設定
                .build();
    }
}
