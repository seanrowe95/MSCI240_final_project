/**
     * @author Sean Rowe
     * December 15th, 2020
     * MSCI 240 Final Project
     */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Program {
    /**
     * This is the main method of your program.
     * 
     * @param args Command-line arguments
     * @throws IOException If reading from your file had an
     *                     error.
     */
    public static void main(String[] args)
            throws IOException {
        int source = 300712; // 300712 = Jim Gaffigan


        Scanner scanner = new Scanner(new File("data/adj.txt"));
        IntGraphList graph = IntGraphList.read(scanner,50000);
        scanner.close();

        DFSTree dfsTree = new DFSTree(graph);
        printComponentReport(dfsTree);

        BFSTree bfsTree = new BFSTree(graph, source);
        printPathReport(bfsTree);

        System.out.println("Source,Fraction,Mean Distance");
        var allSources = new ArrayList<>(graph.getVertices());
        Collections.shuffle(allSources);
        int min = Math.min(allSources.size(), 100);
        
        List<Double> distanceMeans = new ArrayList<>();
        Statistics stats = new Statistics(bfsTree.getDistancesToConnected());
        int moreConnectedCount = 0;
        
        for (int s : allSources.subList(0,min)) {
        	if (bfsTree.getDistanceTo(s) >= 1) {
        		BFSTree newBFSTree = new BFSTree(graph, s);
        		Statistics connectedStats = new Statistics(newBFSTree.getDistancesToConnected());
        		distanceMeans.add(connectedStats.getMean());
        		printSimplePathReport(newBFSTree);
        	} else {
        		moreConnectedCount += 1;
        	}
        }
        
        for (double mean : distanceMeans) {
        	if (mean > stats.getMean()) {
        		moreConnectedCount += 1;
        	}
        }
        
        double moreConnectedFraction = Double.valueOf(moreConnectedCount) / min;
        double specialThreshold = 0.95;
        double trulySpecialThreshold = 0.99;
        System.out.printf("Is my source actor special? %b\n", moreConnectedFraction > specialThreshold);
        System.out.printf("Is my source actor truly special? %b\n", moreConnectedFraction > trulySpecialThreshold);
        
        
        
    }

    /**
     * Prints information pertaining to the DFS search done on the data set
     * 
     * @param dfsTree - object that has information to build a DFS tree from
     */
    public static void printComponentReport(DFSTree dfsTree) {
        System.out.println(dfsTree.getComponentSizes().size());
        Statistics stats = new Statistics(dfsTree.getComponentSizes());
        System.out.printf("Max component size is %,d\n", stats.getMax());
        System.out.printf("Min component size is %,d\n", stats.getMin());
        System.out.printf("Mean component size is %f\n", stats.getMean());
        System.out.printf("Q1 component size is %f\n", stats.getQ1());
        System.out.printf("Q2 component size is %f\n", stats.getQ2());
        System.out.printf("Q3 component size is %f\n", stats.getQ3());
        
        for (int countKey : stats.getSortedUniqueKeys()) {
        	System.out.printf("There are %d components of size %d\n", stats.getCountOf(countKey), countKey);
        }
    }

    /**
     * Prints information pertaining to the BFS search done on the data set
     * 
     * @param bfsTree - object that has information to build a BFS tree from
     */
    public static void printPathReport(BFSTree bfsTree) {
    	Statistics stats = new Statistics(bfsTree.getDistances());
    	
    	for (int countKey : stats.getSortedUniqueKeys()) {
        	System.out.printf("There are %d actors who are at a distance of %d\n", stats.getCountOf(countKey), countKey);
        }
    	
    	List<Integer> connectedDistances = bfsTree.getDistancesToConnected();
    	double fractionReached = Double.valueOf(connectedDistances.size())/ Double.valueOf((bfsTree.getGraph().getNumVertices() - 1));
    	Statistics connectedStats = new Statistics(connectedDistances);
    	
    	System.out.printf("Fraction of actors reached is %f\n", fractionReached);
    	System.out.printf("Max shortest distance is %,d\n", connectedStats.getMax());
        System.out.printf("Mean shortest distance is %f\n", connectedStats.getMean());
        System.out.printf("Q1 shortest distance is %f\n", connectedStats.getQ1());
        System.out.printf("Q2 shortest distance is %f\n", connectedStats.getQ2());
        System.out.printf("Q3 shortest distance is %f\n", connectedStats.getQ3());
        System.out.printf("Does my source actor have six or fewer “degrees of"
        		+ " separation” from the majority of actors: %b\n", bfsTree.kevinBacon());
    }

    /**
     * Prints minimal information pertaining to the DFS search done on the data 
     * set, to be used to compare different  sources
     * 
     * @param bfsTree - object that has information to build a BFS tree from
     */
    public static void printSimplePathReport(BFSTree bfsTree) {
    	List<Integer> connectedDistances = bfsTree.getDistancesToConnected();
    	double fractionReached = Double.valueOf(connectedDistances.size())/ Double.valueOf((bfsTree.getGraph().getNumVertices() - 1));
    	Statistics connectedStats = new Statistics(connectedDistances);
    	System.out.printf("%d,%f,%f\n", bfsTree.getSource(),fractionReached,connectedStats.getMean());
    }
}
