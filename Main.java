import java.util.*;

public class Main {
    static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    static int maxRepeatKey = 0;
    static int maxRepeatValue = 0;

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void main(String[] args) {

        Object lock = new Object();

        List<Thread> threads = new ArrayList<>();

        for (int j = 0; j < 1000; j++) {
            Thread thread = new Thread(() -> {
                String s = generateRoute("RLRFR", 100);
                int counter = 0;
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if (c == 'R') {
                        counter++;
                    }
                }

                synchronized (lock) {
                    if (!sizeToFreq.containsKey(counter)) {
                        sizeToFreq.put(counter, 1);
                    } else {
                        sizeToFreq.put(counter, sizeToFreq.get(counter) + 1);
                    }

                    if (sizeToFreq.get(counter) > maxRepeatValue) {
                        maxRepeatKey = counter;
                        maxRepeatValue = sizeToFreq.get(counter);
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Самое частое количество повторений " + maxRepeatKey + " (встретилось " + maxRepeatValue + " раз)");

        for (Integer integer : sizeToFreq.keySet()) {
            System.out.println("Другие размеры: " + integer + " " + "(" + sizeToFreq.get(integer) + " раз)");
        }
    }
}














































