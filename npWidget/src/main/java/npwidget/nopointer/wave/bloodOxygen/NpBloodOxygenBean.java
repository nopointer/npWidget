package npwidget.nopointer.wave.bloodOxygen;

import java.util.ArrayList;
import java.util.List;

public class NpBloodOxygenBean {

    /**
     * 数据点
     */
    private List<NpBloodOxygenEntry> entryList = new ArrayList<>();


    public List<NpBloodOxygenEntry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<NpBloodOxygenEntry> entryList) {
        this.entryList = entryList;
    }
}
