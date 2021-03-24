package com.component.photoEditor.base;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.component.photoEditor.R;

import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * @author Fouzi LAMKADMI
 * @version 1.0.0-1
 * @since 02/01/2019
 */
public abstract class BaseActivity extends DaggerAppCompatActivity implements BaseView, BaseFragment.Callback {

    private Toolbar toolbar;

    private Menu menuToolbar;

    private Unbinder mUnBinder;


    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
    }


    @Override
    public void setContentView(int pLayoutResID) {
        super.setContentView(pLayoutResID);
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * La méthode créant les options dans le menu_main.
     *
     * @param pMenu Le menu_main.
     * @return vrai si les options ont bien été crées.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        menuToolbar = pMenu;
        getMenuInflater().inflate(R.menu.menu_main, pMenu);
        return true;
    }

    /**
     * Méthode lancé un appel lors de la sélection d'un item dans le menu_main.
     *
     * @param pItem l'item sélectionné
     * @return vrai si il n'y a pas eu de soucis.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        int id = pItem.getItemId();
        switch (id) {
            case android.R.id.home:
                boolean retourOK = onHomePressed();
                if (!retourOK) {
                    return false;
                }
                break;
        }
        return super.onOptionsItemSelected(pItem);
    }


    /**
     * A surcharger pour capter l'évènement clic sur la flèche "back" en haut à gauche
     *
     * @return true si l'évènement est consommé
     */
    protected boolean onHomePressed() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    public Menu getMenuToolbar() {
        return menuToolbar;
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }
}
