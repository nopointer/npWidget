package npwidget.nopointer.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;

public class ValueAnimUtil {


    public static void startValueAnimByFloat(float start, float end, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(start, end);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(animatorUpdateListener);
        valueAnimator.start();
    }

    public static void startValueAnimByInt(int start, int end, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(start, end);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(animatorUpdateListener);
        valueAnimator.start();
    }


}
