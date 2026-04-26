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
    // UC3 + UC4: Enum (Units)
    // =========================
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

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

        // UC3 & UC4 equality
        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double thisValue = unit.toFeet(value);
            double otherValue = other.unit.toFeet(other.value);

            return Double.compare(thisValue, otherValue) == 0;
        }

        // =========================
        // UC5: Conversion Method
        // =========================
        public static double convert(double value, LengthUnit source, LengthUnit target) {

            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }

            if (source == null || target == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }

            // Convert to base (feet)
            double baseValue = source.toFeet(value);

            // Convert to target
            return baseValue / target.factor;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        // ===== UC1 =====
        System.out.println("=== UC1: Feet Equality ===");
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        System.out.println("1.0 ft vs 1.0 ft → " + f1.equals(f2));

        // ===== UC2 =====
        System.out.println("\n=== UC2: Inches Equality ===");
        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(1.0);
        System.out.println("1.0 inch vs 1.0 inch → " + i1.equals(i2));

        // ===== UC3 =====
        System.out.println("\n=== UC3: Cross Unit Equality ===");
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
        System.out.println("1.0 ft vs 12 inch → " + q1.equals(q2));

        // ===== UC4 =====
        System.out.println("\n=== UC4: Extended Units ===");
        System.out.println("1 yard vs 3 feet → " +
                new QuantityLength(1.0, LengthUnit.YARDS)
                        .equals(new QuantityLength(3.0, LengthUnit.FEET)));

        System.out.println("1 cm vs 0.393701 inch → " +
                new QuantityLength(1.0, LengthUnit.CENTIMETERS)
                        .equals(new QuantityLength(0.393701, LengthUnit.INCH)));

        // ===== UC5 =====
        System.out.println("\n=== UC5: Unit Conversion ===");

        System.out.println("1 ft → inch = " +
                QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCH));

        System.out.println("3 yard → feet = " +
                QuantityLength.convert(3.0, LengthUnit.YARDS, LengthUnit.FEET));

        System.out.println("36 inch → yard = " +
                QuantityLength.convert(36.0, LengthUnit.INCH, LengthUnit.YARDS));

        System.out.println("1 cm → inch = " +
                QuantityLength.convert(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCH));
    }
}