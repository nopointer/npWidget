package npwidget.nopointer.combinationControl.date.dateChoose;

public abstract class NpDataChooseCallback {

    public String formatDateShow(NpDateBean npDateBean) {
        return npDateBean.getSimpleTitle();
    }

    public abstract void onSelectDate(NpDateBean npDateBean);

}
