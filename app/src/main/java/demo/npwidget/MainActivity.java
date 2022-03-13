package demo.npwidget;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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


public class MainActivity extends FragmentActivity {


    public static final String URL1 = "http://mlb2.app168.com/index.php/home/common/serviceAgree.html";
    public static final String URL2 = "http://mlb2.app168.com/index.php/home/common/privacy.html";


    private NpSleepStateAreaView npStateLineView;

    NpColorBarProgressView npCircleProgressView;

    private NpBatteryView npBatteryView;

    private UseProtocolAuthDialog useProtocolAuthDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        npStateLineView = findViewById(R.id.NpChartLineView);
        npCircleProgressView = findViewById(R.id.npCircleProgressView);
        npBatteryView = findViewById(R.id.npBatteryView);


        findViewById(R.id.debug_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(MainActivity.this, "我就是来搞笑的");
            }
        });

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

        loadBattery();

//        startActivity(new Intent(this,NpColumnViewActivity.class));
//        startActivity(new Intent(this,NpLineViewActivity.class));
//        startActivity(new Intent(this,NpPointViewActivity.class));
//        startActivity(new Intent(this,NpPointViewActivity.class));
        startActivity(new Intent(this, NpCircleProgressViewActivity.class));


//        startActivity(new Intent(this,NpRectViewActivity.class));
//        startActivity(new Intent(this, NpCountDwonViewActivity.class));
//        startActivity(new Intent(this, NpColorBarProgressViewActivity.class));
    }

    private void loadBattery() {
        npBatteryView.setBatteryColor(0xFF009900);
        npBatteryView.setBorderColor(0xFF000000);
        npBatteryView.setBatteryBorderRadius(5);
        npBatteryView.setBorderWidth(4);
//        npBatteryView.setInnerPadding(10);
        npBatteryView.setTopRectHeight(8);
        npBatteryView.setTopRectWidth(16);
        npBatteryView.setBatteryValue(100);
        npBatteryView.setLowBatteryColor(0xFFFF0000);
        npBatteryView.setShowAtLastOne(true);
        npBatteryView.setShowType(NpBatteryView.TYPE_CONTINUOUS);
//        float[] customBatteryArray = new float[]{0, 5, 20, 40, 60, 80, 100};
//        npBatteryView.setCustomBatteryArray(customBatteryArray);
        npBatteryView.invalidate();
    }

    private void loadColorBar() {
//


        NpColorBarBean localNpColorBarBean = new NpColorBarBean();
        localNpColorBarBean.setShowStartEndValue(true);
        localNpColorBarBean.setMinValue(35.0F);
        localNpColorBarBean.setCursorColor(0xFFFF3500);
        localNpColorBarBean.setValueColor(0xFFFF0000);
        localNpColorBarBean.setTextSize(SizeUtils.sp2px(this, 14));
        localNpColorBarBean.setValuePosition(NpPosition.CENTER);
        localNpColorBarBean.setCursorPosition(NpPosition.TOP);
        localNpColorBarBean.setCursorEquilateral(true);
        localNpColorBarBean.setCursorWidth(34);
        localNpColorBarBean.setCursorMarginColorBar(5);
        localNpColorBarBean.setCurrentValue(38.5F);
        localNpColorBarBean.setMaxValue(42.0F);
        localNpColorBarBean.setUseRoundMode(true);
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new NpColorBarEntity(0xFF05B4F2, 0xFF39F2FF));//35 36
        localArrayList.add(new NpColorBarEntity(0xFF39F2FF, 0xFFF4F785));//36 37
//        localArrayList.add(new NpColorBarEntity(0xFFFFE785, 0xFFFFE585));//37 38
//        localArrayList.add(new NpColorBarEntity(0xFFFFD285, 0xFFFFD285));//38 39
//        localArrayList.add(new NpColorBarEntity(0xFFFFBE86, 0xFFFFB886));//39 40
//        localArrayList.add(new NpColorBarEntity(0xFFFFA886, 0xFFFFA886));//40 41
//        localArrayList.add(new NpColorBarEntity(0xFFFF9386, 0xFFFF9386));//41 42
        localNpColorBarBean.setNpColorBarEntityList(localArrayList);
        npCircleProgressView.setNpColorBarBean(localNpColorBarBean);
        npCircleProgressView.invalidate();

    }

    private void loadDebug() {
        NpSleepStateAreaBean npStateBean = new NpSleepStateAreaBean();
        npStateBean.setStateAreaType(NpSleepStateAreaBean.StateAreaType.SPLIT_HEIGHT);
        npStateBean.setClipLineWidth(1);
        npStateBean.setClipLineColor(Color.BLACK);
        npStateBean.setSelectPartRectColor(0xFFE5E5E5);
        List<NpSleepEntry> dataList = new ArrayList<>();

        String string = "5DDC10C40F02 5DDC14480501 5DDC15740F02 5DDC18F80501";
        string += "5DDC1A242302 5DDC22580F01 5DDC25DC0502 5DDC27080503 5DDC28343C02 5DDC36442D01 5DDC40D00A025DDC432805035DDC445419025DDC4A3005015DDC4B5C23025DDC53900A015DDC55E83C025DDC63F805015DDC65245002";
        string = string.replace(" ", "");
        int length = string.length() / 12;
        for (int i = 0; i < length; i++) {
            NpSleepEntry npSleepEntry = new NpSleepEntry();
            npSleepEntry.setSleepType(3 - Integer.valueOf(string.substring(12 * i + 10, 12 * i + 12)));
            if (npSleepEntry.getSleepType() == 0) {
                npSleepEntry.setColor(0xFF129EF7);
                npSleepEntry.setPosition(0);
            } else if (npSleepEntry.getSleepType() == 1) {
                npSleepEntry.setColor(0xFF2FE7E7);
                npSleepEntry.setPosition(1);
            } else {
                npSleepEntry.setPosition(2);
                npSleepEntry.setColor(0xFFFF59B3);
            }
//            npSleepEntry.setStartTime(devMinuteSleepBean.getDate());
            npSleepEntry.setDuration(Integer.parseInt(string.substring(12 * i + 8, 12 * i + 10), 16));
            dataList.add(npSleepEntry);
        }
        npStateBean.setDataList(dataList);

        npStateBean.setLeftText("00:00");
        npStateBean.setLeftTextColor(0xFF000000);
        npStateBean.setRightText("23:59");
        npStateBean.setRightTextColor(0xFF000000);
        npStateBean.setEnableClickPart(true);

        npStateLineView.setPartSelectCallback(new NpSleepStateAreaView.PartSelectCallback() {
            @Override
            public void onSelect(int index, NpSleepEntry npSleepEntry) {

            }

            @Override
            public String drawText(NpSleepEntry npSleepEntry) {
                String text = "";
                long startTime = npSleepEntry.getStartTime();
                long endTime = npSleepEntry.getDuration() * 60 + startTime;

                String timeString = new SimpleDateFormat("HH:mm").format(startTime * 1000L) + " ~ " + new SimpleDateFormat("HH:mm").format(endTime * 1000L);


                Log.e("npSleepEntry", npSleepEntry.toString());

                switch (npSleepEntry.getSleepType()) {
                    case 0:
                        text = "清醒:" + timeString;
                        break;
                    case 1:
                        text = "浅睡:" + timeString;
                        break;
                    case 2:
                        text = "深睡:" + timeString;
                        break;
                }
                return text;
            }
        });
        npStateBean.setLeftRightTextSize(30);
        npStateLineView.setNpStateBean(npStateBean);
        npStateLineView.invalidate();

    }
}
