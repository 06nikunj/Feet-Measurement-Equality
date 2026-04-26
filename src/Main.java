public class Main {

    // =========================
    // UC1: Feet Class
    // =========================
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || this.getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // =========================
    // UC2: Inches Class
    // =========================
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || this.getClass() != obj.getClass()) return false;

            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // =========================
    // UC3 + UC4: Enum for Units
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
    // UC3: Generic Quantity Class
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

        System.out.println("=== UC1: Feet Equality ===");
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        System.out.println("1.0 ft vs 1.0 ft → " + f1.equals(f2));

        System.out.println("\n=== UC2: Inches Equality ===");
        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(1.0);
        System.out.println("1.0 inch vs 1.0 inch → " + i1.equals(i2));

        System.out.println("\n=== UC3: Cross Unit Equality ===");
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
        System.out.println("1.0 ft vs 12 inch → " + q1.equals(q2));

        System.out.println("\n=== UC4: Extended Units ===");

        // Yard ↔ Feet
        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q4 = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println("1 yard vs 3 feet → " + q3.equals(q4));

        // Yard ↔ Inch
        QuantityLength q5 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q6 = new QuantityLength(36.0, LengthUnit.INCH);
        System.out.println("1 yard vs 36 inch → " + q5.equals(q6));

        // CM ↔ CM
        QuantityLength q7 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);
        QuantityLength q8 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);
        System.out.println("2 cm vs 2 cm → " + q7.equals(q8));

        // CM ↔ Inch
        QuantityLength q9 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength q10 = new QuantityLength(0.393701, LengthUnit.INCH);
        System.out.println("1 cm vs 0.393701 inch → " + q9.equals(q10));
    }
}