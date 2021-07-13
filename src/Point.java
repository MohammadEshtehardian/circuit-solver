import java.util.ArrayList;

public class Point {
    public int x;
    public int y;
    public String name;
    public ArrayList<Point> neighborPoints;


    Point(int x,int y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
        this.neighborPoints = new ArrayList<>();
    }

    Point(Point point){
        this.x = point.x;
        this.y = point.y;
        this.name = point.name;
        this.neighborPoints = new ArrayList<>(point.neighborPoints);
    }

    public int numberOfNeighbor(Point point){
        int s = 0;
        for (Point point1 : neighborPoints){
            if(point.name.equals(point1.name)) s++;
        }
        return s;
    }
}
