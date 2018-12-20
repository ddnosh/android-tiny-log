package com.androidwind.log;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class LogConfig implements LogConfigContract {

    //whether log
    public boolean isEnable;

    @Override
    public void setEnable(boolean enable) {
        this.isEnable = enable;
    }

}
