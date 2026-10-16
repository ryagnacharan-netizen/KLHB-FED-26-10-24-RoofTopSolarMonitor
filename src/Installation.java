package src;
public class Installation {
    private final String id;
    private final double panelCapacity;
    private final double systemCost;
    private final Battery battery;

    public Installation(String id, double panelCapacity, double systemCost, Battery battery) {
        this.id = id;
        this.panelCapacity = panelCapacity;
        this.systemCost = systemCost;
        this.battery = battery;
    }

    public String getId() { return id; }
    public double getSystemCost() { return systemCost; }
    public Battery getBattery() { return battery; }
    public boolean hasBattery() { return battery != null; }
}