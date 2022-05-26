package cn.edu.nju.shape;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;

// 抽象图形类
public abstract class AbstractShape implements Serializable {
    public int beginx, beginy, endx, endy; // 起点和终点坐标

    public Rectangle2D.Double boundingRect; // 边框矩形
    public Color color; // 图元的颜色
    public boolean chosen;  // 被选中

    public AbstractShape() {

    }


    //使用序列化技术实现深拷贝
    public AbstractShape deepClone() throws IOException, ClassNotFoundException, OptionalDataException {
        //将对象写入流中
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oss = new ObjectOutputStream(bao);
        oss.writeObject(this);
        //将对象从流中取出
        ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (AbstractShape) ois.readObject();
    }

    public AbstractShape(AbstractShape as) {
        beginx = as.beginx;
        beginy = as.beginy;
        endx = as.endx;
        endy = as.endy;
    }

    public AbstractShape(int bx, int by, int ex, int ey, Color c) {
        beginx = bx;
        beginy = by;
        endx = ex;
        endy = ey;
        color = new Color(c.getRGB());
        boundingRect = new Rectangle2D.Double(beginx, beginy, Math.abs(endx - beginx), Math.abs(endy - beginy));
    }

    public void translate(int dx, int dy) {
        beginx += dx;
        beginy += dy;
        endx += dx;
        endy += dy;
        update();
    }

    public void updateBound() { // 更新边框矩形
        boundingRect.setRect(Math.min(beginx, endx), Math.min(beginy, endy),
                Math.abs(endx - beginx), Math.abs(endy - beginy));
    }

    public void setRect(Shape rect) { // 改变图元位置or大小为rect框内
        beginx = rect.beginx;
        beginy = rect.beginy;
        endx = rect.endx;
        endy = rect.endy;
        update();
    }

//    public void updateBound(Shape shape) { // 更新边框矩形
//        beginx = shape.beginx;
//        beginy = shape.beginy;
//        endx = shape.endx;
//        endy = shape.endy;
//
//        boundingRect.setRect(shape.beginx,shape.beginy,
//                Math.abs(shape.endx-shape.beginx),Math.abs(shape.endy-shape.beginy));
//    }


    public abstract void draw(Graphics2D g); // 绘制该图元

    public abstract boolean contains(Point p); // 判断点p是否在图元内部

    public abstract void update();  // 根据shape更新坐标信息

    public abstract void changeSize(int w, int h); // 改变图元大小

}


