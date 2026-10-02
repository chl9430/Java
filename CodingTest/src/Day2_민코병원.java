import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

class Patient implements Comparable<Patient> {
    int id;
    String name;
    int age;
    int urgency;
    boolean treated;

    public Patient(int id, String name, int age, int urgency) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.urgency = urgency;
        this.treated = false;
    }

    @Override
    public int compareTo(Patient o) {
        if (this.urgency != o.urgency) {
            return Integer.compare(o.urgency, this.urgency);
        }

        if (this.age != o.age) {
            return Integer.compare(this.age, o.age);
        }

        return Integer.compare(this.id, o.id);
    }
}

public class Day2_민코병원 {
    static void main() {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt()) return;

        int Q = sc.nextInt();

        Queue<Patient> queueA = new LinkedList<>();
        PriorityQueue<Patient> pqB = new PriorityQueue<>();

        int patientIdCounter = 0;

        for (int q = 0; q < Q; q++) {
            int type = sc.nextInt();

            if (type == 1) {

                String name = sc.next();
                int age = sc.nextInt();
                int urgency = sc.nextInt();

                Patient patient = new Patient(patientIdCounter++, name, age, urgency);
                queueA.add(patient);
                pqB.add(patient);
            } else if (type == 2) {

                String ward = sc.next();

                if (ward.equals("A")) {
                    while (!queueA.isEmpty() && queueA.peek().treated) {
                        queueA.poll();
                    }

                    if (queueA.isEmpty()) {
                        System.out.println("EMPTY");
                    } else {
                        Patient p = queueA.poll();
                        p.treated = true;
                        System.out.println(p.name);
                    }
                } else if (ward.equals("B")) {
                    while (!pqB.isEmpty() && pqB.peek().treated) {
                        pqB.poll();
                    }

                    if (pqB.isEmpty()) {
                        System.out.println("EMPTY");
                    } else {
                        Patient p = pqB.poll();
                        p.treated = true;
                        System.out.println(p.name);
                    }
                }
            }
        }

        sc.close();
    }
}
