import java.util.ArrayList;

public class Calculation {

     public Calculation(){}

     public double derivation(ArrayList<Double> f , double dx , double x){
        int i = (int) (Math.round(x/dx));
        if(i == 1) return f.get(1) / dx;
        return  (f.get(i) - f.get(i-1)) / dx;
     }

     public double integral(ArrayList<Double> f , double dx , double x){
        double s = 0;
        int n = (int) (Math.round(x/dx));
        for (int i = 0 ; i < n ; i++){
            s += f.get(i) * dx;
        }
        return s;
     }

     public double RMS(ArrayList<Double> f, double dx , double x1 , double x2){
         ArrayList<Double> g = new ArrayList<>();
         for (int i = 0 ; i < f.size() ; i++){
             g.add(f.get(i)*f.get(i));
         }
         double a = integral(g,dx,x2) - integral(g,dx,x1);
         return Math.sqrt(a/(x2-x1));
     }


}
