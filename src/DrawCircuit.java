import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DrawCircuit extends JFrame {

    ArrayList<Point> points;

    DrawCircuit(File file){
        setLayout(null);
        points = new ArrayList<>();
        setSize(1800,1000);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pointMaker();
        try(Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()){
                String[] A = input(scanner.nextLine());
                if(A[0].charAt(0) == 'd') break;
                if(A[0].charAt(0) == 'R'){
                    DrawResistor drawResistor = null;
                    Point point1 = null,point2 = null;
                    if(searchForPoint(A[1]) != -1 && searchForPoint(A[2]) != -1) {
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[1]) == -1){
                        int n = Integer.parseInt(A[2]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(S));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[2]) == -1){
                        int n = Integer.parseInt(A[1]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(S));
                    }
                    assert point2 != null;
                    if (point1.x == point2.x) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.y >= point1.y)
                                drawResistor = new DrawResistor(point1.x, point1.y, point2.x, point2.y,A[0]);
                            else drawResistor = new DrawResistor(point2.x, point2.y, point1.x, point1.y,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.y >= point1.y)
                                drawResistor = new DrawResistor(point1.x + 50, point1.y, point2.x + 50, point2.y,A[0]);
                            else drawResistor = new DrawResistor(point2.x + 50, point2.y, point1.x + 50, point1.y,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 50, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 50, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.y >= point1.y)
                                drawResistor = new DrawResistor(point1.x + 100, point1.y, point2.x + 100, point2.y,A[0]);
                            else drawResistor = new DrawResistor(point2.x + 100, point2.y, point1.x + 100, point1.y,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 100, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 100, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    else if (point1.y == point2.y) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.x >= point1.x)
                                drawResistor = new DrawResistor(point1.x, point1.y, point2.x, point2.y,A[0]);
                            else drawResistor = new DrawResistor(point2.x, point2.y, point1.x, point1.y,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.x >= point1.x)
                                drawResistor = new DrawResistor(point1.x, point1.y - 50, point2.x, point2.y - 50,A[0]);
                            else drawResistor = new DrawResistor(point2.x, point2.y - 50, point1.x, point1.y - 50,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 50);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 50);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.x >= point1.x)
                                drawResistor = new DrawResistor(point1.x, point1.y - 100, point2.x, point2.y - 100,A[0]);
                            else drawResistor = new DrawResistor(point2.x, point2.y + 100, point1.x, point1.y - 100,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 100);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 100);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    add(drawResistor);
                }
                else if(A[0].charAt(0) == 'L'){
                    DrawInductor drawInductor = null;
                    Point point1 = null,point2 = null;
                    if(searchForPoint(A[1]) != -1 && searchForPoint(A[2]) != -1) {
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[1]) == -1){
                        int n = Integer.parseInt(A[2]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(S));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[2]) == -1){
                        int n = Integer.parseInt(A[1]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(S));
                    }
                    assert point2 != null;
                    if (point1.x == point2.x) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.y >= point1.y)
                                drawInductor = new DrawInductor(point1.x, point1.y, point2.x, point2.y,A[0]);
                            else drawInductor = new DrawInductor(point2.x, point2.y, point1.x, point1.y,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.y >= point1.y)
                                drawInductor = new DrawInductor(point1.x + 50, point1.y, point2.x + 50, point2.y,A[0]);
                            else drawInductor = new DrawInductor(point2.x + 50, point2.y, point1.x + 50, point1.y,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 50, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 50, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.y >= point1.y)
                                drawInductor = new DrawInductor(point1.x + 100, point1.y, point2.x + 100, point2.y,A[0]);
                            else drawInductor = new DrawInductor(point2.x + 100, point2.y, point1.x + 100, point1.y,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 100, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 100, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    else if (point1.y == point2.y) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.x >= point1.x)
                                drawInductor = new DrawInductor(point1.x, point1.y, point2.x, point2.y,A[0]);
                            else drawInductor = new DrawInductor(point2.x, point2.y, point1.x, point1.y,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.x >= point1.x)
                                drawInductor = new DrawInductor(point1.x, point1.y - 50, point2.x, point2.y - 50,A[0]);
                            else drawInductor = new DrawInductor(point2.x, point2.y - 50, point1.x, point1.y - 50,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 50);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 50);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.x >= point1.x)
                                drawInductor = new DrawInductor(point1.x, point1.y - 100, point2.x, point2.y - 100,A[0]);
                            else drawInductor = new DrawInductor(point2.x, point2.y + 100, point1.x, point1.y - 100,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 100);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 100);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    add(drawInductor);
                }
                else if(A[0].charAt(0) == 'V'){
                    DrawVoltageSource drawVoltageSource = null;
                    Point point1 = null,point2 = null;
                    if(searchForPoint(A[1]) != -1 && searchForPoint(A[2]) != -1) {
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[1]) == -1){
                        int n = Integer.parseInt(A[2]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(S));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[2]) == -1){
                        int n = Integer.parseInt(A[1]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(S));
                    }
                    assert point2 != null;
                    if (point1.x == point2.x) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.y >= point1.y)
                                drawVoltageSource = new DrawVoltageSource(point1.x, point1.y, point2.x, point2.y,1,A[0]);
                            else drawVoltageSource = new DrawVoltageSource(point2.x, point2.y, point1.x, point1.y,2,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.y >= point1.y)
                                drawVoltageSource = new DrawVoltageSource(point1.x + 50, point1.y, point2.x + 50, point2.y,1,A[0]);
                            else drawVoltageSource = new DrawVoltageSource(point2.x + 50, point2.y, point1.x + 50, point1.y,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 50, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 50, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.y >= point1.y)
                                drawVoltageSource = new DrawVoltageSource(point1.x + 100, point1.y, point2.x + 100, point2.y,1,A[0]);
                            else drawVoltageSource = new DrawVoltageSource(point2.x + 100, point2.y, point1.x + 100, point1.y,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 100, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 100, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    else if (point1.y == point2.y) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.x >= point1.x)
                                drawVoltageSource = new DrawVoltageSource(point1.x, point1.y, point2.x, point2.y,1,A[0]);
                            else drawVoltageSource = new DrawVoltageSource(point2.x, point2.y, point1.x, point1.y,2,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.x >= point1.x)
                                drawVoltageSource = new DrawVoltageSource(point1.x, point1.y - 50, point2.x, point2.y - 50,1,A[0]);
                            else drawVoltageSource = new DrawVoltageSource(point2.x, point2.y - 50, point1.x, point1.y - 50,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 50);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 50);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.x >= point1.x)
                                drawVoltageSource = new DrawVoltageSource(point1.x, point1.y - 100, point2.x, point2.y - 100,1,A[0]);
                            else drawVoltageSource = new DrawVoltageSource(point2.x, point2.y + 100, point1.x, point1.y - 100,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 100);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 100);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    add(drawVoltageSource);
                }
                else if(A[0].charAt(0) == 'C'){
                    DrawCapacitor drawCapacitor = null;
                    Point point1 = null,point2 = null;
                    if(searchForPoint(A[1]) != -1 && searchForPoint(A[2]) != -1) {
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[1]) == -1){
                        int n = Integer.parseInt(A[2]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(S));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[2]) == -1){
                        int n = Integer.parseInt(A[1]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(S));
                    }
                    assert point2 != null;
                    if (point1.x == point2.x) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.y >= point1.y)
                                drawCapacitor = new DrawCapacitor(point1.x, point1.y, point2.x, point2.y,A[0]);
                            else drawCapacitor = new DrawCapacitor(point2.x, point2.y, point1.x, point1.y,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.y >= point1.y)
                                drawCapacitor = new DrawCapacitor(point1.x + 50, point1.y, point2.x + 50, point2.y,A[0]);
                            else drawCapacitor = new DrawCapacitor(point2.x + 50, point2.y, point1.x + 50, point1.y,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 50, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 50, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.y >= point1.y)
                                drawCapacitor = new DrawCapacitor(point1.x + 100, point1.y, point2.x + 100, point2.y,A[0]);
                            else drawCapacitor = new DrawCapacitor(point2.x + 100, point2.y, point1.x + 100, point1.y,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 100, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 100, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    else if (point1.y == point2.y) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.x >= point1.x)
                                drawCapacitor = new DrawCapacitor(point1.x, point1.y, point2.x, point2.y,A[0]);
                            else drawCapacitor = new DrawCapacitor(point2.x, point2.y, point1.x, point1.y,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.x >= point1.x)
                                drawCapacitor = new DrawCapacitor(point1.x, point1.y - 50, point2.x, point2.y - 50,A[0]);
                            else drawCapacitor = new DrawCapacitor(point2.x, point2.y - 50, point1.x, point1.y - 50,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 50);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 50);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.x >= point1.x)
                                drawCapacitor = new DrawCapacitor(point1.x, point1.y - 100, point2.x, point2.y - 100,A[0]);
                            else drawCapacitor = new DrawCapacitor(point2.x, point2.y + 100, point1.x, point1.y - 100,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 100);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 100);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    add(drawCapacitor);
                }
                else if(A[0].charAt(0) == 'D'){
                    DrawDiode drawDiode = null;
                    Point point1 = null,point2 = null;
                    if(searchForPoint(A[1]) != -1 && searchForPoint(A[2]) != -1) {
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[1]) == -1){
                        int n = Integer.parseInt(A[2]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(S));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[2]) == -1){
                        int n = Integer.parseInt(A[1]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(S));
                    }
                    assert point2 != null;
                    if (point1.x == point2.x) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.y >= point1.y)
                                drawDiode = new DrawDiode(point1.x, point1.y, point2.x, point2.y,1,A[0]);
                            else drawDiode = new DrawDiode(point2.x, point2.y, point1.x, point1.y,2,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.y >= point1.y)
                                drawDiode = new DrawDiode(point1.x + 50, point1.y, point2.x + 50, point2.y,1,A[0]);
                            else drawDiode = new DrawDiode(point2.x + 50, point2.y, point1.x + 50, point1.y,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 50, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 50, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.y >= point1.y)
                                drawDiode = new DrawDiode(point1.x + 100, point1.y, point2.x + 100, point2.y,1,A[0]);
                            else drawDiode = new DrawDiode(point2.x + 100, point2.y, point1.x + 100, point1.y,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 100, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 100, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    else if (point1.y == point2.y) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.x >= point1.x)
                                drawDiode = new DrawDiode(point1.x, point1.y, point2.x, point2.y,1,A[0]);
                            else drawDiode = new DrawDiode(point2.x, point2.y, point1.x, point1.y,2,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.x >= point1.x)
                                drawDiode = new DrawDiode(point1.x, point1.y - 50, point2.x, point2.y - 50,1,A[0]);
                            else drawDiode = new DrawDiode(point2.x, point2.y - 50, point1.x, point1.y - 50,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 50);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 50);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.x >= point1.x)
                                drawDiode = new DrawDiode(point1.x, point1.y - 100, point2.x, point2.y - 100,1,A[0]);
                            else drawDiode = new DrawDiode(point2.x, point2.y + 100, point1.x, point1.y - 100,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 100);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 100);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    add(drawDiode);
                }
                else if(A[0].charAt(0) == 'F' || A[0].charAt(0) == 'G'){
                    DrawRCurrentSource drawRCurrentSource = null;
                    Point point1 = null,point2 = null;
                    if(searchForPoint(A[1]) != -1 && searchForPoint(A[2]) != -1) {
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[1]) == -1){
                        int n = Integer.parseInt(A[2]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(S));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[2]) == -1){
                        int n = Integer.parseInt(A[1]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(S));
                    }
                    assert point2 != null;
                    if (point1.x == point2.x) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.y >= point1.y)
                                drawRCurrentSource = new DrawRCurrentSource(point1.x, point1.y, point2.x, point2.y,1,A[0]);
                            else drawRCurrentSource = new DrawRCurrentSource(point2.x, point2.y, point1.x, point1.y,2,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.y >= point1.y)
                                drawRCurrentSource = new DrawRCurrentSource(point1.x + 50, point1.y, point2.x + 50, point2.y,1,A[0]);
                            else drawRCurrentSource = new DrawRCurrentSource(point2.x + 50, point2.y, point1.x + 50, point1.y,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 50, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 50, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.y >= point1.y)
                                drawRCurrentSource = new DrawRCurrentSource(point1.x + 100, point1.y, point2.x + 100, point2.y,1,A[0]);
                            else drawRCurrentSource = new DrawRCurrentSource(point2.x + 100, point2.y, point1.x + 100, point1.y,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 100, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 100, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    else if (point1.y == point2.y) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.x >= point1.x)
                                drawRCurrentSource = new DrawRCurrentSource(point1.x, point1.y, point2.x, point2.y,1,A[0]);
                            else drawRCurrentSource = new DrawRCurrentSource(point2.x, point2.y, point1.x, point1.y,2,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.x >= point1.x)
                                drawRCurrentSource = new DrawRCurrentSource(point1.x, point1.y - 50, point2.x, point2.y - 50,1,A[0]);
                            else drawRCurrentSource = new DrawRCurrentSource(point2.x, point2.y - 50, point1.x, point1.y - 50,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 50);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 50);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.x >= point1.x)
                                drawRCurrentSource = new DrawRCurrentSource(point1.x, point1.y - 100, point2.x, point2.y - 100,1,A[0]);
                            else drawRCurrentSource = new DrawRCurrentSource(point2.x, point2.y + 100, point1.x, point1.y - 100,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 100);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 100);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    add(drawRCurrentSource);
                }
                else if(A[0].charAt(0) == 'E' || A[0].charAt(0) == 'H'){
                    DrawRVoltageSource drawRVoltageSource = null;
                    Point point1 = null,point2 = null;
                    if(searchForPoint(A[1]) != -1 && searchForPoint(A[2]) != -1) {
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[1]) == -1){
                        int n = Integer.parseInt(A[2]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(S));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[2]) == -1){
                        int n = Integer.parseInt(A[1]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(S));
                    }
                    assert point2 != null;
                    if (point1.x == point2.x) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.y >= point1.y)
                                drawRVoltageSource = new DrawRVoltageSource(point1.x, point1.y, point2.x, point2.y,1,A[0]);
                            else drawRVoltageSource = new DrawRVoltageSource(point2.x, point2.y, point1.x, point1.y,2,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.y >= point1.y)
                                drawRVoltageSource = new DrawRVoltageSource(point1.x + 50, point1.y, point2.x + 50, point2.y,1,A[0]);
                            else drawRVoltageSource = new DrawRVoltageSource(point2.x + 50, point2.y, point1.x + 50, point1.y,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 50, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 50, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.y >= point1.y)
                                drawRVoltageSource = new DrawRVoltageSource(point1.x + 100, point1.y, point2.x + 100, point2.y,1,A[0]);
                            else drawRVoltageSource = new DrawRVoltageSource(point2.x + 100, point2.y, point1.x + 100, point1.y,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 100, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 100, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    else if (point1.y == point2.y) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.x >= point1.x)
                                drawRVoltageSource = new DrawRVoltageSource(point1.x, point1.y, point2.x, point2.y,1,A[0]);
                            else drawRVoltageSource = new DrawRVoltageSource(point2.x, point2.y, point1.x, point1.y,2,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.x >= point1.x)
                                drawRVoltageSource = new DrawRVoltageSource(point1.x, point1.y - 50, point2.x, point2.y - 50,1,A[0]);
                            else drawRVoltageSource = new DrawRVoltageSource(point2.x, point2.y - 50, point1.x, point1.y - 50,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 50);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 50);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.x >= point1.x)
                                drawRVoltageSource = new DrawRVoltageSource(point1.x, point1.y - 100, point2.x, point2.y - 100,1,A[0]);
                            else drawRVoltageSource = new DrawRVoltageSource(point2.x, point2.y + 100, point1.x, point1.y - 100,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 100);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 100);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    add(drawRVoltageSource);
                }
                else if(A[0].charAt(0) == 'I'){
                    DrawCurrentSource drawCurrentSource = null;
                    Point point1 = null,point2 = null;
                    if(searchForPoint(A[1]) != -1 && searchForPoint(A[2]) != -1) {
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[1]) == -1){
                        int n = Integer.parseInt(A[2]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(S));
                        point2 = points.get(searchForPoint(A[2]));
                    }
                    else if(searchForPoint(A[2]) == -1){
                        int n = Integer.parseInt(A[1]);
                        if(n % 6 == 0) n = 6;
                        else n = n%6;
                        String S = String.format("0%d",n);
                        point1 = points.get(searchForPoint(A[1]));
                        point2 = points.get(searchForPoint(S));
                    }
                    assert point2 != null;
                    if (point1.x == point2.x) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.y >= point1.y)
                                drawCurrentSource = new DrawCurrentSource(point1.x, point1.y, point2.x, point2.y,1,A[0]);
                            else drawCurrentSource = new DrawCurrentSource(point2.x, point2.y, point1.x, point1.y,2,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.y >= point1.y)
                                drawCurrentSource = new DrawCurrentSource(point1.x + 50, point1.y, point2.x + 50, point2.y,1,A[0]);
                            else drawCurrentSource = new DrawCurrentSource(point2.x + 50, point2.y, point1.x + 50, point1.y,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 50, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 50, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.y >= point1.y)
                                drawCurrentSource = new DrawCurrentSource(point1.x + 100, point1.y, point2.x + 100, point2.y,1,A[0]);
                            else drawCurrentSource = new DrawCurrentSource(point2.x + 100, point2.y, point1.x + 100, point1.y,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x + 100, point1.y);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x + 100, point2.y);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    else if (point1.y == point2.y) {
                        if(point1.numberOfNeighbor(point2) == 0) {
                            if (point2.x >= point1.x)
                                drawCurrentSource = new DrawCurrentSource(point1.x, point1.y, point2.x, point2.y,1,A[0]);
                            else drawCurrentSource = new DrawCurrentSource(point2.x, point2.y, point1.x, point1.y,2,A[0]);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 1){
                            if(point2.x >= point1.x)
                                drawCurrentSource = new DrawCurrentSource(point1.x, point1.y - 50, point2.x, point2.y - 50,1,A[0]);
                            else drawCurrentSource = new DrawCurrentSource(point2.x, point2.y - 50, point1.x, point1.y - 50,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 50);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 50);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                        else if(point1.numberOfNeighbor(point2) == 2){
                            if(point2.x >= point1.x)
                                drawCurrentSource = new DrawCurrentSource(point1.x, point1.y - 100, point2.x, point2.y - 100,1,A[0]);
                            else drawCurrentSource = new DrawCurrentSource(point2.x, point2.y + 100, point1.x, point1.y - 100,2,A[0]);
                            DrawWire drawWire1 = new DrawWire(point1.x, point1.y, point1.x, point1.y - 100);
                            DrawWire drawWire2 = new DrawWire(point2.x, point2.y, point2.x, point2.y - 100);
                            add(drawWire1);
                            add(drawWire2);
                            point1.neighborPoints.add(point2);
                            point2.neighborPoints.add(point1);
                        }
                    }
                    add(drawCurrentSource);
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setVisible(true);
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        for (int i = 1 ; i <= 6 ; i++){
            for (int j = 1 ; j <= 5 ; j++){
                int x = 500 + (i-1)*150;
                int y = 100 + (j-1)*150;
                g.fillOval(x,y,7,7);
                String A = String.format("%d",i + (5-j) * 6);
                g.drawString(A,x+10,y-10);
                Point point = new Point(x,y,A);
                points.add(point);
            }
        }
        for(int i = 1 ; i <= 6 ; i++){
            int x = 500 + (i-1)*150;
            int y = 850;
            g.fillOval(x,y,7,7);
            String A = String.format("%d",0);
            g.drawString(A,x+10,y-10);
            Point point = new Point(x,y,"0");
            points.add(point);
        }
        g.drawLine(500,853,1250,853);
    }

    public int searchForPoint(String name){
        for (int i = 0 ; i < points.size() ; i++){
            if(points.get(i).name.equals(name)) return i;
        }
        return -1;
    }

    public String[] input(String string){
        return string.split("\\s+");
    }

    public void pointMaker(){
        for (int i = 1 ; i <= 6 ; i++){
            for (int j = 1 ; j <= 5 ; j++){
                int x = 500 + (i-1)*150;
                int y = 100 + (j-1)*150;
                String A = String.format("%d",i + (5-j) * 6);
                Point point = new Point(x,y,A);
                points.add(point);
            }
        }
        for(int i = 1 ; i <= 6 ; i++){
            int x = 500 + (i-1)*150;
            int y = 850;
            String A = String.format("0%d",i);
            Point point = new Point(x,y,A);
            points.add(point);
        }
    }
}
