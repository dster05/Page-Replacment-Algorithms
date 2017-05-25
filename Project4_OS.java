/*
 * Description: This is the class that contains the main function. The user inputs values
 * for the number of page references, page reference number range and the number
 * of physical frames. A Pages object is then created and then tested with all
 * of the 3 page fault optimization algorithms. The idea is to compare number of
 * page faults between the three types of algorithms.
 *
 * Programmers: Daniel Gallegos and Teepu Khan
 * Date: May 8, 2017
 * Course: CSC341 @ CSUDH
 *
 * Class: Project4_OS.java
 *
 */

package project4_os;

import java.util.Scanner;

public class Project4_OS 
{
    public static Pages test;
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) 
    {
        System.out.print("Please enter the number of page references: ");
        int a = input.nextInt();
        System.out.print("Please enter a range for page reference number (starts at 0, ends at input number): ");
        int b = input.nextInt();
        System.out.print("Please enter the number of physical frames: ");
        int c = input.nextInt();
        System.out.println();
        
        test = new Pages(a, b, c);
        
        test.Fifo();
        test.LRU();
        test.Optimal();
    }
}
