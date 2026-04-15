import java.util.* ;
class PRODUCT {
    public static int  calculateSum( int a, int b){
        return a*b ;
    }
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        System.out.println("the product of 2 number is :"+ calculateSum(a , b ));
        sc.close();
    }
}