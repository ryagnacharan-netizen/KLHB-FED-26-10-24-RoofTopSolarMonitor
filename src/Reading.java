package src;
public record Reading(String timestamp, double generation, double consumption) {
    public Reading {
        if (generation < 0 || consumption < 0) {
            throw new IllegalArgumentException("Generation and consumption cannot be negative.");
        }
    }
}

    

