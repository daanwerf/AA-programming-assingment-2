import java.util.Comparator;

public class Job implements Comparable<Job> {
    private int index;
    private int processingTime;
    private int deadline;

    public Job(int index, int processingTime, int deadline) {
        this.index = index;
        this.processingTime = processingTime;
        this.deadline = deadline;
    }

    public int getIndex() {
        return index;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public int getDeadline() {
        return deadline;
    }

    @Override
    public int compareTo(Job j2) {
        return Integer.compare(this.getDeadline(), j2.getDeadline());
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
