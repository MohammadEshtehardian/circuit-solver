import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame implements ActionListener {

    private JButton button;
    private JButton button1;
    private JTextField inputTextField;
    private JTextField outputTextField;
    private JTextField drawTextField;
    private String inputAddress;
    private String outputAddress;
    private Circuit circuit;
    private boolean solved = false;
    private TextPanel textPanel;
    private JButton loadButton;
    private JTextField jTextField;
    private JButton calculation;

    public Circuit getCircuit() {
        return circuit;
    }

    public String getInputAddress() {
        return inputAddress;
    }

    public String getOutputAddress() {
        return outputAddress;
    }

    public MainFrame(){
        super("A&M Circuit Solver");
        setLayout(null);
        setSize(1800,1000);
        getContentPane().setBackground(Color.lightGray);
        button = new JButton("RUN");
        button.setBounds(1650,30,100,30);
        add(button);
        button1 = new JButton("DRAW");
        button1.setBounds(1500,30,100,30);
        add(button1);
        inputTextField = new JTextField("Input File Address");
        inputTextField.setFont(new Font("Serif",Font.PLAIN,14));
        outputTextField = new JTextField("Output File Address");
        outputTextField.setFont(new Font("Serif",Font.PLAIN,14));
        inputTextField.setBounds(50,30,200,30);
        outputTextField.setBounds(50,70,200,30);
        add(inputTextField);
        add(outputTextField);
        drawTextField = new JTextField("Please Enter Your Element Name");
        drawTextField.setFont(new Font("Serif",Font.PLAIN,14));
        drawTextField.setBounds(50,110,200,30);
        add(drawTextField);
        inputTextField.addActionListener(this);
        button.addActionListener(this);
        button1.addActionListener(this);
        loadButton = new JButton("LOAD");
        loadButton.addActionListener(this);
        loadButton.setBounds(1350,30,100,30);
        add(loadButton);
        jTextField = new JTextField("Please Enter your node's names");
        jTextField.setBounds(300,70,200,30);
        jTextField.setFont(new Font("Serif",Font.PLAIN,14));
        add(jTextField);
        calculation = new JButton("Calculate");
        calculation.setBounds(1200,30,100,30);
        add(calculation);
        calculation.addActionListener(this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) (e.getSource());
        if(jButton == button){
            try {
                textPanel.run();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            inputAddress = inputTextField.getText();
            outputAddress = outputTextField.getText();
            File output = new File(outputAddress);
            File input = new File(inputAddress);
            Scan scan = null;
            try {
                scan = new Scan(input);
                new DrawCircuit(input);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            assert scan != null;
            if (scan.zamin==0){
                System.out.println("-2");
            }
            circuit = new Circuit(scan.finalTime,scan.dt,scan.di,scan.dv,scan.nodes,scan.resistors,scan.currentSources,scan.capacitors,scan.inductors,scan.gCurrentSources,scan.fCurrentSources,scan.voltageSources,scan.eVoltageSources,scan.hVoltageSources,scan.diodes);
            circuit.solveWithDiode();
            try {
                circuit.print(output);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(this,"Circuit has been solved","A&M Message",JOptionPane.INFORMATION_MESSAGE);
            solved = true;
        }
        else if(jButton == button1){
            if(solved) {
                String drawString = drawTextField.getText();
                circuit.drawer(drawString,this);
            }
            else {
                System.out.println("Circuit Not Solved");
            }
        }
        else if(jButton == loadButton){
            inputAddress = inputTextField.getText();
            textPanel = new TextPanel(new File(inputAddress));
            JScrollPane scrollPane = new JScrollPane(textPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBounds(190,190,1410,610);
            add(scrollPane);
            repaint();
        }
        else if(jButton == calculation){
            String S = jTextField.getText();
            if(!S.equals("END")){
                String A , B , name1 , name2;
                double time;
                A = S.substring(0,S.indexOf(' '));
                B = S.substring(S.indexOf(' '));
                B = B.trim();
                name1 = A;
                name2 = B.substring(0,B.indexOf(' '));
                B = B.substring(B.indexOf(' '));
                B = B.trim();
                time = toDouble(B);
                Node node = new Node();
                int n1 = node.search(name1,circuit.getNodes());
                int n2 = node.search(name2,circuit.getNodes());
                if(n1 == -1 || n2 == -1) JOptionPane.showMessageDialog(this,"ERROR","A&M Message",JOptionPane.WARNING_MESSAGE);
                else {
                    double voltage = circuit.getNodes().get(n1).getVoltage().get((int)(Math.round(time/circuit.getDt())-1))-circuit.getNodes().get(n2).getVoltage().get((int)(Math.round(time/circuit.getDt())-1));
                    JOptionPane.showMessageDialog(this,toString(voltage),"A&M Message",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
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
}
