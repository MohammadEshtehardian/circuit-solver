import java.util.ArrayList;

public class CurrentSource extends Branch{

    private double domain;
    private double frequency;
    private double phase;

    public void setDomain(double domain) {
        this.domain = domain;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public void setPhase(double phase) {
        this.phase = phase;
    }

    public double getDomain() {
        return domain;
    }

    public double getFrequency() {
        return frequency;
    }

    public double getPhase() {
        return phase;
    }

    public CurrentSource(Node node1 , Node node2 , double value , String name , double domain , double frequency , double phase){
        this.node1 = new Node(node1);
        this.node2 = new Node(node2);
        this.value = value;
        this.name = name;
        this.domain = domain;
        this.frequency = frequency;
        this.phase = phase;
        this.voltages = new ArrayList<Double>();
        this.currents = new ArrayList<Double>();
        this.powers = new ArrayList<Double>();
    }

    public CurrentSource(){
        this.voltages = new ArrayList<Double>();
        this.currents = new ArrayList<Double>();
        this.powers = new ArrayList<Double>();
    }

    public CurrentSource(CurrentSource currentSource){
        this.phase = currentSource.phase;
        this.domain = currentSource.domain;
        this.frequency = currentSource.frequency;
        this.name = currentSource.name;
        this.value = currentSource.value;
        this.node1 = new Node(currentSource.node1);
        this.node2 = new Node(currentSource.node2);
        this.voltages = currentSource.voltages;
        this.currents = currentSource.currents;
        this.powers = currentSource.powers;
    }

    public boolean equals(CurrentSource currentSource){
        return this.name.equals(currentSource.name);
    }

    @Override
    double voltage() {
        return node1.getVoltage().get(node1.getVoltage().size()-1) - node2.getVoltage().get(node2.getVoltage().size()-1);
    }

    @Override
    double current() {
        return value;
    }

    @Override
    double current(double t, double dt) {
        return value + domain*Math.sin(2 * Math.PI * frequency * t + phase);
    }

    @Override
    double power() {
        return value*voltage();
    }

    @Override
    double power(double t, double dt) {
        return current(t,dt)*voltage();
    }
}
