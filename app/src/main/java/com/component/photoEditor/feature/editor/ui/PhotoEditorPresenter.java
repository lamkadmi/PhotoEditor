package com.component.photoEditor.feature.editor.ui;


public class PhotoEditorPresenter implements PhotoEditorContrat.Presenter {

    private PhotoEditorContrat.View view;

    public PhotoEditorPresenter() {

    }

    @Override
    public void attachView(PhotoEditorContrat.View mView) {
        this.view = mView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void doUndo() {
        view.onUndo();
    }

    @Override
    public void doRedo() {
        view.onRedo();
    }

    @Override
    public void doSave() {
        view.onSave();
    }

    @Override
    public void doShare() {
        view.onShare();
    }
}
