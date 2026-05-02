import java.util.*;

// =========================
// MAIN CLASS
// =========================
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
    // UC10: Interface
    // =========================
    interface IMeasurable {
        double getFactor();

        default double toBase(double value) {
            return value * getFactor();
        }

        default double fromBase(double base) {
            return base / getFactor();
        }
    }

    // =========================
    // UC3–UC8: Length Enum
    // =========================
    enum LengthUnit implements IMeasurable {
        FEET(1.0),
        INCH(1.0 / 12),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double getFactor() {
            return factor;
        }
    }

    // =========================
    // UC9: Weight Enum
    // =========================
    enum WeightUnit implements IMeasurable {
        KILOGRAM(1.0),
        GRAM(0.001);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double getFactor() {
            return factor;
        }
    }

    // =========================
    // UC3–UC9: Length Class
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

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // =========================
    // UC9: Weight Class
    // =========================
    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {
            QuantityWeight other = (QuantityWeight) obj;
            return Double.compare(
                    unit.toBase(value),
                    other.unit.toBase(other.value)
            ) == 0;
        }
    }

    // =========================
    // UC10: Generic Class
    // =========================
    static class Quantity<U extends IMeasurable> {
        private final double value;
        private final U unit;

        public Quantity(double value, U unit) {
            this.value = value;
            this.unit = unit;
        }

        public Quantity<U> convertTo(U target) {
            double base = unit.toBase(value);
            return new Quantity<>(target.fromBase(base), target);
        }

        public Quantity<U> add(Quantity<U> other, U target) {
            double sum = unit.toBase(value) + other.unit.toBase(other.value);
            return new Quantity<>(target.fromBase(sum), target);
        }

        @Override
        public boolean equals(Object obj) {
            Quantity<?> other = (Quantity<?>) obj;

            if (unit.getClass() != other.unit.getClass()) return false;

            return Double.compare(
                    unit.toBase(value),
                    other.unit.toBase(other.value)
            ) == 0;
        }
    }

    // =========================
    // UC11: PalindromeChecker (OOP)
    // =========================
    static class PalindromeChecker {

        public boolean checkPalindrome(String input) {

            if (input == null) return false;

            // remove spaces and lower case
            input = input.replaceAll("\\s+", "").toLowerCase();

            Stack<Character> stack = new Stack<>();

            // push all characters
            for (char c : input.toCharArray()) {
                stack.push(c);
            }

            // compare
            for (char c : input.toCharArray()) {
                if (c != stack.pop()) {
                    return false;
                }
            }

            return true;
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        // UC1
        System.out.println("UC1 → " + new Feet(1).equals(new Feet(1)));

        // UC2
        System.out.println("UC2 → " + new Inches(1).equals(new Inches(1)));

        // UC3–UC8
        QuantityLength l1 = new QuantityLength(1, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(12, LengthUnit.INCH);

        System.out.println("Length Equal → " + l1.equals(l2));
        System.out.println("Length Convert → " + l1.convertTo(LengthUnit.INCH));

        // UC9
        QuantityWeight w1 = new QuantityWeight(1, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000, WeightUnit.GRAM);

        System.out.println("Weight Equal → " + w1.equals(w2));

        // UC10
        Quantity<LengthUnit> g1 = new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> g2 = new Quantity<>(12, LengthUnit.INCH);

        System.out.println("UC10 Equal → " + g1.equals(g2));

        // UC11
        PalindromeChecker checker = new PalindromeChecker();

        System.out.println("madam → " + checker.checkPalindrome("madam"));
        System.out.println("hello → " + checker.checkPalindrome("hello"));
        System.out.println("race car → " + checker.checkPalindrome("race car"));
    }
}