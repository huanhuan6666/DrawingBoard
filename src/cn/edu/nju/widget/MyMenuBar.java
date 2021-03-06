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

    private String skin; // ????????????
    private int fontSize = 20; // ??????????????????

    private ArrayList<Observer> observers;

    public MyMenuBar() {
        observers = new ArrayList<Observer>(); // ????????????
        // ??????
        MyMenu fileMenu = new MyMenu("??????");
        // ???????????????????????????
        MyMenuItem fileOpen = new MyMenuItem("??????", new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/open.png"))));
        MyMenuItem fileSave = new MyMenuItem("??????", new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/save.png"))));
        MyMenuItem fileImage = new MyMenuItem("????????????", new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/out.png"))));
        MyMenuItem fileExit = new MyMenuItem("??????", new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/exit.png"))));
        // ???????????????
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

        // ????????????????????????
        MyMenu setSkin = new MyMenu("????????????");
        MyMenuItem Spring = new MyMenuItem("???");
        MyMenuItem Summer = new MyMenuItem("???");
        MyMenuItem Autumn = new MyMenuItem("???");
        MyMenuItem Winter = new MyMenuItem("???");
        MyMenuItem Default = new MyMenuItem("??????");

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


        // ????????????????????????
        MyMenu setFont = new MyMenu("????????????");
        MyMenuItem YaHei = new MyMenuItem("????????????");
        MyMenuItem FangSong = new MyMenuItem("??????");
        MyMenuItem HeiTi = new MyMenuItem("??????");
        MyMenuItem Moren = new MyMenuItem("??????");

        YaHei.addActionListener(e -> {
            Font font = new Font("????????????", Font.PLAIN, getFontSize());
            notifyFontObservers(font);
        });
        FangSong.addActionListener(e -> {
            Font font = new Font("??????", Font.PLAIN, getFontSize());
            notifyFontObservers(font);
        });
        HeiTi.addActionListener(e -> {
            Font font = new Font("??????", Font.PLAIN, getFontSize());
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
        input = JOptionPane.showInputDialog("???????????????????????????");
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
            //???????????????????????????????????????????????????
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            FileFilter MyFilter = new FileNameExtensionFilter(".my", "my");
            chooser.setFileFilter(MyFilter);
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();
            //?????????????????????
            if (file == null) {
                JOptionPane.showMessageDialog(null, "??????????????????");
            } else {
                //??????????????????????????????????????????????????????????????????
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                //???????????????????????????????????????????????????????????????
                MainWindow.getDrawPanel().setShapes((ArrayList<AbstractShape>) ois.readObject());
                MainWindow.getDrawPanel().setChooseMore(false);
                ois.close();
                MainWindow.getDrawPanel().repaint();
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    // ?????????????????????
    public void saveFile() {
        System.out.println("save a file!");
        //???????????????????????????????????????????????????
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
            JOptionPane.showMessageDialog(null, "??????????????????");
        } else {
            try {
                //?????????????????????????????????????????????
                FileOutputStream fis = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fis);
                //??????????????????????????????????????????????????????????????????????????????
                oos.writeObject(MainWindow.getDrawPanel().getShapes());
                JOptionPane.showMessageDialog(null, "???????????????");
                oos.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    // ????????????
    public void saveImage() {
        BufferedImage bImage = new BufferedImage(MainWindow.getDrawPanel().getWidth(), MainWindow.getDrawPanel().getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bImage.getGraphics();
        MainWindow.getDrawPanel().paint(g2);

        //????????????????????????
        JFileChooser fileChooser = new JFileChooser();//?????????????????????
        FileFilter JPegFilter = new FileNameExtensionFilter(".jpg", "jpg", "jpeg");
        FileFilter PngFilter = new FileNameExtensionFilter(".png", "png");
        FileFilter BmpFilter = new FileNameExtensionFilter(".bmp", "bmp");

        fileChooser.setFileFilter(JPegFilter);
        fileChooser.addChoosableFileFilter(PngFilter);
        fileChooser.addChoosableFileFilter(BmpFilter);
//        fileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));
        // ????????????????????????????????????????????????????????????
        fileChooser.setCurrentDirectory(new File("."));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File fileName = fileChooser.getSelectedFile();
            if (!fileName.getName().endsWith(fileChooser.getFileFilter().getDescription())) {
                String t = fileName.getPath() + fileChooser.getFileFilter().getDescription();
                fileName = new File(t);
            }
            fileName.canWrite();
            if ("".equals(fileName.getName())) {
                JOptionPane.showMessageDialog(fileChooser, "??????????????????", "??????????????????", JOptionPane.ERROR_MESSAGE);
            }
            try {
                ImageIO.write(bImage, "jpeg", fileName);//??????????????????
                JOptionPane.showMessageDialog(null, "???????????????");
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
    public void notifySkinObservers(String type) { // ??????????????????
        for (Observer observer : observers) {
            observer.updateSkin(type);
        }
    }

    @Override
    public void notifyFontObservers(Font font) { // ??????????????????
        for (Observer observer : observers) {
            observer.updateFont(font);
        }
    }
}
