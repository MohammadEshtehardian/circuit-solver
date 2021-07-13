import java.util.ArrayList;

public class FCurrentSource extends Branch {

    private Branch branch;

    public FCurrentSource(Node node1 , Node node2 , double value , String name , Branch branch){
        this.node1 = new Node(node1);
        this.node2 = new Node(node2);
        this.value = value;
        this.name = name;
        this.branch = branch;
        this.powers = new ArrayList<Double>();
        this.voltages = new ArrayList<Double>();
        this.currents = new ArrayList<Double>();
    }

    public FCurrentSource(FCurrentSource fCurrentSource){
        this.node1 = new Node(fCurrentSource.node1);
        this.node2 = new Node(fCurrentSource.node2);
        this.voltages = new ArrayList<>(fCurrentSource.voltages);
        this.currents = new ArrayList<>(fCurrentSource.currents);
        this.powers = new ArrayList<>(fCurrentSource.powers);
        this.value = fCurrentSource.value;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    @Override
    double voltage() {
        return node1.getVoltage().get(node1.getVoltage().size()-1) - node2.getVoltage().get(node2.getVoltage().size()-1);
    }

    @Override
    double current() {
        return value*branch.current();
    }

    @Override
    double current(double t, double dt) {
        return value*branch.current(t,dt);
    }

    @Override
    double power() {
        return voltage()*current();
    }

    @Override
    double power(double t, double dt) {
        return voltage()*current(t,dt);
    }
}
