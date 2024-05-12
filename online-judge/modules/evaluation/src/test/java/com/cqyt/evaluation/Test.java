package com.cqyt.evaluation;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        for(int i = 0; i < s.length(); i++){
            if(i % 60 == 0) System.out.println();
            System.out.print(s.charAt(i));
        }

    }
}
