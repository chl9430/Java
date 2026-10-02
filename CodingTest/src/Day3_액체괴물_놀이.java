import java.util.Scanner;
import java.util.PriorityQueue;

public class Day3_액체괴물_놀이 {
    static void main() {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        PriorityQueue<Long> pq = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            pq.add(sc.nextLong());
        }

        long totalCost = 0;

        while (pq.size() > 1) {
            long first = pq.poll();
            long second = pq.poll();

            long sum = first + second;
            totalCost += sum;

            pq.add(sum);
        }

        System.out.println(totalCost);

        sc.close();
    }
}
