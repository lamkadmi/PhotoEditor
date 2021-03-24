package com.component.photoEditor.base;


import com.component.photoEditor.di.DaggerPhotoEditorComponent;
import com.component.photoEditor.utils.AppLogger;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;


public class BaseApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();


        AppLogger.init();
    }

    @Override
    protected AndroidInjector<BaseApplication> applicationInjector() {
        return DaggerPhotoEditorComponent
                .builder().create(this);
    }

}
