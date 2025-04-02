
import java.awt.*;

public class Bird {
    Point point;
    int width = 34;
    int height = 24;
    Image image;

    Bird(Image img) {
        image = img;
        point = new Point(0, 0);
        resetSetting();
    }

    public void resetSetting() {
        this.point.setPoint(360 / 8, 640 / 8);
    }

}
