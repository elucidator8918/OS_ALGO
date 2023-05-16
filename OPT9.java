import java.util.*;

public class OPT9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int frames, hit = 0, fault = 0, ref_len;
        Set<Integer> buffer = new LinkedHashSet<>();
        int reference[];
        int mem_layout[][];
        System.out.println("Please enter the number of Frames: ");
        frames = sc.nextInt();
        System.out.println("Please enter the length of the Reference string: ");
        ref_len = sc.nextInt();
        reference = new int[ref_len];
        mem_layout = new int[ref_len][frames];
        System.out.println("Please enter the reference string: ");
        for (int i = 0; i < ref_len; i++) {
            reference[i] = sc.nextInt();
        }
        System.out.println();
        for (int i = 0; i < ref_len; i++) {
            int page = reference[i];
            if (buffer.contains(page)) {
                hit++;
            } else {
                if (buffer.size() == frames) {
                    Iterator<Integer> iterator = buffer.iterator();
                    int farthest = -1, evict = 0;
                    while (iterator.hasNext()) {
                        int j = iterator.next();
                        boolean found = false;
                        for (int k = i + 1; k < ref_len; k++) {
                            if (j == reference[k]) {
                                found = true;
                                if (k > farthest) {
                                    farthest = k;
                                    evict = j;
                                }
                                break;
                            }
                        }
                        if (!found) {
                            evict = j;
                            break;
                        }
                    }
                    buffer.remove(evict);
                }
                buffer.add(page);
                fault++;
            }
            Iterator<Integer> iterator = buffer.iterator();
            for (int j = 0; j < frames; j++) {
                if (iterator.hasNext()) {
                    mem_layout[i][j] = iterator.next();
                }
            }
        }
        for (int i = 0; i < frames; i++) {
            for (int j = 0; j < ref_len; j++) {
                System.out.printf("%3d ", mem_layout[j][i]);
            }
            System.out.println();
        }
        float hit_ratio = ((float) hit / ref_len);
        System.out.println("The number of Hits: " + hit);
        System.out.println("Hit Ratio: " + (float) hit_ratio);
        System.out.println("The number of Faults: " + fault);
        System.out.println("Miss Ratio: " + (1 - hit_ratio));
    }
}
