import java.io.*;
import java.util.*;

public class pb{
    static Scanner in = new Scanner(System.in);
    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args){
        System.out.print("Enter the value of n: ");
        int n = in.nextInt();

        System.out.println();
        int answer = solve(n);
        System.out.println("The minimum number of moves is: "+answer);
    }

    public static int solve(int n){
        if(n<=2) return n;
        return 1 + 2*solve(n-2) + solve(n-1);
    }
}
