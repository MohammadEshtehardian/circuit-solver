import java.util.ArrayList;

public class Capacitor extends Branch {

    private double initialVoltage = 0;

    public void setInitialVoltage(double initialVoltage) {
        this.initialVoltage = initialVoltage;
    }

    public double getInitialVoltage() {
        return initialVoltage;
    }

    public Capacitor(Node node1 , Node node2 , double value , String name){
        this.node1 = new Node(node1);
        this.node2 = new Node(node2);
        this.value = value;
        this.name = name;
        this.powers = new ArrayList<Double>();
        this.voltages = new ArrayList<Double>();
        this.currents = new ArrayList<Double>();
    }

    public Capacitor(Capacitor capacitor){
        this.node1 = new Node(capacitor.node1);
        this.node2 = new Node(capacitor.node2);
        this.name = capacitor.name;
        this.value = capacitor.value;
        this.voltages = capacitor.voltages;
        this.currents = capacitor.currents;
        this.powers = capacitor.powers;
    }

    public Capacitor(){
        voltages = new ArrayList<Double>();
        currents = new ArrayList<Double>();
        powers = new ArrayList<Double>();
    }

    public boolean equals(Capacitor capacitor){
        if(capacitor.name.equals(this.name)) return true;
        return false;
    }

    @Override
    double voltage() {
        return node1.getVoltage().get(node1.getVoltage().size()-1) - node2.getVoltage().get(node2.getVoltage().size()-1);
    }

    @Override
    double current() {
        return 0;
    }

    @Override
    double current(double t , double dt) {
        Calculation calculation = new Calculation();
        return value*calculation.derivation(voltages , dt , t);
    }

    @Override
    double power() {
        return 0;
    }

    @Override
    double power(double t, double dt) {
        return current(t,dt)*voltage();
    }
}
