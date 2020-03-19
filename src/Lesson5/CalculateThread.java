package Lesson5;

public class CalculateThread extends Thread {

    private int startIndex;
    private int endIndex;
    private int shift;
    private float[] array;

    public long startTime = 0L;
    public long finishTime = 0L;

    public CalculateThread(float[] array, int startIndex, int endIndex, int shift) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.shift = shift;
        start();
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        for (int i = startIndex; i <= endIndex ; i++) {
            array[i] *= Math.sin(0.2f + (i + shift) / 5) * Math.cos(0.2f + (i + shift) / 5) * Math.cos(0.4f + (i + shift) / 2);
        }
        finishTime = System.currentTimeMillis();
    }
}
