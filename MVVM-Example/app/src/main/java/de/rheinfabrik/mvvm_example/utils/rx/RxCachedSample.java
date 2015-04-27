package de.rheinfabrik.mvvm_example.utils.rx;

import rx.Observable;

public class RxCachedSample {

    // Public API

    /**
     * Custom operator which behaves similar to sample. The only difference is that it will cache the latest base observable value.
     */
    public static <U, T> Observable<T> cachedSample(Observable<T> base, Observable<U> sampler) {
        return base.lift(new RxCachedSampleWithObservableOperator<>(sampler));
    }
}
