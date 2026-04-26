public class Main {

    // =========================
    // UC1: Feet
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
    // UC2: Inches
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
    // UC8: Length Enum
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

        public double toBase(double value) {
            return value * factor;
        }

        public double fromBase(double base) {
            return base / factor;
        }
    }

    // =========================
    // Length Quantity
    // =========================
    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value) || unit == null)
                throw new IllegalArgumentException();
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(
                    unit.toBase(value),
                    other.unit.toBase(other.value)
            ) == 0;
        }

        public QuantityLength convertTo(LengthUnit target) {
            double base = unit.toBase(value);
            return new QuantityLength(target.fromBase(base), target);
        }

        public static QuantityLength add(QuantityLength q1, QuantityLength q2) {
            double sum = q1.unit.toBase(q1.value) + q2.unit.toBase(q2.value);
            return new QuantityLength(q1.unit.fromBase(sum), q1.unit);
        }

        public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit target) {
            double sum = q1.unit.toBase(q1.value) + q2.unit.toBase(q2.value);
            return new QuantityLength(target.fromBase(sum), target);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // =========================
    // UC9: Weight Enum
    // =========================
    enum WeightUnit {
        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double toBase(double value) {
            return value * factor;
        }

        public double fromBase(double base) {
            return base / factor;
        }
    }

    // =========================
    // UC9: Weight Quantity
    // =========================
    static class QuantityWeight {

        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (!Double.isFinite(value) || unit == null)
                throw new IllegalArgumentException();
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityWeight other = (QuantityWeight) obj;

            return Double.compare(
                    unit.toBase(value),
                    other.unit.toBase(other.value)
            ) == 0;
        }

        public QuantityWeight convertTo(WeightUnit target) {
            double base = unit.toBase(value);
            return new QuantityWeight(target.fromBase(base), target);
        }

        public static QuantityWeight add(QuantityWeight q1, QuantityWeight q2) {
            double sum = q1.unit.toBase(q1.value) + q2.unit.toBase(q2.value);
            return new QuantityWeight(q1.unit.fromBase(sum), q1.unit);
        }

        public static QuantityWeight add(QuantityWeight q1, QuantityWeight q2, WeightUnit target) {
            double sum = q1.unit.toBase(q1.value) + q2.unit.toBase(q2.value);
            return new QuantityWeight(target.fromBase(sum), target);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // =========================
    // MAIN
    // =========================
    public static void main(String[] args) {

        // UC1
        System.out.println("UC1 → " + new Feet(1.0).equals(new Feet(1.0)));

        // UC2
        System.out.println("UC2 → " + new Inches(1.0).equals(new Inches(1.0)));

        // UC3–UC8 Length
        System.out.println("Length Equal → " +
                new QuantityLength(1, LengthUnit.FEET)
                        .equals(new QuantityLength(12, LengthUnit.INCH)));

        System.out.println("Length Convert → " +
                new QuantityLength(1, LengthUnit.FEET)
                        .convertTo(LengthUnit.INCH));

        System.out.println("Length Add → " +
                QuantityLength.add(
                        new QuantityLength(1, LengthUnit.FEET),
                        new QuantityLength(12, LengthUnit.INCH)
                ));

        // UC9 Weight
        System.out.println("Weight Equal → " +
                new QuantityWeight(1, WeightUnit.KILOGRAM)
                        .equals(new QuantityWeight(1000, WeightUnit.GRAM)));

        System.out.println("Weight Convert → " +
                new QuantityWeight(1, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.POUND));

        System.out.println("Weight Add → " +
                QuantityWeight.add(
                        new QuantityWeight(1, WeightUnit.KILOGRAM),
                        new QuantityWeight(1000, WeightUnit.GRAM)
                ));
    }
}