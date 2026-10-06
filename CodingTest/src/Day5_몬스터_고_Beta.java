import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Day5_몬스터_고_Beta {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        Map<Integer, String> monsterMap = new TreeMap<>();

        for (int i = 0; i < n; i++) {
            String command = scanner.next();

            if (command.equals("catch")) {
                int mNumber = scanner.nextInt();
                String mName = scanner.next();

                if (monsterMap.size() < m) {
                    monsterMap.put(mNumber, mName);
                }
            } else if (command.equals("release")) {
                int mNumber = scanner.nextInt();

                monsterMap.remove(mNumber);
            } else if (command.equals("check")) {
                StringBuilder sb = new StringBuilder();
                int count = 0;

                for (String name : monsterMap.values()) {
                    sb.append(name);
                    if (++count < monsterMap.size()) {
                        sb.append(" ");
                    }
                }
                System.out.println(sb.toString());
            }
        }

        scanner.close();
    }
}