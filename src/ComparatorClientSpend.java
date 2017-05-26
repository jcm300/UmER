/*
 *Inverse comparator of Costs
*/

import java.util.Comparator;

public class ComparatorClientSpend implements Comparator<Double>{
    public int compare (Double m1, Double m2){
        if(m1==m2) return 0;
        else if(m1<m2) return 1;
             else return -1;
    }
}
