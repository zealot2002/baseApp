package com.zzy.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.common.R;
import com.zzy.commonlib.utils.PxUtils;


public class TagEditDialog extends Dialog {
    public interface OnClickOkListener {
        void clickOk(String content);
    }

    public TagEditDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private TextView tvCancel,tvOk;
        private EditText etContent;
        private Context context;
        private OnClickOkListener listener;

        public Builder(Context context,OnClickOkListener listener) {
            this.context = context;
            this.listener = listener;
        }
        public TagEditDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final TagEditDialog dialog = new TagEditDialog(context, R.style.common_dialog_warning);// R.style.dialog

            View layout = inflater.inflate(R.layout.tag_edit_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            tvCancel = layout.findViewById(R.id.tvCancel);
            tvOk = layout.findViewById(R.id.tvOk);
            etContent = layout.findViewById(R.id.etContent);

            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.clickOk(etContent.getText().toString().trim());
                    }
                }
            });
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setContentView(layout);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.width = PxUtils.dp2px(context,315);
            lp.height = PxUtils.dp2px(context,165);

            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);

            return dialog;
        }
    }
}
