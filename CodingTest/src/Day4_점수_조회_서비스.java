import java.util.*;

class Student {
    String name;
    int language;
    int math;
    int science;
    int sociology;

    public Student(String name, int language, int math, int science, int sociology) {
        this.name = name;
        this.language = language;
        this.math = math;
        this.science = science;
        this.sociology = sociology;
    }

    public int getScore(String subject) {
        switch (subject) {
            case "language":
                return language;
            case "math":
                return math;
            case "science":
                return science;
            case "sociology":
                return sociology;
            default:
                return 0;
        }
    }
}

public class Day4_점수_조회_서비스 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt()) return;
        int N = sc.nextInt();
        int M = sc.nextInt();

        Map<Long, Student> studentMap = new HashMap<>();

        for (int i = 0 ; i < N; i++)
        {
            long id = sc.nextLong();
            String name = sc.next();
            int lang = sc.nextInt();
            int math = sc.nextInt();
            int sci = sc.nextInt();
            int soc = sc.nextInt();

            studentMap.put(id, new Student(name, lang, math, sci, soc));
        }

        for (int i = 0; i < M; i++) {
            long queryId = sc.nextLong();
            String subject = sc.next();

            Student student = studentMap.get(queryId);
            System.out.println(student.name + " " + student.getScore(subject));
        }

        sc.close();
    }
}
