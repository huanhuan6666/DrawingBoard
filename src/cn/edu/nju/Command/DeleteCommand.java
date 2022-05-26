package cn.edu.nju.Command;

import cn.edu.nju.shape.AbstractShape;
import cn.edu.nju.widget.Operation;

import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class DeleteCommand implements Command {
    private Operation operation = null; // 操作执行者
    private Set<AbstractShape> operandShapes; // 操作数 是一份深拷贝

    public DeleteCommand(Operation o, Set<AbstractShape> c) {
        operation = o;
        operandShapes = new HashSet<AbstractShape>(c); // 深拷贝一份
//        operandShapes = c;
    }

    @Override
    public void execute(MouseEvent e) {

    }

    @Override
    public void execute() { // 删除操作数图元
        operation.delete(operandShapes);
    }

    @Override
    public void undo() {
        operation.add(operandShapes);
    }
}
