package com.training.springtips.jmx;

public class ProfileController implements ProfileControllerMBean {

    private boolean isEnable = true;

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public boolean isEnable() {
        return isEnable;
    }
}
