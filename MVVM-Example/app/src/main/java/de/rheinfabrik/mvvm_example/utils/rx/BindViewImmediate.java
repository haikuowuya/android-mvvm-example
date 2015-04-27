package de.rheinfabrik.mvvm_example.utils.rx;


import android.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Copied from current develop of rxandroid but on immediate scheduler.
 */
public class BindViewImmediate {

    // Public Api

    /**
     * Bind view on immediate scheduler.
     */
    public static <T> Observable<T> bindViewImmediate(View view, Observable<T> source) {
        if (view == null || source == null)
            throw new IllegalArgumentException("View and Observable must be given");
        return source.takeUntil(Observable.create(new OnSubscribeViewDetachedFromWindowFirst(view))).observeOn(Schedulers.immediate());
    }

    // Private Api

    private static class OnSubscribeViewDetachedFromWindowFirst implements Observable.OnSubscribe<View> {
        private final View view;

        public OnSubscribeViewDetachedFromWindowFirst(View view) {
            this.view = view;
        }

        @Override
        public void call(final Subscriber<? super View> subscriber) {
            final SubscriptionAdapter adapter = new SubscriptionAdapter(subscriber, view);
            subscriber.add(adapter);
            view.addOnAttachStateChangeListener(adapter);
        }

        // This could be split into a couple of anonymous classes.
        // We pack it into one for the sake of memory efficiency.
        private class SubscriptionAdapter implements View.OnAttachStateChangeListener,
                Subscription {
            private Subscriber<? super View> subscriber;
            private View view;

            public SubscriptionAdapter(Subscriber<? super View> subscriber, View view) {
                this.subscriber = subscriber;
                this.view = view;
            }

            @Override
            public void onViewAttachedToWindow(View v) {
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (!isUnsubscribed()) {
                    Subscriber<? super View> originalSubscriber = subscriber;
                    unsubscribe();
                    originalSubscriber.onNext(v);
                    originalSubscriber.onCompleted();
                }
            }

            @Override
            public void unsubscribe() {
                if (!isUnsubscribed()) {
                    view.removeOnAttachStateChangeListener(this);
                    view = null;
                    subscriber = null;
                }
            }

            @Override
            public boolean isUnsubscribed() {
                return view == null;
            }
        }
    }
}
