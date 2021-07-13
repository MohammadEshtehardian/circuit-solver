import javax.swing.*;
import java.awt.*;

public class DrawWire extends JPanel {
    private int x11, x12, y11, y12;

    DrawWire(int x1, int y1, int x2, int y2) {
        this.x11 = x1-5;
        this.x12 = x2-5;
        this.y11 = y1-35;
        this.y12 = y2-35;
        setBounds(0,0,5000,5000);
    }
    @Override
    public void paint(Graphics g){
        g.drawLine(x11,y11,x12,y12);
    }
}
