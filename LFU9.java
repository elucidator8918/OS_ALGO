import java.util.*;

public class LFU9 {

static class LFUPage {
    int page;
    int frequency;
    int timestamp;

    LFUPage(int page, int frequency, int timestamp) {
        this.page = page;
        this.frequency = frequency;
        this.timestamp = timestamp;
    }
}

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Please enter the number of frames: ");
        int frames = sc.nextInt();

        System.out.print("Please enter the length of the reference string: ");
        int refLength = sc.nextInt();

        int[] referenceString = new int[refLength];
        System.out.print("Please enter the reference string: ");
        for (int i = 0; i < refLength; i++) {
            referenceString[i] = sc.nextInt();
        }

        Map<Integer, LFUPage> pageTable = new HashMap<>();
        PriorityQueue<LFUPage> pq = new PriorityQueue<>((a, b) -> {
            if (a.frequency == b.frequency) {
                return Integer.compare(a.timestamp, b.timestamp);
            }
            return Integer.compare(a.frequency, b.frequency);
        });

        int timestamp = 0;
        int pageFaults = 0;
        int pageHits = 0;

        int[][] frameTable = new int[refLength][frames];

        for (int i = 0; i < refLength; i++) {
            int page = referenceString[i];
            boolean isHit = false;

            if (pageTable.containsKey(page)) {
                LFUPage lfuPage = pageTable.get(page);
                pq.remove(lfuPage);
                lfuPage.frequency++;
                lfuPage.timestamp = timestamp;
                pq.offer(lfuPage);
                pageHits++;
                isHit = true;
            } else {
                if (pageTable.size() == frames) {
                    LFUPage lfuPage = pq.poll();
                    pageTable.remove(lfuPage.page);
                }

                LFUPage newPage = new LFUPage(page, 1, timestamp);
                pageTable.put(page, newPage);
                pq.offer(newPage);
                pageFaults++;
            }

            int index = 0;
            for (LFUPage lfuPage : pq) {
                frameTable[i][index] = lfuPage.page;
                index++;
            }

            System.out.print("Page: " + page + " - ");
            if (isHit) {
                System.out.print("Hit");
            } else {
                System.out.print("Fault");
            }
            System.out.print(" - Frame Table: ");
            for (int j = 0; j < frames; j++) {
                System.out.print(frameTable[i][j] + " ");
            }
            System.out.println();

            timestamp++;
        }

        System.out.println("Page Hits: " + pageHits);
        System.out.println("Page Faults: " + pageFaults);
    }
}
