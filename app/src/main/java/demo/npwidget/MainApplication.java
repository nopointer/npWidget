package demo.npwidget;

import android.app.Application;


public class MainApplication extends Application {


    public static MainApplication mainApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static MainApplication getMainApplication() {
        return mainApplication;
    }


}
