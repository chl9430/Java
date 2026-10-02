import java.util.*;

public class Day1_요세푸스_순열 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int K = sc.nextInt();

        ArrayDeque<Integer> deque = new ArrayDeque<>();

        for (int i = 1; i <= N; i++)
        {
            deque.offer(i);
        }

        StringBuilder sb = new StringBuilder();

        while (deque.isEmpty() == false)
        {
            for (int i = 0; i < K - 1; i++)
            {
                deque.offer(deque.poll());
            }

            sb.append(deque.poll());

            if (!deque.isEmpty()) {
                sb.append(" ");
            }
        }

        System.out.println(sb.toString());
        sc.close();
    }
}