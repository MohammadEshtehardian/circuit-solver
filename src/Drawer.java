import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Drawer extends JFrame implements MouseListener {

    private ArrayList<Double> f;
    private double finalTime;
    private double dt;
    private String yLabel;
    final boolean[] clicked = {false};
    private double t1 ;
    private double t2 ;
    private int scale;
    private int mouseX;
    private int mouseY;
    private JTextField textFieldX;
    private JTextField textFieldY;
    private JComboBox<Integer> comboBox;
    private JTextField textField;
    private JTextField textField1;
    private JButton button;
    private JButton button1;
    private JTextField textFieldYOnGraph;
    private JTextField textFieldForAVG;
    private JTextField textFieldForRMS;
    private JTextField textFieldForMin;
    private JTextField textFieldForMax;

    Drawer(ArrayList<Double> f, double finalTime, double dt, String yLabel){
        this.scale = 5;
        this.f = new ArrayList<>(f);
        this.finalTime = finalTime;
        this.dt = dt;
        this.yLabel = yLabel;
        this.t1 = 0;
        this.t2 = finalTime;
        clicked[0] = false;
        setLayout(null);
        comboBox = new JComboBox<>();
        comboBox.addItem(1);
        comboBox.addItem(2);
        comboBox.addItem(3);
        comboBox.addItem(4);
        comboBox.addItem(5);
        comboBox.setBounds(800,900,100,30);
        add(comboBox);
        textField = new JTextField();
        textField.setBounds(120,900,50,30);
        textField.setFont(new Font("Dialog",Font.BOLD,12));
        textField.setText(toString(t1));
        add(textField);
        textField1 = new JTextField();
        textField1.setBounds(200,900,50,30);
        textField1.setFont(new Font("Dialog",Font.BOLD,12));
        textField1.setText(toString(t2));
        add(textField1);
        textFieldForAVG = new JTextField();
        textFieldForAVG.setBounds(300,900,50,30);
        add(textFieldForAVG);
        textFieldForRMS = new JTextField();
        textFieldForRMS.setBounds(400,900,50,30);
        textFieldForRMS.setEditable(false);
        add(textFieldForRMS);
        textFieldForMin = new JTextField();
        textFieldForMax = new JTextField();
        textFieldForMin.setBounds(500,900,50,30);
        textFieldForMax.setBounds(600,900,50,30);
        textFieldForMin.setEditable(false);
        textFieldForMax.setEditable(false);
        add(textFieldForMin);
        add(textFieldForMax);
        textFieldX = new JTextField("Time");
        textFieldY = new JTextField(yLabel);
        textFieldYOnGraph = new JTextField(yLabel + "On Graph");
        textFieldX.setEditable(false);
        textFieldY.setEditable(false);
        textFieldYOnGraph.setEditable(false);
        textFieldYOnGraph.setBounds(130,60,100,30);
        add(textFieldYOnGraph);
        textFieldX.setBounds(20,20,100,30);
        add(textFieldX);
        textFieldY.setBounds(20,60,100,30);
        add(textFieldY);
        button = new JButton("Grid");
        button.setBounds(800,70,100,30);
        add(button);
        button1 = new JButton("Draw");
        button1.setVisible(true);
        button1.setBounds(800,20,100,30);
        add(button1);
        getContentPane().setBackground(new Color(190,190,190));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000,1000);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g){
        super.paintComponents(g);
        g.setColor(new Color(10,100,20));
        g.drawLine(100,200,100,900);
        g.drawLine(100,550,900,550);
        g.drawLine(100,200,105,205);
        g.drawLine(100,200,95,205);
        g.drawLine(900,550,895,555);
        g.drawLine(900,550,895,545);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.PLAIN , 15));
        g.drawString("from",95,960);
        g.drawString("to",185,960);
        g.drawString("AVG",270,960);
        g.drawString("RMS",370,960);
        g.drawString("Min",470,960);
        g.drawString("Max",570,960);
        g.drawString("Scale",770,960);
        g.setFont(new Font("Serif", Font.BOLD , 20));
        g.drawString(yLabel+"-Time Graph",getWidth()/2-60,100);
        g.drawString(yLabel,100,180);
        g.drawString("Time",920,550);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clicked[0]) clicked[0] = false;
                else clicked[0] = true;
                repaint();
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t1 = toDouble(textField.getText());
                t2 = toDouble(textField1.getText());
                scale = (int) comboBox.getSelectedItem();
                repaint();
            }
        });
        if(clicked[0]) {
            g.setColor(Color.black);
            for (int i = 850; i >= 250; i -= 50) {
                if (i == 550) continue;
                g.drawLine(100, i, 900, i);
            }
            for (int i = 150; i <= 850; i += 50) g.drawLine(i, 200, i, 900);
        }
        g.setColor(Color.BLUE);
        ArrayList<Double> f1 = new ArrayList<>();
        for (double i = t1 ; i <= t2 + dt ; i += dt){
            int n = (int)(Math.round(i/dt));
            f1.add(f.get(n));
        }
        for(double time = t1 ; time < t2  ; time+=dt){
            int x1 = 100 + (int)((time - t1)/(t2 - t1)*750);
            int x2 = 100 + (int)((time - t1 + dt)/(t2 - t1)*750);
            int n = (int)(Math.round(time/dt)) - (int)(Math.round(t1/dt));
            if(550 - min(f1)/max(f1)*300*scale/5.0 <= 850 && max(f1) > 0) {
                int y1 = 550 - (int) (f1.get(n) / max(f1) * 300 * scale/5.0);
                int y2 = 550 - (int) (f1.get(n + 1) / max(f1) * 300 * scale/5.0);
                g.drawLine(x1, y1, x2, y2);
            }
            else {
                int y1 = 550 + (int)(f1.get(n)/min(f1)*300 * scale/5.0);
                int y2 = 550 + (int)(f1.get(n + 1) / min(f1) *300 * scale/5.0);
                g.drawLine(x1, y1, x2, y2);
            }
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif",Font.PLAIN,12));
        if(550 - min(f1)/max(f1)*300 * scale/5.0 <= 850 && max(f1) > 0){
            for (int i = 850 ; i >= 250 ; i -= 50){
                g.drawLine(95,i,105,i);
                String B;
                B = toString(-(i-550)*max(f1)/300/scale*5.0);
                g.drawString(B,50,i);
            }
        }
        else {
            for (int i = 850 ; i >= 250 ; i -= 50){
                g.drawLine(95,i,105,i);
                String B;
                B = toString((i-550)*min(f1)/300/scale*5.0);
                g.drawString(B,50,i);
            }
        }
        Calculation calculation = new Calculation();
        double avg = calculation.integral(f,dt,t2) - calculation.integral(f,dt,t1);
        avg /= (t2 - t1);
        textFieldForAVG.setText(toString(avg));
        textFieldForAVG.setEditable(false);
        textFieldForRMS.setText(toString(calculation.RMS(f,dt,t2,t1)));
        textFieldForMax.setText(toString(max(f1)));
        textFieldForMin.setText(toString(min(f1)));
        addMouseListener(this);
    }

    public double max(ArrayList<Double> f){
        double max = 0;
        int s = 0;
        for (int i = 0 ; i < f.size() ; i++){
            for (Double aDouble : f) {
                if (f.get(i) >= aDouble) s++;
            }
            if(s == f.size()) max = f.get(i);
            else s = 0;
        }
        return max;
    }

    public double min(ArrayList<Double> f){
        double min = 0;
        int s = 0;
        for (int i = 0 ; i < f.size() ; i++){
            for (Double aDouble : f) {
                if (f.get(i) <= aDouble) s++;
            }
            if(s == f.size()) min = f.get(i);
            else s = 0;
        }
        return min;
    }

    public String toString(double x){
        String A = "0";
        if(x >= 0) {
            if (x >= 1e-1 && x < 1) A = String.format("%.1fm", x / 1e-3);
            else if (x >= 1e-2 && x < 1e-1) A = String.format("%.2fm", x / 1e-3);
            else if (x >= 1e-3 && x < 1e-2) A = String.format("%.3fm", x / 1e-3);
            else if (x >= 1 && x < 10) A = String.format("%.3f", x);
            else if (x >= 10 && x < 100) A = String.format("%.2f", x);
            else if (x >= 100 && x < 1000) A = String.format("%.1f", x);
            else if (x >= 1e-4 && x < 1e-3) A = String.format("%.1fu", x / 1e-6);
            else if (x >= 1e-5 && x < 1e-4) A = String.format("%.2fu", x / 1e-6);
            else if (x >= 1e-6 && x < 1e-5) A = String.format("%.3fu", x / 1e-6);
            else if (x >= 1e3 && x < 1e4) A = String.format("%.3fk", x / 1e3);
            else if (x >= 1e4 && x < 1e5) A = String.format("%.2fk", x / 1e3);
            else if (x >= 1e5 && x < 1e6) A = String.format("%.1fk", x / 1e3);
            else if (x >= 1e6 && x < 1e7) A = String.format("%.3fM", x / 1e6);
            else if (x >= 1e7 && x < 1e8) A = String.format("%.2fM", x / 1e6);
            else if (x >= 1e8 && x < 1e9) A = String.format("%.1fM", x / 1e6);
            else if (x >= 1e9 && x < 1e10) A = String.format("%.3fG", x / 1e9);
            else if (x >= 1e10 && x < 1e11) A = String.format("%.2fG", x / 1e9);
            else if (x >= 1e11 && x < 1e12) A = String.format("%.1fG", x / 1e9);
            else if (x >= 1e-7 && x < 1e-6) A = String.format("%.1fn", x / 1e-9);
            else if (x >= 1e-8 && x < 1e-7) A = String.format("%.2fn", x / 1e-9);
            else if (x >= 1e-9 && x < 1e-8) A = String.format("%.3fn", x / 1e-9);
            else if (x >= 1e-10 && x < 1e-9) A = String.format("%.1fp", x / 1e-12);
            else if (x >= 1e-11 && x < 1e-10) A = String.format("%.2fp", x / 1e-12);
            else if (x < 1e-11 && x >= 1e-15) A = String.format("%.3fp", x / 1e-12);
        }
        else if(x < 0) {
            A = "-" + toString(Math.abs(x));
        }

        return A;
    }

    public double toDouble(String s){
        double x = 0;
        String A;
        if(s.charAt(s.length()-1) == 'p'){
            A = s.substring(0,s.indexOf('p'));
            x = Double.parseDouble(A) * 1e-12;
        }
        else if(s.charAt(s.length()-1) == 'n'){
            A = s.substring(0,s.indexOf('n'));
            x = Double.parseDouble(A) * 1e-9;
        }
        else if(s.charAt(s.length()-1) == 'u'){
            A = s.substring(0,s.indexOf('u'));
            x = Double.parseDouble(A) * 1e-6;
        }
        else if(s.charAt(s.length()-1) == 'm'){
            A = s.substring(0,s.indexOf('m'));
            x = Double.parseDouble(A) * 1e-3;
        }
        else if(s.charAt(s.length()-1) == 'k'){
            A = s.substring(0,s.indexOf('k'));
            x = Double.parseDouble(A) * 1e3;
        }
        else if(s.charAt(s.length()-1) == 'M'){
            A = s.substring(0,s.indexOf('M'));
            x = Double.parseDouble(A) * 1e6;
        }
        else if(s.charAt(s.length()-1) == 'G'){
            A = s.substring(0,s.indexOf('G'));
            x = Double.parseDouble(A) * 1e9;
        }
        else x = Double.parseDouble(s);
        return x;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        double x = (mouseX - 100)*1.0/750 * (t2 - t1) + t1;
        double y = (550 - mouseY)*1.0 / (300 * scale/5.0) * max(f);
        int n = (int)(Math.round(x/dt));
        double yOnGraph = 0;
        if( n < f.size() && x > t1 ) yOnGraph = f.get(n);
        textFieldX.setText(toString(x));
        textFieldY.setText(toString(y));
        if(n < f.size() && x > t1) textFieldYOnGraph.setText(toString(yOnGraph));
        else textFieldYOnGraph.setText("Out Of Bond");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
