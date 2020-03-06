package Lesson1;

import java.awt.*;
import java.util.Random;

public class Background {
    private int red;
    private int green;
    private int blue;
    private static Random rand = new Random();

    Background() {
        red = (int) (Math.random() * 255);
        green = (int) (Math.random() * 255);
        blue = (int) (Math.random() * 255);
    }

    public void shiftColor() {
        int colorSelection = rand.nextInt(3);
        int direction = rand.nextInt(2) * 4 - 2;
        switch (colorSelection) {
            case 0:
                red += direction;
                break;
            case 1:
                green += direction;
                break;
            case 2:
                blue += direction;
        }
        if (red < 0) {
            red = 0;
        }
        if (green < 0) {
            green = 0;
        }
        if (blue < 0) {
            blue = 0;
        }
        if (red > 255) {
            red = 255;
        }
        if (green > 255) {
            green = 255;
        }
        if (blue > 255) {
            blue = 255;
        }
    }

    public void repaint(MainCanvas canvas, Graphics g) {
        g.setColor(new Color(red, green, blue));
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //Хотел сделать через изменение цвета фона канвы, но не вышло.
        //Видимо еще нет полного понимания, как это все должно работать;)
        //        canvas.setBackground(new Color(red, green, blue));
    }
}
