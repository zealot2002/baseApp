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
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.common.R;
import com.zzy.commonlib.utils.PxUtils;

public class PopupEditDialog extends Dialog {
    public interface OnClickOkListener {
        void clickOk(String content);
    }

    public PopupEditDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Button btnOk;
        private TextView tvTitle;
        private EditText etContent;
        private Context context;
        private String title, btnText;
        private boolean cancelable;
        private OnClickOkListener listener;

        public Builder(Context context, String title,String btnText,OnClickOkListener listener) {
            this.context = context;
            this.title = title;
            this.btnText = btnText;
            this.listener = listener;
            cancelable = true;
        }
        public PopupEditDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final PopupEditDialog dialog = new PopupEditDialog(context, R.style.common_dialog_warning);// R.style.dialog

            View layout = inflater.inflate(R.layout.popup_edit_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            tvTitle = layout.findViewById(R.id.tvTitle);
            btnOk = layout.findViewById(R.id.btnOk);
            etContent = layout.findViewById(R.id.etContent);

            tvTitle.setText(title);
            btnOk.setText(btnText);

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.clickOk(etContent.getText().toString().trim());
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

            return dialog;
        }
    }
}
