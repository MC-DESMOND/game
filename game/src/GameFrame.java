import java.io.IOException;

import javax.swing.JFrame;
public class GameFrame extends JFrame {
    GameFrame() throws IOException{
       this.add(new Gamepanel());
       this.setTitle("Snake Game Demo");
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setResizable(false);
       this.pack();
       this.setVisible(true);
       this.setLocationRelativeTo(null);
    }
}
