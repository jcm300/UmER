import java.util.ArrayDeque;

public interface TaxiQueue {

	public void addWaitingList(Travel t);
	public Travel removeWaitingList();
	public boolean containsWaitingList(Travel t);
	public void setWaitingList(ArrayDeque<Travel> ad);
	public ArrayDeque<Travel> getWaitingList();
    public boolean isAvailable();

}
