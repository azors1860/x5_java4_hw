package task2;

import java.util.concurrent.CountDownLatch;

public class Race {

    private static final int trackLength = 500000;
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatchArrivedCar = new CountDownLatch(5);
        CountDownLatch countDownLatchRace = new CountDownLatch(3);

        for (int i = 1; i <= 5; i++) {
            new Thread(new Car(i, (int) (Math.random() * 100 + 50),countDownLatchArrivedCar,countDownLatchRace)).start();
            Thread.sleep(1000);
        }
        countDownLatchArrivedCar.await();
        System.out.println("На старт!");
        Thread.sleep(2000);
       countDownLatchRace.countDown();
        System.out.println("Внимание!");
        Thread.sleep(2000);
        countDownLatchRace.countDown();
        System.out.println("Марш!");
        countDownLatchRace.countDown();
    }

    public static class Car implements Runnable {
        private final int carNumber;
        private final int carSpeed;
        private final CountDownLatch countDownLatchArrivedCar;
        private final CountDownLatch countDownLatchRace;

        public Car(int carNumber, int carSpeed, CountDownLatch countDownLatchArrivedCar,
                   CountDownLatch countDownLatchRace) {
            this.carNumber = carNumber;
            this.carSpeed = carSpeed;
            this.countDownLatchArrivedCar = countDownLatchArrivedCar;
            this.countDownLatchRace = countDownLatchRace;
        }

        @Override
        public void run() {
            try {
                System.out.printf("Автомобиль №%d подъехал к стартовой прямой.\n", carNumber);
                countDownLatchArrivedCar.countDown();
                countDownLatchRace.await();
                Thread.sleep(trackLength / carSpeed);
                System.out.printf("Автомобиль №%d финишировал!\n", carNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
