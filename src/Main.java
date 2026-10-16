package src;
import java.util.Scanner;

public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Installation homeSystem = new Installation("INS-101", 5.0, 1075.2, null);
        SolarMonitor monitor = new SolarMonitor(homeSystem);
        Tariff standardTariff = new StandardTariff(8.0, 6.0);

        while (true) {
            System.out.println("\n=== Solar Monitor ===");
            System.out.println("1) Add reading  2) Settle  3) Report  4) Exit");
            System.out.print("Choice: ");
            
            if (!scanner.hasNextInt()) break;
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter gen (kWh) and use (kWh) [e.g., 5.4 3.1]: ");
                    double gen = scanner.nextDouble();
                    double use = scanner.nextDouble();
                    try {
                        monitor.addReading("NOW", gen, use);
                    } catch (InvalidReadingException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 2 -> {
                    monitor.settle("July", standardTariff);
                }
                case 3 -> {
                    monitor.reportPayback();
                }
                case 4 -> {
                    monitor.saveDataToFile("solar_data.csv");
                    System.out.println("Exiting. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}