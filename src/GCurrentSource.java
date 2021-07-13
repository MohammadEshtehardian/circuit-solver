import java.util.ArrayList;

public class GCurrentSource extends Branch {

    private Node n1;
    private Node n2;

    GCurrentSource(){
        voltages = new ArrayList<Double>();
        currents = new ArrayList<Double>();
        powers = new ArrayList<Double>();
    }

    GCurrentSource(GCurrentSource gCurrentSource){
        this.name = gCurrentSource.name;
        this.node1 = new Node(gCurrentSource.node1);
        this.node2 = new Node(gCurrentSource.node2);
        this.n1 = new Node(gCurrentSource.n1);
        this.n2 = new Node(gCurrentSource.n2);
        this.voltages = gCurrentSource.voltages;
        this.currents = gCurrentSource.currents;
        this.powers = gCurrentSource.powers;
        this.value = gCurrentSource.value;
    }

    GCurrentSource(Node node1, Node node2, double value, String name, Node n1, Node n2){
        this.n1 = new Node(n1);
        this.n2 = new Node(n2);
        this.node1 = new Node(node1);
        this.node2 = new Node(node2);
        this.value = value;
        this.name = name;
        this.voltages = new ArrayList<Double>();
        this.currents = new ArrayList<Double>();
        this.powers = new ArrayList<Double>();
    }

    @Override
    double voltage() {
        return node1.getVoltage().get(node1.getVoltage().size()-1) - node2.getVoltage().get(node2.getVoltage().size()-1);
    }

    @Override
    double current() {
        return value*(n1.getVoltage().get(n1.getVoltage().size()-1)-n2.getVoltage().get(n2.getVoltage().size()-1));
    }

    @Override
    double current(double t, double dt) {
        return current();
    }

    @Override
    double power() {
        return voltage()*current();
    }

    @Override
    double power(double t, double dt) {
        return power();
    }
}
