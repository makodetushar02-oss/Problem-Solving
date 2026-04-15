
class ReversString {
    public static void main(String args[]){
        StringBuilder sb = new StringBuilder("my name is tushar");
        System.out.println(sb);
        for(int i=0 ; i< sb.length()/2 ; i++ ){
            int Front = i ;
            int Back = sb.length() - 1 - i;

            char Frontchar = sb.charAt(Front);
            char Backchar = sb.charAt(Back);

            sb.setCharAt(Front, Backchar);
            sb.setCharAt(Back, Frontchar);

        }
        System.out.println(sb);
    }
}