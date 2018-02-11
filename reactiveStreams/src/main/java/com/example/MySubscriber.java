package com.example;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by yasu7ri on 2018/02/08.
 */
public class MySubscriber<T> implements Subscriber<T> {
    private Subscription subscription;

    public void onSubscribe(Subscription subscription) {
        System.out.println("  --> onSubscribe");
        this.subscription = subscription;
        this.subscription.request(Long.MAX_VALUE);
    }

    public void onNext(T t) {
        System.out.println("  --> onNext");

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onError(Throwable throwable) {
        System.out.println("onError!!!");
        throwable.printStackTrace();
    }

    public void onComplete() {
        System.out.println("  --> onComplete");
    }
}
