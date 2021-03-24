package com.component.photoEditor.feature.editor.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.component.photoEditor.R;
import com.component.photoEditor.base.BaseActivity;
import com.component.photoEditor.common.EditingToolsAdapter;
import com.component.photoEditor.common.PhotoEditor;
import com.component.photoEditor.common.PhotoEditorView;
import com.component.photoEditor.common.PropertiesPinceauFragment;
import com.component.photoEditor.common.TextEditorDialogFragment;
import com.component.photoEditor.common.TextStyleBuilder;
import com.component.photoEditor.common.ToolType;
import com.component.photoEditor.utils.AppLogger;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoEditorActivity extends BaseActivity implements PhotoEditorContrat.View, EditingToolsAdapter.OnItemSelected,
        PropertiesPinceauFragment.Properties {

    private static final String TAG = PhotoEditorActivity.class.getSimpleName();

    public static final String FILE_PROVIDER_AUTHORITY = "com.component.photoEditor.fileprovider";

    private static final int CAMERA_REQUEST = 52;

    private static final int PICK_REQUEST = 53;

    PhotoEditor mPhotoEditor;

    @BindView(R.id.photoEditorView)
    PhotoEditorView mPhotoEditorView;

    @BindView(R.id.rvConstraintTools)
    RecyclerView mRvTools;

//    @BindView(R.id.imgSave)
//    ImageView save;

    @BindView(R.id.imgClose)
    ImageView close;

    @BindView(R.id.imgUndo)
    ImageView undo;

    @BindView(R.id.imgRedo)
    ImageView redo;

    @BindView(R.id.fab_menu)
    SpeedDialView menu;

    @BindView(R.id.txtCurrentTool)
    TextView mTxtCurrentTool;

    private PropertiesPinceauFragment mPropertiesPinceauFragment;

    private EditingToolsAdapter mEditingToolsAdapter = new EditingToolsAdapter(this);

    @Inject
    PhotoEditorContrat.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.attachView(this);

        LinearLayoutManager llmTools = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvTools.setLayoutManager(llmTools);
        mRvTools.setAdapter(mEditingToolsAdapter);

        mPropertiesPinceauFragment = new PropertiesPinceauFragment();
        mPropertiesPinceauFragment.setPropertiesChangeListener(this);

        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(true) // set flag to make text scalable when pinch
                //.setDefaultTextTypeface(mTextRobotoTf)
                //.setDefaultEmojiTypeface(mEmojiTypeFace)
                .build(); // build photo editor sdk

        menu.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_no_label, R.drawable.ic_paint)
                        .create());

        menu.inflate(R.menu.menu_actions);

        menu.setOnActionSelectedListener(actionItem -> {
            switch (actionItem.getId()) {
                case R.id.fab_no_label :
                    menu.close() ;// To close the Speed Dial with animation
                    break ;// false will close it without animation
                case R.id.action_cammera:
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    break;
                case R.id.action_gallery:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST);
                    break;
                case R.id.action_share:
                    break;
            }
            return false;
        });

        undo.setOnClickListener(v -> mPresenter.doUndo());
        redo.setOnClickListener(v -> mPresenter.doRedo());


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST:
                    mPhotoEditor.clearAllViews();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mPhotoEditorView.getSource().setImageBitmap(photo);
                    break;
                case PICK_REQUEST:
                    try {
                        mPhotoEditor.clearAllViews();
                        Uri uri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        mPhotoEditorView.getSource().setImageBitmap(bitmap);
                    } catch (IOException e) {
                        AppLogger.e(e,e.getMessage());
                    }
                    break;
            }
        }
    }


//    @OnClick(R.id.imgGallery)
//    void onPickPhoto(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST);
//    }
//
//    @OnClick(R.id.imgCamera)
//    void onStartCamera(){
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);
//    }





    @Override
    public void onToolSelected(ToolType toolType) {
        switch (toolType) {
            case PINCEAU:
                mPhotoEditor.setPinceauMode(true);
                //mTxtCurrentTool.setText("Pinceau");
                mPropertiesPinceauFragment.show(getSupportFragmentManager(), mPropertiesPinceauFragment.getTag());
                break;
            case TEXT:
                TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(this);
                textEditorDialogFragment.setOnTextEditorListener((inputText, colorCode) -> {
                    final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                    styleBuilder.withTextColor(getApplicationContext(), colorCode);
                    mPhotoEditor.addText(inputText, styleBuilder);
                    //mTxtCurrentTool.setText("Text");
                });
                break;
            case GOMME:
                mPhotoEditor.setGommeMode();
               // mTxtCurrentTool.setText("Gomme");
                break;
        }
    }

    @Override
    public void onColorChanged(int colorCode) {
        mPhotoEditor.setPinceauColor(colorCode);
        mTxtCurrentTool.setText("Pinceau");
    }

    @Override
    public void onOpacityChanged(int opacity) {
        mPhotoEditor.setOpacity(opacity);
        mTxtCurrentTool.setText("Pinceau");
    }

    @Override
    public void onBrushSizeChanged(int brushSize) {
        mPhotoEditor.setPinceauSize(brushSize);
        mTxtCurrentTool.setText("Pinceau");
    }

    @OnClick(R.id.imgClose)
    void closeDrawingClick() {
        this.finish();
    }

    @Override
    public void onUndo() {
        mPhotoEditor.undo();
    }

    @Override
    public void onRedo() {
        mPhotoEditor.redo();
    }

    @Override
    public void onSave() {

    }

    @Override
    public void onShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, buildFileProviderUri(mSaveImageUri));
        startActivity(Intent.createChooser(intent, getString(R.string.msg_share_image)));
    }

    private Uri buildFileProviderUri(@NonNull Uri uri) {
        return FileProvider.getUriForFile(this,
                FILE_PROVIDER_AUTHORITY,
                new File(uri.getPath()));
    }
}
