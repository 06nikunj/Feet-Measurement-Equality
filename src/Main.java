public class Main {

    // =========================
    // UC1: Feet Class
    // =========================
    static class Feet {

        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        // Override equals()
        @Override
        public boolean equals(Object obj) {

            // Same reference (Reflexive)
            if (this == obj) return true;

            // Null check + type check
            if (obj == null || this.getClass() != obj.getClass()) return false;

            // Cast
            Feet other = (Feet) obj;

            // Compare values
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // =========================
    // MAIN METHOD (UC1 FLOW)
    // =========================
    public static void main(String[] args) {

        // Input values
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        System.out.println("Input: 1.0 ft and 1.0 ft");

        boolean result = f1.equals(f2);

        System.out.println("Output: Equal (" + result + ")");
    }
}