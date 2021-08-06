package task3;

import java.util.concurrent.Semaphore;

public class Parking {
    private static final boolean[] PARKING_PLACES = new boolean[5];

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(5);
        for (int i = 1; i <= 7; i++) {
            new Thread(new Car(i, semaphore)).start();
            Thread.sleep(400);
        }
    }

    public static class Car implements Runnable {
        private final int carNumber;
        Semaphore semaphore;

        public Car(int carNumber, Semaphore semaphore) {
            this.carNumber = carNumber;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            System.out.printf("Автомобиль №%d подъехал к парковке.\n", carNumber);
            try {
                semaphore.acquire();
                int parkingNumber = -1;
                synchronized (PARKING_PLACES) {
                    for (int i = 0; i < 5; i++)
                        if (!PARKING_PLACES[i]) {
                            PARKING_PLACES[i] = true;
                            parkingNumber = i;
                            System.out.printf("Автомобиль №%d припарковался на месте %d.\n", carNumber, i);
                            break;
                        }
                }
                Thread.sleep(5000);
                synchronized (PARKING_PLACES) {
                    PARKING_PLACES[parkingNumber] = false;
                }
                semaphore.release();
                System.out.printf("Автомобиль №%d покинул парковку.\n", carNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
