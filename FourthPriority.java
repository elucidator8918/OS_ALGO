import java.util.*;

public class FourthPriority {
    static class Process {
      int id;             // process id
      int arrivalTime;    // arrival time
      int burstTime;      // burst time
      int burstTimecopy;
      int priority;       // priority of the process
      int startTime;      // starting time
      int completionTime; // completion time
      int turnaroundTime; // turnaround time
      int waitingTime;    // waiting time
      int responseTime;   // response time
      int flag;
      public Process(int id, int arrivalTime, int burstTime, int priority) {
          this.id = id;
          this.startTime=-1;
          this.flag=0;
          this.arrivalTime = arrivalTime;
          this.burstTime = burstTime;
          this.burstTimecopy = burstTime;
          this.priority = priority;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        System.out.print("Enter number of processes: ");
        n = sc.nextInt();
        List<Process> processes = new ArrayList<>();

        for (int id = 1; id <= n; id++) {
            System.out.print("Enter arrival time for process " + id + ": ");
            int arrivalTime = sc.nextInt();
            System.out.print("Enter burst time for process " + id + ": ");
            int burstTime = sc.nextInt();
            System.out.print("Enter priority [smallest number is high] for process " + id + ": ");
            int priority = sc.nextInt();
            processes.add(new Process(id, arrivalTime, burstTime, priority));
        }

        // sort processes by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int curTime = 0; // current time
        int completed = 0; // number of completed processes
        while (completed < n) {            
            Process selectedProcess = null;
            int highestPriority = Integer.MAX_VALUE; // highest priority     
            for (Process p : processes) {                
                if (p.arrivalTime <= curTime && p.priority < highestPriority && p.flag==0) {                    
                    highestPriority = p.priority;
                    selectedProcess = p;
                }
            }            
            if (selectedProcess != null) {                               
                if (selectedProcess.startTime==-1)
                selectedProcess.startTime = curTime;                                    
                curTime++;
                selectedProcess.burstTimecopy--;                
                if (selectedProcess.burstTimecopy == 0) {
                    selectedProcess.completionTime = curTime;
                    selectedProcess.turnaroundTime = selectedProcess.completionTime - selectedProcess.arrivalTime;
                    selectedProcess.waitingTime = selectedProcess.turnaroundTime - selectedProcess.burstTime;
                    selectedProcess.responseTime = selectedProcess.startTime - selectedProcess.arrivalTime;
                    completed++;
                    selectedProcess.flag=1;                    
                }                
            } 
            else                          
              curTime++;                      
        }

        float sumTAT = 0;
        float sumWT = 0;
        float sumRT = 0;
        System.out.println("\nProcess No.\tPriority\tA.T\tB.T.\tC.T.\tT.A.T.\tW.T.\tR.T.");
        for (Process p : processes) {
            System.out.println("Process " + p.id + "\t\t" + p.priority + "\t\t" + p.arrivalTime + "\t" +
                               p.burstTime + "\t" + p.completionTime + "\t" + p.turnaroundTime + "\t" +
                               p.waitingTime + "\t" + p.responseTime);
            sumTAT += p.turnaroundTime;
            sumWT += p.waitingTime;
            sumRT += p.responseTime;
        }
        System.out.println("Average Turn Around Time: "+(sumTAT/n));
        System.out.println("Average Waiting Time: "+(sumWT/n));
        System.out.println("Average Response Time: "+(sumRT/n));
        System.out.println("\n\t\tGANTT CHART\nPROCESS \tStart Time\tCompletion Time");
        processes.sort(Comparator.comparingInt(p -> p.startTime));
        for(Process p: processes)
        System.out.println("PROCESS "+p.id+"\t\t"+p.startTime+"\t\t"+p.completionTime);
    }    
}
