import java.util.ArrayList;
import java.util.Set;

public class Node {
    private String name;
    private ArrayList<Double> voltage;
    private ArrayList<Node> neighborNodes;
    private ArrayList<Resistor> resistors;
    private ArrayList<CurrentSource> currentSources;
    private ArrayList<Capacitor> capacitors;
    private ArrayList<Inductor> inductors;
    private ArrayList<GCurrentSource> gCurrentSources;
    private ArrayList<FCurrentSource> fCurrentSources;
    private ArrayList<VoltageSource> voltageSources;
    private ArrayList<EVoltageSource> eVoltageSources;
    private ArrayList<HVoltageSource> hVoltageSources;
    private ArrayList<Diode> diodes;
    private boolean addedToUnion = false;
    private String unionName;
    public boolean added = false;

    public Node(){
        voltage = new ArrayList<Double>();
        neighborNodes = new ArrayList<Node>();
        resistors = new ArrayList<Resistor>();
        currentSources = new ArrayList<CurrentSource>();
        capacitors = new ArrayList<Capacitor>();
        inductors = new ArrayList<Inductor>();
        gCurrentSources = new ArrayList<GCurrentSource>();
        fCurrentSources = new ArrayList<FCurrentSource>();
        voltageSources = new ArrayList<VoltageSource>();
        eVoltageSources = new ArrayList<>();
        hVoltageSources = new ArrayList<>();
        diodes = new ArrayList<>();
    }

    public Node(String name){
        this.name = name;
        this.unionName = name;
        this.voltage = new ArrayList<Double>();
        this.neighborNodes = new ArrayList<Node>();
        this.resistors = new ArrayList<Resistor>();
        this.currentSources = new ArrayList<CurrentSource>();
        this.capacitors = new ArrayList<Capacitor>();
        this.inductors = new ArrayList<Inductor>();
        this.gCurrentSources = new ArrayList<GCurrentSource>();
        this.fCurrentSources = new ArrayList<FCurrentSource>();
        this.voltageSources = new ArrayList<>();
        this.eVoltageSources = new ArrayList<>();
        this.hVoltageSources = new ArrayList<>();
        this.diodes = new ArrayList<>();
    }

    public Node(Node node){
        this.voltage = node.voltage;
        this.unionName = node.unionName;
        this.resistors = new ArrayList<>(node.resistors);
        this.currentSources = new ArrayList<>(node.currentSources);
        this.capacitors = new ArrayList<>(node.capacitors);
        this.name = node.name;
        this.inductors = new ArrayList<>(node.inductors);
        this.gCurrentSources = new ArrayList<>(node.gCurrentSources);
        this.fCurrentSources = new ArrayList<>(node.fCurrentSources);
        this.voltageSources = new ArrayList<>(node.voltageSources);
        this.neighborNodes = new ArrayList<>();
        this.eVoltageSources = new ArrayList<>(node.eVoltageSources);
        this.hVoltageSources = new ArrayList<>(node.hVoltageSources);
        this.diodes = new ArrayList<>(node.diodes);
    }

    public ArrayList<Diode> getDiodes() {
        return diodes;
    }

    public ArrayList<HVoltageSource> gethVoltageSources() {
        return hVoltageSources;
    }

    public void sethVoltageSources(ArrayList<HVoltageSource> hVoltageSources) {
        this.hVoltageSources = hVoltageSources;
    }

    public ArrayList<EVoltageSource> geteVoltageSources() {
        return eVoltageSources;
    }

    public void seteVoltageSources(ArrayList<EVoltageSource> eVoltageSources) {
        this.eVoltageSources = eVoltageSources;
    }

    public boolean isAddedToUnion() {
        return addedToUnion;
    }

