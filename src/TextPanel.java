import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextPanel extends JTextArea {

    private File file;
    TextPanel(File file){
        super();
        this.file = file;
        Border border = BorderFactory.createLineBorder(Color.BLACK,5);
        setBorder(border);
        setLayout(null);
        setBounds(200,200,1400,600);
        setFont(new Font("Serif",Font.PLAIN,20));
        ArrayList<String> A = new ArrayList<>();
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()){
                A.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String string : A){
            append(string);
            append("\n");
        }
    }
    public void run() throws IOException {
        File file1 = new File(String.valueOf(file));
        FileWriter fileWriter = new FileWriter(file1);
        String[] B = getText().split("\n");
        for (int i = 0 ; i < B.length ; i++){
            fileWriter.write(B[i]);
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

}
