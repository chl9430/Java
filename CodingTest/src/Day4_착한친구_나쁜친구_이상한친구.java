import java.util.*;

public class Day4_착한친구_나쁜친구_이상한친구 {
    static void main() {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt()) return;
        int N = sc.nextInt();

        Map<String, Integer> goodVotes = new HashMap<>();
        Map<String, Integer> badVotes = new HashMap<>();
        Set<String> allFriend = new HashSet<>();

        for (int i = 0; i < N; i++)
        {
            String good = sc.next(); // jason
            String bad = sc.next(); // nara

            allFriend.add(good);
            allFriend.add(bad);

            goodVotes.put(good, goodVotes.getOrDefault(good, 0) + 1);
            badVotes.put(bad, badVotes.getOrDefault(bad, 0) + 1);
        }

        int goodCount = 0;
        int badCount = 0;
        int weirdCount = 0;

        for (String name : allFriend) {
            int g = goodVotes.getOrDefault(name, 0);
            int b = badVotes.getOrDefault(name, 0);

            if (g > b) {
                goodCount++;
            } else if (g < b) {
                badCount++;
            } else {
                weirdCount++;
            }
        }

        System.out.println(goodCount + " " + badCount + " " + weirdCount);

        sc.close();
    }
}
