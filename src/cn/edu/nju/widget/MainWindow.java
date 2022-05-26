package cn.edu.nju.widget;


import cn.edu.nju.Command.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;


public class MainWindow extends JFrame {
    private static volatile MainWindow instance = null; // 单例
    private MyMenuBar menu; // 菜单构件
    private MyColorPanel colorPanel;  // 颜色盘构件

    private static MyDrawPanel drawPanel;  // 画布构件
    private MyToolBar toolBar;  // 工具栏

    private static String currentMode;    // 当前画笔
    private static MyStatusBar statusBar;  // 状态栏构件

    public static MyDrawPanel getDrawPanel() {
        return drawPanel;
    }
    public static MyStatusBar getStatusBar() {
        return statusBar;
    }


    public static String getCurrentMode() {
        return currentMode;
    }

    public static void setCurrentMode(String currentMode) {
        MainWindow.currentMode = currentMode;
    }

    private static Color penColor = new Color(0, 162, 232); // 画笔颜色

    public static Color getPenColor() {
        return penColor;
    }

    public static void setPenColor(Color c) {
        penColor = c;
    }

    private void InitWidget() {
        menu = new MyMenuBar();
        setJMenuBar(menu);
        toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);
        statusBar = new MyStatusBar();
        add(statusBar, BorderLayout.SOUTH);

        toolBar.toolButtons[0].addActionListener(e -> {
            currentMode = "line";
            System.out.println("current choice " + currentMode);
        });
        toolBar.toolButtons[1].addActionListener(e -> {
            currentMode = "circle";
            System.out.println("current choice " + currentMode);
        });
        toolBar.toolButtons[2].addActionListener(e -> {
            currentMode = "ellipse";
            System.out.println("current choice " + currentMode);
        });
        toolBar.toolButtons[3].addActionListener(e -> {
            currentMode = "triangle";
            System.out.println("current choice " + currentMode);
        });
        toolBar.toolButtons[4].addActionListener(e -> {
            currentMode = "rectangle";
            System.out.println("current choice " + currentMode);
        });
        toolBar.toolButtons[5].addActionListener(e -> {
            currentMode = "text";
            System.out.println("current choice " + currentMode);
        });
        // 复制选中的图元
        toolBar.toolButtons[6].addActionListener(e -> {
            currentMode = "copy";
            System.out.println("current choice " + currentMode);
        });
        toolBar.toolButtons[6].registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMode = "copy";
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);

        // 粘贴选中的图元
        toolBar.toolButtons[6].registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // drawPanel.paste(null, true);
                PasteCommand cmd = new PasteCommand(drawPanel.getOperation(), drawPanel.getChosenShapes());
                cmd.execute();
                drawPanel.addCmd(cmd);
                System.out.println("undocmd size: " + drawPanel.getUndoCmds().size());
                repaint();
                currentMode = "null";
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);

        // 删除选中的图元
        toolBar.toolButtons[7].addActionListener(e -> {
            // drawPanel.delete();
            DeleteCommand cmd = new DeleteCommand(drawPanel.getOperation(), drawPanel.getChosenShapes());
            cmd.execute();
            drawPanel.addCmd(cmd);
            repaint();
            System.out.println("current choice " + currentMode);
        });
        toolBar.toolButtons[7].registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // drawPanel.delete();
                DeleteCommand cmd = new DeleteCommand(drawPanel.getOperation(), drawPanel.getChosenShapes());
                cmd.execute();
                drawPanel.addCmd(cmd);
                repaint();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);

        // 撤回操作
        toolBar.toolButtons[8].addActionListener(e -> {
            ArrayList<Command> undoCmds = drawPanel.getUndoCmds();
            if(undoCmds.size() > 0) {
                Command cmd = undoCmds.get(undoCmds.size() - 1);
                cmd.undo();
                undoCmds.remove(cmd);
                System.out.println("undocmd size: " + drawPanel.getUndoCmds().size());
            }
            repaint();
        });
        toolBar.toolButtons[8].registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Command> undoCmds = drawPanel.getUndoCmds();
                if(undoCmds.size() > 0) {
                    Command cmd = undoCmds.get(undoCmds.size() - 1);
                    cmd.undo();
                    undoCmds.remove(cmd);
                }
                repaint();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK), JComponent.WHEN_IN_FOCUSED_WINDOW);

        colorPanel = new MyColorPanel();
        add(colorPanel, BorderLayout.WEST);

        menu.attach(colorPanel); // 注册观察者
        menu.attach(toolBar);
        menu.attach(statusBar);

        drawPanel = new MyDrawPanel();
        add(drawPanel, BorderLayout.CENTER);
    }

    private MainWindow() {
            setTitle("绘图");
            setSize(1200, 900);
            setLocationRelativeTo(null); // 居中显式
            setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/icon.png"))).getImage());
            currentMode = "null"; // 初始未选择
            InitWidget();

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            setVisible(true);
        }

        public static void setFocus() {
            drawPanel.requestFocus();
        }
        // 双检锁 懒汉式获取单例的外部接口
        public static MainWindow getInstance () {
            if (instance == null) {
                synchronized (MainWindow.class) {
                    if (instance == null) {
                        instance = new MainWindow();
                        drawPanel.mainWindow = instance;
                    }
                }
            }
            return instance;
        }

    }
