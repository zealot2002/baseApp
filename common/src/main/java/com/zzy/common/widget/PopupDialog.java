package com.zzy.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zzy.common.R;
import com.zzy.commonlib.utils.PxUtils;

public class PopupDialog extends Dialog {
    public PopupDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Button btnOk;
        private TextView tvTitle, tvContent;
        private Context context;
        private String title, btnText;
        private boolean cancelable,isAutoClose;

        public Builder(Context context, String title,String btnText) {
            this.context = context;
            this.title = title;
            this.btnText = btnText;
            cancelable = true;
            isAutoClose = true;
        }
        public Builder(Context context, String title, String btnText,
                       boolean cancelable,boolean isAutoClose) {
            this.context = context;
            this.title = title;
            this.btnText = btnText;
            this.cancelable = cancelable;
            this.isAutoClose = isAutoClose;
        }
        public PopupDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final PopupDialog dialog = new PopupDialog(context, R.style.common_dialog_warning);// R.style.dialog

            View layout = inflater.inflate(R.layout.popup_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            tvTitle = layout.findViewById(R.id.tvTitle);
            btnOk = layout.findViewById(R.id.btnOk);

            tvTitle.setText(title);
            btnOk.setText(btnText);

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isAutoClose){
                        dialog.cancel();
                    }
                }
            });

            dialog.setContentView(layout);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.width = PxUtils.dp2px(context,300);
            lp.height = PxUtils.dp2px(context,300);

            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(cancelable);

            dialog.show();
            return dialog;
        }
    }
}
