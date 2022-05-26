package cn.edu.nju.Command;

import java.awt.event.MouseEvent;

public interface Command {
    public void execute(MouseEvent e); // 使用鼠标执行的命令
    public void execute(); // 键盘执行的命令
    public void undo(); // 撤销命令
}
