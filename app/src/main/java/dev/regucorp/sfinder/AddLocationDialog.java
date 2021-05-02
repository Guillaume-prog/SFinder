package dev.regucorp.sfinder;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Guillaume ROUSSIN on 12/28/20
 */
public class AddLocationDialog {

    public interface AddLocationDialogListener {
        void onSubmit(String text);
    }

    private static Dialog d;
    private static EditText text;

    private static AddLocationDialogListener listener;

    public static void showDialog(Activity act, AddLocationDialogListener l) {
        if(d == null) initDialog(act);
        text.setText("");

        listener = l;
        d.show();
    }

    private static void initDialog(Activity act) {
        d = new Dialog(act);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(false);
        d.setContentView(R.layout.add_screen_layout);

        text = d.findViewById(R.id.textInput);

        Button cancelBtn = d.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(v -> {
            d.dismiss();
        });

        Button addBtn = d.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(v -> {
            if(listener != null) listener.onSubmit(text.getText().toString());
            d.dismiss();
        });
    }
}
