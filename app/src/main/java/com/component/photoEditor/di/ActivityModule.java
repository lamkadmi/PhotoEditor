package com.component.photoEditor.di;

import com.component.photoEditor.feature.editor.di.PhotoEditorModule;
import com.component.photoEditor.feature.editor.ui.PhotoEditorActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {PhotoEditorModule.class})
    abstract PhotoEditorActivity contributesPhotoEditorActivity();
}
