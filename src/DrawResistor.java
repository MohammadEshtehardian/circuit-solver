import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
public class DrawResistor extends JPanel {
    private int x11, x12, y11, y12, x3, y3, a = 35;
    private String matn;
    DrawResistor(int x1, int y1, int x2, int y2 , String text) {
        this.x11 = x1-5;
        this.x12 = x2-5;
        this.y11 = y1-35;
        this.y12 = y2-35;
        this.matn=text.intern();
        setBounds(0,0,5000,5000);
    }

    @Override
    public void paint(Graphics g) {
        int x1 = x11, x2 = x12, y1 = y11, y2 = y12;
        g.setColor(Color.BLACK);
        g.fillArc(x1-5,y1-5,10,10,0,360);
        g.fillArc(x2-5,y2-5,10,10,0,360);
        if ( x1 == x2){
            g.drawLine(x1, y1, x1, y1 + (y2 - y1) / 2 - a);
            g.drawLine(x1, y1 + (y2 - y1) / 2 + a, x1, y2);
            g.drawString(matn,x1-a, y1 + (y2 - y1) / 2 - a -5);
            y3=y1+(y2-y1)/2-a;
            int j=0;
            x3=x1;
            for (int i=a/5 ; i<=2*a ; i+=(a/5)){
                y2=y3;
                y3=y3+a/5;
                j=j+1;
                x1=x3;
                if (j%3==1){
                    x3=x2;
                }
                else if (j%3==0){
                    x3=x2+a/2;
                }
                else {
                    x3=x2-a/2;
                }
                g.drawLine(x1,y2,x3,y3);
            }
        }
        else if (y1 == y2){
            g.drawLine(x1, y1, x1 + (x2 - x1) / 2 - a, y1);
            g.drawLine(x1 + (x2 - x1) / 2 + a, y1, x2, y2);
            g.drawString(matn,x1 + (x2 - x1) / 2 - 5*a/3, y1-10);
            x3=x1+(x2-x1)/2-a;
            int j=0;
            y3=y1;
            for (int i=a/5 ; i<=2*a ; i+=(a/5)){
                x2=x3;
                x3=x3+a/5;
                j=j+1;
                y1=y3;
                if (j%3==1){
                    y3=y2;
                }
                else if (j%3==0){
                    y3=y2+a/2;
                }
                else {
                    y3=y2-a/2;
                }
                g.drawLine(x2,y1,x3,y3);
            }
        }
    }
}
