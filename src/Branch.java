import java.util.ArrayList;

abstract class Branch {
    protected String name;
    protected Node node1;
    protected Node node2;
    protected double value;
    protected ArrayList<Double> voltages;
    protected ArrayList<Double> currents;
    protected ArrayList<Double> powers;

    public ArrayList<Double> getVoltages() {
        return voltages;
    }

    public ArrayList<Double> getCurrents() {
        return currents;
    }

    public void setVoltages(ArrayList<Double> voltages) {
        this.voltages = voltages;
    }

    public void setCurrents(ArrayList<Double> currents) {
        this.currents = currents;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    public void setNode2(Node node2) {
        this.node2 = node2;
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPowers(ArrayList<Double> powers) {
        this.powers = powers;
    }

    public ArrayList<Double> getPowers() {
        return powers;
    }

    public String getName() {
        return name;
    }

    abstract double voltage();
    abstract double current();
    abstract double current(double t , double dt);
    public void addVoltage(double voltage){
        voltages.add(voltage);
    }
    public void addCurrent(double current){
        currents.add(current);
    }
    public void addPower(double power){
        powers.add(power);
    }
    abstract double power();
    abstract double power(double t , double dt);
    public void remove(){
        voltages.remove(voltages.size()-1);
        currents.remove(currents.size()-1);
        powers.remove(powers.size()-1);
    }
}
