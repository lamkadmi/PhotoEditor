package com.component.photoEditor.feature.editor.ui;

import com.component.photoEditor.base.BasePresenter;
import com.component.photoEditor.base.BaseView;

public interface PhotoEditorContrat {

    interface View extends BaseView {
        void onUndo();
        void onRedo();
        void onSave();
        void onShare();
    }

    interface Presenter extends BasePresenter<View> {
        void doUndo();
        void doRedo();
        void doSave();
        void doShare();
    }

}
