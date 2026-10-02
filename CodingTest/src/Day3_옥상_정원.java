import java.util.ArrayDeque;
import java.util.Scanner;

public class Day3_옥상_정원 {
    static void main() {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        ArrayDeque<Long> deque = new ArrayDeque<>();

        long totalCount = 0;

        for (int i = 0; i < n; i++)
        {
            long height = sc.nextLong();

            while (!deque.isEmpty() && deque.peek() <= height)
            {
                deque.pop();
            }

            totalCount += deque.size();

            deque.push(height);
        }

        System.out.println(totalCount);
        sc.close();
    }
}
