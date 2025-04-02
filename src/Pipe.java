import java.awt.Image;

public class Pipe {
    Point point;
    int width = 64;
    int height = 512;
    boolean isPass;
    Image image;

    Pipe(Image img) {
        point = new Point(360 - 64, 0);
        image = img;
        isPass = false;
    }
}
