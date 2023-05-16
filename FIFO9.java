import java.util.*;

public class FIFO9 {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter no of frames in main memory: ");
        int numFrames = sc.nextInt();

        Queue<Integer> frames = new LinkedList<>();
        System.out.print("Enter no of pages to be executed: ");
        int numPages = sc.nextInt();

        int[] pageStream = new int[numPages];
        System.out.println("Enter page stream:");

        for (int i = 0; i < numPages; i++) {
            pageStream[i] = sc.nextInt();
        }

        char check = 'F';
        int j = 0, count1 = 0, count2 = 0;

        System.out.println("\nPage Stream");

        for (int i = 0; i < numPages; i++) {
            int currentPage = pageStream[i];

            if (frames.contains(currentPage)) {
                check = 'H';
                count2++;
            } else {
                if (j < numFrames) {
                    frames.offer(currentPage);
                    j++;
                } else {
                    int removedPage = frames.poll();
                    frames.offer(currentPage);
                }

                count1++;
            }

            System.out.print(currentPage + "\t\t\t");

            for (int k = 0; k < numFrames; k++) {
                if (k < frames.size()) {
                    System.out.print(frames.toArray()[k] + "\t");
                } else {
                    System.out.print("\t");
                }
            }

            System.out.println(check);
            check = 'F';
        }

        float hitRatio = (float) count2 / numPages;
        float missRatio = (float) count1 / numPages;

        System.out.println("\nNo. of page hits = " + count2);
        System.out.println("No. of page faults = " + count1);
        System.out.println("Hit ratio = " + hitRatio);
        System.out.println("Miss ratio = " + missRatio);
    }
}
