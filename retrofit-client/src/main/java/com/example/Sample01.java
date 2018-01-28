package com.example;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class Sample01 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        service.ListRepos("srym")
                .observeOn(Schedulers.io())
                // .subscribeOn(Schedulers.io())
                .subscribe(
                        list -> list.stream().forEach(repo -> System.out.println(Thread.currentThread().getName() + ":" + repo.getId())),
                        throwable -> System.out.println(throwable.getMessage()),
                        () -> System.out.println("complete")
                );

        // mainスレッドとは別のスレッドで事項されているので一旦スリープして待つ
        Thread.sleep(5000L);
    }
}
