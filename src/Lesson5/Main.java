package Lesson5;

import java.util.Arrays;

public class Main {
    private static final int ARRAY_SIZE = 10000000;
    private static float[] array = new float[ARRAY_SIZE];
    private static long startTime, midTime, finishTime, overallTime;

    public static void main(String[] args) {


        sequentRun();

        parallelRunNoCopy(2);
        parallelRunNoCopy(4);
        parallelRunNoCopy(8);
        parallelRunNoCopy(37);

        parallelRunWithCopy(2);
        parallelRunWithCopy(4);
        parallelRunWithCopy(8);
        parallelRunWithCopy(37);

    }

    //Параллельная обработка массива без копирования в массивы меньшего размера
    private static void parallelRunNoCopy(int nThreads) {
        int tempStartIndex = 0;
        int step;

        if (nThreads < 1) throw new RuntimeException("Illegal threads number");
        //проверяем, чтобы количество потоков не оказалось больше размера массива
        if (nThreads > ARRAY_SIZE) nThreads = ARRAY_SIZE;
        step = ARRAY_SIZE / nThreads;
        CalculateThread[] calculateThreads = new CalculateThread[nThreads];

        Arrays.fill(array, 1);  //вставил внутрь метода, чтобы не загромождать main()

        System.out.printf("\nParallel execution without copying of array. Number of threads: %d\n", nThreads);
        startTime = System.currentTimeMillis();
        System.out.println("Start time: " + startTime);

        //производим обсчет каждой части исходного массива в отдельном потоке
        for (int i = 0; i < nThreads - 1; i++) {
            calculateThreads[i] = new CalculateThread(array, tempStartIndex, tempStartIndex + step, 0);
            tempStartIndex += step;
        }
        calculateThreads[nThreads - 1] = new CalculateThread(array, tempStartIndex, ARRAY_SIZE - 1, 0);

        //ждем окончания всех потоков
        try {
            for (int i = 0; i < nThreads; i++) {
                calculateThreads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finishTime = System.currentTimeMillis();

        //выводим информацию о времени работы каждого потока
        //закомментарил вывод более подробной информации
        for (int i = 0; i < nThreads; i++) {
//            System.out.printf("Thread #%d. StartTime = %d \t EndTime = %d \t Duration(ms) = %d\n",
//                    i, calculateThreads[i].startTime, calculateThreads[i].finishTime, calculateThreads[i].finishTime - calculateThreads[i].startTime);
            System.out.printf("Thread #%d. Duration = %.3f seconds.\n", i, ((float) (calculateThreads[i].finishTime - calculateThreads[i].startTime)) / 1000);
        }

        System.out.println("Finish time: " + finishTime);
        overallTime = finishTime - startTime;
        System.out.printf("\nOverall time elapsed: %.3f seconds.\n", (float) overallTime / 1000);
        System.out.print("Checking array: ");
        checkArray();
        System.out.println("==============================================================================================================================");
    }

    //Параллельная обработка массива с копированием в массивы меньшего размера
    private static void parallelRunWithCopy(int nThreads) {

        int tempStartIndex = 0;
        int step;

        if (nThreads < 1) throw new RuntimeException("Illegal threads number");

        //проверяем, чтобы количество потоков не оказалось больше размера массива
        if (nThreads > ARRAY_SIZE) nThreads = ARRAY_SIZE;
        System.out.printf("\nParallel execution with copying to smaller arrays. Number of threads: %d\n", nThreads);

        float[][] partsArray = new float[nThreads][]; //массив массивов для копирования частей исходного массива)
        CalculateThread[] calculateThreads = new CalculateThread[nThreads];
        step = ARRAY_SIZE / nThreads;

        Arrays.fill(array, 1);  //вставил внутрь метода, чтобы не загромождать main()

        startTime = System.currentTimeMillis();
        System.out.print("\nStart splitting the array...");

        //разделяем массив на nThreads частей копированием )))
        for (int i = 0; i < nThreads - 1; i++) {
            partsArray[i] = new float[step];
            System.arraycopy(array, tempStartIndex, partsArray[i], 0, step);
            tempStartIndex += step;
            System.out.print(".");
        }
        //последняя часть
        partsArray[nThreads - 1] = new float[ARRAY_SIZE - tempStartIndex];
        System.arraycopy(array, tempStartIndex, partsArray[nThreads - 1], 0, ARRAY_SIZE - tempStartIndex);

        finishTime = System.currentTimeMillis();
        System.out.printf("DONE!\n  Time elapsed: %.3f seconds.\n", ((float) (finishTime - startTime)) / 1000);
        System.out.print("Start processing...");
        midTime = finishTime;

        //производим обсчет каждой части в отдельном потоке
        for (int i = 0; i < nThreads - 1; i++) {
            calculateThreads[i] = new CalculateThread(partsArray[i], 0, step - 1, i * step);
        }
        calculateThreads[nThreads - 1] = new CalculateThread(partsArray[nThreads - 1], 0, ARRAY_SIZE - (nThreads - 1) * step - 1, (nThreads - 1) * step);

        //ждем окончания всех потоков
        try {
            for (int i = 0; i < nThreads; i++) {
                calculateThreads[i].join();
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finishTime = System.currentTimeMillis();
        System.out.printf("DONE!\n  Time elapsed: %.3f seconds.\n", ((float) (finishTime - midTime)) / 1000);
        System.out.println("Detailed information about each thread:");
        for (int i = 0; i < nThreads; i++) {
            System.out.printf("Thread #%d. Duration = %.3f seconds.\n", i, ((float) (calculateThreads[i].finishTime - calculateThreads[i].startTime)) / 1000);
        }

        System.out.print("Start merging...");
        midTime = finishTime;

        //объединяем массивы обратно
        for (int i = 0; i < nThreads - 1; i++) {
            System.arraycopy(partsArray[i], 0, array, i * step, step);
            System.out.print(".");
        }
        //последняя часть
        System.arraycopy(partsArray[nThreads - 1], 0, array, (nThreads - 1) * step, ARRAY_SIZE - tempStartIndex);

        finishTime = System.currentTimeMillis();
        System.out.printf("DONE!\n  Time elapsed: %.3f seconds.\n", ((float) (finishTime - midTime)) / 1000);

        overallTime = finishTime - startTime;
        System.out.printf("\nOverall time elapsed: %.3f seconds.\n", (float) overallTime / 1000);
        System.out.print("Checking array: ");
        checkArray();
        System.out.println("==============================================================================================================================");
    }

    //Простая обработка всего массива - 1 поток
    private static void sequentRun() {
        System.out.print("Sequential execution. The same thing as a parallel execution with only 1 thread.");
        parallelRunNoCopy(1);
    }

    //выводит значения тестового набора элементов массива
    private static void checkArray() {
        for (int i = 0; i < ARRAY_SIZE; i += ARRAY_SIZE / 13) {
            System.out.printf("%.3f\t", array[i]);
        }
        System.out.println();
    }

}
