package cn.edu.nju.Command;

import cn.edu.nju.shape.AbstractShape;
import cn.edu.nju.widget.Operation;

import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class PasteCommand implements Command {
    private Operation operation = null; // 操作执行者
    private Set<AbstractShape> operandShapes; // 操作数 是一份深拷贝 最终指向拷贝后的结果图元们

    public PasteCommand(Operation o, Set<AbstractShape> c) {
        operation = o;
        operandShapes = new HashSet<AbstractShape>(c); // 深拷贝一份
//        operandShapes = c;
    }

    @Override
    public void execute(MouseEvent e) { // 鼠标复制
        Set<AbstractShape> res = operation.paste(e, operandShapes);
        operandShapes = new HashSet<AbstractShape>(res);  // 拷贝后的结果
    }

    @Override
    public void execute() { // 键盘复制
        Set<AbstractShape> res = operation.paste(operandShapes);
        operandShapes = new HashSet<AbstractShape>(res);  // 拷贝后的结果
    }

    @Override
    public void undo() {
        operation.delete(operandShapes);
    }
}
