import javax.swing.JFrame;

public class app {

    public static void main(String[] args) {
        JFrame mainWindow = new JFrame("Flappy Bird");

        int bWidth = 360;
        int bHeight = 640;
        mainWindow.setSize(bWidth, bHeight);

        mainWindow.setLocationRelativeTo(null);
        mainWindow.setResizable(false);

        FlappyBird flappyBird = new FlappyBird();
        mainWindow.add(flappyBird);

        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}