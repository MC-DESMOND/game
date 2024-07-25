import java.io.IOException;
 import java.awt.*;
import javax.swing.JFrame;
public class GameFrame extends JFrame {
    GameFrame() throws IOException{
       this.add(new Gamepanel(new BorderLayout()));
       this.setTitle("Car Driving");
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setResizable(false);
       this.pack();
       this.setVisible(true);
       this.setLocationRelativeTo(null);
    }
}
