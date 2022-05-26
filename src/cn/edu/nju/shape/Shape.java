package cn.edu.nju.shape;

import java.awt.*;

public class Shape extends AbstractShape {
    public Shape(AbstractShape as) {
        super(as);
    }

    public Shape(int bx, int by, int ex, int ey) {
        super();
        beginx = bx;
        beginy = by;
        endx = ex;
        endy = ey;
    }


    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    public boolean contains(Point p) {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void changeSize(int w, int h) {
        endx = beginx + w;
        endy = beginy + h;
        update();
    }
}
