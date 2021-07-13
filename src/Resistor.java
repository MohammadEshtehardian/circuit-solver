import java.util.ArrayList;

public class Resistor extends Branch {

    public Resistor(Node node1 , Node node2 , double value , String name){
        this.node1 = new Node(node1);
        this.node2 = new Node(node2);
        this.value = value;
        this.name = name;
        this.voltages = new ArrayList<Double>();
        this.currents = new ArrayList<Double>();
        this.powers = new ArrayList<Double>();
    }

    public Resistor(){
        this.voltages = new ArrayList<Double>();
        this.currents = new ArrayList<Double>();
        this.powers = new ArrayList<Double>();
    }

    public Resistor(Resistor resistor){
        this.name = resistor.name;
        this.node1 = new Node(resistor.node1);
        this.node2 = new Node(resistor.node2);
        this.value = resistor.value;
        this.currents = resistor.currents;
        this.voltages = resistor.voltages;
        this.powers = resistor.powers;
    }

    public boolean equals(Resistor resistor){
        return this.name.equals(resistor.name);
    }

    @Override
    double voltage() {
        return node1.getVoltage().get(node1.getVoltage().size()-1) - node2.getVoltage().get(node2.getVoltage().size()-1);
    }

    @Override
    double current() {
        return voltage()/value;
    }

    @Override
    double current(double t, double dt) {
        return current();
    }

    @Override
    double power() {
        return current()*voltage();
    }

    @Override
    double power(double t, double dt) {
        return power();
    }

}
