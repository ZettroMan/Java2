package Lesson1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainCircles extends JFrame {
    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private static int nBalls = 0; //сначала нет ни одного шарика

    private Sprite[] sprites = new Sprite[10];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainCircles();
            }
        });
    }

    private MainCircles() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Шарики");
        initApplication();

        MainCanvas canvas = new MainCanvas(this);

        //возможно привязку листенера надо было добавить непосредственно в конструктор
        //MainCanvas, а сам код - в MainCanvas. Но тогда нужно было бы каким-то образом
        //обеспечивать доступ из MainCanvas к sprites, nBalls и growUp().
        //Прошу при разборе ДЗ показать, как надо делать правильно.
        //Заранее спасибо!
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        if (nBalls == sprites.length) { //Увеличиваем массив. Шаг увеличения размера массива
                            growUp(10);         //оставил в качестве параметра (можно было и без него обойтись)
                        }
                        if (sprites[nBalls] == null) {  //создаем, только если еще не был ранее создан
                            sprites[nBalls] = new Ball(canvas); //создаем шарик со случайными начальными координатами
                        }
                        nBalls++; //увеличиваем количество шариков только после создания и инициализации нового Ball
                        break;
                    case MouseEvent.BUTTON2: //убираем все шарики с экрана
                        nBalls = 0;
                        break;
                    case MouseEvent.BUTTON3: //убираем последний добавленный шарик
                        if (nBalls > 0) nBalls--;
                }
            }
        });
        add(canvas);

        setVisible(true);
    }

    private void growUp(int growStep) {
        Sprite[] newSprites = new Sprite[sprites.length + growStep];
        for (int i = 0; i < sprites.length; i++) {
            newSprites[i] = sprites[i];
            sprites[i] = null; //очищаем ссылки для GC (на всякий случай, в этой программе это не используется)
        }
        sprites = newSprites; //присваиваем ссылку на новый массив --> sprites
                              //старый массив остается в памяти, но уже становится доступным для GC
                              //если я не верно рассуждаю - пожалуйста поправьте
    }

    private void initApplication() {
        for (int i = 0; i < nBalls; i++) {
            sprites[i] = new Ball();
        }
    }

    public void onCanvasRepainted(MainCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime);
        render(canvas, g);
    }

    private void update(MainCanvas canvas, float deltaTime) {
        for (int i = 0; i < nBalls; i++) {
            sprites[i].update(canvas, deltaTime);
        }
    }

    private void render(MainCanvas canvas, Graphics g) {
        for (int i = 0; i < nBalls; i++) {
            sprites[i].render(canvas, g);
        }
    }
}
