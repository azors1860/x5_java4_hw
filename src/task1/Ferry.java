package task1;

import java.util.concurrent.CyclicBarrier;

public class Ferry {

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new FerryBoat());
        for (int i = 0; i < 9; i++) {
            new Thread(new Car(i, cyclicBarrier)).start();
            Thread.sleep(400);
        }
    }

    public static class FerryBoat implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(500);
                System.out.println("Паром переправил автомобили!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Car implements Runnable {
        private final int carNumber;
        private final CyclicBarrier cyclicBarrier;
        public Car(int carNumber, CyclicBarrier cyclicBarrier) {
            this.carNumber = carNumber;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.printf("Автомобиль №%d подъехал к паромной переправе.\n", carNumber);
                cyclicBarrier.await();
                System.out.printf("Автомобиль №%d продолжил движение.\n", carNumber);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
