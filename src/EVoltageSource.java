import java.util.ArrayList;

public class EVoltageSource extends Branch {

    private Node n1;
    private Node n2;
    private boolean added = false;
    public boolean counted;

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public EVoltageSource(Node node1, Node node2, double value, String name, Node n1, Node n2){
        this.node1 = new Node(node1);
        this.node2 = new Node(node2);
        this.n1 = new Node(n1);
        this.n2 = new Node(n2);
        this.name = name;
        this.value = value;
        this.voltages = new ArrayList<>();
        this.currents = new ArrayList<>();
        this.powers = new ArrayList<>();
    }

    @Override
    double voltage() {
        return value*(n1.getVoltage().get(n1.getVoltage().size()-1) - n2.getVoltage().get(n2.getVoltage().size()-1));
    }

    double voltage(int s){
        if(s == 0) return 0;
        return value*(n1.getVoltage().get(n1.getVoltage().size()-1) - n2.getVoltage().get(n2.getVoltage().size()-1));
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
        return current()*voltage();
    }

    @Override
    double power(double t, double dt) {
        return current(t,dt)*voltage();
    }
}
