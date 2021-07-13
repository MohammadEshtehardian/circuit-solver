import com.sun.org.apache.xpath.internal.objects.XNumber;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class Scan {
    String a;
    public ArrayList<Node> nodes;
    public ArrayList<Resistor> resistors;
    public ArrayList<CurrentSource> currentSources;
    public ArrayList<Capacitor> capacitors;
    public ArrayList<Inductor> inductors;
    public ArrayList<GCurrentSource> gCurrentSources;
    public ArrayList<FCurrentSource> fCurrentSources;
    public ArrayList<VoltageSource> voltageSources;
    public ArrayList<EVoltageSource> eVoltageSources;
    public ArrayList<HVoltageSource> hVoltageSources;
    public ArrayList<Diode> diodes;
    public double finalTime;
    public double dt;
    public double di;
    public double dv;
    public int zamin;

    public Scan() {
        a = "";
        nodes = new ArrayList<Node>();
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
        finalTime = 0;
        dt = 0;
        di = 0;
        dv = 0;
    }

    public Scan(File input) throws IOException {
        resistors = new ArrayList<Resistor>();
        capacitors = new ArrayList<Capacitor>();
        inductors = new ArrayList<Inductor>();
        currentSources = new ArrayList<CurrentSource>();
        gCurrentSources = new ArrayList<GCurrentSource>();
        fCurrentSources = new ArrayList<FCurrentSource>();
        voltageSources = new ArrayList<>();
        eVoltageSources = new ArrayList<>();
        hVoltageSources = new ArrayList<>();
        nodes = new ArrayList<Node>();
        diodes = new ArrayList<>();
        int n1, n2;
        Node node = new Node();
        Node node0 = new Node("0");
        nodes.add(node0);
        Node node1, node2, node3, node4;
        zamin = 0;
        Circuit circuit = new Circuit();
        try (Scanner sc = new Scanner(input)) {
            while (sc.hasNextLine()) {
                a = sc.nextLine();
                if (a.charAt(0) != '*') {
                    String namenode1;
                    String namenode2;
                    if (a.charAt(0) == 'I') {
                        namenode1 = nodeName1(a);
                        namenode2 = nodeName2(a);
                        n1 = node.search(namenode1, nodes);
                        n2 = node.search(namenode2, nodes);
                        if (n1 == -1) {
                            node1 = new Node(namenode1);
                            nodes.add(node1);
                        } else {
                            node1 = nodes.get(n1);
                        }
                        if (n2 == -1) {
                            node2 = new Node(namenode2);
                            nodes.add(node2);
                        } else {
                            node2 = nodes.get(n2);
                        }
                        if (namenode2.equals("0") || namenode1.equals("0")) {
                            zamin = 1;
                        }
                        double domain = valueo((a.substring(a.indexOf(' ')).trim()));
                        String b;
                        b = a.substring(a.indexOf(' ')).trim();
                        double frequency = valueo(b.substring(b.indexOf(' ')).trim());
                        b = b.substring(b.indexOf(' ')).trim();
                        double phase = valueo(b.substring(b.indexOf(' ')).trim());
                        CurrentSource currentSource = new CurrentSource(node1, node2, valueo(a), name(a), domain, frequency, phase);
                        currentSources.add(currentSource);
                        node1.getCurrentSources().add(currentSource);
                        node2.getCurrentSources().add(currentSource);
                        if (node1.getNeighborNodes().contains(node2)) {
                        } else {
                            node1.getNeighborNodes().add(node2);
                            node2.getNeighborNodes().add(node1);
                        }
                    }
                    else if (a.charAt(0) == 'D') {
                        namenode1 = nodeName1(a);
                        namenode2 = nodeName2(a);
                        n1 = node.search(namenode1, nodes);
                        n2 = node.search(namenode2, nodes);
                        if (n1 == -1) {
                            node1 = new Node(namenode1);
                            nodes.add(node1);
                        } else {
                            node1 = nodes.get(n1);
                        }
                        if (n2 == -1) {
                            node2 = new Node(namenode2);
                            nodes.add(node2);
                        } else {
                            node2 = nodes.get(n2);
                        }
                        if (namenode2.equals("0") || namenode1.equals("0")) {
                            this.zamin = 1;
                        }
                        Diode diode = new Diode(node1, node2, name(a));
                        diodes.add(diode);
                        node1.getDiodes().add(diode);
                        node2.getDiodes().add(diode);
                        if (node1.getNeighborNodes().contains(node2)) {
                        } else {
                            node1.getNeighborNodes().add(node2);
                            node2.getNeighborNodes().add(node1);
                        }
                    }
                    else if (a.charAt(0) == 'R') {
                        namenode1 = nodeName1(a);
                        namenode2 = nodeName2(a);
                        n1 = node.search(namenode1, nodes);
                        n2 = node.search(namenode2, nodes);
                        if (n1 == -1) {
                            node1 = new Node(namenode1);
                            nodes.add(node1);
                        } else {
                            node1 = nodes.get(n1);
                        }
                        if (n2 == -1) {
                            node2 = new Node(namenode2);
                            nodes.add(node2);
                        } else {
                            node2 = nodes.get(n2);
                        }
                        if (namenode2.equals("0") || namenode1.equals("0")) {
                            this.zamin = 1;
                        }
                        Resistor resistor = new Resistor(node1, node2, valueo(a), name(a));
                        resistors.add(resistor);
                        node1.getResistors().add(resistor);
                        node2.getResistors().add(resistor);
                        if (node1.getNeighborNodes().contains(node2)) {
                        } else {
                            node1.getNeighborNodes().add(node2);
                            node2.getNeighborNodes().add(node1);
                        }
                    }
                    else if (a.charAt(0) == 'C') {
                        namenode1 = nodeName1(a);
                        namenode2 = nodeName2(a);
                        n1 = node.search(namenode1, nodes);
                        n2 = node.search(namenode2, nodes);
                        if (n1 == -1) {
                            node1 = new Node(namenode1);
                            nodes.add(node1);
                        } else {
                            node1 = nodes.get(n1);
                        }
                        if (n2 == -1) {
                            node2 = new Node(namenode2);
                            nodes.add(node2);
                        } else {
                            node2 = nodes.get(n2);
                        }
                        if (namenode2.equals("0") || namenode1.equals("0")) {
                            this.zamin = 1;
                        }
                        Capacitor capacitor = new Capacitor(node1, node2, valueo(a), name(a));
                        capacitors.add(capacitor);
                        node1.getCapacitors().add(capacitor);
                        node2.getCapacitors().add(capacitor);
                        if (node1.getNeighborNodes().contains(node2)) {
                        } else {
                            node1.getNeighborNodes().add(node2);
                            node2.getNeighborNodes().add(node1);
                        }
                    }
                    else if (a.charAt(0) == 'L') {
                        namenode1 = nodeName1(a);
                        namenode2 = nodeName2(a);
                        n1 = node.search(namenode1, nodes);
                        n2 = node.search(namenode2, nodes);
                        if (n1 == -1) {
                            node1 = new Node(namenode1);
                            nodes.add(node1);
                        } else {
                            node1 = nodes.get(n1);
                        }
                        if (n2 == -1) {
                            node2 = new Node(namenode2);
                            nodes.add(node2);
                        } else {
                            node2 = nodes.get(n2);
                        }
                        if (namenode2.equals("0") || namenode1.equals("0")) {
                            this.zamin = 1;
                        }
                        Inductor inductor = new Inductor(node1, node2, valueo(a), name(a), 0);
                        inductors.add(inductor);
                        node1.getInductors().add(inductor);
                        node2.getInductors().add(inductor);
                        if (node1.getNeighborNodes().contains(node2)) {
                        } else {
                            node1.getNeighborNodes().add(node2);
                            node2.getNeighborNodes().add(node1);
                        }
                    }
                    else if (a.charAt(0) == 'G') {
                        namenode1 = nodeName1(a);
                        namenode2 = nodeName2(a);
                        n1 = node.search(namenode1, nodes);
                        n2 = node.search(namenode2, nodes);
                        if (n1 == -1) {
                            node1 = new Node(namenode1);
                            nodes.add(node1);
                        } else {
                            node1 = nodes.get(n1);
                        }
                        if (n2 == -1) {
                            node2 = new Node(namenode2);
                            nodes.add(node2);
                        } else {
                            node2 = nodes.get(n2);
                        }
                        if (namenode2.equals("0") || namenode1.equals("0")) {
                            this.zamin = 1;
                        }
                        String b;
                        b = a.substring(a.indexOf(' ')).trim();
                        b = b.substring(b.indexOf(' ')).trim();
                        String namenode3, namenode4;
                        namenode3 = "";
                        namenode4 = "";
                        namenode3 = nodeName1(b);
                        namenode4 = nodeName2(b);
                        int n3, n4;
                        n3 = node.search(namenode3, nodes);
                        n4 = node.search(namenode4, nodes);
                        if (n3 == -1) {
                            node3 = new Node(namenode3);
                            nodes.add(node3);
                        } else {
                            node3 = nodes.get(n3);
                        }
                        if (n4 == -1) {
                            node4 = new Node(namenode4);
                            nodes.add(node4);
                        } else {
                            node4 = nodes.get(n4);
                        }
                        if (namenode4.equals("0") || namenode3.equals("0")) {
                            this.zamin = 1;
                        }
                        GCurrentSource gCurrentSource = new GCurrentSource(node1, node2, valueo(b), name(a), node3, node4);
                        gCurrentSources.add(gCurrentSource);
                        node1.getgCurrentSources().add(gCurrentSource);
                        node2.getgCurrentSources().add(gCurrentSource);
                        if (node1.getNeighborNodes().contains(node2)) {
                        } else {
                            node1.getNeighborNodes().add(node2);
                            node2.getNeighborNodes().add(node1);
                        }
                    }
                    else if (a.charAt(0) == 'F') {
                        namenode1 = nodeName1(a);
                        namenode2 = nodeName2(a);
                        n1 = node.search(namenode1, nodes);
                        n2 = node.search(namenode2, nodes);
                        if (n1 == -1) {
                            node1 = new Node(namenode1);
                            nodes.add(node1);
                        } else {
                            node1 = nodes.get(n1);
                        }
                        if (n2 == -1) {
                            node2 = new Node(namenode2);
                            nodes.add(node2);
                        } else {
                            node2 = nodes.get(n2);
                        }
                        if (namenode2.equals("0") || namenode1.equals("0")) {
                            this.zamin = 1;
                        }
                        String b;
                        b = a.substring(a.indexOf(' ')).trim();
                        b = b.substring(b.indexOf(' ')).trim();
                        b = b.substring(b.indexOf(' ')).trim();
                        if (name(b).charAt(0) == 'C') {
                            FCurrentSource fCurrentSource = new FCurrentSource(node1, node2, value(b), name(a), capacitors.get(searchForCapacitor(name(b))));
                            fCurrentSources.add(fCurrentSource);
                            node1.getfCurrentSources().add(fCurrentSource);
                            node2.getfCurrentSources().add(fCurrentSource);
                        }
                        else if (name(b).charAt(0)=='R'){
                            FCurrentSource fCurrentSource = new FCurrentSource(node1, node2, value(b), name(a), resistors.get(searchForResistor(name(b))));
                            fCurrentSources.add(fCurrentSource);
                            node1.getfCurrentSources().add(fCurrentSource);
                            node2.getfCurrentSources().add(fCurrentSource);
                        }
                        else if (name(b).charAt(0)=='L'){
                            FCurrentSource fCurrentSource = new FCurrentSource(node1, node2, value(b), name(a), inductors.get(searchForInductor(name(b))));
                            fCurrentSources.add(fCurrentSource);
                            node1.getfCurrentSources().add(fCurrentSource);
                            node2.getfCurrentSources().add(fCurrentSource);
                        }
                        else if (name(b).charAt(0)=='D'){
                            FCurrentSource fCurrentSource = new FCurrentSource(node1, node2, value(b), name(a), diodes.get(searchForDiode(name(b))));
                            fCurrentSources.add(fCurrentSource);
                            node1.getfCurrentSources().add(fCurrentSource);
                            node2.getfCurrentSources().add(fCurrentSource);
                        }
                        else if (name(b).charAt(0)=='I'){
                            FCurrentSource fCurrentSource = new FCurrentSource(node1, node2, value(b), name(a), currentSources.get(searchForCurrentSources(name(b))));
                            fCurrentSources.add(fCurrentSource);
                            node1.getfCurrentSources().add(fCurrentSource);
                            node2.getfCurrentSources().add(fCurrentSource);
                        }
                        else if (name(b).charAt(0)=='V'){
                            FCurrentSource fCurrentSource = new FCurrentSource(node1, node2, value(b), name(a), voltageSources.get(searchForVoltageSources(name(b))));
                            fCurrentSources.add(fCurrentSource);
                            node1.getfCurrentSources().add(fCurrentSource);
                            node2.getfCurrentSources().add(fCurrentSource);
                        }
                        else if (name(b).charAt(0)=='G'){
                            FCurrentSource fCurrentSource = new FCurrentSource(node1, node2, value(b), name(a), gCurrentSources.get(searchForGCurrentSource(name(b))));
                            fCurrentSources.add(fCurrentSource);
                            node1.getfCurrentSources().add(fCurrentSource);
                            node2.getfCurrentSources().add(fCurrentSource);
                        }
                        else if (name(b).charAt(0)=='E'){
                            FCurrentSource fCurrentSource = new FCurrentSource(node1, node2, value(b), name(a), eVoltageSources.get(searchForEVoltageSource(name(b))));
                            fCurrentSources.add(fCurrentSource);
                            node1.getfCurrentSources().add(fCurrentSource);
                            node2.getfCurrentSources().add(fCurrentSource);
                        }

                        if (node1.getNeighborNodes().contains(node2)) {
                        } else {
                            node1.getNeighborNodes().add(node2);
                            node2.getNeighborNodes().add(node1);
                        }
                    }
                    else if (a.charAt(0) == 'V') {
                        namenode1 = nodeName1(a);
                        namenode2 = nodeName2(a);
                        n1 = node.search(namenode1, nodes);
                        n2 = node.search(namenode2, nodes);
                        if (n1 == -1) {
                            node1 = new Node(namenode1);
                            nodes.add(node1);
                        } else {
                            node1 = nodes.get(n1);
                        }
                        if (n2 == -1) {
                            node2 = new Node(namenode2);
                            nodes.add(node2);
                        } else {
                            node2 = nodes.get(n2);
                        }
                        if (namenode2.equals("0") || namenode1.equals("0")) {
                            zamin = 1;
                        }
                        double domain = valueo((a.substring(a.indexOf(' ')).trim()));
                        String b;
                        b = a.substring(a.indexOf(' ')).trim();
                        double frequency = valueo(b.substring(b.indexOf(' ')).trim());
                        b = b.substring(b.indexOf(' ')).trim();
                        double phase = valueo(b.substring(b.indexOf(' ')).trim());
                        VoltageSource voltageSource = new VoltageSource(node1, node2, valueo(a), name(a), domain, frequency, phase);
                        voltageSources.add(voltageSource);
                        node1.getVoltageSources().add(voltageSource);
                        node2.getVoltageSources().add(voltageSource);
                        if (node1.getNeighborNodes().contains(node2)) {
                        } else {
                            node1.getNeighborNodes().add(node2);
                            node2.getNeighborNodes().add(node1);
                        }
                    }
                    else if (a.charAt(0) == 'H') {
                        namenode1 = nodeName1(a);
                        namenode2 = nodeName2(a);
                        n1 = node.search(namenode1, nodes);
                        n2 = node.search(namenode2, nodes);
                        if (n1 == -1) {
                            node1 = new Node(namenode1);
                            nodes.add(node1);
                        } else {
                            node1 = nodes.get(n1);
                        }
                        if (n2 == -1) {
                            node2 = new Node(namenode2);
                            nodes.add(node2);
                        } else {
                            node2 = nodes.get(n2);
                        }
                        if (namenode2.equals("0") || namenode1.equals("0")) {
                            this.zamin = 1;
                        }
                        String b;
                        b = a.substring(a.indexOf(' ')).trim();
                        b = b.substring(b.indexOf(' ')).trim();
                        b = b.substring(b.indexOf(' ')).trim();
                        if (name(b).charAt(0) == 'C') {
                            HVoltageSource hVoltageSource = new HVoltageSource(node1, node2, value(b), name(a), capacitors.get(searchForCapacitor(name(b))));
                            node1.gethVoltageSources().add(hVoltageSource);
                            node2.gethVoltageSources().add(hVoltageSource);
                            hVoltageSources.add(hVoltageSource);
                        }
                        else if (name(b).charAt(0)=='R'){
                            HVoltageSource hVoltageSource = new HVoltageSource(node1, node2, value(b), name(a), resistors.get(searchForResistor(name(b))));
                            node1.gethVoltageSources().add(hVoltageSource);
                            node2.gethVoltageSources().add(hVoltageSource);
                            hVoltageSources.add(hVoltageSource);
                        }
                        else if (name(b).charAt(0)=='L'){
                            HVoltageSource hVoltageSource = new HVoltageSource(node1, node2, value(b), name(a), inductors.get(searchForInductor(name(b))));
                            hVoltageSources.add(hVoltageSource);
                            node1.gethVoltageSources().add(hVoltageSource);
                            node2.gethVoltageSources().add(hVoltageSource);
                        }
                        else if (name(b).charAt(0)=='D'){
                            HVoltageSource hVoltageSource = new HVoltageSource(node1, node2, value(b), name(a), diodes.get(searchForDiode(name(b))));
                            hVoltageSources.add(hVoltageSource);
                            node1.gethVoltageSources().add(hVoltageSource);
                            node2.gethVoltageSources().add(hVoltageSource);
                        }
                        else if (name(b).charAt(0)=='I'){
                            HVoltageSource hVoltageSource = new HVoltageSource(node1, node2, value(b), name(a), currentSources.get(searchForCurrentSources(name(b))));
                            hVoltageSources.add(hVoltageSource);
                            node1.gethVoltageSources().add(hVoltageSource);
                            node2.gethVoltageSources().add(hVoltageSource);
                        }
                        else if (name(b).charAt(0)=='V'){
                            HVoltageSource hVoltageSource = new HVoltageSource(node1, node2, value(b), name(a), voltageSources.get(searchForVoltageSources(name(b))));
                            hVoltageSources.add(hVoltageSource);
                            node1.gethVoltageSources().add(hVoltageSource);
                            node2.gethVoltageSources().add(hVoltageSource);
                        }
                        else if (name(b).charAt(0)=='G'){
                            HVoltageSource hVoltageSource = new HVoltageSource(node1, node2, value(b), name(a), gCurrentSources.get(searchForGCurrentSource(name(b))));
                            hVoltageSources.add(hVoltageSource);
                            node1.gethVoltageSources().add(hVoltageSource);
                            node2.gethVoltageSources().add(hVoltageSource);
                        }
                        else if (name(b).charAt(0)=='E'){
                            HVoltageSource hVoltageSource = new HVoltageSource(node1, node2, value(b), name(a), eVoltageSources.get(searchForEVoltageSource(name(b))));
                            hVoltageSources.add(hVoltageSource);
                            node1.gethVoltageSources().add(hVoltageSource);
                            node2.gethVoltageSources().add(hVoltageSource);
                        }
                        else if(name(b).charAt(0) == 'F'){
                            HVoltageSource hVoltageSource = new HVoltageSource(node1, node2, value(b), name(a), fCurrentSources.get(searchForFCurrentSource(name(b))));
                            hVoltageSources.add(hVoltageSource);
                            node1.gethVoltageSources().add(hVoltageSource);
                            node2.gethVoltageSources().add(hVoltageSource);
                        }

                        if (node1.getNeighborNodes().contains(node2)) {
                        } else {
                            node1.getNeighborNodes().add(node2);
                            node2.getNeighborNodes().add(node1);
                        }
                    }
                    else if (a.charAt(0) == 'E') {
                        namenode1 = nodeName1(a);
                        namenode2 = nodeName2(a);
                        n1 = node.search(namenode1, nodes);
                        n2 = node.search(namenode2, nodes);
                        if (n1 == -1) {
                            node1 = new Node(namenode1);
                            nodes.add(node1);
                        } else {
                            node1 = nodes.get(n1);
                        }
                        if (n2 == -1) {
                            node2 = new Node(namenode2);
                            nodes.add(node2);
                        } else {
                            node2 = nodes.get(n2);
                        }
                        if (namenode2.equals("0") || namenode1.equals("0")) {
                            this.zamin = 1;
                        }
                        String b;
                        b = a.substring(a.indexOf(' '), a.length()).trim();
                        b = b.substring(b.indexOf(' '), b.length()).trim();
                        String namenode3, namenode4;
                        namenode3 = "";
                        namenode4 = "";
                        namenode3 = nodeName1(b);
                        namenode4 = nodeName2(b);
                        int n3, n4;
                        n3 = node.search(namenode3, nodes);
                        n4 = node.search(namenode4, nodes);
                        if (n3 == -1) {
                            node3 = new Node(namenode3);
                            nodes.add(node3);
                        } else {
                            node3 = nodes.get(n3);
                        }
                        if (n4 == -1) {
                            node4 = new Node(namenode4);
                            nodes.add(node4);
                        } else {
                            node4 = nodes.get(n4);
                        }
                        if (namenode4.equals("0") || namenode3.equals("0")) {
                            this.zamin = 1;
                        }
                        EVoltageSource eVoltageSource = new EVoltageSource(node1, node2, valueo(b), name(a), node3, node4);
                        eVoltageSources.add(eVoltageSource);
                        node1.geteVoltageSources().add(eVoltageSource);
                        node2.geteVoltageSources().add(eVoltageSource);
                        if (node1.getNeighborNodes().contains(node2)) {
                        } else {
                            node1.getNeighborNodes().add(node2);
                            node2.getNeighborNodes().add(node1);
                        }
                    }
                    else if (a.charAt(0) == 'd') {
                        if (a.charAt(1) == 'V') {
                            dv = value(a);
                        } else if (a.charAt(1) == 'I') {
                            di = value(a);
                        } else if (a.charAt(1) == 'T') {
                            dt = value(a);
                        }
                    }
                    else if (a.charAt(0) == '.') {
                        finalTime = value(a);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    String name(String a) {
        return a.substring(0, a.indexOf(' '));
    }
    String nodeName1(String a) {
        String b;
        b = a.substring(a.indexOf(' ')).trim();
        b = b.substring(0, b.indexOf(' '));
        return b;
    }
    String nodeName2(String a) {
        String b;
        b = a.substring(a.indexOf(' ')).trim();
        b = b.substring(b.indexOf(' ')).trim();
        b = b.substring(0, b.indexOf(' '));
        return b;
    }
    double value(String a) {
        String b;
        b = a.substring(a.indexOf(' ')).trim();
        if (b.charAt(b.length() - 1) == 'p') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, -12);
        } else if (b.charAt(b.length() - 1) == 'n') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, -9);
        } else if (b.charAt(b.length() - 1) == 'u') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, -6);
        } else if (b.charAt(b.length() - 1) == 'm') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, -3);
        } else if (b.charAt(b.length() - 1) == 'k') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, 3);
        } else if (b.charAt(b.length() - 1) == 'M') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, 6);
        } else if (b.charAt(b.length() - 1) == 'G') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, 9);
        } else {
            return Double.parseDouble(b);
        }
    }
    double valueo(String a) {
        String c;
        c = a.substring(a.indexOf(' ')).trim();
        c = c.substring(c.indexOf(' ')).trim();
        c = c.substring(c.indexOf(' ')).trim();
        String b;
        if (c.indexOf(' ') > 0) {
            b = c.substring(0, c.indexOf(' ')).trim();
        } else {
            b = c.trim();
        }
        if (b.charAt(b.length() - 1) == 'p') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, -12);
        } else if (b.charAt(b.length() - 1) == 'n') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, -9);
        } else if (b.charAt(b.length() - 1) == 'u') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, -6);
        } else if (b.charAt(b.length() - 1) == 'm') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, -3);
        } else if (b.charAt(b.length() - 1) == 'k') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, 3);
        } else if (b.charAt(b.length() - 1) == 'M') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, 6);
        } else if (b.charAt(b.length() - 1) == 'G') {
            return Double.parseDouble(b.substring(0, b.length() - 1)) * Math.pow(10, 9);
        } else {
            return Double.parseDouble(b);
        }
    }
    int searchForCurrentSources(String s){
        for (int i = 0 ; i < currentSources.size() ; i++){
            if(currentSources.get(i).getName().equals(s)) return i;
        }
        return -1;
    }
    int searchForVoltageSources(String s){
        for (int i = 0 ; i < voltageSources.size() ; i++){
            if(voltageSources.get(i).getName().equals(s)) return i;
        }
        return -1;
    }
    int searchForResistor(String s){
        for (int i = 0 ; i < resistors.size() ; i++){
            if(resistors.get(i).getName().equals(s)) return i;
        }
        return -1;
    }
    int searchForCapacitor(String s){
        for (int i = 0 ; i < capacitors.size() ; i++){
            if(capacitors.get(i).getName().equals(s)) return i;
        }
        return -1;
    }
    int searchForInductor(String s){
        for (int i = 0 ; i < inductors.size() ; i++){
            if(inductors.get(i).getName().equals(s)) return i;
        }
        return -1;
    }
    int searchForGCurrentSource(String s){
        for (int i = 0 ; i < gCurrentSources.size() ; i++){
            if(gCurrentSources.get(i).getName().equals(s)) return i;
        }
        return -1;
    }
    int searchForFCurrentSource(String s){
        for (int i = 0 ; i < fCurrentSources.size() ; i++){
            if(fCurrentSources.get(i).getName().equals(s)) return i;
        }
        return -1;
    }
    int searchForDiode(String s){
        for (int i = 0 ; i < diodes.size() ; i++){
            if(diodes.get(i).getName().equals(s)) return i;
        }
        return -1;
    }
    int searchForEVoltageSource(String s){
        for (int i = 0 ; i < eVoltageSources.size() ; i++){
            if(eVoltageSources.get(i).getName().equals(s)) return i;
        }
        return -1;
    }
    int searchForHVoltageSource(String s){
        for (int i = 0 ; i < hVoltageSources.size() ; i++){
            if(hVoltageSources.get(i).getName().equals(s)) return i;
        }
        return -1;
    }
}