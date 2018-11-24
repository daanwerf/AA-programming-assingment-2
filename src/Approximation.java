import java.util.ArrayList;
import java.util.Collections;

public class Approximation {
    public static void main (String args[]) {
        String filename = "C:\\Users\\daanv\\IdeaProjects\\Programming assignment 2\\instances\\random_RDD=0.2_TF=0.2_#10.dat";
        ArrayList<Job> jobs = DynamicAlgorithm.readInstance(filename);
        Collections.sort(jobs);

        JobList jobList = new JobList(jobs);

        double epsilon = 3;
        ArrayList<Job> scaledJobList = ScaleJobs(jobs, epsilon);
//		System.out.println(scaledJobList);
        JobList scaledList = new JobList(scaledJobList);
        Schedule ascheduled = DynamicAlgorithm.OptimalSchedule(scaledList, 0);
        System.out.println(ascheduled.toString());
        System.out.println(ascheduled.getTardiness());
        Schedule arescheduled = new Schedule(0);
        int index = 1;
        for(int i = 0; i < ascheduled.size(); i++) {
            for(int j = 0; j < scaledList.size(); j++) {
                if(ascheduled.get(i).equals(scaledList.get(j))) {
                    index = j;
                }
            }
            arescheduled.add(jobs.get(index));
        }
        System.out.println(arescheduled);
        System.out.println(arescheduled.getTardiness());
    }

    private static ArrayList<Job> ScaleJobs(ArrayList<Job> jobsList, double epsilon){
        ArrayList<Job> scaledJobList = new ArrayList<Job>();
        double Tmax = findTMax(jobsList);
//    	System.out.println(Tmax);

        double n = jobsList.size();
        double k = (2*epsilon) / (n*(n+1)) * Tmax;
//    	System.out.println(k);

        double q, d;
        for (int i = 0; i < jobsList.size(); i++) {
            q = Math.floor(jobsList.get(i).getProcessingTime() / k);
            d = jobsList.get(i).getDeadline() / k;
            int index = jobsList.get(i).getIndex();
            scaledJobList.add(new Job(index, q,d));
        }
        return scaledJobList;
    }

    private static double findTMax(ArrayList<Job> jobsList) {
        double totalTime = 0;
        double currentTmax = 0;

        for (int i = 0; i < jobsList.size(); i++) {
            totalTime += jobsList.get(i).getProcessingTime();
            if(Math.max(0, totalTime - jobsList.get(i).getDeadline()) > currentTmax) {
                currentTmax = totalTime - jobsList.get(i).getDeadline();
            }

        }
        return currentTmax;
    }
}
