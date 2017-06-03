import java.util.ArrayDeque;
import java.util.ArrayList;

public interface TaxiQueue {

	public void addWaitingList(InfoTravel t);
	public InfoTravel removeWaitingList();
	public boolean containsWaitingList(InfoTravel t);
	public void setWaitingList(ArrayDeque<InfoTravel> ad);
	public ArrayDeque<InfoTravel> getWaitingList();
    public boolean isAvailable();
    public ArrayList<Travel> dispatchQueue();
}
