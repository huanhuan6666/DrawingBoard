package cn.edu.nju.shape;

import java.awt.*;
import java.awt.geom.Ellipse2D;

// 椭圆图形
public class Ellipse extends AbstractShape{
    private Ellipse2D.Double ellipse;

    public Ellipse(int bx, int by, int ex, int ey, Color c) {
        super(bx, by, ex, ey, c);
        ellipse = new Ellipse2D.Double();
        ellipse.setFrame(boundingRect);
    }

    @Override
    public void update() {
        updateBound();
        ellipse.setFrame(boundingRect);
    }

    @Override
    public void changeSize(int w, int h) {
        endx = beginx + w;
        endy = beginx + h;
        update();
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
            g2d.draw(ellipse);
        }
        g2d.setStroke(new BasicStroke());
        g2d.setPaint(color);
        g2d.fill(ellipse);
    }

    @Override
    public boolean contains(Point p) {
        return ellipse.contains(p);
    }
}
