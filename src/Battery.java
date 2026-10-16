package src;
public class Battery {
    private final double capacity;
    private double currentCharge;

    public Battery(double capacity) {
        this.capacity = capacity;
        this.currentCharge = 0.0;
    }

    public double charge(double amount) {
        double freeCapacity = capacity - currentCharge;
        double added = Math.min(amount, freeCapacity);
        currentCharge += added;
        return amount - added; // Returns remaining uncharged energy
    }

    public double discharge(double amount) {
        double drawn = Math.min(amount, currentCharge);
        currentCharge -= drawn;
        return drawn; // Returns actual energy drawn
    }

    public double getCurrentCharge() { return currentCharge; }
    public double getCapacity() { return capacity; }
}