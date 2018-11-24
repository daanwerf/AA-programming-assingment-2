import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.IntStream;

public class DynamicAlgorithm {
    private static HashMap<SubproblemKey, Schedule> memory = new HashMap<>();

    public static ArrayList<Job> readInstance(String filename){
        ArrayList<Job> jobList = new ArrayList<>();

        try {
            int numJobs = 0;

            Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));
            if(sc.hasNextInt()){
                numJobs = sc.nextInt();
                int nextJobID = 0;

                while (sc.hasNextInt() && nextJobID < numJobs) {
                    int processingTime = sc.nextInt();
                    int deadline = sc.nextInt();
                    jobList.add(new Job(nextJobID, processingTime, deadline));
                    nextJobID++;
                }
            }
            sc.close();

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    public static void main (String args[]) {
        String filename = "C:\\Users\\daanv\\IdeaProjects\\Programming assignment 2\\instances\\random_RDD=0.2_TF=0.2_#10.dat";
        ArrayList<Job> jobs = readInstance(filename);
        Collections.sort(jobs);

        JobList jobList = new JobList(jobs);
        Schedule s = OptimalSchedule(jobList, 0);
        System.out.print("Total tardiness: " + s.getTardiness());
    }

    /**
     * Return the schedule with the lowest tardiness
     * @param joblist the list containing jobs
     * @param time the time the processing of the jobs starts
     * @return the optimal schedule
     */
    public static Schedule OptimalSchedule(JobList joblist, double time) {
        if(joblist.isEmpty()) {
            return new Schedule(time);
        } else if(joblist.size() == 1) {
            Schedule s = new Schedule(time);
            s.add(joblist.get(0));
            return s;
        } else {
            SubproblemKey key = new SubproblemKey(joblist, time, joblist.size());

            if(memory.containsKey(key)) {
                System.out.println("original: " + joblist);
                Schedule found = memory.get(key);
                System.out.println("found:"  + found);
                return found;
            }

            int n = joblist.size();
            int k = getIndexHighestDeadline(joblist);
            Job jobK = joblist.get(k);

            Schedule optimalDeltaScheduleA = new Schedule(0);
            Schedule optimalDeltaScheduleB = new Schedule(0);
            int optimalK = Integer.MIN_VALUE;
            double lowestTotalDeltaTardiness = Double.MAX_VALUE;

            int[] deltaOptions = getDeltaOptions(n, k);
            for(int delta : deltaOptions) {
                JobList subsetA = makeSubsetA(joblist, delta, k);
                JobList subsetB = makeSubsetB(joblist, n-1, delta, k);
                subsetA.remove(k);

                double scheduleBTime = time + getTotalProcessingTime(subsetA) + jobK.getProcessingTime();

                Schedule optimalScheduleA = OptimalSchedule(subsetA, time);
                Schedule optimalScheduleB = OptimalSchedule(subsetB, scheduleBTime);

                double totalDeltaTardiness = optimalScheduleA.getTardiness() + Math.max(0, (scheduleBTime - jobK.getDeadline())) + optimalScheduleB.getTardiness();

                if (totalDeltaTardiness < lowestTotalDeltaTardiness) {
                    optimalK = k;
                    lowestTotalDeltaTardiness = totalDeltaTardiness;
                    optimalDeltaScheduleA = optimalScheduleA;
                    optimalDeltaScheduleB = optimalScheduleB;

                }
            }
            Schedule optimalSchedule = optimalDeltaScheduleA;

            optimalSchedule.add(joblist.get(optimalK));

            for(int i = 0; i < optimalDeltaScheduleB.size(); i++) {
                Job current = optimalDeltaScheduleB.get(i);
                optimalSchedule.add(current);
            }

            optimalSchedule.setNewStartingTime(time);

            memory.put(key, optimalSchedule);

            return optimalSchedule;
        }
    }

    /**
     * Creates the subset A as described in the paper
     * @param joblist list of jobs to create the subset from
     * @param delta the value used to decide which jobs belong int he subset
     * @param k the index of the job with the highest processing time
     * @return subset A
     */
    private static JobList makeSubsetA(JobList joblist, int delta, int k) {
        ArrayList<Job> subsetA = new ArrayList<>();
        for(int i = 0; i <= k+delta; i++) {
            subsetA.add(joblist.get(i));
        }
        return new JobList(subsetA);
    }

    /**
     * Creates the subset B as described in the paper
     * @param joblist list of jobs to create the subset from
     * @param delta the value used to decide which jobs belong int he subset
     * @param k the index of the job with the highest processing time
     * @return subset B
     */
    private static JobList makeSubsetB(JobList joblist, int n, int delta, int k) {
        ArrayList<Job> subsetB = new ArrayList<>();
        for(int i = k+delta+1; i <= n; i++) {
            subsetB.add(joblist.get(i));
        }

        return new JobList(subsetB);
    }

    /**
     * Finds and returns the index of the job with the highest processing time in the joblist
     * @param joblist the list of jobs
     * @return integer k as the index of the job with the highest processing time
     */
    private static int getIndexHighestDeadline(JobList joblist) {
        double highest = Double.MIN_VALUE;
        int indexHighest = -1;
        for(int i = 0; i < joblist.size(); i++) {
            if(joblist.get(i).getProcessingTime() > highest) {
                highest = joblist.get(i).getProcessingTime();
                indexHighest = i;
            }
        }
        return indexHighest;
    }

    private static int getIndexLowestDeadline(JobList joblist) {
        double lowest = Double.MAX_VALUE;
        int indexLowest = -1;
        for(int i = 0; i < joblist.size(); i++) {
            if(joblist.get(i).getProcessingTime() < lowest) {
                lowest = joblist.get(i).getProcessingTime();
                indexLowest = i;
            }
        }
        return indexLowest;
    }

    /**
     * Finds the total processing time of all jobs in the job list
     * @param joblist the list of jobs
     * @return the total processing time of all the jobs in job list
     */
    private static double getTotalProcessingTime(JobList joblist) {
        double total = 0;
        for(int i = 0; i < joblist.size(); i++) {
            total = total + joblist.get(i).getProcessingTime();
        }
        return total;
    }

    /**
     * Finds all the possible values for delta, as described in the paper
     * @param n the amount of jobs
     * @param k the index of the job with the highest processing time
     * @return an array of integers containing all options for delta
     */
    private static int[] getDeltaOptions(int n, int k) {
        return IntStream.range(0, n-k).toArray();
    }
}