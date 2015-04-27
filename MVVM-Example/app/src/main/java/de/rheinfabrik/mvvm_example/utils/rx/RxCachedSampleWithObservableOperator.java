package de.rheinfabrik.mvvm_example.utils.rx;

import java.util.concurrent.atomic.AtomicReference;

import rx.Observable;
import rx.Subscriber;
import rx.observers.SerializedSubscriber;

/**
 * Custom operator which behaves similar to sample. The only difference is that it will cache the latest base observable value.
 */
public class RxCachedSampleWithObservableOperator<T, U> implements Observable.Operator<T, T> {

    // Members

    static final Object EMPTY_TOKEN = new Object();
    final Observable<U> sampler;

    // Constructor

    public RxCachedSampleWithObservableOperator(Observable<U> sampler) {
        this.sampler = sampler;
    }

    // Observable.Operator

    @Override
    public Subscriber<? super T> call(Subscriber<? super T> child) {
        final SerializedSubscriber<T> s = new SerializedSubscriber<>(child);
        final AtomicReference<Object> value = new AtomicReference<>(EMPTY_TOKEN);

        Subscriber<U> samplerSub = new Subscriber<U>(child) {

            @Override
            public void onNext(U t) {
                Object localValue = value.get();
                if (localValue != EMPTY_TOKEN) {
                    @SuppressWarnings("unchecked")
                    T v = (T) localValue;
                    s.onNext(v);
                }
            }

            @Override
            public void onError(Throwable e) {
                s.onError(e);
                unsubscribe();
            }

            @Override
            public void onCompleted() {
                s.onCompleted();
                unsubscribe();
            }

        };

        Subscriber<T> result = new Subscriber<T>(child) {

            @Override
            public void onNext(T t) {
                value.set(t);
            }

            @Override
            public void onError(Throwable e) {
                s.onError(e);
                unsubscribe();
            }

            @Override
            public void onCompleted() {
                s.onCompleted();
                unsubscribe();
            }
        };

        sampler.unsafeSubscribe(samplerSub);

        return result;
    }
}
