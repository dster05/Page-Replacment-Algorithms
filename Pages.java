/*
 * Description: This class constructs the Pages object as well as provides methods
 * for implementing 3 page fault management algorithms.
 *
 * Programmers: Daniel Gallegos and Teepu Khan
 * Date: May 8, 2017
 * Course: CSC341 @ CSUDH
 *
 * Class: Pages.java
 *
 */
package project4_os;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ListIterator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

public class Pages 
{
    private Random random = new Random();// Used for random number generator
    public Queue<Integer> frames = new LinkedList<Integer>(); // Used for the number of frames.
    public Integer[] refString; // Reference string.
    public int pageRef; // Page reference count.
    public int range; // Range for page reference values, this number represents the max.
    public int phyFrames; // The number of physical frames.

    // Constructor. Sets values according to user input.
    public Pages(int a, int b, int c)
    {
        this.pageRef = a; //number of page references inputed by user
        this.range = b;//the range from 0 - b inputed by user
        this.phyFrames = c;//the number of page frames inputed by user

        this.refString = new Integer[a];
        this.fillInPR(pageRef ,range);
    }

    // Fills and prints Reference String.
    public void fillInPR(int y, int z)
    {
        for(int i = 0; i < y; i++) 
        {
            refString[i] = random.nextInt(z+1); // Adding random page reference number
        }
        System.out.print("REFERENCE STRING: ");
        
        for(int i = 0; i < y; i++) 
        {
            System.out.print( " | " + refString[i] + " | "); // Prints array of reference string.
        }
        System.out.println();
    }

    // This method implements the first in first out algorithm.
    public void Fifo() 
    {
        int pageFault = 0; // Counter for the number of page faults.
        System.out.println("================FIRST IN FIRST OUT===============");
        for(int i = 0; i < pageRef; i++) 
        {
            // Checks if queue contains page reference.
            if(!frames.contains(refString[i]))
            {
                //if frames queue does not contain the page reference number
                //then increment page fault
                //if frame.size() is equal to the number of physical frames
                //then poll or remove first element or oldest element from queue
                //then add new reference page
                pageFault++;

                if(frames.size() == phyFrames){ // Will limit queue to the number of frames Enter by user.
                    frames.poll();
                }
                frames.add(refString[i]); // Adds page reference to queue.
            }

            // Prints the contents of the queue.
            for(Object item : frames) 
            {
                System.out.print("--" + item + "--");
            }
            System.out.println();
        }
        System.out.println("There were " + pageFault + " page faults.\n"); 
    }
    
    // This method implements the least recently used algorithm.
    public void LRU()
    {
        LinkedList<Integer> stack = new LinkedList();    // Linked list that will function as a stack.
        int faultCounter = 0;
        
        // This prints out the reference string, for easy reference.
        System.out.print("REFERENCE STRING: ");
        for (int i = 0; i < refString.length; i++)
        {
            System.out.print(" | " + refString[i] + " | ");
        }
        System.out.println();
        System.out.println("================LEAST RECENTLY USED===============");
        
        for (int j = 0; j < refString.length; j++)
        {
            if (stack.contains(refString[j]))
            {                                
                // Removes item from list that equals the current reference string value
                // by getting index where that value is contained. Then adds that same value
                // to the 'top' of list.
                stack.remove(stack.indexOf(refString[j]));
                stack.offer(refString[j]);
            }
            else
            {
                // If the lists size is already at maximum size, then we remove the
                // least recently used item which is the 'bottom' of the list,
                // thus assuring we never exceed the maximum number of frames. After
                // that it proceeds as normal, 'pushing' the reference value onto the
                // 'top' of the list. This counts as a page fault.
                if (stack.size() == phyFrames)
                {
                    stack.removeFirst();
                }
                stack.offer(refString[j]);
                faultCounter++;
            }
            
            // Prints out the current items in the 'stack'.
            for (int k = 0; k < stack.size(); k++)
            {
                System.out.print("--" + stack.get(k) + "--");
            }
            System.out.println();            
        }
        
        System.out.println("There were " + faultCounter + " page faults.\n");
    }
    
    // This method implements the optimal algorithm.
    public void Optimal()
    {       
        LinkedList<Integer> refStringList = new LinkedList();    // Linked list that represents the reference string.
        LinkedList<Integer> frameList = new LinkedList();    // Linked list that represents frames.
        int faultCounter = 0;
        
        // Fills the list representation of reference string with all values.
        for (int s = 0; s < refString.length; s++)
        {
            refStringList.offer(refString[s]);
        }
        
        // This prints out the reference string, for easy reference.
        System.out.print("REFERENCE STRING: ");
        for (int i = 0; i < refString.length; i++)
        {
            System.out.print(" | " + refString[i] + " | ");
        }
        System.out.println();
        System.out.println("================OPTIMAL===============");
        
        for (int j = 0; j < refString.length; j++)
        {
            refStringList.poll();   // So that we don't check against previously checked values.
            if (frameList.size() < phyFrames)   // If all frames aren't full...
            {
                if (frameList.contains(refString[j]))
                {
                    // Do nothing. Placeholder for the sake of understanding the code logic.
                }
                else if (frameList.contains(refString[j]) == false)
                {
                    frameList.offer(refString[j]);
                    faultCounter++;
                }
                
                // Prints out the current items in the frame list.
                for (int l = 0; l < frameList.size(); l++)
                {
                    System.out.print("--" + frameList.get(l) + "--");
                }
                System.out.println();
            }
            else if ((frameList.size() == phyFrames) && frameList.contains(refString[j]))
            {
                // Do nothing. Placeholder for the sake of understanding the code logic.
                
                // Prints out the current items in the 'stack'.
                for (int l = 0; l < frameList.size(); l++)
                {
                    System.out.print("--" + frameList.get(l) + "--");
                }
                System.out.println();
            }
            else if ((frameList.size() == phyFrames) && (frameList.contains(refString[j]) == false))
            {
                int latestAppearFrame = -1;
                
                // Run through entire frame list to ascertain which frame appears latest.
                for (int k = 0; k < frameList.size(); k++)
                {
                    if (refStringList.contains(frameList.get(k)) == false)
                    {
                        latestAppearFrame = k;
                        break;  // No need to search further.
                    }
                    else if (refStringList.contains(frameList.get(k)) && (latestAppearFrame < 0))
                    {
                        latestAppearFrame = k;
                    }
                    else if (refStringList.contains(frameList.get(k)) && (refStringList.indexOf(frameList.get(latestAppearFrame)) < refStringList.indexOf(frameList.get(k))))
                    {
                        latestAppearFrame = k;
                    }                    
                }
                
                // Swap frame that is appearing latest with current reference string value.
                frameList.set(latestAppearFrame, refString[j]);
                faultCounter++;
                
                // Prints out the current items in the 'stack'.
                for (int l = 0; l < frameList.size(); l++)
                {
                    System.out.print("--" + frameList.get(l) + "--");
                }
                System.out.println();
            }
        }
        
        System.out.println("There were " + faultCounter + " page faults.\n");
    }       
}




