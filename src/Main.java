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
            if (obj == null || getClass() != obj.getClass()) return false;
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
            if (obj == null || getClass() != obj.getClass()) return false;
            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // =========================
    // UC3 + UC4: Units Enum
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

        // UC3 Equality
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double v1 = unit.toFeet(value);
            double v2 = other.unit.toFeet(other.value);

            return Double.compare(v1, v2) == 0;
        }

        // =========================
        // UC5: Conversion
        // =========================
        public static double convert(double value, LengthUnit source, LengthUnit target) {

            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
            if (source == null || target == null) throw new IllegalArgumentException("Unit cannot be null");

            double base = source.toFeet(value);
            return base / target.factor;
        }

        // =========================
        // UC6: Addition (default unit)
        // =========================
        public static QuantityLength add(QuantityLength q1, QuantityLength q2) {

            if (q1 == null || q2 == null) throw new IllegalArgumentException("Null input");

            double base1 = q1.unit.toFeet(q1.value);
            double base2 = q2.unit.toFeet(q2.value);

            double sum = base1 + base2;

            double result = sum / q1.unit.factor;

            return new QuantityLength(result, q1.unit);
        }

        // =========================
        // UC7: Addition with Target Unit
        // =========================
        public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit targetUnit) {

            if (q1 == null || q2 == null || targetUnit == null)
                throw new IllegalArgumentException("Invalid input");

            double base1 = q1.unit.toFeet(q1.value);
            double base2 = q2.unit.toFeet(q2.value);

            double sum = base1 + base2;

            double result = sum / targetUnit.factor;

            return new QuantityLength(result, targetUnit);
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

        // UC1
        System.out.println("=== UC1 ===");
        System.out.println(new Feet(1.0).equals(new Feet(1.0)));

        // UC2
        System.out.println("\n=== UC2 ===");
        System.out.println(new Inches(1.0).equals(new Inches(1.0)));

        // UC3
        System.out.println("\n=== UC3 ===");
        System.out.println(
                new QuantityLength(1.0, LengthUnit.FEET)
                        .equals(new QuantityLength(12.0, LengthUnit.INCH))
        );

        // UC4
        System.out.println("\n=== UC4 ===");
        System.out.println(
                new QuantityLength(1.0, LengthUnit.YARDS)
                        .equals(new QuantityLength(3.0, LengthUnit.FEET))
        );

        // UC5
        System.out.println("\n=== UC5 ===");
        System.out.println("1 ft → inch = " +
                QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCH));

        // UC6
        System.out.println("\n=== UC6 ===");
        System.out.println(
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(12.0, LengthUnit.INCH)
                )
        );

        // UC7
        System.out.println("\n=== UC7 ===");

        System.out.println(
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(12.0, LengthUnit.INCH),
                        LengthUnit.FEET
                )
        );

        System.out.println(
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(12.0, LengthUnit.INCH),
                        LengthUnit.INCH
                )
        );

        System.out.println(
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(12.0, LengthUnit.INCH),
                        LengthUnit.YARDS
                )
        );
    }
}