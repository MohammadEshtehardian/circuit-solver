import java.util.ArrayList;

public class Union {
    private ArrayList<Node> nodes;
    private String name;

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Union(String name){
        nodes = new ArrayList<>();
        this.name = name;
    }

    public Union(Union union){
        this.name = union.name;
        this.nodes = new ArrayList<>(union.nodes);
    }

    public void updateVoltages(double t, double dt, int s){
        nodes.get(0).added = true;
        while (!finish()){
            for (Node node : nodes){
                for(Node node1 : node.getNeighborNodes()){
                    if(exists(node1) && node1.added && !node.added){
                        for (VoltageSource voltageSource : node.getVoltageSources()){
                            if(node.equals(voltageSource.node1) && node1.equals(voltageSource.node2)){
                                node.removeVoltage(node.getVoltage().size()-1);
                                node.addVoltage(node1.getVoltage().get(node1.getVoltage().size()-1) + voltageSource.voltage(t));
                                node.added = true;
                                break;
                            }
                            else if(node.equals(voltageSource.node2) && node1.equals(voltageSource.node1)){
                                node.removeVoltage(node.getVoltage().size()-1);
                                node.addVoltage(node1.getVoltage().get(node1.getVoltage().size()-1) - voltageSource.voltage(t));
                                node.added = true;
                                break;
                            }
                        }
                        for (EVoltageSource eVoltageSource : node.geteVoltageSources()){
                            if(node.equals(eVoltageSource.node1) && node1.equals(eVoltageSource.node2)){
                                node.removeVoltage(node.getVoltage().size()-1);
                                node.addVoltage(node1.getVoltage().get(node1.getVoltage().size()-1) + eVoltageSource.voltage(s));
                                node.added = true;
                                break;
                            }
                            else if(node.equals(eVoltageSource.node2) && node1.equals(eVoltageSource.node1)){
                                node.removeVoltage(node.getVoltage().size()-1);
                                node.addVoltage(node1.getVoltage().get(node1.getVoltage().size()-1) - eVoltageSource.voltage(s));
                                node.added = true;
                                break;
                            }
                        }
                        for (HVoltageSource hVoltageSource : node.gethVoltageSources()){
                            if(node.equals(hVoltageSource.node1) && node1.equals(hVoltageSource.node2)){
                                node.removeVoltage(node.getVoltage().size()-1);
                                node.addVoltage(node1.getVoltage().get(node1.getVoltage().size()-1) + hVoltageSource.voltage(t,dt));
                                node.added = true;
                                break;
                            }
                            else if(node.equals(hVoltageSource.node2) && node1.equals(hVoltageSource.node1)){
                                node.removeVoltage(node.getVoltage().size()-1);
                                node.addVoltage(node1.getVoltage().get(node1.getVoltage().size()-1) - hVoltageSource.voltage(t,dt));
                                node.added = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (Node node : nodes){
            node.added = false;
        }
    }

    public void simpleUpdate(double t , int s){
        nodes.get(0).added = true;
        while (!finish()){
            for (Node node : nodes){
                for(Node node1 : node.getNeighborNodes()){
                    if(exists(node1) && node1.added && !node.added){
                        for (VoltageSource voltageSource : node.getVoltageSources()){
                            if(node.equals(voltageSource.node1) && node1.equals(voltageSource.node2)){
                                node.removeVoltage(node.getVoltage().size()-1);
                                node.addVoltage(node1.getVoltage().get(node1.getVoltage().size()-1) + voltageSource.voltage(t));
                                node.added = true;
                                break;
                            }
                            else if(node.equals(voltageSource.node2) && node1.equals(voltageSource.node1)){
                                node.removeVoltage(node.getVoltage().size()-1);
                                node.addVoltage(node1.getVoltage().get(node1.getVoltage().size()-1) - voltageSource.voltage(t));
                                node.added = true;
                                break;
                            }
                        }
                        for (EVoltageSource eVoltageSource : node.geteVoltageSources()){
                            if(node.equals(eVoltageSource.node1) && node1.equals(eVoltageSource.node2)){
                                node.removeVoltage(node.getVoltage().size()-1);
                                node.addVoltage(node1.getVoltage().get(node1.getVoltage().size()-1) + eVoltageSource.voltage(s));
                                node.added = true;
                                break;
                            }
                            else if(node.equals(eVoltageSource.node2) && node1.equals(eVoltageSource.node1)){
                                node.removeVoltage(node.getVoltage().size()-1);
                                node.addVoltage(node1.getVoltage().get(node1.getVoltage().size()-1) - eVoltageSource.voltage(s));
                                node.added = true;
                                break;
                            }
                        }
                        for (HVoltageSource hVoltageSource : node.gethVoltageSources()){
                            if(node.equals(hVoltageSource.node1) && node1.equals(hVoltageSource.node2)){
                                node.removeVoltage(node.getVoltage().size()-1);
                                node.addVoltage(node1.getVoltage().get(node1.getVoltage().size()-1) + hVoltageSource.voltage(s));
                                node.added = true;
                                break;
                            }
                            else if(node.equals(hVoltageSource.node2) && node1.equals(hVoltageSource.node1)){
                                node.removeVoltage(node.getVoltage().size()-1);
                                node.addVoltage(node1.getVoltage().get(node1.getVoltage().size()-1) - hVoltageSource.voltage(s));
                                node.added = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (Node node : nodes){
            node.added = false;
        }
    }

    public boolean exists(Node node){
        for (Node node1 : nodes){
            if(node.equals(node1)) return true;
        }
        return false;
    }

    public boolean finish(){
        int s = 0;
        for (Node node : nodes){
            if (node.added) s++;
        }
        return s == nodes.size();
    }

    public double current(double t , double dt){
        double current = 0;
        for (Node node : nodes){
            current += node.current(t, dt);
        }
        return current;
    }
}
