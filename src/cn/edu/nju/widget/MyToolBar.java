package cn.edu.nju.widget;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MyToolBar extends JToolBar implements Observer{
    private final String[] images = {"/icon/Line.png", "/icon/Circle.png", "/icon/Ellipse.png",
                                    "/icon/Triangle.png", "/icon/Rectangle.png", "/icon/text.png",
                                    "/icon/copy.png", "/icon/Trash.png", "/icon/back.png", };
    private final String[] tipText = {"直线", "圆形", "椭圆", "三角形", "矩形", "文本", "复制", "删除", "撤回", };

    public JButton[] toolButtons = new JButton[images.length];  // 工具按钮

    public MyToolBar() {
        setName("工具栏");
        setLayout(new FlowLayout(FlowLayout.LEFT));
//        setBackground(new Color(195, 195, 195, 123));
        setFloatable(true); // 可拖动

        for(int i = 0; i < images.length; i++) {
            toolButtons[i] = new JButton();
            // 配置每个按钮
            toolButtons[i].setSize(28, 28);
            toolButtons[i].setToolTipText(tipText[i]);
            toolButtons[i].setBackground(Color.WHITE);

            ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(images[i])));
            Image image = imageIcon.getImage().getScaledInstance(toolButtons[i].getWidth(), toolButtons[i].getHeight(), imageIcon.getImage().SCALE_DEFAULT);
            toolButtons[i].setIcon(new ImageIcon(image));
            add(toolButtons[i]);
        }
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

    }
}
