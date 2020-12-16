import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This class is a helper class for calculating statistics
 * based on a list of integer values. In both Question 1 and
 * Question 2, you will need to calculate the min, max,
 * mean, Q1, Q2, and Q3 of a list of integers, as well as
 * count the frequency of each integer.
 *
 * IMPORTANT: This class is not yet complete, so must be
 * completed by you.
 *
 * To accomplish this, you should fill in the missing parts
 * of this code. Project 3 builds upon Projects 1 and 2
 * here! Finding the top N foods by calorie count is similar
 * to finding the first second and third quartiles. I
 * recommend you consider using a min heap (via the
 * PriorityQueue class) to complete this code. However, you
 * may use whatever means you prefer (sorting, your solution
 * to Project 1, etc.).
 *
 * @author Mark Hancock
 * @author Sean Rowe
 *
 */
public class Statistics {
    private HashMap<Integer, Integer> counts = new HashMap<>();
    private int min;
    private int max;
    private double mean;
    private double q1;
    private double q2;
    private double q3;

    /**
     * Constructs a new Statistics object based on a list of
     * integers. This class doesn't store the integers, but
     * instead calculates the min, max, Q1-Q3, and counts
     * the number of occurrences of each value.
     *
     * @param list the list of integers to collect
     *             statistics from.
     */
    public Statistics(List<Integer> list) {
        calculateFromList(list);
    }

    /**
     * This method calculates the min, max, mean, Q1, Q2,
     * and Q3 values from a list of integers, and also
     * counts the frequency (number of occurrences) of each
     * repeated value in the list.
     *
     * @param list a list of unsorted integers, with
     *             possible repeated values.
     */
    private void calculateFromList(List<Integer> list) {
    	buildCountsHash(list);
    	Collections.sort(list);
    	List<Integer> sortedKeys = getSortedUniqueKeys();
    	min = sortedKeys.get(0);
    	max = sortedKeys.get(sortedKeys.size()-1);
    	mean = getSum()/list.size();
    	q2 = calculateQ2(list);
    	q1 = calculateQ1(list);
    	q3 = calculateQ3(list);
    }
    
    /**
     * Builds the HashMap of counts of integers in the list
     *
     * @return HashMap of counts of integers in the list
     */
    private void buildCountsHash(List<Integer> list) {
    	 for (int val : list) {
    		 if (counts.containsKey(val)) {
    			 counts.put(val, counts.get(val) + 1);
    		 } else {
    			 counts.put(val, 1);
    		 }
    	 }
    }
    
    /**
     * Calculates the second quartile value from the list
     *
     * @param a sorted list of integers
     * @return the first second value from the list
     */
    private double calculateQ2(List<Integer> list) {
    	if (list.size() % 2 == 1) {
    		return list.get(list.size()/2);
    	} else {
    		int index_1 = list.size()/2;
    		int index_2 = index_1 - 1;
    		return Double.valueOf(list.get(index_1) + list.get(index_2)) / 2;
    	}
    }
    
    /**
     * Calculates the first quartile value from the list
     *
     * @param a sorted list of integers
     * @return the first quartile value from the list
     */
    private double calculateQ1(List<Integer> list) {
    	int topIndex = list.size()/2;
		return calculateQ2(list.subList(0, topIndex));
    }
    
    /**
     * Calculates the third quartile value from the list
     *
     * @param a sorted list of integers
     * @return the third quartile value from the list
     */
    private double calculateQ3(List<Integer> list) {
    	if (list.size() % 2 == 1) {
    		int bottomIndex = list.size()/2 + 1;
    		return calculateQ2(list.subList(bottomIndex, list.size()));
    	} else {
    		int bottomIndex = list.size()/2;
    		return calculateQ2(list.subList(bottomIndex, list.size()));
    	}
    }

    /**
     * Returns a list of ordered unique values based on the
     * original list with duplicates.
     *
     * @return a list of ordered unique values based on the
     *         original list with duplicates
     */
    public List<Integer> getSortedUniqueKeys() {
        ArrayList<Integer> keys = new ArrayList<>(
                counts.keySet());
        Collections.sort(keys);
        return keys;
    }

    /**
     * Returns the number of occurrences of a specific
     * value.
     *
     * @param value the value to check for occurrences
     * @return the number of occurrences
     */
    public int getCountOf(int value) {
        return counts.get(value);
    }

    /**
     * Returns the minimum value from the list.
     *
     * @return the minimum value from the list
     */
    public int getMin() {
        return min;
    }

    /**
     * Returns the maximum value from the list.
     *
     * @return the maximum value from the list
     */
    public int getMax() {
        return max;
    }

    /**
     * Returns the mean value from the list.
     *
     * @return the mean value from the list
     */
    public double getMean() {
        return mean;
    }

    /**
     * Returns the first quartile value from the list.
     *
     * @return the first quartile value from the list
     */
    public double getQ1() {
        return q1;
    }

    /**
     * Returns the second quartile (a.k.a. median) value
     * from the list.
     *
     * @return the first quartile value from the list
     */
    public double getQ2() {
        return q2;
    }

    /**
     * Returns the third quartile value from the list.
     *
     * @return the third quartile value from the list
     */
    public double getQ3() {
        return q3;
    }
    
    /**
     * Returns the sum for all of the values in the list.
     *
     * @return the sum for all of the values in the list
     */
    private double getSum() {
    	double sum = 0;
    	for (int key : counts.keySet()) {
    		sum += (key * counts.get(key));
    	}
    	return sum;
    }
}
