package se.soprasteria.s2qaportal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestRunScheduleData {

    public List<HashMap<String,String>> testRunStatusList = new ArrayList<>();
    public boolean hasData = false;

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }
}
