package cn.edu.nju.widget;

import java.awt.*;

public interface Subject {

    public void attach(Observer observer);
    public void detach(Observer observer);
    void notifySkinObservers(String type);
    void notifyFontObservers(Font font);
}