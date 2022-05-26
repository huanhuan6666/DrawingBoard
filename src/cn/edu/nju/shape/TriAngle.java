package cn.edu.nju.shape;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

public class TriAngle extends AbstractShape {

    private Line2D.Double cathetiH, cathetiV, hypotenuse;
    private Point p1, p2, p3;
    private GeneralPath path;    // 使用三个点构建一个三角形

    @Override
    public void update() {
        updateBound();
    }

    @Override
    public void changeSize(int w, int h) {
        endx = beginx + w;
        endy = beginy + h;
        update();
    }

    public TriAngle(int bx, int by, int ex, int ey, Color c) {
        super(bx, by, ex, ey, c);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(color);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GeneralPath path;
        path = new GeneralPath();
        path.moveTo(beginx, beginy);
        path.lineTo(endx, endy);
        path.lineTo(beginx, endy);

        if(chosen) {
            g2d.setPaint(Color.red);
            final float[] dash1 = {10.0f};
            BasicStroke dashed =
                    new BasicStroke(5.0f,
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER,
                            10.0f, dash1, 0.0f);
            g2d.setStroke(dashed);
            g2d.draw(boundingRect);
        }
        else {
            g2d.setPaint(Color.black);
            g2d.draw(path);
        }
        g2d.setStroke(new BasicStroke());
        g2d.setPaint(color);
        g2d.fill(path);

    }

    @Override
    public boolean contains(Point p) {
        GeneralPath path;
        path = new GeneralPath();
        path.moveTo(beginx, beginy);
        path.lineTo(endx, endy);
        path.lineTo(beginx, endy);
        return path.contains(p);
    }
}