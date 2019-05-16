package com.zzy.update.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.commonlib.utils.PxUtils;
import com.zzy.update.R;

/**
 * 自定义弹窗
 *
 * @author dell
 */
public class UpdateDialog extends Dialog {

    public UpdateDialog(Context context) {
        super(context);
    }

    public UpdateDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String versionName;
        private String changeList;
        private OnClickListener onUpdateListener;
        private boolean isForce;

/*******************************************************************************************************/

        public Builder(Activity context) {
            this.context = context;
        }

        public Builder(Activity context, int theme) {
            this.context = context;
        }

        public Builder changeList(String var) {
            this.changeList = var;
            return this;
        }
        public Builder versionName(String var) {
            this.versionName = var;
            return this;
        }
        public Builder onUpdateListener(OnClickListener var) {
            this.onUpdateListener = var;
            return this;
        }
        public Builder isForce(boolean var) {
            this.isForce = var;
            return this;
        }

        @SuppressLint("NewApi")
        public UpdateDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final UpdateDialog dialog = new UpdateDialog(context, R.style.update_dialog);
            View layout = inflater.inflate(R.layout.update_dialog_confirm, null);

            TextView tvVersion = layout.findViewById(R.id.tvVersion);
            EditText etChangeList = layout.findViewById(R.id.etChangeList);
            etChangeList.setKeyListener(null);

            tvVersion.setText("V5.0.0");
            etChangeList.setText(changeList+"");

            Button btnOk = layout.findViewById(R.id.btnOk);
            Button btnOk2 = layout.findViewById(R.id.btnOk2);
            Button btnCancel = layout.findViewById(R.id.btnCancel);

            if(isForce){
                btnOk.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                btnOk2.setVisibility(View.VISIBLE);
                btnOk2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onUpdateListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }else{
                btnOk.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                btnOk2.setVisibility(View.GONE);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onUpdateListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            dialog.setContentView(layout);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.width = PxUtils.dp2px(context,290);
            lp.height = PxUtils.dp2px(context,365);

            dialog.getWindow().setAttributes(lp);
            dialog.setCancelable(false);
            return dialog;
        }
    }
}
