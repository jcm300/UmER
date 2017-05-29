public class InvertedComparatorOfDoubles implements Comparator{
    public int compare (Double d1, double d2){
        if(d1==d2) return 0;
        else if(d1<d2) return 1;
             else return -1;
    }
}
