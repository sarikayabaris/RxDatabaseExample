package com.mobsmile.rxdb.ui.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Base Presenter class, to be extended by all presenters.
 * A view/fragment/activity can be attached to the presenter, and the presenter will take care of the business logic for this view.
 * The presenters follow a "Reactive" MVP pattern as described here: https://medium.com/@mvarnagiris/reactive-mvp-part-1-b751ce3e3246.
 * This presenter allows Rx subscriptions to be made when the view is attached, which will all then be unsubscribed in bulk when the view is detached.
 */
public abstract class Presenter<V extends Presenter.View> {

    private V mView;
    private CompositeSubscription mSubscriptions;

    /**
     * Attach a view to the presenter. This should be called when the view is attached to the screen and this view is now considered "active"
     *
     * @param view The view to attach - this will be a View, Fragment or Activity which implements this presenter's View interface
     */
    public void attach(V view) {
        verifyThatViewIsNotAttached(view);
        mView = view;
        mSubscriptions = new CompositeSubscription();
        onViewAttached(view);
    }

    /**
     * Detach a view from the presenter. This should be called when the view is detached from the screen and this view is now considered "inactive"
     *
     * @param view The view to attach - this will be a View, Fragment or Activity which implements this presenter's View interface
     */
    public void detach(V view) {
        verifyThatViewIsAttached(view);
        mView = null;
        mSubscriptions.unsubscribe();
        onViewDetached(null);
    }

    /**
     * To be overridden in subclasses. This is called when a view is attached to the presenter.
     * Any initial actions can be performed here. Additionally, Rx subscriptions should be made here.
     * It is very important that these are done through calling {@link #addSubscription(Subscription)},
     * as this will ensure that they are unsubscribed when the view is detached.
     * If {@link #addSubscription(Subscription)} is not used, the view will be leaked after it is
     * detached and the subscription will last indefinitely.
     *
     * @param view The view that has been attached - this will be a View, Fragment or Activity which implements this presenter's View interface
     */
    protected void onViewAttached(V view) {

    }

    /**
     * To be overridden in subclasses. This is called when a view is detached from the presenter.
     *
     * @param view The view that has been detached - this will be a View, Fragment or Activity which implements this presenter's View interface
     */
    protected void onViewDetached(V view) {

    }

    /**
     * Add an Rx subscription to the list of subscriptions being tracked. These will all be unsubscribed when the view is detached.
     *
     * @param subscription The subscription that needs to be tracked
     */
    protected void addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
    }

    private void verifyThatViewIsNotAttached(V view) {
        if (mView != null) {
            throw new IllegalStateException("Cannot attach " + view + " as " + mView + " is already attached.");
        }
    }

    private void verifyThatViewIsAttached(V view) {
        if (mView != view) {
            throw new IllegalStateException("Cannot detach " + view + " as the currently attached view is " + mView);
        }
    }

    /**
     * A type declaration for the view interface. Each Presenter subclass should include its own view
     * interface which extends this one. The view interface will expose observables for user input,
     * as well as providing methods to update the UI.
     * The view interface can then be implemented by an Activity, Fragment or custom View.
     */
    public interface View {

    }
}
