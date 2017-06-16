package com.mobsmile.rxdb.db;

import rx.Observable;
import rx.functions.Func0;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import static rx.Observable.empty;

/**
 * This file created by barissarikaya on 5/29/17.
 */
public class Caller<T> {

    private final Func0<Observable<T>> requestFunction;
    private final BehaviorSubject<Boolean> loading = BehaviorSubject.create();
    private final PublishSubject<String> errors = PublishSubject.create();
    private final Cache<T> cache;

    public Caller(Func0<Observable<T>> requestFunction) {
        this(requestFunction, new Cache<>());
    }

    public Caller(Func0<Observable<T>> requestFunction, Cache<T> cache) {
        this.requestFunction = requestFunction;
        this.cache = cache;
    }

    public void refresh() {
        requestFunction.call()
                .take(1)
                .doOnSubscribe(() -> loading.onNext(true))
                .doOnNext(it -> loading.onNext(false))
                .doOnError(it -> loading.onNext(false))
                .doOnError(it -> errors.onNext(mapError(it)))
                .onErrorResumeNext(it -> empty())
                .subscribe(cache::write);
    }

    public Observable<Boolean> observeLoading() {
        return loading;
    }

    public Observable<T> observeData() {
        if (!cache.hasData() && !isLoading()) {
            refresh();
        }
        return cache.observe();
    }

    public Observable<String> observeErrors() {
        return errors;
    }

    public boolean isLoading() {
        return loading.hasValue() && loading.getValue();
    }

    public String mapError(Throwable throwable) {
        if (throwable instanceof Exception) {
            if (throwable.getMessage() != null) {
                return throwable.getMessage();
            }
        }
        return null;
    }
}
