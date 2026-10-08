package frc.lib;
import java.util.ArrayList;

public class SlidingWindowAverage {
    private static final int windowSize = 10;
    private ArrayList<Double> window = new ArrayList<>();
    private double sum = 0;

    // Method to add a new value and maintain the window size
    public void add(double value) {
        // If the window is already full, remove the oldest value
        if (window.size() == windowSize) {
            double oldest = window.remove(0);  // Remove the oldest value (at index 0)
            sum -= oldest;  // Subtract the oldest value from the sum
        }
        window.add(value);  // Add the new value
        sum += value;  // Add the new value to the sum
    }
    // Method to get the average of the current window
    public double getAverage() {
        if (window.isEmpty()) {
            return 0;  // Prevent division by zero
        }
        return sum / window.size();
    }
}