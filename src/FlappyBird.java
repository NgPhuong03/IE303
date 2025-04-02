import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

// 
public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int bWidth = 360;
    int bHeight = 640;
    Image backgroundImage;
    Image birdImage;
    Image pipeImage;
    Image botPipeImage, topPipeImage;

    Bird bird;
    ArrayList<Pipe> pipes;

    int birdVelocity = 0;
    int pipeVelocity = -5;
    int gravity = 1;
    int score = 0;
    boolean isGameOver = false;

    Timer gameloop;
    Timer pipeloop;

    FlappyBird() {
        setFocusable(true);
        addKeyListener(this);

        backgroundImage = new ImageIcon(getClass().getResource("/images/flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("/images/flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("/images/toppipe.png")).getImage();
        botPipeImage = new ImageIcon(getClass().getResource("/images/bottompipe.png")).getImage();

        bird = new Bird(birdImage);
        pipes = new ArrayList<>();

        gameloop = new Timer(1000 / 60, this);
        gameloop.start();

        pipeloop = new Timer(1200, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                placePipe();
            }

        });
        pipeloop.start();

    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(backgroundImage, 0, 0, bWidth, bHeight, null);
        graphics.drawImage(birdImage, bird.point.x, bird.point.y, bird.width, bird.height, null);

        for (Pipe p : pipes) {
            graphics.drawImage(p.image, p.point.x, p.point.y, p.width, p.height, null);
        }

        graphics.setColor(Color.white);
        graphics.setFont(new Font("Arial", Font.PLAIN, 32));

        if (isGameOver) {
            graphics.drawString("Game is over: " + score / 2, 40, bHeight / 2);
        } else {
            graphics.drawString(String.valueOf(score / 2), 40, 40);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
            birdVelocity = -12;
            if (isGameOver) {
                resetSetting();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBird();
        pipeMove();
        repaint();

        if (isGameOver) {
            gameloop.stop();
            pipeloop.stop();
        }
    }

    public void moveBird() {
        birdVelocity += gravity;
        bird.point.y += birdVelocity;

        bird.point.y = Math.max(0, bird.point.y);
        bird.point.y = Math.min(bHeight - 60, bird.point.y);

        if (bird.point.y == 0 || bird.point.y == bHeight - 60) {
            isGameOver = true;
        }
    }

    public void placePipe() {
        Pipe topPipe = new Pipe(topPipeImage);
        int randomY = topPipe.point.y - (int) (topPipe.height / 5) - (int) (Math.random() * (topPipe.height - 180));
        topPipe.point.y = randomY;
        pipes.add(topPipe);

        int spacebetween = topPipe.height / 4;

        Pipe botPipe = new Pipe(botPipeImage);
        botPipe.point.y = randomY + topPipe.height + spacebetween;
        pipes.add(botPipe);
    }

    public void pipeMove() {

        for (Pipe p : pipes) {
            p.point.setX(p.point.x + pipeVelocity);
            if (bird.point.x + bird.width > p.point.x + p.width && !p.isPass) {
                score++;
                p.isPass = true;

            }

            if (isDead(p)) {
                isGameOver = true;
            }
        }
    }

    public void resetSetting() {
        isGameOver = false;
        score = 0;
        birdVelocity = 0;
        bird.resetSetting();
        pipes.clear();
        gameloop.start();
        pipeloop.start();
    }

    public boolean isDead(Pipe pipe) {
        return bird.point.x < pipe.point.x + pipe.width &&
                bird.point.x + bird.width > pipe.point.x &&
                bird.point.y < pipe.point.y + pipe.height &&
                bird.point.y + bird.height > pipe.point.y;
    }

}