    public void setAddedToUnion(boolean addedToUnion) {
        this.addedToUnion = addedToUnion;
    }

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
    }

    public ArrayList<VoltageSource> getVoltageSources() {
        return voltageSources;
    }

    public void setVoltageSources(ArrayList<VoltageSource> voltageSources) {
        this.voltageSources = voltageSources;
    }

    public ArrayList<FCurrentSource> getfCurrentSources() {
        return fCurrentSources;
    }

    public ArrayList<GCurrentSource> getgCurrentSources() {
        return gCurrentSources;
    }

    public void setfCurrentSources(ArrayList<FCurrentSource> fCurrentSources) {
        this.fCurrentSources = fCurrentSources;
    }

    public void setgCurrentSources(ArrayList<GCurrentSource> gCurrentSources) {
        this.gCurrentSources = gCurrentSources;
    }

    public void setInductors(ArrayList<Inductor> inductors) {
        this.inductors = inductors;
    }

    public ArrayList<Inductor> getInductors() {
        return inductors;
    }

    public void setCapacitors(ArrayList<Capacitor> capacitors) {
        this.capacitors = capacitors;
    }

    public ArrayList<Capacitor> getCapacitors() {
        return capacitors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<CurrentSource> getCurrentSources() {
        return currentSources;
    }

    public ArrayList<Resistor> getResistors() {
        return resistors;
    }

    public void setCurrentSources(ArrayList<CurrentSource> currentSources) {
        this.currentSources = currentSources;
    }

    public void setResistors(ArrayList<Resistor> resistors) {
        this.resistors = resistors;
    }

    public void setNeighborNodes(ArrayList<Node> neighborNodes) {
        this.neighborNodes = neighborNodes;
    }

    public ArrayList<Node> getNeighborNodes() {
        return neighborNodes;
    }

    public void setVoltage(ArrayList<Double> voltage) {
        this.voltage = voltage;
    }

    public ArrayList<Double> getVoltage() {
        return voltage;
    }

    public boolean equals(Node node){
        return this.name.equals(node.name);
    }

    public double current(double t , double dt){
        double current = 0;
        for (CurrentSource source : currentSources) {
            CurrentSource currentSource = new CurrentSource(source);
            if (equals(currentSource.node1)) {
                current -= currentSource.current(t, dt);
            } else current += currentSource.current(t, dt);
        }
        for (Resistor element : resistors) {
            Resistor resistor = new Resistor(element);
            if (equals(resistor.node1)) {
                current += resistor.current();
            } else current -= resistor.current();
        }
        for (Capacitor item : capacitors) {
            Capacitor capacitor = new Capacitor(item);
            if (equals(item.node1)) {
                current += item.current(t, dt);
            } else current -= item.current(t, dt);
        }
        for (Inductor value : inductors) {
            Inductor inductor = new Inductor(value);
            if (equals(inductor.node1)) {
                current += inductor.current(t, dt);
            } else current -= inductor.current(t, dt);
        }
        for(GCurrentSource gCurrentSource : gCurrentSources){
            if(equals(gCurrentSource.node1)) current -= gCurrentSource.current();
            else current += gCurrentSource.current();
        }
        for (FCurrentSource fCurrentSource : fCurrentSources){
            if(equals(fCurrentSource.node1)) current -= fCurrentSource.current(t, dt);
            else current += fCurrentSource.current(t, dt);
        }
        return current;
    }

    public void addVoltage(double voltage){
        this.voltage.add(voltage);
    }

    public void addResistor(Resistor resistor){
        this.resistors.add(resistor);
    }

    public void addCurrentSource(CurrentSource currentSource){
        this.currentSources.add(currentSource);
    }

    public void addCapacitor(Capacitor capacitor){
        this.capacitors.add(capacitor);
    }

    public void removeVoltage(int i){
        this.voltage.remove(this.voltage.get(i));
    }

    public int search(String S, ArrayList<Node> nodes){
        for (int i = 0 ; i < nodes.size() ; i++){
            if(S.equals(nodes.get(i).name)) return i;
        }
        return -1;
    }
}
