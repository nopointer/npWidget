package npwidget.extra.kongzue.dialog.interfaces;


import npwidget.extra.kongzue.dialog.util.BaseDialog;

public interface DialogLifeCycleListener {

    void onCreate(BaseDialog dialog);

    void onShow(BaseDialog dialog);

    void onDismiss(BaseDialog dialog);

}
