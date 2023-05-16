import java.util.*;

public class FourthSJF {
    static class Process {
        int id;
        int arrivalTime;
        int burstTime;
        int startTime;
        int completionTime;
        int turnaroundTime;
        int waitingTime;
	      int flag;
        public Process(int id, int arrivalTime, int burstTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
	          this.flag=0;
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n;
        System.out.print("Enter number of processes: ");
        n = sc.nextInt();
        List<Process> processes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for Process " + (i+1) + ": ");
            int at = sc.nextInt();
            System.out.print("Enter burst time for Process " + (i+1) + ": ");
            int bt = sc.nextInt();
            processes.add(new Process(i+1, at, bt));
        }
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        int curTime = 0; //Current time
        int completed = 0; //To count number of processes completed
        int minBurstTime = Integer.MAX_VALUE; //To store the shortest burst time
        Process currentProcess = null;
        while (completed < n) {
            for (Process process : processes) {
                if (process.arrivalTime <= curTime && process.burstTime < minBurstTime && process.startTime == 0 && process.flag==0) {
                    minBurstTime = process.burstTime;
                    currentProcess = process;
                }
            }
            if (currentProcess == null) {
                curTime++;
                continue;
            }
            currentProcess.startTime = curTime;
            currentProcess.completionTime = curTime + currentProcess.burstTime;
            currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
            currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
	    currentProcess.flag=1;
            curTime = currentProcess.completionTime;
            completed++;
            minBurstTime = Integer.MAX_VALUE;
            currentProcess = null;
        }

        float sumTat = 0, sumWt = 0;
        System.out.println("\nProcess No.\tA.T\tB.T.\tC.T.\tT.A.T.\tW.T.");
        for (Process process : processes) {
            System.out.println("Process " + process.id + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" + process.completionTime + "\t\t" + process.turnaroundTime + "\t\t" + process.waitingTime);
            sumTat += process.turnaroundTime;
            sumWt += process.waitingTime;
        }
        System.out.println("Average Turn Around Time: " + (sumTat/n));
        System.out.println("Average Waiting Time: " + (sumWt/n));

        //To prepare Gantt Chart processes are sorted according to start time
        Collections.sort(processes, Comparator.comparingInt(p -> p.startTime));
        System.out.println("\n\t\tGANTT CHART\nPROCESS \tStart Time\tCompletion Time");
        for (Process process : processes) {
            System.out.println("PROCESS " + process.id + "\t\t" + process.startTime + "\t\t" + process.completionTime);
        }
    }
}
