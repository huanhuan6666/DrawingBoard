package cn.edu.nju.widget;

import cn.edu.nju.shape.AbstractShape;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

class MyMenuItem extends JMenuItem implements Observer {

    public MyMenuItem(String text, Icon icon) {
        super(text, icon);
        setFont(new Font(null, Font.PLAIN, 20));
    }

    public MyMenuItem(String text) {
        super(text);
        setFont(new Font(null, Font.PLAIN, 20));
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

class MyMenu extends JMenu implements Observer {

    public MyMenu(String text) {
        super(text);
        setFont(new Font(null, Font.PLAIN, 20));
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


public class MyMenuBar extends JMenuBar implements Subject {

    private String skin; // 皮肤类型
    private int fontSize = 20; // 界面字体大小

    private ArrayList<Observer> observers;

    public MyMenuBar() {
        observers = new ArrayList<Observer>(); // 观察者们
        // 菜单
        MyMenu fileMenu = new MyMenu("文件");
        // 文件菜单的菜单项项
        MyMenuItem fileOpen = new MyMenuItem("打开", new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/open.png"))));
        MyMenuItem fileSave = new MyMenuItem("保存", new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/save.png"))));
        MyMenuItem fileImage = new MyMenuItem("导出图片", new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/out.png"))));
        MyMenuItem fileExit = new MyMenuItem("退出", new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/exit.png"))));
        // 设置快捷键
        fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        fileOpen.addActionListener(e -> openFile());
        fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        fileSave.addActionListener(e -> saveFile());
        fileImage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        fileImage.addActionListener(e -> saveImage());
        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        fileExit.addActionListener(e -> exitFile());

        fileMenu.add(fileOpen);
        fileMenu.add(fileSave);
        fileMenu.add(fileImage);
        fileMenu.add(fileExit);

        // 设置菜单的菜单项
        MyMenu setSkin = new MyMenu("设置皮肤");
        MyMenuItem Spring = new MyMenuItem("春");
        MyMenuItem Summer = new MyMenuItem("夏");
        MyMenuItem Autumn = new MyMenuItem("秋");
        MyMenuItem Winter = new MyMenuItem("冬");
        MyMenuItem Default = new MyMenuItem("默认");

//        Spring.setFont(new Font(Font.DIALOG,  Font.BOLD + Font.ITALIC, 40));

        Spring.addActionListener(e -> {
            skin = "spring";
            notifySkinObservers(skin);
        });
        Summer.addActionListener(e -> {
            skin = "summer";
            notifySkinObservers(skin);
        });
        Autumn.addActionListener(e -> {
            skin = "autumn";
            notifySkinObservers(skin);
        });
        Winter.addActionListener(e -> {
            skin = "winter";
            notifySkinObservers(skin);
        });
        Default.addActionListener(e -> {
            skin = "default";
            notifySkinObservers(skin);
        });

        setSkin.add(Default);
        setSkin.add(Spring);
        setSkin.add(Summer);
        setSkin.add(Autumn);
        setSkin.add(Winter);


        // 设置菜单的菜单项
        MyMenu setFont = new MyMenu("界面字体");
        MyMenuItem YaHei = new MyMenuItem("微软雅黑");
        MyMenuItem FangSong = new MyMenuItem("仿宋");
        MyMenuItem HeiTi = new MyMenuItem("黑体");
        MyMenuItem Moren = new MyMenuItem("默认");

        YaHei.addActionListener(e -> {
            Font font = new Font("微软雅黑", Font.PLAIN, getFontSize());
            notifyFontObservers(font);
        });
        FangSong.addActionListener(e -> {
            Font font = new Font("仿宋", Font.PLAIN, getFontSize());
            notifyFontObservers(font);
        });
        HeiTi.addActionListener(e -> {
            Font font = new Font("黑体", Font.PLAIN, getFontSize());
            notifyFontObservers(font);
        });
        Moren.addActionListener(e -> {
            Font font = new Font(null, Font.PLAIN, getFontSize());
            notifyFontObservers(font);
        });

        setFont.add(YaHei);
        setFont.add(FangSong);
        setFont.add(HeiTi);
        setFont.add(Moren);

        add(fileMenu);
        add(setSkin);
        add(setFont);

        attach(Spring);
        attach(Summer);
        attach(Autumn);
        attach(Winter);
        attach(Default);
        attach(fileOpen);
        attach(fileSave);
        attach(fileImage);
        attach(fileExit);
        attach(setSkin);
        attach(fileMenu);

        attach(setFont);
        attach(YaHei);
        attach(FangSong);
        attach(HeiTi);
        attach(Moren);
    }

    public int getFontSize() {
        String input;
        input = JOptionPane.showInputDialog("请输入界面字体大小");
        try {
            fontSize = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return fontSize;
        }
        return Math.max(fontSize, 15);
    }

    public void openFile() {
        System.out.println("open a file!");
        try {
            //弹出选择对话框，选择需要读入的文件
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            FileFilter MyFilter = new FileNameExtensionFilter(".my", "my");
            chooser.setFileFilter(MyFilter);
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();
            //如果为选中文件
            if (file == null) {
                JOptionPane.showMessageDialog(null, "没有选择文件");
            } else {
                //选中了相应的文件，则柑橘选中的文件创建对象输入流
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                //将读出来的对象转换成父类对象的容器进行接收
                MainWindow.getDrawPanel().setShapes((ArrayList<AbstractShape>) ois.readObject());
                MainWindow.getDrawPanel().setChooseMore(false);
                ois.close();
                MainWindow.getDrawPanel().repaint();
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    // 保存可加载文件
    public void saveFile() {
        System.out.println("save a file!");
        //选择要保存的位置以及文件名字和信息
        JFileChooser chooser = new JFileChooser();
        FileFilter MyFilter = new FileNameExtensionFilter(".my", "my");
        chooser.setFileFilter(MyFilter);
        chooser.setCurrentDirectory(new File("."));
        chooser.showSaveDialog(null);

        File file = chooser.getSelectedFile();
        if (!file.getName().endsWith(chooser.getFileFilter().getDescription())) {
            String t = file.getPath() + chooser.getFileFilter().getDescription();
            file = new File(t);
        }
        if (file == null) {
            JOptionPane.showMessageDialog(null, "没有选择文件");
        } else {
            try {
                //根据要保存的文件创建对象输出流
                FileOutputStream fis = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fis);
                //将容器里面所绘制的图形利用对象流全部写入选中的文件中
                oos.writeObject(MainWindow.getDrawPanel().getShapes());
                JOptionPane.showMessageDialog(null, "保存成功！");
                oos.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    // 保存图片
    public void saveImage() {
        BufferedImage bImage = new BufferedImage(MainWindow.getDrawPanel().getWidth(), MainWindow.getDrawPanel().getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bImage.getGraphics();
        MainWindow.getDrawPanel().paint(g2);

        //把图像保存为文件
        JFileChooser fileChooser = new JFileChooser();//文件保存对话框
        FileFilter JPegFilter = new FileNameExtensionFilter(".jpg", "jpg", "jpeg");
        FileFilter PngFilter = new FileNameExtensionFilter(".png", "png");
        FileFilter BmpFilter = new FileNameExtensionFilter(".bmp", "bmp");

        fileChooser.setFileFilter(JPegFilter);
        fileChooser.addChoosableFileFilter(PngFilter);
        fileChooser.addChoosableFileFilter(BmpFilter);
//        fileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));
        // 返回当前的文本过滤器，并设置成当前的选择
        fileChooser.setCurrentDirectory(new File("."));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File fileName = fileChooser.getSelectedFile();
            if (!fileName.getName().endsWith(fileChooser.getFileFilter().getDescription())) {
                String t = fileName.getPath() + fileChooser.getFileFilter().getDescription();
                fileName = new File(t);
            }
            fileName.canWrite();
            if ("".equals(fileName.getName())) {
                JOptionPane.showMessageDialog(fileChooser, "无效的文件名", "无效的文件名", JOptionPane.ERROR_MESSAGE);
            }
            try {
                ImageIO.write(bImage, "jpeg", fileName);//保存图像文件
                JOptionPane.showMessageDialog(null, "导出成功！");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void exitFile() {
        System.out.println("Exit!");
        System.exit(0);
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifySkinObservers(String type) { // 通知更新皮肤
        for (Observer observer : observers) {
            observer.updateSkin(type);
        }
    }

    @Override
    public void notifyFontObservers(Font font) { // 通知更新字体
        for (Observer observer : observers) {
            observer.updateFont(font);
        }
    }
}
