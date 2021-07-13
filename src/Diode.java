import org.omg.CORBA.MARSHAL;

import java.util.ArrayList;

public class Diode extends Branch {

    public Diode(Node node1, Node node2, String name){
        this.node1 = new Node(node1);
        this.node2 = new Node(node2);
        this.name = name;
        this.voltages = new ArrayList<>();
        this.currents = new ArrayList<>();
        this.powers = new ArrayList<>();
    }

    private boolean on = true;

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public boolean correct(double di){
        if(on && currents.get(currents.size()-1) > 0){
            return true;
        }
        else if(!on && voltages.get(voltages.size() - 1) >= 0) return true;
        if(on && currents.get(currents.size()-1) < 0 && Math.abs(currents.get(currents.size()-1)) < 1e-9) return true;
        if(!on && voltages.get(voltages.size()-1) < 0 && Math.abs(voltages.get(voltages.size()-1)) < 1e-9) return true;
        return false;
    }

    @Override
    double voltage() {
        return 0;
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
