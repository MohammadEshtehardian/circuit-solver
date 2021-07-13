import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
public class DrawDiode extends JPanel {
    private int x11, x12, y11, y12, x3, y3, a = 20, c;
    private String matn;
    DrawDiode(int x1, int y1, int x2, int y2, int b , String text) {
        this.x11 = x1-5;
        this.x12 = x2-5;
        this.y11 = y1-35;
        this.y12 = y2-35;
        this.c = b;
        this.matn=text.intern();
        setBounds(0, 0, 5000, 5000);
    }
    @Override
    public void paint(Graphics g) {
        int x1 = x11, x2 = x12, y1 = y11, y2 = y12, jahat = c;
        g.setColor(Color.BLACK);
        g.fillArc(x1 - 5, y1 - 5, 10, 10, 0, 360);
        g.fillArc(x2 - 5, y2 - 5, 10, 10, 0, 360);
        if (y1 == y2) {
            g.drawLine(x1, y1, x1 + (x2 - x1) / 2 - a, y1);
            g.drawLine(x1 + (x2 - x1) / 2 + a, y1, x2, y2);
            g.drawString(matn,x1 + (x2 - x1) / 2 - 2*a, y1-10);
            if (jahat==1){
                g.drawLine(x1 + (x2 - x1) / 2 - a, y1,x1 + (x2 - x1) / 2 + 9*a/10, y1+a);
                g.drawLine(x1 + (x2 - x1) / 2 - a, y1,x1 + (x2 - x1) / 2 + 9*a/10, y1-a);
                g.fillRect(x1 + (x2 - x1) / 2 + 9*a/10, y1-a,a/10,2*a);
                g.fillRect(x1 + (x2 - x1) / 2 - a, y1-a,a/10,2*a);
            }
            else if (jahat==2){
                g.drawLine(x1 + (x2 - x1) / 2 - a, y1+a,x1 + (x2 - x1) / 2 + 9*a/10, y1);
                g.drawLine(x1 + (x2 - x1) / 2 - a, y1-a,x1 + (x2 - x1) / 2 + 9*a/10, y1);
                g.fillRect(x1 + (x2 - x1) / 2 + 9*a/10, y1-a,a/10,2*a);
                g.fillRect(x1 + (x2 - x1) / 2 - a, y1-a,a/10,2*a);
            }
        }
        else if (x1 == x2) {
            g.drawLine(x1, y1, x1, y1 + (y2 - y1) / 2 - a);
            g.drawLine(x1, y1 + (y2 - y1) / 2 + a, x1, y2);
            g.drawString(matn,x1-a, y1 + (y2 - y1) / 2 - a -5);
            if (jahat==1){
                g.drawLine(x1,y1 + (y2 - y1) / 2 - a,x1+a,y1 + (y2 - y1) / 2 + 9*a/10);
                g.drawLine(x1,y1 + (y2 - y1) / 2 - a,x1-a,y1 + (y2 - y1) / 2 + 9*a/10);
                g.fillRect(x1-a,y1 + (y2 - y1) / 2 - a,2*a,a/10);
                g.fillRect(x1-a,y1 + (y2 - y1) / 2 + 9*a/10,2*a,a/10);
            }
            else if (jahat==2){
                g.drawLine(x1+a,y1 + (y2 - y1) / 2 - a,x1,y1 + (y2 - y1) / 2 + 9*a/10);
                g.drawLine(x1-a,y1 + (y2 - y1) / 2 - a,x1,y1 + (y2 - y1) / 2 + 9*a/10);
                g.fillRect(x1-a,y1 + (y2 - y1) / 2 - a,2*a,a/10);
                g.fillRect(x1-a,y1 + (y2 - y1) / 2 + 9*a/10,2*a,a/10);
            }
        }
    }
}
