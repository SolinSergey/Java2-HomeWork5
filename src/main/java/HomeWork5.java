public class HomeWork5 {
    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) throws InterruptedException {
        singleTread();
        twoTread();
    }

    public static void singleTread(){
        float[] arr = new float[SIZE];
        for (int i=0;i<SIZE;i++){
            arr[i]=1.0f;
        }

        long startTime=System.currentTimeMillis();
        for (int i=0;i<SIZE;i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время обсчета в один поток: " + (System.currentTimeMillis() - startTime) + " ms.");
        System.out.println();
    }

    public static void twoTread() throws InterruptedException {
        float[] arr = new float[SIZE];
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        for (int i=0;i<SIZE;i++){
            arr[i]=1.0f;
        }
        long startTime=System.currentTimeMillis();
        System.arraycopy(arr,0,a1,0,HALF);
        System.arraycopy(arr,HALF,a2,0,HALF);
        long delenie = System.currentTimeMillis()-startTime;
        System.out.println("Время разбивки на 2 массива: "+  delenie + " ms.");
        Thread thread1 = new Thread(() -> {
            long time1 = System.currentTimeMillis();
            for (int i = 0; i <HALF; i++) {
                a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            time1=System.currentTimeMillis()-time1;
            System.out.println("Обсчет 1-ой половины массива: "+  time1 + " ms.");

        });
        Thread thread2 = new Thread(() -> {
            long time2 = System.currentTimeMillis();
            for (int j = HALF; j <SIZE; j++) {
                a2[j-HALF] = (float)(a2[j-HALF] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
            }
            time2=System.currentTimeMillis()-time2;
            System.out.println("Обсчет 2-ой половины массива: "+  time2 + " ms.");
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        long unionArray = System.currentTimeMillis();
        System.arraycopy(a1,0,arr,0,HALF);
        System.arraycopy(a2,0,arr,HALF,HALF);
        unionArray=System.currentTimeMillis()-unionArray;
        System.out.println("Время склейки 2-х массивов в 1: "+  unionArray + " ms.");
        System.out.println("Время обсчета в два потока: " + (System.currentTimeMillis() - startTime) + " ms.");

    }
}
