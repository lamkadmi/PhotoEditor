package com.component.photoEditor.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.component.photoEditor.R;

public class DialogFactory {

    private DialogFactory() {
        // empty constructor
    }

    public static Dialog createAlerteDialog(Context context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle("Erreur")
                .setMessage(message)
                .setPositiveButton("OK", null);
        return alertDialog.create();
    }

    /**
     * Affichage d'une popup avec les boutons Oui et Non
     * pour calculer son itinéraire.
     *
     * @param pTitle   Le titre de la popup.
     * @param pMessage Le pMessage à afficher.
     */
    public static void affichePopupOuiNon(Context context, String pTitle, String pMessage, final BasePopupReponseListener pListener) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setTitle(pTitle);
        builder.setMessage(pMessage);
        builder.setPositiveButton(context.getString(R.string.oui), (dialogInterface, i) -> pListener.onReponse(EBasePopupReponse.OUI, null));
        builder.setNegativeButton(context.getString(R.string.non), (dialogInterface, i) -> pListener.onReponse(EBasePopupReponse.NON, null));
        builder.setOnCancelListener(dialogInterface -> pListener.onReponse(EBasePopupReponse.ANNULER, null));
        builder.create().show();
    }

    /**
     * Affichage d'une popup d'information.
     * pour calculer son itinéraire.
     *
     * @param pTitle     Le titre de la popup.
     * @param pMessage   Le pMessage à afficher.
     * @param pMethodeOK La méthode à appeler lorsque l'utilisateur clique sur OK.
     */
    public static void affichePopupInformation(Context context, String pTitle, String pMessage, DialogInterface.OnClickListener pMethodeOK) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(pTitle);
        builder.setMessage(pMessage);
        builder.setPositiveButton(context.getString(R.string.ok), pMethodeOK);
        builder.create().show();
    }


    public enum EBasePopupReponse {
        OK,
        OUI,
        NON,
        ANNULER
    }

}
