package demo.npwidget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import demo.npwidget.demos.DemoListActivity;
import npwidget.nopointer.base.NpPosition;
import npwidget.nopointer.dialog.UseProtocolAuthDialog;
import npwidget.nopointer.progress.battery.NpBatteryView;
import npwidget.nopointer.progress.npColorBars.NpColorBarBean;
import npwidget.nopointer.progress.npColorBars.NpColorBarEntity;
import npwidget.nopointer.progress.npColorBars.cursorTop.NpColorBarProgressView;
import npwidget.nopointer.sleepView.NpSleepEntry;
import npwidget.nopointer.sleepView.sleepStateAreaView.NpSleepStateAreaBean;
import npwidget.nopointer.sleepView.sleepStateAreaView.NpSleepStateAreaView;
import npwidget.nopointer.utils.SizeUtils;


public class MainActivity extends Activity {



    NpColorBarProgressView npCircleProgressView;



    private UseProtocolAuthDialog useProtocolAuthDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        npCircleProgressView = findViewById(R.id.npCircleProgressView);


        startActivity(new Intent(this,DemoListActivity.class));

        finish();
//
//        if (useProtocolAuthDialog == null) {
//            useProtocolAuthDialog = new UseProtocolAuthDialog(this) {
//                @Override
//                public void onAction(boolean isAgree) {
//                    super.onAction(isAgree);
//
//                }
//
//                @Override
//                public void onUrlClick(String url) {
//                    super.onUrlClick(url);
//                    Log.e("url", url);
////                    Intent intent = new Intent(MainActivity.this, WebActivity.class);
//                    switch (url) {
////                        case NetCfg.URL1:
////                            intent.putExtra("title", getResources().getString(R.string.use_protocol_auth_message0));
////                            break;
////                        case NetCfg.URL2:
////                            intent.putExtra("title", getResources().getString(R.string.use_protocol_auth_message1));
////                            break;
//                    }
////                    intent.putExtra("url", url);
////                    startActivity(intent);
//                }
//            };
//        }
//        String title = getResources().getString(R.string.use_protocol_auth_title);
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(getResources().getString(R.string.i_have_read_and_agreed));
//        stringBuilder.append("<a href='" + URL1 + "'>《").append(getResources().getString(R.string.use_protocol_auth_message0)).append("》</a>");
//        stringBuilder.append(getResources().getString(R.string.and));
//        stringBuilder.append("<a href='" + URL2 + "'>").append(getResources().getString(R.string.use_protocol_auth_message1)).append("</a>");
//
//        useProtocolAuthDialog.setTextShow(title, stringBuilder.toString());
//
//        useProtocolAuthDialog.setBottomLeftRightText(R.string.disagree_text, R.string.agree_text);


//        loadDebug();

//        startActivity(new Intent(this,NpCountDwonViewActivity.class));

//        DialogSettings.style = DialogSettings.STYLE;
//        loadDebug();

//        startService(new Intent(this, KeepService.class));
//        startService(new Intent(this, GuardService.class));

//        SettingsPageUtils.goToSettingPage(this, PageType.AUTO_START);

//        startActivity(new Intent(this, NpTimeCirclePickerViewActivity.class));
//        startActivity(new Intent(this, NpCountDwonViewActivity.class));
//        startActivity(new Intent(this, NpLineViewActivity.class));
        //进度
//        startActivity(new Intent(this, NpRectViewActivity.class));
//        startActivity(new Intent(this, NpOxWaveViewActivity.class));

//        startActivity(new Intent(this, NpDateTypeActivity.class));
//        startActivity(new Intent(this, NpColumnViewActivity.class));
//        startActivity(new Intent(this, NpPointViewActivity.class));
//        startActivity(new Intent(this, NpCountDwonViewActivity.class));
//        startActivity(new Intent(this, NpPolylineViewActivity.class));

//        loadDebug();

//        loadColorBar();
//        loadBattery();

//        WaitDialog.show(this, "");
//        WaitDialog.dismiss(2000);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        TipDialog.show(MainActivity.this, "成功！", TipDialog.TYPE.SUCCESS).setOnDismissListener(new OnDismissListener() {
//                            @Override
//                            public void onDismiss() {
//
//                            }
//                        }) ;
//                    }
//                });
//            }
//        }, 1000);


//        CustomDialog.build(this, R.layout.layout_custom_dialog, new CustomDialog.OnBindView() {
//            @Override
//            public void onBind(final CustomDialog dialog, View v) {
//                ImageView btnOk = v.findViewById(R.id.btn_ok);
//
//                btnOk.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.doDismiss();
//                    }
//                });
//            }
//        }).setAlign(CustomDialog.ALIGN.TOP).setCancelable(true).show();


//        InputDialog.show(this, "title", "message", "确定", "取消")
//                .setOnOkButtonClickListener(new OnInputDialogButtonClickListener() {
//                    @Override
//                    public boolean onClick(BaseDialog baseDialog, View v, String inputStr) {
//                        return false;
//                    }
//                })
//                .setOnOkButtonClickListener(new OnDialogButtonClickListener() {
//                    @Override
//                    public boolean onClick(BaseDialog baseDialog, View v) {
//                        InputDialog dialog = (InputDialog) baseDialog;
//                        String nickName = dialog.getInputText();
//                        Toast.makeText(getApplicationContext(), nickName, 0).show();
//                        return true;
//                    }
//                })
//                .setOnCancelButtonClickListener(new OnDialogButtonClickListener() {
//                    @Override
//                    public boolean onClick(BaseDialog baseDialog, View v) {
//                        InputDialog dialog = (InputDialog) baseDialog;
//                        String nickName = dialog.getInputText();
//                        Toast.makeText(getApplicationContext(), nickName, 0).show();
//                        return true;
//                    }
//                }).setOnOtherButtonClickListener(new OnDialogButtonClickListener() {
//            @Override
//            public boolean onClick(BaseDialog baseDialog, View v) {
//                InputDialog dialog = (InputDialog) baseDialog;
//                String nickName = dialog.getInputText();
//                Toast.makeText(getApplicationContext(), nickName, 0).show();
//                return false;
//            }
//        }).setMessage("sssss");

//       final LinearLayout mLayout =findViewById(R.id.rlayout);
//
//       new Handler().postDelayed(new Runnable() {
//           @Override
//           public void run() {
//               SelectCarPopWindow.getInstance().showSelectCarPopWindow(MainActivity.this, new OnItemClickListener() {
//                   @Override
//                   public void selectString(String select) {
////                mTextView.setText(select);
//                       SelectCarPopWindow.getInstance().dismissSelectCarPopWindow();
//                   }
//               }, new OnShrinkButtonClickListener() {
//                   @Override
//                   public void click() {
//                       SelectCarPopWindow.getInstance().dismissSelectCarPopWindow();
//                   }
//               },mLayout);
//           }
//       },1000);


//        startActivity(new Intent(this,NpColumnViewActivity.class));
//        startActivity(new Intent(this,NpLineViewActivity.class));
//        startActivity(new Intent(this,NpPointViewActivity.class));
//        startActivity(new Intent(this, NpCircleProgressViewActivity.class));


//        startActivity(new Intent(this,NpRectViewActivity.class));
//        startActivity(new Intent(this, NpCountDwonViewActivity.class));
//        startActivity(new Intent(this, NpColorBarProgressViewActivity.class));
    }


}
