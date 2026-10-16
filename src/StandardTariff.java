package src;
public class StandardTariff implements Tariff {
    private final double importRate;
    private final double exportRate;

    public StandardTariff(double importRate, double exportRate) {
        this.importRate = importRate;
        this.exportRate = exportRate;
    }

    @Override
    public double calculateCredit(double exportedKwh) {
        return exportedKwh * exportRate;
    }

    @Override
    public double calculateCost(double importedKwh) {
        return importedKwh * importRate;
    }
}