import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class Main {
    public static void main(String[] args) {
        //Генерация массива
        int[] input = new Random().ints(40_000_000).toArray();
        //Однопоточная сумма
        System.out.println("---------------------");
        System.out.println("Однопоточный вариант:");
        Long timeStart = System.currentTimeMillis();
        System.out.println("Начало работы в  " + timeStart);
        int sumOne = 0;
        //int sumOne = Arrays.stream(input).sum();
        for (int i = 0; i < input.length; i++) {
            sumOne += input[i];
        }
        Long timeEnd = System.currentTimeMillis();
        System.out.println("Окончание работы в  " + timeEnd);
        System.out.println("Время работы: " + (timeEnd - timeStart));

        //Многопоточная сумма
        //input = new Random().ints(20_000_000).toArray();
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        RecursiveTask recursiveTask = new RecursiveSumTask(input);
        System.out.println("---------------------");
        System.out.println("Многопоточный вариант:");
        timeStart = System.currentTimeMillis();
        System.out.println("Начало работы в " + timeStart);
        int sumMult = (int) forkJoinPool.invoke(recursiveTask);
        timeEnd = System.currentTimeMillis();
        System.out.println("Окончание работы в " + timeEnd);
        System.out.println("Время работы: " + (timeEnd - timeStart));

        System.out.println("---------------------");
        System.out.println("Результаты: ");
        System.out.println("Однопоточный: " + sumOne);
        System.out.println("Многопоточный: " + sumMult);
    }
}
