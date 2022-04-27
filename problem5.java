import java.io.*;
import java.util.*;

public class problem5{
    static Scanner in = new Scanner(System.in);
    static PrintWriter out = new PrintWriter(System.out);


    static final int MAXN = 100;
    static long[] mem = new long[MAXN];

    public static void main(String[] args){
        System.out.print("Enter the value of n: ");
        int n = in.nextInt();

        System.out.println();
        long answer = solve(n);
        System.out.println("The minimum number of moves is: "+answer);
    }

    public static long solve(int n){
        if(n<=2) return n;
        if(mem[n] != 0)
            return mem[n];

        mem[n] = 1 + 2*solve(n-2) + solve(n-1);
        return mem[n];
    }
}
