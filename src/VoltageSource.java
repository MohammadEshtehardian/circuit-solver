import java.util.ArrayList;

public class VoltageSource extends Branch {

    private double domain;
    private double frequency;
    private double phase;
    private boolean added = false;
    public boolean counted = false;

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public VoltageSource(VoltageSource voltageSource){
        this.node1 = new Node(voltageSource.node1);
        this.node2 = new Node(voltageSource.node2);
        this.voltages = new ArrayList<>(voltageSource.voltages);
        this.currents = new ArrayList<>(voltageSource.currents);
        this.powers = new ArrayList<>(voltageSource.powers);
        this.value = voltageSource.value;
        this.name = voltageSource.name;
    }

    public VoltageSource(Node node1, Node node2, double value, String name, double domain, double frequency, double phase) {
        this.node1 = new Node(node1);
        this.node2 = new Node(node2);
        this.value = value;
        this.name = name;
        this.voltages = new ArrayList<>();
        this.currents = new ArrayList<>();
        this.powers = new ArrayList<>();
        this.domain = domain;
        this.frequency = frequency;
        this.phase = phase;
    }

    @Override
    double voltage() {
        return value ;
    }

    double voltage(double t){
        return value + domain*Math.sin(2*Math.PI*frequency*t+phase);
    }

    @Override
    double current() {
        return 0;
    }

    @Override
    double current(double t, double dt) {
        return 0;
    }

    @Override
    double power() {
        return value*current();
    }

    @Override
    double power(double t, double dt) {
        return value*current(t,dt);
    }
}
