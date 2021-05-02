package dev.regucorp.sfinder;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 Created by Guillaume ROUSSIN on 12/29/20    
 */
public class ViewLocationDialog {

    private static Dialog d;
    private static TextView textView;

    public static void showDialog(Activity act, String text) {
        if(d == null) initDialog(act);

        if(text.equals(""))
            textView.setText(R.string.info_noAccText);
        else
            textView.setText(text);

        d.show();
    }

    private static void initDialog(Activity act) {
        d = new Dialog(act);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(false);
        d.setContentView(R.layout.info_screen_layout);

        textView = d.findViewById(R.id.locationText);

        Button cancelBtn = d.findViewById(R.id.backBtn);
        cancelBtn.setOnClickListener(v -> {
            d.dismiss();
        });
    }
}
