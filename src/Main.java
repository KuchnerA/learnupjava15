import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {

        List<Integer> numbers = List.of(3, 5, 7, 9, 11);

        int[] randomElements = new int[1000];
        for (int i = 0; i < randomElements.length; i++) {
            randomElements[i] = 100 + new Random().nextInt(100);
        }

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<Integer>> futures = new ArrayList<>();
        for (int x : numbers) {
            Future<Integer> future = service.submit(() -> {
                int sum = 0;
                for (int i = 0; i < randomElements.length; i++) {
                    if (randomElements[i] % x == 0) {
                        sum += randomElements[i];
                    }
                }
                return sum;

            });
            futures.add(future);
        }
        service.shutdown();

        int max = 0;
        int i = -1;
        for (Future<Integer> future : futures) {
            int x = future.get();
            if (x > max) {
                max = x;
                i = futures.indexOf(future);
            }
        }

        System.out.println("Сумма чисел делящихся на " + numbers.get(i) + " максимальная и равна " + max);
    }

}
