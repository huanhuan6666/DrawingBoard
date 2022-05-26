package cn.edu.nju.widget;

import cn.edu.nju.shape.*;
import cn.edu.nju.shape.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Operation {

    private MyDrawPanel drawPanel;

    public Operation(MyDrawPanel p) {
        drawPanel = p;
    }

    public Set<AbstractShape> paste(MouseEvent e, Set<AbstractShape> chosenShapes) { // 复制粘贴一份chosenShapes
        System.out.println("PASTE!!!" + chosenShapes.size());
        AbstractShape newShape = null;
        Set<AbstractShape> newChosens = new HashSet<AbstractShape>(); // 复制完新的图元被选中
        int dx = 20, dy = 15;
        boolean tmp = false;
        for (AbstractShape shape : chosenShapes) {
            try {
                newShape = shape.deepClone();
                newChosens.add(newShape);
                shape.chosen = false; // 原图元取消选中
                if(!tmp) { // 选中多个图元求其中一个图元的偏移向量
                    dx = e.getX() - newShape.beginx;
                    dy = e.getY() - newShape.beginy;
                    tmp = true;
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            if (newShape != null) {
                newShape.translate(dx, dy);
                drawPanel.getShapes().add(newShape);
            }
        }
        drawPanel.setChosenShapes(newChosens);
        return newChosens;
        // repaint();
    }

    public Set<AbstractShape> paste(Set<AbstractShape> operandShapes) { // 键盘复制粘贴不需要鼠标位置
        System.out.println("PASTE!!!" + operandShapes.size());
        AbstractShape newShape = null;
        Set<AbstractShape> newChosens = new HashSet<AbstractShape>(); // 复制完新的图元被选中
        int dx = 20, dy = 15;
        for (AbstractShape shape : operandShapes) {
            try {
                newShape = shape.deepClone();
                newChosens.add(newShape);
                shape.chosen = false; // 原图元取消选中
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            if (newShape != null) {
                newShape.translate(dx, dy);
                drawPanel.getShapes().add(newShape);
            }
        }
        drawPanel.setChosenShapes(newChosens);
        return newChosens;
    }

    public void delete(Set<AbstractShape> operandShapes) { // 从画布上删除chosenShapes
        for (AbstractShape shape : operandShapes) {
            drawPanel.getShapes().remove(shape);
        }
    }

    public void add(Set<AbstractShape> operandShapes) { // 在画布上添加chosenShapes
        for (AbstractShape shape : operandShapes) {
            drawPanel.getShapes().add(shape);
        }
    }

    public void changeSize(int w, int h, Set<AbstractShape> operandShapes) { // 调整图元大小为size
        for(AbstractShape shape : operandShapes) {
            Shape rect = new Shape(shape.beginx, shape.beginy, shape.beginx+w, shape.beginy+h);
            shape.setRect(rect);
        }
    }

    public void dragSize(int dx, int dy, Set<AbstractShape> operandShapes) { // 拖拽调整大小 拖拽向量为dx dy
        for(AbstractShape shape : operandShapes) {
            Shape rect = new Shape(shape.beginx, shape.beginy, shape.endx+dx, shape.endy+dy);
            shape.setRect(rect);
        }
    }

    public void move(int dx, int dy, Set<AbstractShape> operandShapes) { // 拖拽移动图元 拖拽向量为dx dy
        for(AbstractShape shape : operandShapes) {
            Shape rect = new Shape(shape.beginx+dx, shape.beginy+dy, shape.endx+dx, shape.endy+dy);
            shape.setRect(rect);
        }
    }

    public AbstractShape add(String type, MouseEvent e) { // 在画布e处添加type类型的图元
        Color penColor = MainWindow.getPenColor();
        AbstractShape newShape = null;
        switch (type) {
            case "line": {
                newShape = new Line(e.getX(), e.getY(), e.getX() + 150, e.getY() + 150, penColor);
                System.out.println("choose line!");
                break;
            }
            case "circle": {
                newShape = new Ellipse(e.getX(), e.getY(), e.getX() + 150, e.getY() + 150, penColor);
                System.out.println("choose circle!");
                break;
            }
            case "ellipse": {
                newShape = new Ellipse(e.getX(), e.getY(), e.getX() + 200, e.getY() + 100, penColor);
                System.out.println("choose ellipse!");
                break;
            }
            case "triangle": {
                newShape = new TriAngle(e.getX(), e.getY(), e.getX() + 150, e.getY() + 150, penColor);
                System.out.println("choose triangle!");
                break;
            }
            case "rectangle": {
                newShape = new RectAngle(e.getX(), e.getY(), e.getX() + 150, e.getY() + 150, penColor);
                System.out.println("choose rectangle!");
                break;
            }
            case "text": {
                String input;
                input = JOptionPane.showInputDialog("请输入文字");
                newShape = new Text(e.getX(), e.getY(), e.getX() + 150, e.getY() + 150, penColor, input);
                System.out.println("choose text!");
                break;
            }
        }
        drawPanel.setChosenShape(newShape);
        drawPanel.getShapes().add(newShape);
        return newShape;
    }
}
