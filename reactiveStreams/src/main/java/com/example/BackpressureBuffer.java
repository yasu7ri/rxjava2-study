package com.example;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by yasu7ri on 2018/02/12.
 */
public class BackpressureBuffer {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(100L, TimeUnit.MILLISECONDS)
                .take(10)
                .doOnSubscribe(s -> System.out.println("<-- subscribe"))
                .doOnNext(data -> System.out.println("Flowable generated data:" + data))
                .onBackpressureBuffer();

        flowable.doOnRequest(req -> System.out.println("<-- request:" + req))
                .observeOn(Schedulers.computation(), false, 2)
                .doOnRequest(req -> System.out.println("  <-- request:" + req))
                .subscribe(new MySubscriber<Long>());

        Thread.sleep(11_000L);
    }
}
