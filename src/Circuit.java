import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Circuit {
    private ArrayList<Node> nodes;
    private double finalTime;
    private double dt;
    private double di;
    private double dv;
    private ArrayList<Resistor> resistors;
    private ArrayList<CurrentSource> currentSources;
    private ArrayList<Capacitor> capacitors;
    private ArrayList<Inductor> inductors;
    private ArrayList<GCurrentSource> gCurrentSources;
    private ArrayList<FCurrentSource> fCurrentSources;
    private ArrayList<VoltageSource> voltageSources;
    private ArrayList<Union> unions;
    private ArrayList<EVoltageSource> eVoltageSources;
    private ArrayList<HVoltageSource> hVoltageSources;
    private ArrayList<Diode> diodes;

    public Circuit(){
        this.nodes = new ArrayList<Node>();
        this.resistors = new ArrayList<Resistor>();
        this.currentSources = new ArrayList<CurrentSource>();
        this.capacitors = new ArrayList<Capacitor>();
        this.inductors = new ArrayList<Inductor>();
        this.gCurrentSources = new ArrayList<GCurrentSource>();
        this.fCurrentSources = new ArrayList<FCurrentSource>();
        this.voltageSources = new ArrayList<>();
        this.unions = new ArrayList<>();
        this.eVoltageSources = new ArrayList<>();
        this.diodes = new ArrayList<>();
    }

    public Circuit(Circuit circuit){
        for (Node node : circuit.getNodes()){
            Node node1 = new Node(node);
            this.nodes.add(node1);
        }
        this.finalTime = circuit.finalTime;
        this.dt = circuit.dt;
        this.di = circuit.di;
        this.dv = circuit.dv;
        this.resistors = new ArrayList<>(circuit.resistors);
        this.currentSources = new ArrayList<>(circuit.currentSources);
        this.capacitors = new ArrayList<>(circuit.capacitors);
        this.inductors = new ArrayList<>(circuit.inductors);
        this.gCurrentSources = new ArrayList<>(circuit.gCurrentSources);
        this.fCurrentSources = new ArrayList<>(circuit.fCurrentSources);
        this.voltageSources = new ArrayList<>(circuit.voltageSources);
        this.unions = circuit.unionCreate();
        this.eVoltageSources = new ArrayList<>(circuit.eVoltageSources);
        this.hVoltageSources = new ArrayList<>(circuit.hVoltageSources);
        this.diodes = new ArrayList<>(circuit.diodes);
    }

    public Circuit(double finalTime , double dt , double di , double dv , ArrayList<Node> nodes , ArrayList<Resistor> resistors , ArrayList<CurrentSource> currentSources , ArrayList<Capacitor> capacitors , ArrayList<Inductor> inductors , ArrayList<GCurrentSource> gCurrentSources , ArrayList<FCurrentSource> fCurrentSources , ArrayList<VoltageSource> voltageSources , ArrayList<EVoltageSource> eVoltageSources , ArrayList<HVoltageSource> hVoltageSources , ArrayList<Diode> diodes) {
        this.finalTime = finalTime;
        this.di = di;
        this.dt = dt;
        this.dv = dv;
        this.nodes = nodes;
        this.resistors = resistors;
        this.currentSources = currentSources;
        this.capacitors = capacitors;
        this.inductors = inductors;
        this.fCurrentSources = fCurrentSources;
        this.gCurrentSources = gCurrentSources;
        this.voltageSources = voltageSources;
        this.eVoltageSources = eVoltageSources;
        this.hVoltageSources = hVoltageSources;
        this.diodes = diodes;
        this.unions = unionCreate();
    }

    public void setDi(double di) {
        this.di = di;
    }

    public void setDv(double dv) {
        this.dv = dv;
    }

    public double getDi() {
        return di;
    }

    public double getDv() {
        return dv;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public double getDt() {
        return dt;
    }

    public void setFinalTime(double finalTime) {
        this.finalTime = finalTime;
    }

    public double getFinalTime() {
        return finalTime;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Inductor> getInductors() {
        return inductors;
    }

    public void setInductors(ArrayList<Inductor> inductors) {
        this.inductors = inductors;
    }

    public ArrayList<Capacitor> getCapacitors() {
        return capacitors;
    }

    public void setCapacitors(ArrayList<Capacitor> capacitors) {
        this.capacitors = capacitors;
    }

    public void setResistors(ArrayList<Resistor> resistors) {
        this.resistors = resistors;
    }

    public void setCurrentSources(ArrayList<CurrentSource> currentSources) {
        this.currentSources = currentSources;
    }

    public ArrayList<Resistor> getResistors() {
        return resistors;
    }

    public ArrayList<CurrentSource> getCurrentSources() {
        return currentSources;
    }

    public int searchForCurrentSources(String s){
        for (int i = 0 ; i < currentSources.size() ; i++){
            if(currentSources.get(i).getName().equals(s)) return i;
        }
        return -1;
    }

    public int searchForVoltageSources(String s){
        for (int i = 0 ; i < voltageSources.size() ; i++){
            if(voltageSources.get(i).getName().equals(s)) return i;
        }
        return -1;
    }

    public int searchForResistor(String s){
        for (int i = 0 ; i < resistors.size() ; i++){
            if(resistors.get(i).getName().equals(s)) return i;
        }
        return -1;
    }

    public int searchForCapacitor(String s){
        for (int i = 0 ; i < capacitors.size() ; i++){
            if(capacitors.get(i).getName().equals(s)) return i;
        }
        return -1;
    }

    public int searchForInductor(String s){
        for (int i = 0 ; i < inductors.size() ; i++){
            if(inductors.get(i).getName().equals(s)) return i;
        }
        return -1;
    }

    public int searchForGCurrentSource(String s){
        for (int i = 0 ; i < gCurrentSources.size() ; i++){
            if(gCurrentSources.get(i).getName().equals(s)) return i;
        }
        return -1;
    }

    public String toString(double x){
        String A = "0";
        if(x >= 0) {
            if (x >= 1e-1 && x < 1) A = String.format("%.1fm", x / 1e-3);
            else if (x >= 1e-2 && x < 1e-1) A = String.format("%.2fm", x / 1e-3);
            else if (x >= 1e-3 && x < 1e-2) A = String.format("%.3fm", x / 1e-3);
            else if (x >= 1 && x < 10) A = String.format("%.3f", x);
            else if (x >= 10 && x < 100) A = String.format("%.2f", x);
            else if (x >= 100 && x < 1000) A = String.format("%.1f", x);
            else if (x >= 1e-4 && x < 1e-3) A = String.format("%.1fu", x / 1e-6);
            else if (x >= 1e-5 && x < 1e-4) A = String.format("%.2fu", x / 1e-6);
            else if (x >= 1e-6 && x < 1e-5) A = String.format("%.3fu", x / 1e-6);
            else if (x >= 1e3 && x < 1e4) A = String.format("%.3fk", x / 1e3);
            else if (x >= 1e4 && x < 1e5) A = String.format("%.2fk", x / 1e3);
            else if (x >= 1e5 && x < 1e6) A = String.format("%.1fk", x / 1e3);
            else if (x >= 1e6 && x < 1e7) A = String.format("%.3fM", x / 1e6);
            else if (x >= 1e7 && x < 1e8) A = String.format("%.2fM", x / 1e6);
            else if (x >= 1e8 && x < 1e9) A = String.format("%.1fM", x / 1e6);
            else if (x >= 1e9 && x < 1e10) A = String.format("%.3fG", x / 1e9);
            else if (x >= 1e10 && x < 1e11) A = String.format("%.2fG", x / 1e9);
            else if (x >= 1e11 && x < 1e12) A = String.format("%.1fG", x / 1e9);
            else if(x > 1e12) A = String.format("%fG", 1/1e9);
            else if (x >= 1e-7 && x < 1e-6) A = String.format("%.1fn", x / 1e-9);
            else if (x >= 1e-8 && x < 1e-7) A = String.format("%.2fn", x / 1e-9);
            else if (x >= 1e-9 && x < 1e-8) A = String.format("%.3fn", x / 1e-9);
            else if (x >= 1e-10 && x < 1e-9) A = String.format("%.1fp", x / 1e-12);
            else if (x >= 1e-11 && x < 1e-10) A = String.format("%.2fp", x / 1e-12);
            else if (x < 1e-11 && x >= 1e-15) A = String.format("%.3fp", x / 1e-12);
        }
        else if(x < 0) {
            A = "-" + toString(Math.abs(x));
        }

        return A;
    }

    public int searchForFCurrentSource(String s){
        for (int i = 0 ; i < fCurrentSources.size() ; i++){
            if(fCurrentSources.get(i).getName().equals(s)) return i;
        }
        return -1;
    }

    public int searchForDiode(String s){
        for (int i = 0 ; i < diodes.size() ; i++){
            if(diodes.get(i).getName().equals(s)) return i;
        }
        return -1;
    }

    public int searchForEVoltageSource(String s){
        for (int i = 0 ; i < eVoltageSources.size() ; i++){
            if(eVoltageSources.get(i).getName().equals(s)) return i;
        }
        return -1;
    }

    public int searchForHVoltageSource(String s){
        for (int i = 0 ; i < hVoltageSources.size() ; i++){
            if(hVoltageSources.get(i).getName().equals(s)) return i;
        }
        return -1;
    }

    public Circuit newCircuit(Circuit circuit){
        Circuit circuit1 = new Circuit();
        ArrayList<Node> nodes1 = new ArrayList<>();
        ArrayList<VoltageSource> voltageSources1 = new ArrayList<>(circuit.voltageSources);
        ArrayList<Diode> diodes1 = new ArrayList<>();
        ArrayList<CurrentSource> currentSources1 = new ArrayList<>(circuit.currentSources);
        for (Node value : circuit.nodes) {
            Node node = new Node(value);
            nodes1.add(node);
        }
        for (Node node : nodes1){
            for (Node node1 : circuit.nodes){
                if(node1.equals(node)){
                    for (Node node2 : node1.getNeighborNodes()){
                        int k = node.search(node2.getName(),nodes1);
                        node.getNeighborNodes().add(nodes1.get(k));
                    }
                    break;
                }
            }
        }
        for (Diode diode : circuit.diodes){
            if(diode.isOn()){
                VoltageSource voltageSource = new VoltageSource(diode.node1,diode.node2,0,"V"+diode.getName(),0,0,0);
                Node node = new Node();
                int n = node.search(diode.node1.getName(),nodes1);
                int m = node.search(diode.node2.getName(),nodes1);
                nodes1.get(n).getDiodes().remove(diode);
                nodes1.get(m).getDiodes().remove(diode);
                nodes1.get(n).getVoltageSources().add(voltageSource);
                nodes1.get(m).getVoltageSources().add(voltageSource);
                voltageSources1.add(voltageSource);
            }
            else {
                CurrentSource currentSource = new CurrentSource(diode.node1,diode.node2,0,"I"+diode.getName(),0,0,0);
                Node node = new Node();
                int n = node.search(diode.node1.getName(),nodes1);
                int m = node.search(diode.node2.getName(),nodes1);
                nodes1.get(n).getDiodes().remove(diode);
                nodes1.get(m).getDiodes().remove(diode);
                nodes1.get(n).getCurrentSources().add(currentSource);
                nodes1.get(m).getCurrentSources().add(currentSource);
                currentSources1.add(currentSource);
            }
        }
        circuit1 = new Circuit(circuit.finalTime,circuit.dt,circuit.di,circuit.dv,nodes1,circuit.resistors,currentSources1,circuit.capacitors,circuit.inductors,circuit.gCurrentSources,circuit.fCurrentSources,voltageSources1,circuit.eVoltageSources,circuit.hVoltageSources,diodes1);
        return circuit1;
    }

    public void solveWithDiode(){
        if(diodes.size() >= 1){
            for (double time = 0 ; time <= finalTime + dt ; time += dt) {
                int r = 1;
                int n = diodes.size();
                boolean b = false;
                while (!b) {
                    for (int i = 1; i <= n; i++) {
                        int m = (int) Math.pow(2, n - i);
                        for (int j = 0 ; j < Math.pow(2, i) ; j++) {
                            if (j * m < r && r <= (1 + j) * m) {
                                if (j % 2 == 1) diodes.get(i - 1).setOn(false);
                                else diodes.get(i - 1).setOn(true);
                            }
                        }
                    }
                    Circuit circuit = newCircuit(this);
                    int s = 0;
                    //System.out.println(voltageSources.get(0).getCurrents().size());
                    //System.out.println(inductors.get(0).getNode2().current(time, dt));
                    circuit.solve(time, dt, s);
                    //System.out.println(time);
                    //System.out.println(voltageSources.get(0).getCurrents().size());
                    //System.out.println("\n");
                    for (Diode diode : diodes) {
                        for (VoltageSource voltageSource : circuit.voltageSources) {
                            if (voltageSource.getName().equals("V" + diode.getName())) {
                                diode.getCurrents().add(voltageSource.getCurrents().get(voltageSource.getCurrents().size() - 1));
                                diode.addVoltage(voltageSource.getVoltages().get(voltageSource.getVoltages().size() - 1));
                                diode.addPower(voltageSource.getPowers().get(voltageSource.getPowers().size() - 1));
                            }
                        }
                        for (CurrentSource currentSource : circuit.currentSources){
                            if (currentSource.getName().equals("I" + diode.getName())) {
                                diode.getCurrents().add(currentSource.getCurrents().get(currentSource.getCurrents().size() - 1));
                                diode.addVoltage(currentSource.getVoltages().get(currentSource.getVoltages().size() - 1));
                                diode.addPower(currentSource.getPowers().get(currentSource.getPowers().size() - 1));
                            }
                        }
                    }
                    //System.out.println(voltageSources.get(0).getCurrents().size());
                    //System.out.println(r);
                    //System.out.println(diodes.get(1).getCurrents().get(diodes.get(1).getCurrents().size()-1));
                    //System.out.println(diodes.get(1).isOn());
                    //System.out.println(diodes.get(1).getVoltages().get(diodes.get(1).getVoltages().size()-1));
                    //System.out.println("\n");
                    r++;
                    b = solvedWithDiode(r);
                    if (!b) {
                        for (Diode diode : diodes) {
                            diode.remove();
                        }
                        for (Node node : circuit.nodes) {
                            node.getVoltage().remove(node.getVoltage().get(node.getVoltage().size() - 1));
                        }
                        for (EVoltageSource eVoltageSource : eVoltageSources) {
                            eVoltageSource.remove();
                        }
                        for (HVoltageSource hVoltageSource : hVoltageSources) {
                            hVoltageSource.remove();
                        }
                        for (Resistor resistor : circuit.resistors) {
                            resistor.remove();
                        }
                        for (VoltageSource voltageSource : circuit.voltageSources) {
                            voltageSource.remove();
                        }
                        for (CurrentSource currentSource : circuit.currentSources) {
                            currentSource.remove();
                        }
                        for (Capacitor capacitor : capacitors) {
                            capacitor.remove();
                        }
                        for (Inductor inductor : inductors) {
                            inductor.remove();
                        }
                        for (GCurrentSource gCurrentSource : gCurrentSources) {
                            gCurrentSource.remove();
                        }
                        for (FCurrentSource fCurrentSource : fCurrentSources) {
                            fCurrentSource.remove();
                        }
                        for (Node node : circuit.nodes) {
                            node.added = false;
                        }
                    }
                }
            }
        }
        else {
            solve();
        }
    }

    public void solve() {
        simpleSolve();
        for (double time = dt ; time <= finalTime+dt ; time+=dt) {
            int s = 0;
            solve(time,dt,s);
        }
    }

    public void simpleSolve(){
        ArrayList<Resistor> resistors1 = new ArrayList<Resistor>(resistors);
        ArrayList<HVoltageSource> hVoltageSources1 = new ArrayList<>();
        ArrayList<Capacitor> capacitors1 = new ArrayList<Capacitor>();
        ArrayList<Inductor> inductors1 = new ArrayList<Inductor>();
        ArrayList<Node> nodes1 = new ArrayList<Node>();
        ArrayList<FCurrentSource> fCurrentSources1 = new ArrayList<>();
        ArrayList<VoltageSource> voltageSources1 = new ArrayList<>(voltageSources);
        ArrayList<CurrentSource> currentSources1 = new ArrayList<>(currentSources);
        ArrayList<EVoltageSource> eVoltageSources1 = new ArrayList<>(eVoltageSources);
        ArrayList<Diode> diodes1 = new ArrayList<>();
        for (Node value : nodes) {
            Node node = new Node(value);
            nodes1.add(node);
        }
        for (Capacitor capacitor : capacitors){
            //Resistor resistor = new Resistor(capacitor.node1,capacitor.node2,1e-3,"R"+capacitor.name);
            VoltageSource voltageSource = new VoltageSource(capacitor.node1,capacitor.node2,0,"V"+capacitor.name,0,0,0);
            //resistors1.add(resistor);
            voltageSources1.add(voltageSource);
            Node node = new Node();
            int n = node.search(capacitor.node1.getName(),nodes1);
            int m = node.search(capacitor.node2.getName(),nodes1);
            nodes1.get(n).getCapacitors().remove(capacitor);
            nodes1.get(m).getCapacitors().remove(capacitor);
            nodes1.get(n).getVoltageSources().add(voltageSource);
            nodes1.get(m).getVoltageSources().add(voltageSource);
        }
        for (Inductor inductor : inductors){
            //Resistor resistor = new Resistor(inductor.node1 , inductor.node2 , 1e7 , "R"+inductor.name);
            CurrentSource currentSource = new CurrentSource(inductor.node1,inductor.node2,0,"I"+inductor.name,0,0,0);
            //resistors1.add(resistor);
            currentSources1.add(currentSource);
            Node node = new Node();
            int n = node.search(inductor.node1.getName(),nodes1);
            int m = node.search(inductor.node2.getName(),nodes1);
            nodes1.get(n).getInductors().remove(inductor);
            nodes1.get(m).getInductors().remove(inductor);
            nodes1.get(n).getCurrentSources().add(currentSource);
            nodes1.get(m).getCurrentSources().add(currentSource);
        }
        for (Node node : nodes1){
            for (Node node1 : nodes){
                if(node1.equals(node)){
                    for (Node node2 : node1.getNeighborNodes()){
                        int k = node.search(node2.getName(),nodes1);
                        node.getNeighborNodes().add(nodes1.get(k));
                    }
                    break;
                }
            }
        }
        for (FCurrentSource fCurrentSource : fCurrentSources) {
            if (fCurrentSource.getBranch().getName().charAt(0) == 'C') {
                for (Capacitor capacitor : capacitors) {
                    for (VoltageSource voltageSource : voltageSources1) {
                        if (voltageSource.name.equals("V" + capacitor.name)) {
                            FCurrentSource fCurrentSource1 = new FCurrentSource(fCurrentSource);
                            fCurrentSource1.setName("f" + fCurrentSource.getName());
                            fCurrentSource1.setBranch(voltageSource);
                            fCurrentSources1.add(fCurrentSource1);
                            Node node = new Node();
                            int n = node.search(fCurrentSource.node1.getName(), nodes1);
                            int m = node.search(fCurrentSource.node2.getName(), nodes1);
                            nodes1.get(n).getfCurrentSources().remove(fCurrentSource);
                            nodes1.get(m).getfCurrentSources().remove(fCurrentSource);
                            nodes1.get(n).getfCurrentSources().add(fCurrentSource1);
                            nodes1.get(m).getfCurrentSources().add(fCurrentSource1);
                        }
                    }
                }
            } else if (fCurrentSource.getBranch().getName().charAt(0) == 'L') {
                for (Inductor inductor : inductors) {
                    for (CurrentSource currentSource : currentSources1) {
                        if (currentSource.name.equals("I" + inductor.name)) {
                            FCurrentSource fCurrentSource1 = new FCurrentSource(fCurrentSource);
                            fCurrentSource1.setName("f" + fCurrentSource.getName());
                            fCurrentSource1.setBranch(currentSource);
                            fCurrentSources1.add(fCurrentSource1);
                            Node node = new Node();
                            int n = node.search(fCurrentSource.node1.getName(), nodes1);
                            int m = node.search(fCurrentSource.node2.getName(), nodes1);
                            nodes1.get(n).getfCurrentSources().remove(fCurrentSource);
                            nodes1.get(m).getfCurrentSources().remove(fCurrentSource);
                            nodes1.get(n).getfCurrentSources().add(fCurrentSource1);
                            nodes1.get(m).getfCurrentSources().add(fCurrentSource1);
                        }
                    }
                }
            }
            else {
                fCurrentSources1.add(fCurrentSource);
            }
        }
        for (HVoltageSource hVoltageSource : hVoltageSources){
            if (hVoltageSource.getBranch().getName().charAt(0) == 'C') {
                for (Capacitor capacitor : capacitors) {
                    for (VoltageSource voltageSource : voltageSources1) {
                        if (voltageSource.name.equals("V" + capacitor.name)) {
                            HVoltageSource hVoltageSource1 = new HVoltageSource(hVoltageSource);
                            hVoltageSource1.setName("h" + hVoltageSource.getName());
                            hVoltageSource1.setBranch(voltageSource);
                            hVoltageSources1.add(hVoltageSource1);
                            Node node = new Node();
                            int n = node.search(hVoltageSource.node1.getName(), nodes1);
                            int m = node.search(hVoltageSource.node2.getName(), nodes1);
                            nodes1.get(n).gethVoltageSources().remove(hVoltageSource);
                            nodes1.get(m).gethVoltageSources().remove(hVoltageSource);
                            nodes1.get(n).gethVoltageSources().add(hVoltageSource1);
                            nodes1.get(m).gethVoltageSources().add(hVoltageSource1);
                        }
                    }
                }
            }
            else if (hVoltageSource.getBranch().getName().charAt(0) == 'L') {
                for (Inductor inductor : inductors) {
                    for (CurrentSource currentSource : currentSources1) {
                        if (currentSource.name.equals("I" + inductor.name)) {
                            HVoltageSource hVoltageSource1 = new HVoltageSource(hVoltageSource);
                            hVoltageSource1.setName("h" + hVoltageSource.getName());
                            hVoltageSource1.setBranch(currentSource);
                            hVoltageSources1.add(hVoltageSource1);
                            Node node = new Node();
                            int n = node.search(hVoltageSource.node1.getName(), nodes1);
                            int m = node.search(hVoltageSource.node2.getName(), nodes1);
                            nodes1.get(n).gethVoltageSources().remove(hVoltageSource);
                            nodes1.get(m).gethVoltageSources().remove(hVoltageSource);
                            nodes1.get(n).gethVoltageSources().add(hVoltageSource1);
                            nodes1.get(m).gethVoltageSources().add(hVoltageSource1);
                        }
                    }
                }
            }
            else {
                hVoltageSources1.add(hVoltageSource);
            }
        }
        Circuit circuit = new Circuit(this.finalTime,this.dt,this.di,this.dv,nodes1,resistors1,currentSources1,capacitors1,inductors1,gCurrentSources,fCurrentSources1,voltageSources1,eVoltageSources1,hVoltageSources1,diodes1);
        int s = 0;
        for (Node node : nodes1){
            node.addVoltage(0.0);
        }
        for (Union union : circuit.unions){
            union.simpleUpdate(0,s);
        }
        while (!circuit.finish(0, dt)) {
            for (Union union : circuit.unions){
                union.simpleUpdate(0,s);
            }
            for (int i = 1; i < circuit.unions.size(); i++) {
                double current = circuit.unions.get(i).current(0, dt);
                //System.out.println(current);
                int m = 0;
                double[] voltage = new double[circuit.unions.get(i).getNodes().size()];
                for (Node node : circuit.unions.get(i).getNodes()){
                    voltage[m] = node.getVoltage().get(node.getVoltage().size() - 1) + current / di * dv;
                    node.removeVoltage(node.getVoltage().size() - 1);
                    node.addVoltage(voltage[m]);
                    m++;
                }
                m = 0;
                if (Math.abs(circuit.unions.get(i).current(0, dt)) > Math.abs(current)) {
                    for (Node node : circuit.unions.get(i).getNodes()){
                        voltage[m] -= 2 * current / di * dv;
                        node.removeVoltage(node.getVoltage().size() - 1);
                        node.addVoltage(voltage[m]);
                        m++;
                    }
                }
            }
            if(s > 0) {
                for (VoltageSource voltageSource : voltageSources1) {
                    voltageSource.getCurrents().remove(voltageSource.getCurrents().size() - 1);
                }
                for (EVoltageSource eVoltageSource : eVoltageSources1){
                    eVoltageSource.getCurrents().remove(eVoltageSource.getCurrents().size()-1);
                }
                for (HVoltageSource hVoltageSource : hVoltageSources1){
                    hVoltageSource.getCurrents().remove(hVoltageSource.getCurrents().size()-1);
                }
            }
            for (VoltageSource voltageSource : voltageSources1){
                voltageSource.counted = false;
            }
            for (EVoltageSource eVoltageSource : eVoltageSources1){
                eVoltageSource.counted = false;
            }
            for (HVoltageSource hVoltageSource : hVoltageSources1){
                hVoltageSource.counted = false;
            }
            for(Union union : circuit.unions){
                circuit.currentForVoltageSources(union,0,dt);
            }
            s++;
        }
        if(s > 0) {
            for (VoltageSource voltageSource : voltageSources1) {
                voltageSource.getCurrents().remove(voltageSource.getCurrents().size() - 1);
            }
            for (EVoltageSource eVoltageSource : eVoltageSources1){
                eVoltageSource.getCurrents().remove(eVoltageSource.getCurrents().size()-1);
            }
            for (HVoltageSource hVoltageSource : hVoltageSources1){
                hVoltageSource.getCurrents().remove(hVoltageSource.getCurrents().size()-1);
            }
        }
        for (VoltageSource voltageSource : voltageSources1){
            voltageSource.counted = false;
        }
        for (EVoltageSource eVoltageSource : eVoltageSources1){
            eVoltageSource.counted = false;
        }
        for (HVoltageSource hVoltageSource : hVoltageSources1){
            hVoltageSource.counted = false;
        }
        for(Union union : circuit.unions){
            circuit.currentForVoltageSources(union,0,dt);
        }
        s=0;
        for (EVoltageSource eVoltageSource : eVoltageSources1){
            eVoltageSource.addVoltage(eVoltageSource.voltage());
        }
        for(EVoltageSource eVoltageSource : eVoltageSources1){
            eVoltageSource.addVoltage(eVoltageSource.voltage());
            eVoltageSource.addPower(eVoltageSource.voltage() * eVoltageSource.getCurrents().get(eVoltageSource.getCurrents().size()-1));
        }
        for (CurrentSource currentSource : currentSources) {
            for (CurrentSource currentSource1 : currentSources1) {
                if(currentSource.equals(currentSource1)) {
                    currentSource.addVoltage(currentSource1.voltage());
                    currentSource.addCurrent(currentSource1.current(0, dt));
                    currentSource.addPower(currentSource1.power(0, dt));
                }
            }
        }
        for (VoltageSource voltageSource : voltageSources1){
            voltageSource.addVoltage(voltageSource.voltage(0));
            voltageSource.addPower(voltageSource.voltage(0)*voltageSource.getCurrents().get(voltageSource.getCurrents().size()-1));
        }

        for (GCurrentSource gCurrentSource : gCurrentSources){
            gCurrentSource.addVoltage(gCurrentSource.voltage());
            gCurrentSource.addCurrent(gCurrentSource.current());
            gCurrentSource.addPower(gCurrentSource.power());
        }
        for (Resistor resistor : resistors) {
            resistor.addVoltage(resistor.voltage());
            resistor.addCurrent(resistor.current());
            resistor.addPower(resistor.power());
        }
        for (FCurrentSource fCurrentSource : fCurrentSources){
            for(FCurrentSource fCurrentSource1 : fCurrentSources1){
                if(fCurrentSource.getName().equals(fCurrentSource1.getName())){
                    fCurrentSource.addPower(fCurrentSource1.power(0,dt));
                    fCurrentSource.addCurrent(fCurrentSource1.current(0,dt));
                    fCurrentSource.addVoltage(fCurrentSource1.voltage());
                }
                else if(fCurrentSource1.getName().equals("f"+fCurrentSource.getName())){
                    fCurrentSource.addPower(fCurrentSource1.power(0,dt));
                    fCurrentSource.addCurrent(fCurrentSource1.current(0,dt));
                    fCurrentSource.addVoltage(fCurrentSource1.voltage());
                }
            }
        }
        for (int i = 0 ; i < nodes.size() ; i++){
            nodes.get(i).getVoltage().add(circuit.nodes.get(i).getVoltage().get(0));
        }
        for (Capacitor capacitor : capacitors) {
            for (VoltageSource voltageSource : voltageSources1) {
                if (voltageSource.name.equals("V" + capacitor.name)) {
                    capacitor.addVoltage(capacitor.voltage());
                    capacitor.addCurrent(voltageSource.getCurrents().get(voltageSource.getCurrents().size()-1));
                    capacitor.addPower(capacitor.power());
                }
            }
        }
        for (Inductor inductor : inductors) {
            for (CurrentSource currentSource : currentSources1) {
                if (currentSource.name.equals("I" + inductor.name)) {
                    inductor.addVoltage(currentSource.voltage());
                    inductor.addCurrent(currentSource.current(0,dt));
                    inductor.addPower(currentSource.voltage()*currentSource.current(0,dt));
                }
            }
        }
        for (HVoltageSource hVoltageSource : hVoltageSources1){
            hVoltageSource.addVoltage(hVoltageSource.voltage());
            hVoltageSource.addPower(hVoltageSource.voltage() * hVoltageSource.getCurrents().get(hVoltageSource.getCurrents().size()-1));
        }
        for (HVoltageSource hVoltageSource : hVoltageSources){
            for (HVoltageSource hVoltageSource1 : hVoltageSources1){
                if(hVoltageSource.getName().equals(hVoltageSource1.getName()) || hVoltageSource1.getName().equals("h" + hVoltageSource.getName())){
                    hVoltageSource.addPower(hVoltageSource1.getPowers().get(0));
                    hVoltageSource.addVoltage(hVoltageSource1.getVoltages().get(0));
                    hVoltageSource.addCurrent(hVoltageSource1.getCurrents().get(0));
                }
            }
        }
    }

    public void solve(double time, double dt, int s){
        //System.out.println(time);
        if(time == 0) simpleSolve();
        else {
            int r = 0;
            for (Capacitor capacitor : capacitors) {
                capacitor.getVoltages().add(capacitor.voltage());
            }
            for (Inductor inductor : inductors) {
                inductor.getVoltages().add(inductor.voltage());
            }
            for (Union union : unions) {
                union.updateVoltages(time,dt, s);
            }
            while (!finish(time, dt)) {
                for (Union union : unions) {
                    union.updateVoltages(time,dt, s);
                }
                for (int i = 1; i < unions.size(); i++) {
                    double current = unions.get(i).current(time, dt);
                    //System.out.println(current);
                    int m = 0;
                    double[] voltage = new double[unions.get(i).getNodes().size()];
                    for (Node node : unions.get(i).getNodes()) {
                        voltage[m] = node.getVoltage().get(node.getVoltage().size() - 1) + current / di * dv;
                        //System.out.println(voltage[m] + "\t" + current);
                        node.removeVoltage(node.getVoltage().size() - 1);
                        node.addVoltage(voltage[m]);
                        m++;
                    }
                    //System.out.println(voltage[0] + " " + current*dv/di + " " + 1/(voltage[0]/current/dv*di));
                    for (int j = 0; j < unions.get(i).getNodes().size(); j++) {
                        for (Node node : unions.get(i).getNodes()) {
                            for (Capacitor capacitor : node.getCapacitors()) {
                                capacitor.getVoltages().remove(capacitor.getVoltages().size() - 1);
                                capacitor.getVoltages().add(capacitor.voltage());
                            }
                            for (Inductor inductor : node.getInductors()) {
                                inductor.getVoltages().remove(inductor.getVoltages().size() - 1);
                                inductor.getVoltages().add(inductor.voltage());
                            }
                        }
                    }
                    m = 0;
                    //System.out.println(unions.size());
                    //if(unions.get(i).getNodes().get(0).getName().equals("3")) System.out.println(unions.get(i).current(time,dt) + "\t" + current + unions.get(i).getNodes().get(0).getVoltage());
                    if (Math.abs(unions.get(i).current(time, dt)) >= Math.abs(current)) {
                        for (Node node : unions.get(i).getNodes()) {
                            voltage[m] -= 2 * current / di * dv;
                            //System.out.println(voltage[m] + "\t" + current);
                            node.removeVoltage(node.getVoltage().size() - 1);
                            node.addVoltage(voltage[m]);
                            //if(node.getName().equals("3")) System.out.println(node.getVoltage());
                            m++;
                        }
                    }
                    for (int j = 0; j < unions.get(i).getNodes().size(); j++) {
                        for (Node node : unions.get(i).getNodes()) {
                            for (Capacitor capacitor : node.getCapacitors()) {
                                capacitor.getVoltages().remove(capacitor.getVoltages().size() - 1);
                                capacitor.getVoltages().add(capacitor.voltage());
                            }
                            for (Inductor inductor : node.getInductors()) {
                                inductor.getVoltages().remove(inductor.getVoltages().size() - 1);
                                inductor.getVoltages().add(inductor.voltage());
                            }
                        }
                    }
                }
                if (s > 0) {
                    for (VoltageSource voltageSource : voltageSources) {
                        voltageSource.getCurrents().remove(voltageSource.getCurrents().size() - 1);
                    }
                    for (EVoltageSource eVoltageSource : eVoltageSources) {
                        eVoltageSource.getCurrents().remove(eVoltageSource.getCurrents().size() - 1);
                    }
                    for (HVoltageSource hVoltageSource : hVoltageSources) {
                        hVoltageSource.getCurrents().remove(hVoltageSource.getCurrents().size() - 1);
                    }
                }
                for (VoltageSource voltageSource : voltageSources){
                    voltageSource.counted = false;
                }
                for (EVoltageSource eVoltageSource : eVoltageSources){
                    eVoltageSource.counted = false;
                }
                for (HVoltageSource hVoltageSource : hVoltageSources){
                    hVoltageSource.counted = false;
                }
                for (Union union : unions) {
                    currentForVoltageSources(union, time, dt);
                }
                //System.out.println(voltageSources.get(0).getCurrents().size());
                s++;
                if(s > 1e4*di/dv) break;
            }
            if (s > 0) {
                for (VoltageSource voltageSource : voltageSources) {
                    voltageSource.getCurrents().remove(voltageSource.getCurrents().size() - 1);
                }
                for (EVoltageSource eVoltageSource : eVoltageSources) {
                    eVoltageSource.getCurrents().remove(eVoltageSource.getCurrents().size() - 1);
                }
                for (HVoltageSource hVoltageSource : hVoltageSources) {
                    hVoltageSource.getCurrents().remove(hVoltageSource.getCurrents().size() - 1);
                }
            }
            for (VoltageSource voltageSource : voltageSources){
                voltageSource.counted = false;
            }
            for (EVoltageSource eVoltageSource : eVoltageSources){
                eVoltageSource.counted = false;
            }
            for (HVoltageSource hVoltageSource : hVoltageSources){
                hVoltageSource.counted = false;
            }
            for (Union union : unions) {
                currentForVoltageSources(union, time, dt);
            }
            s = 0;
            for (Node node : nodes) {
                node.addVoltage(node.getVoltage().get(node.getVoltage().size() - 1));
            }
            for (EVoltageSource eVoltageSource : eVoltageSources) {
                eVoltageSource.addVoltage(eVoltageSource.voltage());
                eVoltageSource.addPower(eVoltageSource.voltage() * eVoltageSource.getCurrents().get(eVoltageSource.getCurrents().size() - 1));
            }
            for (HVoltageSource hVoltageSource : hVoltageSources) {
                hVoltageSource.addVoltage(hVoltageSource.voltage(time,dt));
                hVoltageSource.addPower(hVoltageSource.voltage(time,dt) * hVoltageSource.getCurrents().get(hVoltageSource.getCurrents().size() - 1));
            }
            for (Resistor resistor : resistors) {
                resistor.addVoltage(resistor.voltage());
                resistor.addCurrent(resistor.current());
                resistor.addPower(resistor.power());
            }
            for (VoltageSource voltageSource : voltageSources) {
                voltageSource.addVoltage(voltageSource.voltage(time));
                voltageSource.addPower(voltageSource.voltage(time) * voltageSource.getCurrents().get(voltageSource.getCurrents().size() - 1));
            }
            for (CurrentSource currentSource : currentSources) {
                currentSource.addVoltage(currentSource.voltage());
                currentSource.addCurrent(currentSource.current(time, dt));
                currentSource.addPower(currentSource.power(time, dt));
            }
            for (Capacitor capacitor : capacitors) {
                capacitor.getVoltages().remove(capacitor.getVoltages().size() - 1);
                capacitor.addVoltage(capacitor.voltage());
                capacitor.addCurrent(capacitor.current(time, dt));
                capacitor.addPower(capacitor.power(time, dt));
            }
            for (Inductor inductor : inductors) {
                inductor.getVoltages().remove(inductor.getVoltages().size() - 1);
                inductor.getVoltages().add(inductor.voltage());
                inductor.addCurrent(inductor.current(time, dt));
                inductor.addPower(inductor.power(time, dt));
            }
            for (GCurrentSource gCurrentSource : gCurrentSources) {
                gCurrentSource.getVoltages().add(gCurrentSource.voltage());
                gCurrentSource.getCurrents().add(gCurrentSource.current(time, dt));
                gCurrentSource.addPower(gCurrentSource.power(time, dt));
            }
            for (FCurrentSource fCurrentSource : fCurrentSources) {
                fCurrentSource.getVoltages().add(fCurrentSource.voltage());
                fCurrentSource.addCurrent(fCurrentSource.current(time, dt));
                fCurrentSource.addPower(fCurrentSource.power(time, dt));
            }
            for (Node node : nodes) {
                node.added = false;
            }
        }
    }

    public boolean finish(double t , double dt){
        int s = 0;
        for(int i = 1 ; i < unions.size() ; i++){
            if(Math.abs(unions.get(i).current(t, dt)/di) <= 0.005) s++;
        }
        return s == unions.size()-1;
    }

    public void print(File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        for (int i = 1 ; i < nodes.size() ; i++){
            Node node = new Node(nodes.get(i));
            fileWriter.write(node.getName() + " ");
            for (int j = 0 ; j < node.getVoltage().size() ; j++){
                String A = toString(node.getVoltage().get(j));
                fileWriter.write(A + " ");
            }
            fileWriter.write("\n\n");
        }
        for (Resistor value : resistors) {
            Resistor resistor = new Resistor(value);
            fileWriter.write(resistor.getName() + "\n");
            for (int j = 0; j < resistor.getVoltages().size(); j++) {
                fileWriter.write(toString(resistor.getVoltages().get(j)) + " " + toString(resistor.getCurrents().get(j)) + " " + toString(resistor.getPowers().get(j)) + "\n");
            }
            fileWriter.write("\n");
        }
        for (CurrentSource source : currentSources) {
            CurrentSource currentSource = new CurrentSource(source);
            fileWriter.write(currentSource.getName() + "\n");
            for (int j = 0; j < currentSource.getVoltages().size(); j++) {
                fileWriter.write(toString(currentSource.getVoltages().get(j)) + " " + toString(currentSource.getCurrents().get(j)) + " " + toString(currentSource.getPowers().get(j)) + "\n");
            }
            fileWriter.write("\n");
        }
        for (Capacitor value : capacitors) {
            Capacitor capacitor = new Capacitor(value);
            fileWriter.write(capacitor.getName() + "\n");
            for (int j = 0; j < capacitor.getVoltages().size(); j++) {
                fileWriter.write(toString(capacitor.getVoltages().get(j)) + " " + toString(capacitor.getCurrents().get(j)) + " " + toString(capacitor.getPowers().get(j)) + "\n");
            }
            fileWriter.write("\n");
        }
        for (Inductor value : inductors) {
            Inductor inductor = new Inductor(value);
            fileWriter.write(inductor.getName() + "\n");
            for (int j = 0; j < inductor.getVoltages().size(); j++) {
                fileWriter.write(toString(inductor.getVoltages().get(j)) + " " + toString(inductor.getCurrents().get(j)) + " " + toString(inductor.getPowers().get(j)));
                fileWriter.write("\n");
            }
            fileWriter.write("\n");
        }
        for (GCurrentSource gCurrentSource : gCurrentSources) {
            fileWriter.write(gCurrentSource.getName() + "\n");
            for(int j = 0 ; j < gCurrentSource.getVoltages().size() ; j++){
                fileWriter.write(toString(gCurrentSource.getVoltages().get(j)) + " " + toString(gCurrentSource.getCurrents().get(j)) + " " + toString(gCurrentSource.getPowers().get(j)) + "\n");
            }
            fileWriter.write("\n");
        }
        for (FCurrentSource fCurrentSource : fCurrentSources) {
            fileWriter.write(fCurrentSource.getName() + "\n");
            for(int j = 0 ; j < fCurrentSource.getVoltages().size() ; j++){
                fileWriter.write(toString(fCurrentSource.getVoltages().get(j)) + " " + toString(fCurrentSource.getCurrents().get(j)) + " " + toString(fCurrentSource.getPowers().get(j)) + "\n");
            }
            fileWriter.write("\n");
        }
        for (VoltageSource voltageSource : voltageSources){
            fileWriter.write(voltageSource.getName() + "\n");
            for(int j = 0 ; j < voltageSource.getVoltages().size() ; j++){
                fileWriter.write(toString(voltageSource.getVoltages().get(j)) + " " + toString(voltageSource.getCurrents().get(j)) + " " + toString(voltageSource.getPowers().get(j)) + "\n");
            }
            fileWriter.write("\n");
        }
        for (EVoltageSource eVoltageSource : eVoltageSources){
            fileWriter.write(eVoltageSource.getName() + "\n");
            for (int j = 0 ; j < eVoltageSource.getCurrents().size() ; j++){
                fileWriter.write(toString(eVoltageSource.getVoltages().get(j)) + " " + toString(eVoltageSource.getCurrents().get(j)) + " " + toString(eVoltageSource.getPowers().get(j)) + "\n");
            }
            fileWriter.write("\n");
        }
        for (HVoltageSource hVoltageSource : hVoltageSources){
            fileWriter.write(hVoltageSource.getName() + "\n");
            for (int j = 0 ; j < hVoltageSource.getCurrents().size() ; j++){
                fileWriter.write(toString(hVoltageSource.getVoltages().get(j)) + " " + toString(hVoltageSource.getCurrents().get(j)) + " " + toString(hVoltageSource.getPowers().get(j)) + "\n");
            }
            fileWriter.write("\n");
        }
        for (Diode diode : diodes){
            fileWriter.write(diode.getName() + "\n");
            for (int j = 0 ; j < diode.getVoltages().size() ; j++){
                fileWriter.write(toString(diode.getVoltages().get(j)) + " " + toString(diode.getCurrents().get(j)) + " " + diode.getPowers().get(j) + "\n");
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

    public void drawer(String s, JFrame frame){
        if(s.charAt(0) == 'I') {
            int n = searchForCurrentSources(s);
            if(n == -1){
                JOptionPane.showMessageDialog(frame,"Element Doesn't exists", "A&M Message",JOptionPane.WARNING_MESSAGE);
            }
            else {
                CurrentSource currentSource = new CurrentSource(currentSources.get(n));
                Drawer drawer1 = new Drawer(currentSource.getVoltages(), finalTime, dt, "Voltage");
                Drawer drawer2 = new Drawer(currentSource.getCurrents(), finalTime, dt, "Current");
                Drawer drawer3 = new Drawer(currentSource.getPowers(), finalTime, dt, "Power");
            }
        }
        else if(s.charAt(0) == 'R'){
            int n = searchForResistor(s);
            if(n == -1){
                JOptionPane.showMessageDialog(frame,"Element Doesn't exists", "A&M Message",JOptionPane.WARNING_MESSAGE);
            }
            else {
                Resistor resistor = new Resistor(resistors.get(n));
                Drawer drawer1 = new Drawer(resistor.getVoltages(), finalTime, dt, "Voltage");
                Drawer drawer2 = new Drawer(resistor.getCurrents(), finalTime, dt, "Current");
                Drawer drawer3 = new Drawer(resistor.getPowers(), finalTime, dt, "Power");
            }
        }
        else if(s.charAt(0) == 'C'){
            int n = searchForCapacitor(s);
            if(n == -1){
                JOptionPane.showMessageDialog(frame,"Element Doesn't exists", "A&M Message",JOptionPane.WARNING_MESSAGE);
            }
            else {
                Capacitor capacitor = new Capacitor(capacitors.get(n));
                Drawer drawer1 = new Drawer(capacitor.getVoltages(), finalTime, dt, "Voltage");
                Drawer drawer2 = new Drawer(capacitor.getCurrents(), finalTime, dt, "Current");
                Drawer drawer3 = new Drawer(capacitor.getPowers(), finalTime, dt, "Power");
            }
        }
        else if(s.charAt(0) == 'L'){
            int n = searchForInductor(s);
            if(n == -1){
                JOptionPane.showMessageDialog(frame,"Element Doesn't exists", "A&M Message",JOptionPane.WARNING_MESSAGE);
            }
            else {
                Inductor inductor = new Inductor(inductors.get(n));
                Drawer drawer1 = new Drawer(inductor.getVoltages(), finalTime, dt, "Voltage");
                Drawer drawer2 = new Drawer(inductor.getCurrents(), finalTime, dt, "Current");
                Drawer drawer3 = new Drawer(inductor.getPowers(), finalTime, dt, "Power");
            }
        }
        else if(s.charAt(0) == 'G'){
            int n = searchForGCurrentSource(s);
            if(n == -1){
                JOptionPane.showMessageDialog(frame,"Element Doesn't exists", "A&M Message",JOptionPane.WARNING_MESSAGE);
            }
            else {
                GCurrentSource gCurrentSource = new GCurrentSource(gCurrentSources.get(n));
                Drawer drawer1 = new Drawer(gCurrentSource.getVoltages(), finalTime, dt, "Voltage");
                Drawer drawer2 = new Drawer(gCurrentSource.getCurrents(), finalTime, dt, "Current");
                Drawer drawer3 = new Drawer(gCurrentSource.getPowers(), finalTime, dt, "Power");
            }
        }
        else if(s.charAt(0) == 'V'){
            int n = searchForVoltageSources(s);
            if(n == -1){
                JOptionPane.showMessageDialog(frame,"Element Doesn't exists", "A&M Message",JOptionPane.WARNING_MESSAGE);
            }
            else {
                Drawer drawer1 = new Drawer(voltageSources.get(n).getVoltages(), finalTime, dt, "Voltage");
                Drawer drawer2 = new Drawer(voltageSources.get(n).getCurrents(), finalTime, dt, "Current");
                Drawer drawer3 = new Drawer(voltageSources.get(n).getPowers(), finalTime, dt, "Power");
            }
        }
        else if(s.charAt(0) == 'F'){
            int n = searchForFCurrentSource(s);
            if(n == -1){
                JOptionPane.showMessageDialog(frame,"Element Doesn't exists", "A&M Message",JOptionPane.WARNING_MESSAGE);
            }
            else {
                Drawer drawer1 = new Drawer(fCurrentSources.get(n).getVoltages(), finalTime, dt, "Voltage");
                Drawer drawer2 = new Drawer(fCurrentSources.get(n).getCurrents(), finalTime, dt, "Current");
                Drawer drawer3 = new Drawer(fCurrentSources.get(n).getPowers(), finalTime, dt, "Power");
            }
        }
        else if(s.charAt(0) == 'E'){
            int n = searchForEVoltageSource(s);
            if(n == -1){
                JOptionPane.showMessageDialog(frame,"Element Doesn't exists", "A&M Message",JOptionPane.WARNING_MESSAGE);
            }
            else {
                Drawer drawer1 = new Drawer(eVoltageSources.get(n).getVoltages(), finalTime, dt, "Voltage");
                Drawer drawer2 = new Drawer(eVoltageSources.get(n).getCurrents(), finalTime, dt, "Current");
                Drawer drawer3 = new Drawer(eVoltageSources.get(n).getPowers(), finalTime, dt, "Power");
            }
        }
        else if(s.charAt(0) == 'H'){
            System.out.println(hVoltageSources.size());
            int n = searchForHVoltageSource(s);
            System.out.println(n);
            if(n == -1){
                JOptionPane.showMessageDialog(frame,"Element Doesn't exists", "A&M Message",JOptionPane.WARNING_MESSAGE);
            }
            else {
                Drawer drawer1 = new Drawer(hVoltageSources.get(n).getVoltages(), finalTime, dt, "Voltage");
                Drawer drawer2 = new Drawer(hVoltageSources.get(n).getCurrents(), finalTime, dt, "Current");
                Drawer drawer3 = new Drawer(hVoltageSources.get(n).getPowers(), finalTime, dt, "Power");
            }
        }
        else if(s.charAt(0) == 'D'){
            int n = searchForDiode(s);
            if(n == -1){
                JOptionPane.showMessageDialog(frame,"Element Doesn't exists", "A&M Message",JOptionPane.WARNING_MESSAGE);
            }
            else {
                Drawer drawer1 = new Drawer(diodes.get(n).getVoltages(), finalTime, dt, "Voltage");
                Drawer drawer2 = new Drawer(diodes.get(n).getCurrents(), finalTime, dt, "Current");
                Drawer drawer3 = new Drawer(diodes.get(n).getPowers(), finalTime, dt, "Power");
            }
        }
        else {
            JOptionPane.showMessageDialog(frame,"Element Doesn't exists", "A&M Message",JOptionPane.WARNING_MESSAGE);
        }
    }

    public ArrayList<Union> unionCreate(){
        ArrayList<Union> unionArrayList = new ArrayList<>();
        for (Node node : nodes){
            node.setUnionName(node.getName());
            node.setAddedToUnion(false);
        }
        nodes.get(0).setAddedToUnion(true);
        for (Node node : nodes){
            for (Node node1 : node.getNeighborNodes()){
                if(node1.isAddedToUnion() && !node.isAddedToUnion()){
                    for (VoltageSource voltageSource : voltageSources){
                        if((node.equals(voltageSource.node1) && node1.equals(voltageSource.node2)) || ((node.equals(voltageSource.node2) && node1.equals(voltageSource.node1)))){
                            node.setUnionName(node1.getUnionName());
                        }
                        break;
                    }
                    for (EVoltageSource eVoltageSource : eVoltageSources){
                        if((node.equals(eVoltageSource.node1) && node1.equals(eVoltageSource.node2)) || ((node.equals(eVoltageSource.node2) && node1.equals(eVoltageSource.node1)))){
                            node.setUnionName(node1.getUnionName());
                        }
                        break;
                    }
                    for (HVoltageSource hVoltageSource : hVoltageSources){
                        if((node.equals(hVoltageSource.node1) && node1.equals(hVoltageSource.node2)) || ((node.equals(hVoltageSource.node2) && node1.equals(hVoltageSource.node1)))){
                            node.setUnionName(node1.getUnionName());
                        }
                        break;
                    }
                }
                else if(!node.isAddedToUnion() && !node1.isAddedToUnion()){
                    for (VoltageSource voltageSource : voltageSources){
                        if((node.equals(voltageSource.node1) && node1.equals(voltageSource.node2)) || ((node.equals(voltageSource.node2) && node1.equals(voltageSource.node1)))){
                            node1.setUnionName(node.getUnionName());
                        }
                        break;
                    }
                    for (EVoltageSource eVoltageSource : eVoltageSources){
                        if((node.equals(eVoltageSource.node1) && node1.equals(eVoltageSource.node2)) || ((node.equals(eVoltageSource.node2) && node1.equals(eVoltageSource.node1)))){
                            node1.setUnionName(node.getUnionName());
                        }
                        break;
                    }
                    for (HVoltageSource hVoltageSource : hVoltageSources){
                        if((node.equals(hVoltageSource.node1) && node1.equals(hVoltageSource.node2)) || ((node.equals(hVoltageSource.node2) && node1.equals(hVoltageSource.node1)))){
                            node1.setUnionName(node.getUnionName());
                        }
                        break;
                    }
                }
            }
            node.setAddedToUnion(true);
        }
        for (VoltageSource voltageSource : voltageSources){
            Node node = new Node();
            int n = node.search(voltageSource.getNode1().getName(),nodes);
            int m = node.search(voltageSource.getNode2().getName(),nodes);
            if(!nodes.get(n).getUnionName().equals(nodes.get(m).getUnionName())){
                String s = nodes.get(n).getUnionName();
                for (Node node1 : nodes){
                    if(s.equals(node1.getUnionName())) node1.setUnionName(nodes.get(m).getUnionName());
                }
            }
        }
        for (EVoltageSource evoltageSource : eVoltageSources){
            Node node = new Node();
            int n = node.search(evoltageSource.getNode1().getName(),nodes);
            int m = node.search(evoltageSource.getNode2().getName(),nodes);
            if(!nodes.get(n).getUnionName().equals(nodes.get(m).getUnionName())){
                String s = nodes.get(n).getUnionName();
                for (Node node1 : nodes){
                    if(s.equals(node1.getUnionName())) node1.setUnionName(nodes.get(m).getUnionName());
                }
            }
        }
        for (HVoltageSource hVoltageSource : hVoltageSources){
            Node node = new Node();
            int n = node.search(hVoltageSource.getNode1().getName(),nodes);
            int m = node.search(hVoltageSource.getNode2().getName(),nodes);
            if(!nodes.get(n).getUnionName().equals(nodes.get(m).getUnionName())){
                String s = nodes.get(n).getUnionName();
                for (Node node1 : nodes){
                    if(s.equals(node1.getUnionName())){
                        node1.setUnionName(nodes.get(m).getUnionName());
                    }
                }
            }
        }
        for (Node node :nodes){
            int n = searchForUnion(unionArrayList, node.getUnionName());
            if(n == -1){
                Union union = new Union(node.getUnionName());
                union.getNodes().add(node);
                unionArrayList.add(union);
            }
            else {
                unionArrayList.get(n).getNodes().add(node);
            }
        }
        return unionArrayList;
    }

    public int searchForUnion(ArrayList<Union> unionArrayList, String name){
        int n = -1;
        for (int i = 0 ; i < unionArrayList.size() ; i++){
            if(unionArrayList.get(i).getName().equals(name)) n = i;
        }
        return n;
    }

    public boolean contains(ArrayList<VoltageSource> voltageSourceArrayList, VoltageSource voltageSource){
        for (VoltageSource voltageSource1 : voltageSourceArrayList){
            if(voltageSource.getName().equals(voltageSource1.getName())) return true;
        }
        return false;
    }

    public void currentForVoltageSources(Union union, double t, double dt){
        ArrayList<VoltageSource> voltageSourceArrayList = new ArrayList<>();
        ArrayList<EVoltageSource> eVoltageSourceArrayList = new ArrayList<>();
        ArrayList<HVoltageSource> hVoltageSourceArrayList = new ArrayList<>();
        int m = 0;
        for (Node node : union.getNodes()){
            for (VoltageSource voltageSource : node.getVoltageSources()){
                if(!contains(voltageSourceArrayList,voltageSource)) voltageSourceArrayList.add(voltageSource);
            }
            for (HVoltageSource hVoltageSource : node.gethVoltageSources()){
                if(!hVoltageSourceArrayList.contains(hVoltageSource)) hVoltageSourceArrayList.add(hVoltageSource);
            }
            for (EVoltageSource eVoltageSource : node.geteVoltageSources()){
                if(!eVoltageSourceArrayList.contains(eVoltageSource)) eVoltageSourceArrayList.add(eVoltageSource);
            }
        }
        int i = 0 , j = 0 ;
        double[][] A = new double[voltageSourceArrayList.size() + hVoltageSourceArrayList.size() + eVoltageSourceArrayList.size()][voltageSourceArrayList.size() + hVoltageSourceArrayList.size() + eVoltageSourceArrayList.size()+1];
        int k = voltageSourceArrayList.size() + eVoltageSourceArrayList.size() + hVoltageSourceArrayList.size();
        double[] I = new double[voltageSourceArrayList.size() + hVoltageSourceArrayList.size() + eVoltageSourceArrayList.size()];
        for (Node node : union.getNodes()){
            if(k > 0) {
                int s = 0;
                int x = node.getVoltageSources().size() + node.geteVoltageSources().size() + node.gethVoltageSources().size();
                for (VoltageSource voltageSource : node.getVoltageSources()){
                    if(voltageSource.isAdded()) x--;
                }
                for (EVoltageSource eVoltageSource : node.geteVoltageSources()){
                    if(eVoltageSource.isAdded()) x--;
                }
                for (HVoltageSource hVoltageSource : node.gethVoltageSources()){
                    if(hVoltageSource.isAdded()) x--;
                }
                for (VoltageSource voltageSource : voltageSourceArrayList) {
                    if (voltageSource.isAdded()) s++;
                }
                for (EVoltageSource eVoltageSource : eVoltageSourceArrayList) {
                    if (eVoltageSource.isAdded()) s++;
                }
                for (HVoltageSource hVoltageSource : hVoltageSourceArrayList) {
                    if (hVoltageSource.isAdded()) s++;
                }
                if (m + 1 > s + x) {
                    continue;
                }
                for (VoltageSource voltageSource : voltageSourceArrayList) {
                    if (node.equals(voltageSource.node1)) {
                        A[i][j] = -1;
                        voltageSource.setAdded(true);
                    } else if (node.equals(voltageSource.node2)) {
                        A[i][j] = 1;
                        voltageSource.setAdded(true);
                    }
                    j++;
                }
                for (EVoltageSource eVoltageSource : eVoltageSourceArrayList) {
                    if (node.equals(eVoltageSource.node1)) {
                        A[i][j] = -1;
                        eVoltageSource.setAdded(true);
                    } else if (node.equals(eVoltageSource.node2)) {
                        A[i][j] = 1;
                        eVoltageSource.setAdded(true);
                    }
                    j++;
                }
                for (HVoltageSource hVoltageSource : hVoltageSourceArrayList) {
                    if (node.equals(hVoltageSource.node1)) {
                        A[i][j] = -1;
                        hVoltageSource.setAdded(true);
                    } else if (node.equals(hVoltageSource.node2)) {
                        A[i][j] = 1;
                        hVoltageSource.setAdded(true);
                    }
                    j++;
                }
                A[i][k] = -node.current(t, dt);
                j = 0;
                i++;
                m++;
            }
        }
        /*for (i = 0 ; i < k ; i++){
            for (j = 0 ; j < k + 1 ; j++){
                System.out.print(A[i][j] + "\t");
            }
            System.out.println("\n");
        }
        System.out.println("\n");*/
        EquationSolver equationSolver = new EquationSolver(A,I);
        boolean b = equationSolver.solve();
        i = 0;
        for (VoltageSource voltageSource : voltageSourceArrayList){
            voltageSource.setAdded(false);
            if(!voltageSource.counted) voltageSource.addCurrent(I[i]);
            voltageSource.counted = true;
            i++;
        }
        for (EVoltageSource eVoltageSource : eVoltageSourceArrayList){
            eVoltageSource.setAdded(false);
            if(!eVoltageSource.counted) eVoltageSource.addCurrent(I[i]);
            eVoltageSource.counted = true;
            i++;
        }
        for (HVoltageSource hVoltageSource : hVoltageSourceArrayList) {
            hVoltageSource.setAdded(false);
            if (!hVoltageSource.counted) hVoltageSource.addCurrent(I[i]);
            hVoltageSource.counted = true;
            i++;
        }
    }

    public boolean solvedWithDiode(int r){
        if(r == 1) return false;
        int n = 0;
        for (Diode diode : diodes){
            if (diode.correct(di)) n++;
        }
        return n == diodes.size();
    }
}
