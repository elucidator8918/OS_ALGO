import java.util.*;

public class Fifth {
    private static LinkedList<Integer> buffer;
    private static int bufferSize;

    private static class Producer implements Runnable {
        public void run() {
            while (true) {
                synchronized (buffer) {
                    while (buffer.size() == bufferSize) {
                        try {
                            System.out.println("Buffer is Full. Waiting...");
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    int item = (int)(Math.random() * 100);//ITEM GENERATION
                    buffer.add(item);
                    System.out.println("Producer produces item: " + item);

                    buffer.notifyAll();
                }

                try {
                    Thread.sleep(1000); // Delay to simulate production time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Consumer implements Runnable {
        public void run() {
            while (true) {
                synchronized (buffer) {
                    while (buffer.isEmpty()) {
                        try {
                            System.out.println("Buffer is empty. Waiting...");
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    int item = buffer.remove();
                    System.out.println("Consumer consumes item: " + item);

                    buffer.notifyAll();
                }

                try {
                    Thread.sleep(1500); // Delay to simulate consumption time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of empty spaces in the buffer: ");
        bufferSize = sc.nextInt();
        buffer = new LinkedList<>();

        Thread producerThread = new Thread(new Producer());
        Thread consumerThread = new Thread(new Consumer());

        producerThread.start();
        consumerThread.start();
    }
}
