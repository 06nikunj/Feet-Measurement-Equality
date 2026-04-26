public class Main {

    // =========================
    // UC3 + UC4: Enum (Units)
    // =========================
    enum LengthUnit {

        FEET(1.0),
        INCH(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084); // 1 cm = 0.0328084 feet

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double toFeet(double value) {
            return value * factor;
        }
    }

    // =========================
    // UC3: Quantity Class
    // =========================
    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double thisValue = unit.toFeet(value);
            double otherValue = other.unit.toFeet(other.value);

            return Double.compare(thisValue, otherValue) == 0;
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        System.out.println("=== UC4: Extended Units ===");

        // Yard to Feet
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println("1 yard vs 3 feet → " + q1.equals(q2));

        // Yard to Inches
        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q4 = new QuantityLength(36.0, LengthUnit.INCH);
        System.out.println("1 yard vs 36 inch → " + q3.equals(q4));

        // Same Yard
        QuantityLength q5 = new QuantityLength(2.0, LengthUnit.YARDS);
        QuantityLength q6 = new QuantityLength(2.0, LengthUnit.YARDS);
        System.out.println("2 yard vs 2 yard → " + q5.equals(q6));

        // CM to CM
        QuantityLength q7 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);
        QuantityLength q8 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);
        System.out.println("2 cm vs 2 cm → " + q7.equals(q8));

        // CM to Inches
        QuantityLength q9 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength q10 = new QuantityLength(0.393701, LengthUnit.INCH);
        System.out.println("1 cm vs 0.393701 inch → " + q9.equals(q10));
    }
}