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
        System.out.println("One thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
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
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i <HALF; i++) {
                a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i <HALF; i++) {
                a2[i] = (float)(a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.arraycopy(a1,0,arr,0,HALF);
        System.arraycopy(a2,0,arr,HALF,HALF);
        System.out.println("Two thread time: " + (System.currentTimeMillis() - startTime) + " ms.");

    }
}
