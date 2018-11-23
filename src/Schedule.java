import java.util.LinkedList;
import java.util.List;

public class Schedule {
	private List<Job> schedule;
	private int startingTime;
	private int totalProcessingTime;
	private int tardiness;

	public Schedule(int startingTime){
		this.schedule = new LinkedList<>();
		this.startingTime = startingTime;
		this.totalProcessingTime = 0;
		this.tardiness = 0;
	}
	
	public int size(){
		return this.schedule.size();
	}

	public Job get(int i) {
		return this.schedule.get(i);
	}

	public void add(Job j) {
			this.schedule.add(j);
			this.totalProcessingTime = this.totalProcessingTime + j.getProcessingTime();
			updateTardiness();
	}

	public void setNewStartingTime(int startingTime) {
		this.startingTime = startingTime;

		// Calculate the new tardiness with this different starting time
		this.tardiness = 0;
		int time = startingTime;
		for(int i = 0; i < schedule.size(); i++) {
			time = time + schedule.get(i).getProcessingTime();
			this.tardiness = this.tardiness + Integer.max(0, time - schedule.get(i).getDeadline());
		}
	}

	public int getTardiness() {
		return this.tardiness;
	}

	public boolean contains(Job j) {
		return schedule.contains(j);
	}

	public void remove(Job j) {
		schedule.remove(j);
        this.totalProcessingTime = this.totalProcessingTime - j.getProcessingTime();
        updateTardiness();
	}

	public void updateTardiness() {
	    this.tardiness = Integer.max(0, this.totalProcessingTime - this.get(this.size() - 1).getDeadline());
    }

    public int getStartingTime() {
	    return this.startingTime;
    }

	@Override
	public String toString() {
		return schedule.toString();
	}
}
