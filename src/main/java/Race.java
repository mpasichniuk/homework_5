import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Race {
    public static final int CARS_COUNT = 4;
    private static ExecutorService executorService = Executors.newFixedThreadPool(CARS_COUNT);
    public static final AtomicInteger finishCount = new AtomicInteger(0);
    private final Road road;
    private final Tunnel tunnel;

    public Race(Road road, Tunnel tunnel, Road road1) {
        this.road = road;
        this.tunnel = tunnel;


    }


    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        CyclicBarrier cb = new CyclicBarrier(CARS_COUNT + 1,
                () -> System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!"));
        CountDownLatch cdl = new CountDownLatch(CARS_COUNT);

        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] car = new Car[CARS_COUNT];
        for (int i = 0; i < car.length; i++) {
            car[i] = new Car(race, 20 + (int) (Math.random() * 10), cb, cdl, finishCount);
        }

        for (int i = 0; i < car.length; i++) {
            executorService.execute(car[i]);
        }

        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException ex) {
            ex.printStackTrace();
        }

        try {
            cdl.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
            executorService.shutdown();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        }

    }

    class Road {
        public Road(int i) {
        }
    }

class Tunnel {
    }









