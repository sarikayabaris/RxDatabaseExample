package com.mobsmile.rxdb.db;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * This file created by barissarikaya on 5/29/17.
 */
public class Cache<T> {
    private T data;

    private PublishSubject<T> dataSubject = PublishSubject.create();

    /**
     * Write a new value to the cache. Current observers will be notified of the new value.
     * @param data the new value to write
     */
    public void write(T data) {
        this.data = data;
        dataSubject.onNext(data);
    }

    /**
     * "Silently" write a new value to the cache. Current observers will NOT be notified of the new
     * value, however any future observers will see the new value when they subscribe.
     *
     * @param data the new value to write
     */
    public void writeSilently(T data) {
        this.data = data;
    }

    /**
     * Read the current value from the cache.
     * @return The current value stored in the cache.
     */
    public T read() {
        return data;
    }

    /**
     * Observe the values of the cache.If the cache already has a value, it will be emitted right
     * away. Any future values will also be emitted (unless they are written silently).
     * @return Observable stream of cached values
     */
    public Observable<T> observe() {
        if (hasData()) {
            return dataSubject.asObservable().startWith(data);
        } else {
            return dataSubject.asObservable();
        }
    }

    /**
     * Check if the cache currently has a cached value
     * @return true if and only if the cache has a stored value
     */
    public boolean hasData() {
        return data != null;
    }

    /**
     * Clear the stored value.
     */
    public void clear() {
        data = null;
    }
}
