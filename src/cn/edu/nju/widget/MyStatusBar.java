package cn.edu.nju.widget;

import javax.swing.*;
import java.awt.*;

public class MyStatusBar extends JLabel implements Observer{
    public MyStatusBar() {
//        setBackground(new Color(195, 195, 195, 123));
        setFont(new Font(null, Font.PLAIN, 20));
        setOpaque(true);
        setText("状态栏");
    }

    @Override
    public void updateSkin(String str) {
        switch (str) {
            case "spring":
                setBackground(new Color(154, 255, 154, 123));
                break;
            case "summer":
                setBackground(new Color(153, 217, 234, 123));
                break;
            case "autumn":
                setBackground(new Color(255, 215, 0, 123));
                break;
            case "winter":
                setBackground(new Color(224, 255, 255, 123));
                break;
            default:
                setBackground(null);
                break;
        }
    }

    @Override
    public void updateFont(Font font) {
        setFont(font);
    }
}
