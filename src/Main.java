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
    // UC8: Standalone Enum (Conversion Responsibility)
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

        // Convert to base (feet)
        public double toBase(double value) {
            return value * factor;
        }

        // Convert from base (feet)
        public double fromBase(double baseValue) {
            return baseValue / factor;
        }
    }

    // =========================
    // UC3–UC8: Quantity Class
    // =========================
    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value) || unit == null)
                throw new IllegalArgumentException("Invalid input");

            this.value = value;
            this.unit = unit;
        }

        // UC3: Equality
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double v1 = unit.toBase(value);
            double v2 = other.unit.toBase(other.value);

            return Double.compare(v1, v2) == 0;
        }

        // UC5: Convert
        public QuantityLength convertTo(LengthUnit target) {
            double base = unit.toBase(value);
            double result = target.fromBase(base);
            return new QuantityLength(result, target);
        }

        // UC6: Add (default unit)
        public static QuantityLength add(QuantityLength q1, QuantityLength q2) {
            double base1 = q1.unit.toBase(q1.value);
            double base2 = q2.unit.toBase(q2.value);

            double sum = base1 + base2;

            double result = q1.unit.fromBase(sum);
            return new QuantityLength(result, q1.unit);
        }

        // UC7: Add (target unit)
        public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit target) {
            double base1 = q1.unit.toBase(q1.value);
            double base2 = q2.unit.toBase(q2.value);

            double sum = base1 + base2;

            double result = target.fromBase(sum);
            return new QuantityLength(result, target);
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
        System.out.println("UC1 → " + new Feet(1.0).equals(new Feet(1.0)));

        // UC2
        System.out.println("UC2 → " + new Inches(1.0).equals(new Inches(1.0)));

        // UC3
        System.out.println("UC3 → " +
                new QuantityLength(1.0, LengthUnit.FEET)
                        .equals(new QuantityLength(12.0, LengthUnit.INCH)));

        // UC4
        System.out.println("UC4 → " +
                new QuantityLength(1.0, LengthUnit.YARDS)
                        .equals(new QuantityLength(3.0, LengthUnit.FEET)));

        // UC5
        System.out.println("UC5 → 1 ft to inch = " +
                new QuantityLength(1.0, LengthUnit.FEET)
                        .convertTo(LengthUnit.INCH));

        // UC6
        System.out.println("UC6 → " +
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(12.0, LengthUnit.INCH)
                ));

        // UC7
        System.out.println("UC7 → " +
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(12.0, LengthUnit.INCH),
                        LengthUnit.YARDS
                ));

        // UC8 (refactored conversion via enum)
        System.out.println("UC8 → " +
                new QuantityLength(36.0, LengthUnit.INCH)
                        .equals(new QuantityLength(1.0, LengthUnit.YARDS)));
    }
}