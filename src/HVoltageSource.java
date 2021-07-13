import java.util.ArrayList;

public class HVoltageSource extends Branch {

    private Branch branch;
    private boolean added = false;
    public boolean counted;

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Branch getBranch() {
        return branch;
    }

    public HVoltageSource(Node node1, Node node2, double value, String name, Branch branch){
        this.node1 = new Node(node1);
        this.node2 = new Node(node2);
        this.name = name;
        this.value = value;
        this.branch = branch;
        this.voltages = new ArrayList<>();
        this.currents= new ArrayList<>();
        this.powers = new ArrayList<>();
    }

    public HVoltageSource(HVoltageSource hVoltageSource){
        this.name = hVoltageSource.name;
        this.node1 = new Node(hVoltageSource.node1);
        this.node2 = new Node(hVoltageSource.node2);
        this.voltages = new ArrayList<>();
        this.currents = new ArrayList<>();
        this.powers = new ArrayList<>();
        this.value = hVoltageSource.value;
    }

    @Override
    double voltage() {
        if(branch.getName().charAt(0) == 'V' || branch.getName().charAt(0) == 'E' || branch.getName().charAt(0) == 'H'){
            return value*branch.getCurrents().get(branch.getCurrents().size()-1);
        }
        return value * branch.current();
    }

    double voltage(int s){
        if(s == 0) return 0;
        if(branch.getName().charAt(0) == 'V' || branch.getName().charAt(0) == 'E' || branch.getName().charAt(0) == 'H'){
            return value*branch.getCurrents().get(branch.getCurrents().size()-1);
        }
        return value*branch.current();
    }
    double voltage(double t, double dt){
        if(branch.getName().charAt(0) == 'V' || branch.getName().charAt(0) == 'E' || branch.getName().charAt(0) == 'H'){
            return value*branch.getCurrents().get(branch.getCurrents().size()-1);
        }
        return value * branch.current(t, dt);
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
        return 0;
    }

    @Override
    double power(double t, double dt) {
        return 0;
    }
}
