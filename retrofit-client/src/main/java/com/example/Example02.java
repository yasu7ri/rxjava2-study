package com.example;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import java.io.IOException;

/**
 * Created by yasu7ri on 2018/01/28.
 */
public class Example02 {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("--- start ---");

        Retrofit retrofit = new GithubRetrofit().build();
        GitHubService service = retrofit.create(GitHubService.class);

        service.User("yasu7ri")
                .zipWith(service.ListRepos("yasu7ri"), (user, repos) -> user.getName() + " has " + repos.size() + "repositories")
                // Observableがデータを生産するスレッドの指定
                .subscribeOn(Schedulers.io())
                // Observableが吐き出したデータを受け取って加工するスレッドの指定
                //.observeOn(Schedulers.trampoline())
                .subscribe(System.out::println,
                        throwable -> System.out.println(throwable.getMessage()),
                        () -> System.out.println("complete")
                );

        // mainスレッドとは別のスレッドで事項されているので一旦スリープして待つ
        Thread.sleep(5000L);

        System.out.println("--- end ---");
    }
}
