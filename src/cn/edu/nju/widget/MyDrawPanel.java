package cn.edu.nju.widget;

import cn.edu.nju.Command.*;
import cn.edu.nju.shape.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

// 画板类 需要实现上面的鼠标事件
public class MyDrawPanel extends JPanel {
    public MainWindow mainWindow = null;
    private ArrayList<AbstractShape> shapes; // 所有图形的数组

    private boolean chooseMore;

    private AbstractShape chosenShape;     // 当前选中的图形
    private Set<AbstractShape> chosenShapes; // 选中的图形组合

    private Operation operation; // 命令执行者
    private ArrayList<Command> undoCmds = new ArrayList<Command>(); // 撤销命令列表

    private boolean dragSize;   // 拖拽改变大小
    AbstractShape dragShape;    // 拖拽的图形
    private int dragX, dragY;   // 拖拽的初始位置
    private boolean dragMove;   // 拖拽移动图元

    public AbstractShape getChosenShape() {
        return chosenShape;
    }

    public void setChosenShape(AbstractShape chosenShape) {
        this.chosenShape = chosenShape;
        chosenShape.chosen = true;
        chosenShapes.add(chosenShape);
    }

    public void setChooseMore(boolean chooseMore) {
        this.chooseMore = chooseMore;
    }

    public ArrayList<AbstractShape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<AbstractShape> shapes) {
        this.shapes = shapes;
    }

    public Set<AbstractShape> getChosenShapes() {
        return chosenShapes;
    }

    public void setChosenShapes(Set<AbstractShape> chosenShapes) {
        this.chosenShapes = chosenShapes;
    }

    public Operation getOperation() {
        return operation;
    }

    public ArrayList<Command> getUndoCmds() {
        return undoCmds;
    }

    public void addCmd(Command cmd) {
        undoCmds.add(cmd);
    }

    public void removeCmd(Command cmd) {
        undoCmds.remove(cmd);
    }

    public MyDrawPanel() {
        chosenShape = null;
        chosenShapes = new HashSet<AbstractShape>();
        shapes = new ArrayList<AbstractShape>();

        operation = new Operation(this); // 操作是在画板上进行的

        chooseMore = false;
        dragSize = false;   // 是否拖拽调整大小
        setBackground(Color.white);
        addMouseListener(new MouseHandler()); // 监听鼠标点击 滚轮事件
        addMouseMotionListener(new MouseMotionHandler()); // 监听鼠标移动和拖动事件
        addKeyListener(new KeyAdapter() { // 按下ctrl键可以选择多个图元
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    chooseMore = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    chooseMore = false;
                }
            }
        });
    }

    // 绘制所有图元
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(MainWindow.getPenColor());
        for (AbstractShape shape : shapes) {
            shape.draw(g2d);
        }
    }

    // 遍历图元判断鼠标位置是否在图元内部
    private AbstractShape InShape(Point p) {
        for (AbstractShape shape : shapes) {
            // System.out.println("the shape:" + shape);
            if (shape.contains(p))
                return shape;
        }
        return null;
    }

    // 遍历选中的图元看看是否在边缘
    private AbstractShape InEdge(Point p) {
        for (AbstractShape shape : chosenShapes) {
            if (Math.abs(p.x - shape.endx) < 5 &&
                    Math.abs(p.y - shape.endy) < 5) {
                setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                return shape;
            }
        }
        return null;
    }

    // 内部类 监听鼠标的点击事件 滚轮事件等等
    private class MouseHandler extends MouseAdapter {
        @Override
        // 当选中画笔时 点击图元绘制默认大小的图元
        public void mouseClicked(MouseEvent e) {
//            System.out.println("mouse click!");
            MainWindow.setFocus(); // 焦点到画板上
            if (e.getButton() == MouseEvent.BUTTON1) { // 点击鼠标左键
                String currentMode = MainWindow.getCurrentMode();
                Color penColor = MainWindow.getPenColor();
                if (currentMode.equals("null")) {
                    System.out.println("none chosen!");
                } else if (currentMode.equals("copy")) { // 复制图元
                    PasteCommand cmd = new PasteCommand(operation, chosenShapes);
                    cmd.execute(e);
                    undoCmds.add(cmd);
                    System.out.println("undocmd size: " + undoCmds.size());
                    repaint();
                } else { // 创建图元
                    CreateCommand cmd = new CreateCommand(operation, chosenShapes);
                    cmd.execute(currentMode, e);
                    undoCmds.add(cmd);
                    repaint();
                }
                MainWindow.setCurrentMode("null");
                setCursor(Cursor.getDefaultCursor());
                System.out.println("shape's count " + shapes.size());
            } else if (e.getButton() == MouseEvent.BUTTON3) { // 点击鼠标右键
                String input;
                int w, h;
                try {
                    input = JOptionPane.showInputDialog("请输入图元宽");
                    w = Integer.parseInt(input); // 图元的新尺寸
                    input = JOptionPane.showInputDialog("请输入图元高");
                    h = Integer.parseInt(input); // 图元的新尺寸
                    ChangeSizeCommand cmd = new ChangeSizeCommand(operation, chosenShapes, w, h);
                    cmd.execute();
                    undoCmds.add(cmd);
                } catch (NumberFormatException ignored) {
                    ;
                }
                repaint();
            }
        }

        @Override
        // 点击时选中图元
        public void mousePressed(MouseEvent e) {
            int clickX = e.getX();
            // 鼠标点击位置
            int clickY = e.getY();
            String currentMode = MainWindow.getCurrentMode();
            if (currentMode.equals("copy")) { // 复制图元时 点击时不请空chosenShapes
                return;
            } else if ((dragShape = InEdge(e.getPoint())) != null) {
                dragSize = true; // 拖拽调整大小
                dragX = dragShape.endx; // 拖拽初始位置
                dragY = dragShape.endy;
                return;
            } else if (InShape(e.getPoint()) != null) {
                dragMove = true;    // 拖拽移动图元
                dragX = clickX; // 拖拽初始位置
                dragY = clickY;
            }
            // 判断是否选中某个图元
            chosenShape = InShape(e.getPoint());
            if (chosenShape == null) {
                for (AbstractShape shape : shapes) {
                    shape.chosen = false;
                }
                chosenShapes.clear();
            } else {
                chosenShape.chosen = true;
                if (chooseMore) { // 按下CTRL 选择多个
                    chosenShapes.add(chosenShape);
                    System.out.println("chosenshapes' count is " + chosenShapes.size());
                } else {
                    if (!chosenShapes.contains(chosenShape)) { // 点中了新图形 没按CTRL其余的取消选中
                        for (AbstractShape shape : shapes) {
                            if (shape != chosenShape)
                                shape.chosen = false;
                        }
                        chosenShapes.clear();
                        chosenShapes.add(chosenShape);
                    }
                }
            }
            repaint();
            System.out.println("choose shape " + chosenShape);
            System.out.println("mouse press!");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
//            System.out.println("mouse release!");
            int curX = e.getX(), curY = e.getY();
            if (dragSize) {
                dragSize = false;
                dragShape = null;
                int dx = curX - dragX, dy = curY - dragY; // 获取拖拽向量
                ChangeSizeCommand cmd = new ChangeSizeCommand(operation, chosenShapes, dx, dy);
                cmd.executeDrag();
                undoCmds.add(cmd);
                repaint();
            }
            if (dragMove) {
                dragMove = false;
                int dx = curX - dragX, dy = curY - dragY; // 获取拖拽向量
                MoveCommand cmd = new MoveCommand(operation, chosenShapes, dx, dy);
                cmd.execute();
                undoCmds.add(cmd);
                repaint();
            }
        }

    }

    // 内部类 监听鼠标的移动和拖动事件
    private class MouseMotionHandler implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        // 根据鼠标实时位置改变光标类型
        public void mouseMoved(MouseEvent e) {
//            System.out.println("mouse move!");
            if (!MainWindow.getCurrentMode().equals("null")) { // 选中画笔
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            } else { // 选择状态
                if (InShape(e.getPoint()) == null)
                    setCursor(Cursor.getDefaultCursor());
                else
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                InEdge(e.getPoint());
            }
            MainWindow.getStatusBar().setText("坐标:[" + e.getX() + "," + e.getY() + "]像素");
        }

    }
}
