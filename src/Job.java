import java.util.Comparator;

public class Job implements Comparable<Job> {
    private int index;
    private double processingTime;
    private double deadline;

    public Job(int index, double processingTime, double deadline) {
        this.index = index;
        this.processingTime = processingTime;
        this.deadline = deadline;
    }

    public int getIndex() {
        return index;
    }

    public double getProcessingTime() {
        return processingTime;
    }

    public double getDeadline() {
        return deadline;
    }

    @Override
    public int compareTo(Job j2) {
        return Double.compare(this.getDeadline(), j2.getDeadline());
    }

    @Override
    public boolean equals(Object j2) {
        if(!(j2 instanceof Job)) {
            return false;
        } else {
            return (this.index == ((Job) j2).index && this.processingTime == ((Job) j2).processingTime && this.deadline == ((Job) j2).deadline);
        }
    }

    @Override
    public String toString() {
        return "{" +
                index +
                ", " + processingTime +
                ", " + deadline +
                '}';
    }
}
