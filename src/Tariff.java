package src;
public interface Tariff {
    double calculateCredit(double exportedKwh);
    double calculateCost(double importedKwh);
}