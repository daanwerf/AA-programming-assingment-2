class SubproblemKey {
    private JobList joblist;
    private double startTime;
    private int length;

    SubproblemKey(JobList joblist, double startTime, int length) {
      this.joblist = joblist;
      this.startTime = startTime;
      this.length = length;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof SubproblemKey)) return false;
      SubproblemKey other = (SubproblemKey) o;
      return joblist.equals(other.joblist)
          && startTime == other.startTime
          && length == other.length;
    }

    @Override
    public int hashCode() { // note: some random computation that results in few collisions.
      double result = startTime;
      result = 31 * result + length;
      for(int i = 0 ; i < joblist.size(); i++) {
          result = 31 * result + joblist.get(i).getIndex();
      }
      return (int) result;
    }

    public String toString() {
        return ", " + startTime + ", " + length;
    }
}
