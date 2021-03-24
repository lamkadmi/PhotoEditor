package com.component.photoEditor.feature.editor.di;

import com.component.photoEditor.di.ActivityScope;
import com.component.photoEditor.feature.editor.ui.PhotoEditorContrat;
import com.component.photoEditor.feature.editor.ui.PhotoEditorPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PhotoEditorModule {


    @Provides
    @ActivityScope
    PhotoEditorContrat.Presenter provideMainContrat() {
        return new PhotoEditorPresenter();
    }


}
