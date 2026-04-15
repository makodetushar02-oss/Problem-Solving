import java.util.* ;
class FACTORIYAL {
    public static void  calculateFactoriyel( int n){
        if ( n < 0){
            System.out.println("Invalid Number");
            return ;
        }
        int factoriyal = 1 ;
        for(int i = n ; i >= 1; i--){
            factoriyal = factoriyal * i ;
        }
        System.out.println( factoriyal );
        return  ;
    }
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        calculateFactoriyel(n);
        sc.close();
    }
}