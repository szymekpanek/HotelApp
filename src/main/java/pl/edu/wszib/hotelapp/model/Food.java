package pl.edu.wszib.hotelapp.model;

public class Food {
    MealType mealType;
    double pricePerNight;

    public enum MealType {
        BREAKFAST(20),
        DINNER(40);

        private final double price;

        MealType(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }

}
