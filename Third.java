import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Third {
    private static final int NUM_THREADS = 5;
    private static final String[] FILES_TO_DOWNLOAD = {"sample-1.txt", "sample-2.txt", "sample-3.txt", "sample-4.txt", "sample-5.txt"};

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        for (String filename : FILES_TO_DOWNLOAD) {
            executor.submit(new FileDownloadRunnable(filename));
        }
        executor.shutdown();
    }
}

class FileDownloadRunnable implements Runnable {
    private static final String DOWNLOAD_URL_PREFIX = "https://getsamplefiles.com/download/txt/";
    private String filename;

    public FileDownloadRunnable(String filename) {
        this.filename = filename;
    }

    public void run() {
        String fileUrl = DOWNLOAD_URL_PREFIX + filename;
        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream out = new FileOutputStream(filename)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(filename + " downloaded successfully.");
    }
}
