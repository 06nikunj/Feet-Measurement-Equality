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
        // UC5: Conversion
        // =========================
        public static double convert(double value, LengthUnit source, LengthUnit target) {

            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }

            if (source == null || target == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }

            double base = source.toFeet(value);
            return base / target.factor;
        }

        // =========================
        // UC6: Addition
        // =========================
        public static QuantityLength add(QuantityLength q1, QuantityLength q2) {

            if (q1 == null || q2 == null) {
                throw new IllegalArgumentException("Quantity cannot be null");
            }

            if (!Double.isFinite(q1.value) || !Double.isFinite(q2.value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }

            double base1 = q1.unit.toFeet(q1.value);
            double base2 = q2.unit.toFeet(q2.value);

            double sumBase = base1 + base2;

            double result = sumBase / q1.unit.factor;

            return new QuantityLength(result, q1.unit);
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
        System.out.println("1 ft vs 1 ft → " +
                new Feet(1.0).equals(new Feet(1.0)));

        // ===== UC2 =====
        System.out.println("\n=== UC2: Inches Equality ===");
        System.out.println("1 inch vs 1 inch → " +
                new Inches(1.0).equals(new Inches(1.0)));

        // ===== UC3 =====
        System.out.println("\n=== UC3: Cross Unit Equality ===");
        System.out.println("1 ft vs 12 inch → " +
                new QuantityLength(1.0, LengthUnit.FEET)
                        .equals(new QuantityLength(12.0, LengthUnit.INCH)));

        // ===== UC4 =====
        System.out.println("\n=== UC4: Extended Units ===");
        System.out.println("1 yard vs 3 feet → " +
                new QuantityLength(1.0, LengthUnit.YARDS)
                        .equals(new QuantityLength(3.0, LengthUnit.FEET)));

        System.out.println("1 cm vs 0.393701 inch → " +
                new QuantityLength(1.0, LengthUnit.CENTIMETERS)
                        .equals(new QuantityLength(0.393701, LengthUnit.INCH)));

        // ===== UC5 =====
        System.out.println("\n=== UC5: Conversion ===");
        System.out.println("1 ft → inch = " +
                QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCH));

        System.out.println("36 inch → feet = " +
                QuantityLength.convert(36.0, LengthUnit.INCH, LengthUnit.FEET));

        // ===== UC6 =====
        System.out.println("\n=== UC6: Addition ===");

        System.out.println("1 ft + 12 inch → " +
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(12.0, LengthUnit.INCH)
                ));

        System.out.println("12 inch + 1 ft → " +
                QuantityLength.add(
                        new QuantityLength(12.0, LengthUnit.INCH),
                        new QuantityLength(1.0, LengthUnit.FEET)
                ));

        System.out.println("1 yard + 3 feet → " +
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.YARDS),
                        new QuantityLength(3.0, LengthUnit.FEET)
                ));
    }
}