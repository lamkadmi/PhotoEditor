package com.component.photoEditor.di;

import android.content.Context;

import com.component.photoEditor.base.BaseApplication;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainModule {

    @Binds
    @Singleton
    abstract Context provideApplicationContext(BaseApplication app);

}
