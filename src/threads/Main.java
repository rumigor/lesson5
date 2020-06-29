package threads;

import java.util.Arrays;

public class Main {
    static final int SIZE = 10000000;
    static final int H = SIZE / 2;

    public static void main(String[] args) throws InterruptedException {
        float[] arr = new float[SIZE];
        Arrays.fill(arr,1); //заполняем массив единицами
        long a = System.currentTimeMillis();
        oneThreadFilling(arr); //вычисляем значения и заполняем массив в одном потоке
        System.out.println("Время подсчета первым методом: " + (System.currentTimeMillis() - a));
        Arrays.fill(arr,1); //заполняем массив единицами
        long b = System.currentTimeMillis();
        twoThreadsFilling(arr); //вычисляем значения и заполняем массив в двух потоках
        System.out.println("Общее время подсчета вторым методом: " + (System.currentTimeMillis() - b));
    }
    public static void oneThreadFilling(float [] a){
        for (int i = 0; i < a.length; i++) {
            a[i] = (float)(a[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public static void twoThreadsFilling(float [] arr) throws InterruptedException {
        float [] arr1 = new float [H];
        float [] arr2 = new float[H];
        long c = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, H); //создаем массив первой половины
        System.arraycopy(arr, H, arr2, 0, H); //создаем массив второй половины
        System.out.println("Время затраченное на разбиение массива надвое: " + (System.currentTimeMillis() - c));
        //Вычисляем:
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < arr1.length; i++) {
                arr1[i] = (float)(arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            });
        Thread t2 = new Thread(() -> {
            for (int i = H; i < arr2.length+H; i++) {
                arr2[i-H] = (float)(arr2[i-H] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        t1.start();
        t2.start();
        t2.join(); //ждем окончание вычислений во втором потоке
        long d = System.currentTimeMillis();
        System.arraycopy(arr1, 0, arr, 0, H);
        System.arraycopy(arr2, 0, arr, H, H);
        System.out.println("Время затраченное на склейку массива: " + (System.currentTimeMillis() - d));
    }
}
