import java.util.*;

public class FourthRR {
        static class Process {
          public int pid;
          public int arrivalTime;
          public int burstTime;
          public int startTime;
          public int completionTime;
          public int turnaroundTime;
          public int waitingTime;
          public int remainingTime;
          public int flag;
          public Process(int pid, int arrivalTime, int burstTime) {
            this.pid = pid;
            this.flag=0;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.startTime = -1;
            this.completionTime = 0;
            this.turnaroundTime = 0;
            this.waitingTime = 0;
            this.remainingTime = burstTime;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();
        System.out.print("Enter the time quantum: ");
        int timeQuantum = sc.nextInt();

        List<Process> processList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            System.out.print("Enter the arrival time and burst time for process " + i + ": ");
            int arrivalTime = sc.nextInt();
            int burstTime = sc.nextInt();
            processList.add(new Process(i, arrivalTime, burstTime));
        }
        processList.sort(Comparator.comparingInt(p->p.arrivalTime));
        int currentTime = 0;
        Queue<Process> readyQueue = new LinkedList<>();
        List<Integer> ganttChart = new ArrayList<>();
        while (true) {
            boolean done = true;

            for (Process process : processList) {
                if (process.arrivalTime <= currentTime && process.remainingTime > 0 && process.flag==0) {
                    done = false;
                    readyQueue.offer(process);
                }
            }

            if (done && readyQueue.isEmpty()) {
                break;
            }

            Process currentProcess = readyQueue.poll();
            if (currentProcess == null) {
                currentTime++;
                ganttChart.add(-1);
            } else {
                if (currentProcess.startTime == -1) {
                    currentProcess.startTime = currentTime;
                }
                if (currentProcess.remainingTime > timeQuantum) {
                    currentProcess.remainingTime -= timeQuantum;
                    currentTime += timeQuantum;
                    ganttChart.add(currentProcess.pid);
                    readyQueue.offer(currentProcess);
                } else {
                    currentTime += currentProcess.remainingTime;
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    ganttChart.add(currentProcess.pid);
                    currentProcess.flag=1;
                }
            }
        }

        System.out.println("\nGantt Chart:");
        for (Integer pid : ganttChart) {
            if (pid == -1) {
                System.out.print("| IDLE |");
            } else {
                System.out.printf("|  P%d  |", pid);
            }
        }
        System.out.println();

        int totalTAT = 0;
        int totalWT = 0;
        int totalCT = 0;
        int totalBT = 0;
        int totalAT = 0;
        System.out.println("\nProcess Table:");
        System.out.println("+-----+---------------+----------------+------------+--------------+-------------+-------------+");
        System.out.println("| PID | Arrival Time  | Burst Time (BT) | Waiting Time | Turnaround Time |");
        System.out.println("+-----+---------------+----------------+------------+--------------+-------------+-------------+");

        for(Process p: processList)       System.out.println(p.pid+"\t"+p.arrivalTime+"\t"+p.burstTime+"\t"+p.waitingTime+"\t"+p.turnaroundTime);
        System.out.println("\nGantt Chart:");
        System.out.println("+-----+---------------+----------------+------------+--------------+-------------+-------------+");
        System.out.println("| PID | Start Time | Completion Time");
        System.out.println("+-----+---------------+----------------+------------+--------------+-------------+-------------+");
        for(Process p: processList)
        System.out.println(p.pid+"\t"+p.startTime+"\t"+p.completionTime);
        }
}




