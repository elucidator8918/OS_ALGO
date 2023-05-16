import java.util.*;

public class eighth {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of memory partitions: ");
        int totalMem = sc.nextInt();
        int part[] = new int[totalMem];
        int i, j;

        for (i = 0; i < totalMem; i++) {
            System.out.print("Enter memory in memory partition " + (i + 1) + ": ");
            part[i] = sc.nextInt();
        }

        System.out.print("\nEnter number of processes to be added to main memory: ");
        int n = sc.nextInt();
        int mem_p[] = new int[n];

        for (i = 0; i < n; i++) {
            System.out.print("Enter memory to be assigned to process " + (i + 1) + ": ");
            mem_p[i] = sc.nextInt();
        }

        System.out.print("\nEnter memory allocation algorithm (1 for First Fit, 2 for Best Fit, 3 for Next Fit): ");
        int algorithm = sc.nextInt();

        switch (algorithm) {
            case 1:
                firstFit(part, mem_p);
                break;
            case 2:
                bestFit(part, mem_p);
                break;
            case 3:
                nextFit(part, mem_p);
                break;
            default:
                System.out.println("Invalid algorithm choice!");
        }
    }

    public static void firstFit(int[] part, int[] mem_p) {
        int flag[] = new int[part.length];
        int i, j;

        for (i = 0; i < part.length; i++)
            flag[i] = 0;

        for (i = 0; i < mem_p.length; i++) {
            int id = -1;
            for (j = 0; j < part.length; j++) {
                if (flag[j] == 0 && mem_p[i] <= part[j]) {
                    id = j;
                    break;
                }
            }
            if (id != -1) {
                System.out.println("Process " + (i + 1) + "\tMemory Allocated\tPartition: " + part[id]);
                flag[id] = 1;
            } else {
                System.out.println("Process " + (i + 1) + "\tMemory Not Allocated");
            }
        }
    }
    public static void bestFit(int[] part, int[] mem_p) {
        int flag[] = new int[part.length];
        int i, j;

        for (i = 0; i < part.length; i++)
            flag[i] = 0;

        int diff = Integer.MAX_VALUE;
        int id;

        for (i = 0; i < mem_p.length; i++) {
            id = -1;
            for (j = 0; j < part.length; j++) {
                if (flag[j] == 0 && mem_p[i] <= part[j] && part[j] - mem_p[i] < diff) {
                    id = j;
                    diff = part[j] - mem_p[i];
                }
            }
            if (id != -1) {
                System.out.println("Process " + (i + 1) + "\tMemory Allocated\tPartition: " + part[id]);
                flag[id] = 1;
            } else {
                System.out.println("Process " + (i + 1) + "\tMemory Not Allocated");
            }
            diff = Integer.MAX_VALUE;
        }
    }


    public static void nextFit(int[] part, int[] mem_p) {
        int flag[] = new int[part.length];
        int i, j;
        int id, prevId = 0;

        for (i = 0; i < part.length; i++)
            flag[i] = 0;

        for (i = 0; i < mem_p.length; i++) {
            id = -1;
            for (j = prevId; j < part.length; j++) {
                if (flag[j] == 0 && mem_p[i] <= part[j]) {
                    id = j;
                    break;
                }
            }
            if (id != -1) {
                System.out.println("Process " + (i + 1) + "\tMemory Allocated\tPartition: " + part[id]);
                flag[id] = 1;
                prevId = id;
            } else {
                System.out.println("Process " + (i + 1) + "\tMemory Not Allocated");
            }
        }
    }
}
