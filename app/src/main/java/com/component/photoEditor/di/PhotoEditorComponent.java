package com.component.photoEditor.di;

import com.component.photoEditor.base.BaseApplication;
import com.component.photoEditor.feature.editor.di.PhotoEditorModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        MainModule.class,
        ActivityModule.class,
        PhotoEditorModule.class
})

public interface PhotoEditorComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseApplication> {

    }
}
