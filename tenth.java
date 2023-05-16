import java.util.*;

public class tenth {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of tracks on disk: ");
        int tracks = sc.nextInt();
        System.out.print("Enter number of process items: ");
        int n = sc.nextInt();
        int[] rq = new int[n];

        System.out.print("Enter request queue: ");
        for (int i = 0; i < n; i++) {
            rq[i] = sc.nextInt();
        }

        System.out.print("Enter read write head position: ");
        int rw = sc.nextInt();

        System.out.println("Select the disk scheduling algorithm:");
        System.out.println("1. SSTF");
        System.out.println("2. FCFS (using queue)");
        System.out.println("3. SCAN");
        System.out.println("4. C-SCAN");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                sstf(rq, rw);
                break;
            case 2:
                fcfsQueue(rq, rw);
                break;
            case 3:
                scan(rq, rw, tracks);
                break;
            case 4:
                cScan(rq, rw, tracks);
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    public static void sstf(int[] rq, int rw) {
        int n = rq.length;
        int[] track = new int[n];
        int[] flag = new int[n];
        int tot = 0;

        for (int i = 0; i < n; i++) {
            int min = Integer.MAX_VALUE;
            int index = -1;

            for (int j = 0; j < n; j++) {
                if (flag[j] == 0 && Math.abs(rw - rq[j]) < min) {
                    min = Math.abs(rw - rq[j]);
                    index = j;
                }
            }

            track[i] = rq[index];
            tot += Math.abs(rw - rq[index]);
            rw = rq[index];
            flag[index] = 1;
        }

        System.out.println("Track movement:");
        for (int i = 0; i < n; i++) {
            System.out.print(track[i] + "\t");
        }

        System.out.println("\nTotal Track Movement: " + tot);
    }

    public static void fcfsQueue(int[] rq, int rw) {
        int n = rq.length;
        Queue<Integer> queue = new LinkedList<>();
        int tot = 0;

        for (int i = 0; i < n; i++) {
            queue.add(rq[i]);
        }

        System.out.println("Track movement:");
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            System.out.print(curr + "\t");
            tot += Math.abs(rw - curr);
            rw = curr;
        }

        System.out.println("\nTotal Track Movement: " + tot);
    }

public static void scan(int[] rq, int rw, int tracks) {
    int head = rw;
    int totalTrackMovement = 0;
    int end = 0;

    // Sorting the request queue
    Arrays.sort(rq);

    // Finding the first track before the current head position
    for (int i = 0; i < rq.length; i++) {
        if (rq[i] < head) {
            end = rq[i];
        } else {
            break;
        }
    }

    // Calculating total track movement
    totalTrackMovement = Math.abs(tracks - 1 - head) + Math.abs(tracks - 1 - rq[0]);

    System.out.println("Total Track Movement: " + totalTrackMovement);
}



    public static void cScan(int[] rq, int rw, int tracks) {
    int head = rw;
    int totalTrackMovement = 0;
    int end = 0;

    // Sorting the request queue
    Arrays.sort(rq);

    // Finding the first track before the current head position
    for (int i = 0; i < rq.length; i++) {
        if (rq[i] < head) {
            end = rq[i];
        } else {
            break;
        }
    }

    // Calculating total track movement
    totalTrackMovement = Math.abs(tracks - 1 - head) + Math.abs(tracks - 1 - 0) + Math.abs(end - 0);
    System.out.println("Total Track Movement: " + totalTrackMovement);
}

}
