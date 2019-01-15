package jx.com.shoppingtrolley_zihenguncle.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import jx.com.shoppingtrolley_zihenguncle.R;

public class CustomAlertDialog extends AlertDialog {

    private static CustomAlertDialog mCustomProgressDialog = null;

    protected CustomAlertDialog(@NonNull Context context) {
        super(context);
    }

    protected CustomAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


}
