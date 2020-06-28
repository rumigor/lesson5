package threads;

public class ArrayFilling extends Thread {
    float [] arr;
    int h;

    public ArrayFilling(float[] arr, int h) {
        this.arr = arr;
        this.h = h;
    }

    @Override
    public void run() {
        long a = System.currentTimeMillis();
        for (int i = h; i < arr.length+h; i++) {
            arr[i-h] = (float)(arr[i-h] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время затраченное на заполнение половины массива" + "(" + getName() + "): " + (System.currentTimeMillis() - a));
    }
}
