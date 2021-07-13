import java.util.ArrayList;

public class Inductor extends Branch {

    private double initialCurrent;

    public void setInitialCurrent(double initialCurrent) {
        this.initialCurrent = initialCurrent;
    }

    public double getInitialCurrent() {
        return initialCurrent;
    }

    public Inductor(){
        voltages = new ArrayList<Double>();
        currents = new ArrayList<Double>();
        powers = new ArrayList<Double>();
    }

    public Inductor(Inductor inductor){
        this.node1 = new Node(inductor.node1);
        this.node2 = new Node(inductor.node2);
        this.value = inductor.value;
        this.name = inductor.name;
        this.initialCurrent = inductor.initialCurrent;
        this.voltages = inductor.voltages;
        this.currents = inductor.currents;
        this.powers = inductor.powers;
    }

    public Inductor(Node node1, Node node2, double value, String name, double initialCurrent){
        this.node1 = new Node(node1);
        this.node2 = new Node(node2);
        this.value = value;
        this.name = name;
        this.initialCurrent = initialCurrent;
        this.voltages = new ArrayList<Double>();
        this.currents = new ArrayList<Double>();
        this.powers = new ArrayList<Double>();
    }

    @Override
    double voltage() {
        return node1.getVoltage().get(node1.getVoltage().size()-1) - node2.getVoltage().get(node2.getVoltage().size()-1);
    }

    @Override
    double current(double t ,double dt) {
        Calculation calculation = new Calculation();
        return calculation.integral(voltages , dt , t) / value + initialCurrent;
    }

    @Override
    double power() {
        return 0;
    }

    @Override
    double power(double t, double dt) {
        return voltage()*current(t,dt);
    }

    @Override
    double current() {
        return 0;
    }
}
