package com.ProcessScheduling;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ProcessScheduling {
    //print all process in D
    public static void printProcess(Process printP) {
        System.out.println("Id = " + printP.id + " priority = " + printP.process_priority + " duration = " + printP.duration + " arrival time = " + printP.arrivalTime);
    }
    //print executing process
    public static double printRunningProcess(Process process, int currentTime, double total_wait) {
        total_wait = total_wait + currentTime - process.getArrivalTime();
        System.out.println();
        System.out.println("Update Priority");
        System.out.println("Process removed from queue is: id = " + process.id + ", at time " + currentTime + ", wait time = " + (currentTime - process.arrivalTime) + " Total wait time = " + total_wait + "\n" +
                "Process id = " + process.id + "\n" +
                "\tPriority = " + process.process_priority + "\n" +
                "\tArrival = " + process.arrivalTime + "\n" +
                "\tDuration  = " + process.duration + "\n" +
                "Process " + process.getProcess_id() + " finished at time " + (process.duration + currentTime) + "\n");
        return total_wait;
    }
    // //refresh the priority of each process when program executed a process, and print each priority decrementing process.
    public static void refreshPriority(HeapAdaptablePriorityQueue processPQ, int currentTime) {
        HeapAdaptablePriorityQueue.AdaptablePQEntry<Integer, Process> processEntry;
        for (int i = 0; i < processPQ.heap.size(); i++) {
            processEntry = (HeapAdaptablePriorityQueue.AdaptablePQEntry<Integer, Process>) processPQ.heap.get(i);
            Process p = processEntry.getValue();
            int waitingTime = currentTime - p.arrivalTime;

            if (waitingTime > 10) {
                Integer updatePriority = p.getProcess_priority() - 1;
                p.setProcess_priority(updatePriority);
                processPQ.replaceKey(processEntry, updatePriority);
                processPQ.replaceValue(processEntry, p);
                System.out.println("Priority of process " + p.getProcess_id() + " decremented, New priority is " + updatePriority);
            }
        }
    }
    //run a program which can execute process.
    public static void runProcess(Scanner input){
        //store all the process in a arraylist D;
        ArrayList<Process> processlist = new ArrayList<>();//list D

        //init a new HeapAdaptablePriorityQueue to save process.
        HeapAdaptablePriorityQueue<Integer, Process> processPQ = new HeapAdaptablePriorityQueue<>();//list Q
        HeapAdaptablePriorityQueue.AdaptablePQEntry<Integer, Process> processEntry;
        while (input.hasNext()) {
            String s = input.nextLine();
            String[] tempArrays = s.split(" ");
            processlist.add(new Process(Integer.valueOf(tempArrays[0]), Integer.valueOf(tempArrays[1]), Integer.valueOf(tempArrays[2]), Integer.valueOf(tempArrays[3])));
        }
        //print all process in arraylist.
        Iterator<Process> processListIt = processlist.iterator();
        while (processListIt.hasNext()) {
            printProcess(processListIt.next());
        }
        Process p;
        int process_num = processlist.size();
        boolean running = false;
        //set a stopwatch: currentTime
        int currentTime = 0;
        //init a variable, for save every running process end time;
        int endTime = 0;
        double total_wait = 0.0;
        while (!processlist.isEmpty()) {
            p = processlist.get(0);//Get a process p from D that has the earliest arrival time
            if (p.arrivalTime <= currentTime) {
                //move the earliest process from D into Q;
                p = processlist.remove(0);
                processEntry = (HeapAdaptablePriorityQueue.AdaptablePQEntry<Integer, Process>) processPQ.insert(p.process_priority, p);
            }
            //execute a process which has the smallest priority;
            if (!processPQ.isEmpty() && running == false) {
                Process rp = processPQ.removeMin().getValue();
                total_wait = printRunningProcess(rp, currentTime, total_wait);
                endTime = currentTime + rp.duration;//set running process's end time;
                running = true;
            } else {
                currentTime++;
            }
            if (currentTime == endTime) {
                running = false;
                refreshPriority(processPQ, currentTime);
            }
        }
        currentTime = endTime;
        //execute remained process in Q
        while (!processPQ.isEmpty()) {
            refreshPriority(processPQ, currentTime);
            p = processPQ.removeMin().getValue();
            total_wait = printRunningProcess(p, currentTime, total_wait);
            currentTime += p.duration;
        }
        System.out.println("Total wait time = " + total_wait + "\n" +
                "Average wait time = " + total_wait / process_num + "\n");
    }
    public static void main(String[] args) throws IOException {
        //read file, and print process info;
        Scanner input = new Scanner(new File("process_scheduling_input.txt"));
        runProcess(input);
    }
}
