package cn.edu.nju.shape;

import java.awt.*;
import java.awt.geom.Line2D;

public class Line extends AbstractShape {
    private Line2D.Double line;

    public Line(int bx, int by, int ex, int ey, Color c) {
        super(bx, by, ex, ey, c);
        line = new Line2D.Double(beginx, beginy, endx, endy);
    }

    @Override
    public void update() {
        updateBound();
        line.setLine(beginx, beginy, endx, endy);
    }

    @Override
    public void changeSize(int w, int h) {
        endx = beginx + w;
        endy = beginy + h;
        update();
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(color);
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
        g2d.setStroke(new BasicStroke());
        System.out.println("line: " + beginx + endx);
        g2d.setPaint(color);
        g2d.draw(line);
    }

    @Override
    public boolean contains(Point p) {
        return boundingRect.contains(p.x, p.y);
    }
}
