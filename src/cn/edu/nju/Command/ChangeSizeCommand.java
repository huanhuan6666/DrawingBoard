package cn.edu.nju.Command;

import cn.edu.nju.shape.AbstractShape;
import cn.edu.nju.shape.Shape;
import cn.edu.nju.widget.Operation;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ChangeSizeCommand implements Command {
//    private Shape rect;
    private int weight, height;
    private Operation operation = null; // 操作执行者
    private Set<AbstractShape> operandShapes; // 操作数 最终指向新图元
    private HashMap<AbstractShape, Shape> oldRect; // 每个图元的旧rect

    public ChangeSizeCommand(Operation o, Set<AbstractShape> c, int w, int h) {
//        rect = s;
        weight = w;
        height = h;
        operation = o;
        operandShapes = new HashSet<AbstractShape>(c); // 深拷贝一份
        oldRect = new HashMap<AbstractShape, Shape>();
        for(AbstractShape shape : operandShapes) {
            Shape each = new Shape(shape.beginx, shape.beginy, shape.endx, shape.endy);
            oldRect.put(shape, each);
        }
    }

    @Override
    public void execute(MouseEvent e) {

    }

    @Override
    public void execute() {
        operation.changeSize(weight, height, operandShapes);
    }

    public void executeDrag() {
        operation.dragSize(weight, height, operandShapes);
    }

    @Override
    public void undo() {
        for(AbstractShape shape : oldRect.keySet()) {
            shape.setRect(oldRect.get(shape));
        }
    }
}
