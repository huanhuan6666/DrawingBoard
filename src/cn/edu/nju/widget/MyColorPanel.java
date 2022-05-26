package cn.edu.nju.widget;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Objects;

public class MyColorPanel extends JPanel implements Observer {

    private Color[] colors = {new Color(255, 255, 255), new Color(0, 0, 0), new Color(127, 127, 127),
            new Color(195, 195, 195), new Color(136, 0, 21), new Color(185, 122, 87), new Color(237, 28, 36),
            new Color(255, 174, 201), new Color(255, 127, 39), new Color(255, 242, 0), new Color(239, 228, 176),
            new Color(34, 117, 76), new Color(181, 230, 29), new Color(0, 162, 232), new Color(153, 217, 234),
            new Color(63, 72, 204), new Color(112, 146, 190), new Color(163, 73, 164), new Color(200, 191, 231),
            new Color(89, 173, 154), new Color(8, 193, 194), new Color(9, 253, 76), new Color(153, 217, 234),
            new Color(199, 73, 4)};

    public MyColorPanel() {
        // 主面板添加左边面板
        this.setPreferredSize(new Dimension(60, 60));
        this.setLayout(null);
//        this.setBackground(new Color(195, 195, 195, 123));
        // 左边面板添加颜色子面板 由上下两个面板和一个更多颜色按钮组成
        JPanel panelDownChild = new JPanel();
        panelDownChild.setBackground(Color.gray);
        panelDownChild.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelDownChild.setBounds(10, 10, 40, 320);
        panelDownChild.setToolTipText("颜色");
        this.add(panelDownChild);


        // 按钮特效,简单的双线斜面边框
        BevelBorder frontBoard = new BevelBorder(0, Color.gray, Color.white);
        BevelBorder backBoard = new BevelBorder(1, Color.gray, Color.white);

        // 上面板 有前后两个颜色按钮
        JPanel upPanel = new JPanel();
        upPanel.setBackground(Color.white);
        upPanel.setLayout(null);
        upPanel.setBorder(frontBoard);
        upPanel.setPreferredSize(new Dimension(40, 40));

        // 前后两个颜色按钮

        JButton back = new JButton();
        back.setBorder(backBoard);
        back.setBackground(Color.white);
        back.setBounds(15, 15, 20, 20);
        back.addActionListener(e -> {
            // 拿到被选中按钮的对象
            JButton jbt = (JButton) e.getSource();
            // 拿到被选中按钮的背景颜色
            Color c = jbt.getBackground();
            // 把背景颜色复制给WIndowStart中的颜色属性
            MainWindow.setPenColor(c);
            System.out.println(MainWindow.getPenColor());
        });
        upPanel.add(back);

        JButton front = new JButton();
        front.setBounds(5, 5, 20, 20);
        front.setBorder(frontBoard);
        front.setBackground(new Color(0, 162, 232));
        front.setSize(20, 20);
        front.addActionListener(e -> {
            // 拿到被选中按钮的对象
            JButton jbt = (JButton) e.getSource();
            // 拿到被选中按钮的背景颜色
            Color c = jbt.getBackground();
            // 把背景颜色复制给WIndowStart中的颜色属性
            MainWindow.setPenColor(c);
            System.out.println(MainWindow.getPenColor());
        });
        upPanel.add(front);

        // 下面板 剩下的24种颜色块
        JPanel downPanel = new JPanel();
        downPanel.setBackground(Color.white);
        downPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        downPanel.setPreferredSize(new Dimension(40, 300));

        // 循环添加24个颜色按钮到下面版
        for (int i = 0; i < 24; i++) {
            JButton eachColer = new JButton();
            eachColer.setOpaque(true);
            eachColer.setBackground(colors[i]);
            eachColer.setPreferredSize(new Dimension(20, 20));
            eachColer.setBorder(backBoard);
            eachColer.addActionListener(e -> {
                JButton jbt = (JButton) e.getSource();
                Color c = jbt.getBackground();
                MainWindow.setPenColor(c);
                Color tmp = front.getBackground();
                back.setBackground(tmp);
                front.setBackground(c);
                System.out.println(MainWindow.getPenColor());
            });
            downPanel.add(eachColer);
        }

        panelDownChild.add(upPanel);
        panelDownChild.add(downPanel);

        // 更多颜色按钮
        JButton moreColor = new JButton();
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/color_48.png")));
        moreColor.setIcon(imageIcon);
        moreColor.setPreferredSize(new Dimension(40, 40));
        moreColor.setToolTipText("更多颜色");
        downPanel.add(moreColor);
        // 点击弹出颜色对话框
        moreColor.addActionListener(e -> chooseColor());


    }

    public static void chooseColor() {
        MainWindow.getInstance();
        Color pre = MainWindow.getPenColor();
        Color c = JColorChooser.showDialog(null, "请选择颜色", pre);
        MainWindow.setPenColor(c == null ? pre : c);
        System.out.println(MainWindow.getPenColor());
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
