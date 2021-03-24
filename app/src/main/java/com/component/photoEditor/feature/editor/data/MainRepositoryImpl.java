package com.component.photoEditor.feature.editor.data;


import com.component.photoEditor.feature.editor.data.local.MainLocalDataSource;
import com.component.photoEditor.feature.editor.data.remote.MainRemoteDataSource;

import javax.inject.Inject;

public class MainRepositoryImpl implements MainRepository {

    private MainLocalDataSource mLocal;

    private MainRemoteDataSource mRemote;

    @Inject
    public MainRepositoryImpl(MainLocalDataSource mLocal, MainRemoteDataSource mRemote) {
        this.mLocal = mLocal;
        this.mRemote = mRemote;
    }
}
