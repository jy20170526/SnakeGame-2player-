package Game.src;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.io.File;

public class Game extends JFrame {

Game() {
    add(new Board());
    setResizable(false);
    pack();

    setTitle("Kill the Snake");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}

public static void main(String[] args) {

    // Creates a new thread so our GUI can process itself
    EventQueue.invokeLater(new Runnable() {
        @Override
        public void run() {
            JFrame frame = new Game();
            frame.setVisible(true);
        }
    });
    while(true) {
    	File gamemusic =new File("fever.wav");
        music.PlaySound(gamemusic);
    }
}
}