package npwidget.nopointer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cn.droidlover.xrichtext.XRichText;
import npwidget.nopointer.R;

/**
 * 用户使用协议的 授权的对话框,
 * 上传googleplay的 带有来电提醒 短信提醒 需要获取权限的app 都要使用此提示框来 让用户选择授权与否
 */
public class UseProtocolAuthDialog extends Dialog implements View.OnClickListener {

    private TextView titleText;
    private XRichText messageText;

    private Button cancelBtn;
    private Button sureBtn;

    public UseProtocolAuthDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_use_protocol_auth);

        titleText = findViewById(R.id.titleText);
        messageText = findViewById(R.id.messageText);


        cancelBtn = findViewById(R.id.cancelBtn);
        sureBtn = findViewById(R.id.sureBtn);

        cancelBtn.setOnClickListener(this);
        sureBtn.setOnClickListener(this);


        setCancelable(false);
        setCanceledOnTouchOutside(false);
        getWindow().setGravity(Gravity.CENTER);
    }


    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(layoutParams);

    }

    public void setTextShow(String title, String message) {
        show();
        titleText.setText(title);
        messageText.callback(new XRichText.Callback() {
            @Override
            public void onImageClick(List<String> urlList, int position) {

            }

            @Override
            public boolean onLinkClick(String url) {
                onUrlClick(url);
                return true;
            }

            @Override
            public void onFix(XRichText.ImageHolder holder) {
                if (holder.getPosition() % 3 == 0) {
                    holder.setStyle(XRichText.Style.LEFT);
                } else if (holder.getPosition() % 3 == 1) {
                    holder.setStyle(XRichText.Style.CENTER);
                } else {
                    holder.setStyle(XRichText.Style.RIGHT);
                }
            }
        }).text(message);
//                .text("<a href=\"http://my.oschina.net/u/1756518\" >西夏一品堂</a>  <a href=\"http://my.oschina.net/u/175651811\" >@西夏一品堂2</a>");
    }

    public void setBottomLeftRightText(int leftText, int rightText) {
        show();
        cancelBtn.setText(leftText);
        sureBtn.setText(rightText);
    }

    public void setBottomLeftRightText(String leftText, String rightText) {
        show();
        cancelBtn.setText(leftText);
        sureBtn.setText(rightText);
    }

    @Override
    public void onClick(View v) {
        cancel();
        if (v.getId() == R.id.cancelBtn) {
            onAction(false);
        } else if (v.getId() == R.id.sureBtn) {
            onAction(true);
        }
    }


    public void onAction(boolean isAgree) {

    }

    public void onUrlClick(String url) {
    }

}
