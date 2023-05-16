import java.util.*;
import java.lang.*;
class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int startTime;
    int turnaroundTime;
    int waitingTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class FourthFCFS {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        // Get the number of processes
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        // Create an array of Process objects
        Process[] processes = new Process[numProcesses];

        // Get process information from user and create Process objects
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter the arrival time for process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();

            System.out.print("Enter the burst time for process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();

            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        // Sort processes by arrival time
        for (int i = 0; i < numProcesses; i++) {
            for (int j = i + 1; j < numProcesses; j++) {
                if (processes[j].arrivalTime < processes[i].arrivalTime) {
                    Process temp = processes[i];
                    processes[i] = processes[j];
                    processes[j] = temp;
                }
            }
        }

        // Calculate completion time, turnaround time, and waiting time for each process
        int currentTime = 0;
        float sumTurnaroundTime = 0;
        float sumWaitingTime = 0;

        System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < numProcesses; i++) {
            processes[i].startTime = currentTime;
            processes[i].completionTime = currentTime + processes[i].burstTime;
            processes[i].turnaroundTime = processes[i].completionTime - processes[i].arrivalTime;
            processes[i].waitingTime = processes[i].turnaroundTime - processes[i].burstTime;
            sumTurnaroundTime += processes[i].turnaroundTime;
            sumWaitingTime += processes[i].waitingTime;
            currentTime = processes[i].completionTime;

            System.out.println(processes[i].id + "\t\t" + processes[i].arrivalTime + "\t\t" + processes[i].burstTime + "\t\t" + processes[i].completionTime + "\t\t" + processes[i].turnaroundTime + "\t\t" + processes[i].waitingTime);
        }

        // Print average turnaround time and waiting time
        System.out.println("\nAverage Turnaround Time: " + (sumTurnaroundTime / numProcesses));
        System.out.println("Average Waiting Time: " + (sumWaitingTime / numProcesses));

        // Print Gantt chart
        System.out.println("\n\t\tGANTT CHART");
        System.out.println("Process\tStart Time\tCompletion Time");
        for (int i = 0; i < numProcesses; i++)
        System.out.println(processes[i].id + "\t\t"+processes[i].startTime+"\t\t"+processes[i].completionTime);
   }
}
