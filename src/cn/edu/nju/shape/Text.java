package cn.edu.nju.shape;

import java.awt.*;

public class Text extends AbstractShape{
    private String fontName, fontSize; // 字体和大小
    private String text;    // 文本内容
    public Text(int bx, int by, int ex, int ey, Color c, String t) {
        super(bx, by, ex, ey, c);
        text = t;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setPaint(color);
        g2d.setFont(new Font(null, Font.ITALIC, 30));
        int width = g2d.getFontMetrics().stringWidth(text);
        int height = g2d.getFontMetrics().getHeight();
        System.out.println("width is " + width);
        boundingRect.setRect(beginx, beginy - height + 5, width, height);
        g2d.drawString(text, beginx, beginy);

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
    }

    @Override
    public boolean contains(Point p) {
        return boundingRect.contains(p.x, p.y);
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
}
