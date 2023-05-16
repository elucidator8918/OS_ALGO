import java.util.Scanner;
import java.util.Arrays;

public class LRU {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of frames in main memory: ");
        int numFrames = scanner.nextInt();

        int[] frames = new int[numFrames];
        int[] usageFlags = new int[numFrames];
        Arrays.fill(frames, -1);

        System.out.print("Enter the number of pages to be executed: ");
        int numPages = scanner.nextInt();

        int[] pageStream = new int[numPages];
        System.out.println("Enter the page stream:");
        for (int i = 0; i < numPages; i++)
            pageStream[i] = scanner.nextInt();

        char check = 'F';
        int currentPosition = 0, pageHits = 0, pageFaults = 0, currentUsage = 0;

        System.out.println("\nPage Stream");
        for (int i = 0; i < numPages; i++) {
            int lru = findLeastRecentlyUsedFrame(usageFlags);
            check = 'F';

            for (int j = 0; j < numFrames; j++) {
                if (frames[j] == pageStream[i]) {
                    check = 'H';
                    pageHits++;
                    usageFlags[j] = ++currentUsage;
                    break;
                }
            }

            if (check != 'H') {
                if (currentPosition < numFrames) {
                    frames[currentPosition] = pageStream[i];
                    usageFlags[currentPosition] = ++currentUsage;
                    currentPosition++;
                    pageFaults++;
                } else {
                    frames[lru] = pageStream[i];
                    usageFlags[lru] = ++currentUsage;
                    pageFaults++;
                }
            }

            System.out.print(pageStream[i] + "\t\t");
            for (int frame : frames) {
                if (frame != -1)
                    System.out.print(frame + "\t");
                else
                    System.out.print("\t");
            }
            System.out.println(check);
        }

        float hitRatio = (float) pageHits / numPages;
        float missRatio = (float) pageFaults / numPages;
        System.out.println("\nNo. of page hits = " + pageHits);
        System.out.println("No. of page faults = " + pageFaults);
        System.out.println("Hit ratio = " + hitRatio);
        System.out.println("Miss ratio = " + missRatio);

        scanner.close();
    }

    private static int findLeastRecentlyUsedFrame(int[] usageFlags) {
        int minUsage = Integer.MAX_VALUE;
        int lruFrame = 0;

        for (int i = 0; i < usageFlags.length; i++) {
            if (usageFlags[i] < minUsage) {
                minUsage = usageFlags[i];
                lruFrame = i;
            }
        }

        return lruFrame;
    }
}

