package com.component.photoEditor.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class RxAsyncTask<T> implements LifecycleObserver {

    private Disposable disposable;
    private Lifecycle lifecycleOwner;
    private Lifecycle.State event = Lifecycle.State.DESTROYED;

    public abstract Observable<T> doInBackGround();

    public void onPreExecute() {
    }

    public void onPostExecute(T data) {
    }

    private void doExecute() {
        lifecycleOwner.addObserver(this);
        disposable = Observable.defer((Callable<ObservableSource<T>>) () -> doInBackGround()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> onPreExecute()).subscribeWith(new DisposableObserver<T>() {
                    @Override
                    public void onNext(T t) {
                        onPostExecute(t);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public Observable<T> createTask(final T data) {
        return Observable.defer((Callable<ObservableSource<T>>) () -> Observable.just(data));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    private void destroy() {
        if (lifecycleOwner.getCurrentState() == event) {
            lifecycleOwner.removeObserver(this);
            disposable.dispose();
        }
    }

    public void execute(AppCompatActivity activity) {
        this.event = Lifecycle.State.DESTROYED;
        lifecycleOwner = activity.getLifecycle();
        doExecute();
    }

    public void execute(AppCompatActivity activity, Lifecycle.State state) {
        this.event = state;
        lifecycleOwner = activity.getLifecycle();
        doExecute();
    }

    public void execute(Fragment fragment) {
        this.event = Lifecycle.State.DESTROYED;
        lifecycleOwner = fragment.getLifecycle();
        doExecute();
    }

    public void execute(Fragment fragment, Lifecycle.State state) {
        this.event = state;
        lifecycleOwner = fragment.getLifecycle();
        doExecute();
    }
}
