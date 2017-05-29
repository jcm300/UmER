public class TopXComparator implements Comparator{
    public int compare (Account a1, Account a2){
        double d1=0.f, d2=0.f;
        if(a1 instanceof Client){
            d1 = a1.getCosts();
            d2 = a2.getCosts();
        }else{
            d1 = a1.getFluctuationTime();
            d2 = a2.getFluctuationTime();
        }
        if(d1==d2) return 0;
        else if(d1<d2) return 1;
             else return -1;
    }
}
