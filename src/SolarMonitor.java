package src;
import java.io.*;
import java.util.*;

public class SolarMonitor {
    private final Installation installation;
    private final List<Reading> readings = new ArrayList<>();
    private double cumulativeSavings = 0.0;

    public SolarMonitor(Installation installation) {
        this.installation = installation;
    }

    public void addReading(String timestamp, double gen, double use) throws InvalidReadingException {
        if (gen < 0 || use < 0) {
            throw new InvalidReadingException("Generation or consumption cannot be negative!");
        }
        readings.add(new Reading(timestamp, gen, use));
        
        double selfUse = Math.min(gen, use);
        double surplus = Math.max(0, gen - use);
        double deficit = Math.max(0, use - gen);

        if (installation.hasBattery()) {
            surplus = installation.getBattery().charge(surplus);
            deficit -= installation.getBattery().discharge(deficit);
        }

        System.out.printf("→ self %.1f, export %.1f\n", selfUse, surplus);
    }

    public Settlement settle(String period, Tariff tariff) {
        double totalExport = 0;
        double totalImport = 0;
        double totalGen = 0;

        for (Reading r : readings) {
            totalGen += r.generation();
            double surplus = Math.max(0, r.generation() - r.consumption());
            double deficit = Math.max(0, r.consumption() - r.generation());
            totalExport += surplus;
            totalImport += deficit;
        }

        double credit = tariff.calculateCredit(totalExport);
        double cost = tariff.calculateCost(totalImport);
        double netSaved = credit - cost;
        double co2Avoided = totalGen * 0.7;

        this.cumulativeSavings += netSaved;

        System.out.printf("%s: export %.0f kWh @credit = %.0f credit; import %.0f @cost = %.0f cost\n",
                period, totalExport, credit, totalImport, cost);
        System.out.printf("Net saved %.0f. CO2 avoided %.0f kg.\n", netSaved, co2Avoided);

        return new Settlement(period, netSaved, co2Avoided);
    }

    public void reportPayback() {
        if (cumulativeSavings <= 0) {
            System.out.println("Payback: N/A (No net savings achieved yet)");
            return;
        }
        double paybackYears = installation.getSystemCost() / cumulativeSavings;
        System.out.printf("Payback: %.1f yrs at current rate\n", paybackYears);
    }

    public void saveDataToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Reading r : readings) {
                writer.println(r.timestamp() + "," + r.generation() + "," + r.consumption());
            }
            System.out.println("Data saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}