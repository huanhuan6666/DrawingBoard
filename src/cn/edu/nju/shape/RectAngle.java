package cn.edu.nju.shape;

import java.awt.*;
import java.awt.geom.Rectangle2D;

// 矩形
public class RectAngle extends AbstractShape{
    public RectAngle(int bx, int by, int ex, int ey, Color c) {
        super(bx, by, ex, ey, c);
    }

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

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Color edge = new Color(color.getRed()+10, color.getGreen()+10, color.getBlue()+10);
        //g2d.setPaint(edge);
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
            g2d.draw(boundingRect);
        }
        g2d.setStroke(new BasicStroke());
        g2d.setPaint(color);
        g2d.fill(boundingRect);
    }

    @Override
    public boolean contains(Point p) {
        return boundingRect.contains(p);
    }
}
