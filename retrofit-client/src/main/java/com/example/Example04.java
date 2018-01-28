package com.example;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import java.io.IOException;

/**
 * Created by yasu7ri on 2018/01/29.
 */
public class Example04 {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("--- start ---");

        Retrofit retrofit = new GithubRetrofit().build();
        GitHubService service = retrofit.create(GitHubService.class);

        Observable.zip(
                // ログからだとuserとlistReposが非同期に呼ばれているように見えないはなぜ
                // -> 各サービスのObserableを別スレッドで行う指定をすると非同期に呼び出される
                service.User("yasu7ri").subscribeOn(Schedulers.io()),
                service.ListRepos("yasu7ri").subscribeOn(Schedulers.io()),
                (user, repos) -> "[" + Thread.currentThread().getName() + "]" + user.getName() + " has " + repos.size() + " repositories.")
                // Observableがデータを生産するスレッドの指定
                .subscribeOn(Schedulers.io())
                // Observableが吐き出したデータを受け取って加工するスレッドの指定
                //.observeOn(Schedulers.io())
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
