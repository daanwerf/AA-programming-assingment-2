import java.util.ArrayList;

public final class JobList {
    private final ArrayList<Job> list;

    public JobList(ArrayList<Job> list) {
        this.list = list;
    }

    public void remove(int i) {
        list.remove(i);
    }

    public int getLastIndex() {
        return get(list.size() - 1).getIndex();
    }

    public int getFirstIndex() {
        return get(0).getIndex();
    }

    public boolean equals(JobList j2) {
        return list.equals(j2.list);
    }

    public int size() {
        return list.size();
    }

    public Job get(int i) {
        return list.get(i);
    }

    public boolean contains(Job j) {
        return list.contains(j);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public String toString() {
        return list.toString();
    }
}
