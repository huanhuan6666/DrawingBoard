package cn.edu.nju.Command;

import cn.edu.nju.shape.AbstractShape;
import cn.edu.nju.widget.Operation;

import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class CreateCommand implements Command {
    private Operation operation = null; // 操作执行者
    private Set<AbstractShape> operandShapes; // 操作数 最终指向新图元

    public CreateCommand(Operation o, Set<AbstractShape> c) {
        operation = o;
        // operandShapes = new HashSet<AbstractShape>(c); // 深拷贝一份
    }

    @Override
    public void execute(MouseEvent e) {
    }

    public void execute(String type, MouseEvent e) {
        AbstractShape newShape = operation.add(type, e);
        operandShapes = new HashSet<AbstractShape>();
        operandShapes.add(newShape);
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {
        operation.delete(operandShapes);
    }
}
