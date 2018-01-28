package com.example;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import java.io.IOException;

/**
 * Created by yasu7ri on 2018/01/28.
 */
public class Example03 {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("--- start ---");

        Retrofit retrofit = new GithubRetrofit().build();
        GitHubService service = retrofit.create(GitHubService.class);

        Observable.zip(
                // TODO ログからだとuserとlistReposが非同期に呼ばれているように見えないはなぜ
                service.User("yasu7ri"), service.ListRepos("yasu7ri"),
                (user, repos) -> "[" + Thread.currentThread().getName() + "]" + user.getName() + " has " + repos.size() + " repositories.")
                // Observableがデータを生産するスレッドの指定
                .subscribeOn(Schedulers.computation())
                // Observableが吐き出したデータを受け取って加工するスレッドの指定
                .observeOn(Schedulers.io())
                .subscribe(
                        s -> System.out.println(Thread.currentThread().getName() + ":" + s),
                        throwable -> System.out.println(throwable.getMessage()),
                        () -> System.out.println("complete")
                );

        // mainスレッドとは別のスレッドで事項されているので一旦スリープして待つ
        Thread.sleep(5000L);

        System.out.println("--- end ---");
    }

}
