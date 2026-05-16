import java.util.*;
public class Problem3 {
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("enter the size of Array : ");
    int size = sc.nextInt();
    int song[] = new int[size];

     for(int i = 0 ; i < size ; i++){
           song[i] = sc.nextInt();
        }
        
        for(int j = 0; j < song.length/2; j++){
            int temp = song[j];
            song[j] = song[song.length - 1 - j];
            song[song.length-1-j] = temp;
        }
        System.out.println("Reverse Array : " + Arrays.toString(song));
        sc.close();
    }
}