import java.util.Objects;

// ==========================
// UC10: Interface
// ==========================
interface IMeasurable {
    double getConversionFactor();
    String getUnitName();

    default double convertToBaseUnit(double value) {
        return value * getConversionFactor();
    }

    default double convertFromBaseUnit(double baseValue) {
        return baseValue / getConversionFactor();
    }
}

// ==========================
// UC2–UC9: Length Units
// ==========================
enum LengthUnit implements IMeasurable {
    FEET(1.0),
    INCHES(1.0 / 12),
    YARDS(3.0),
    CENTIMETERS(0.0328084);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public String getUnitName() {
        return this.name();
    }
}

// ==========================
// UC9: Weight Units
// ==========================
enum WeightUnit implements IMeasurable {
    KILOGRAM(1.0),
    GRAM(0.001);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public String getUnitName() {
        return this.name();
    }
}

// ==========================
// UC10: Generic Quantity Class
// ==========================
class Quantity<U extends IMeasurable> {
    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid value or unit");
        }
        this.value = value;
        this.unit = unit;
    }

    public Quantity<U> convertTo(U targetUnit) {
        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);
        return new Quantity<>(Math.round(converted * 100.0) / 100.0, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        double base1 = unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);
        double sum = base1 + base2;
        double result = targetUnit.convertFromBaseUnit(sum);
        return new Quantity<>(Math.round(result * 100.0) / 100.0, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Quantity<?> that = (Quantity<?>) obj;

        // Prevent cross-category
        if (this.unit.getClass() != that.unit.getClass()) return false;

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = that.unit.convertToBaseUnit(that.value);

        return Double.compare(base1, base2) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}

// ==========================
// MAIN (UC1 → UC10 DEMO)
// ==========================
public class Main {

    public static void main(String[] args) {

        System.out.println("=== UC1 → UC10 Demonstration ===");

        // UC1–UC2: Equality
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);

        System.out.println("1 ft == 12 inch → " + q1.equals(q2));

        // UC3–UC4: Conversion
        System.out.println("1 ft to inches → " + q1.convertTo(LengthUnit.INCHES));

        // UC5–UC6: Addition
        System.out.println("1 ft + 12 inch → " + q1.add(q2, LengthUnit.FEET));

        // UC9: Weight
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        System.out.println("1 kg == 1000 g → " + w1.equals(w2));

        // UC10: Cross category check
        System.out.println("1 ft == 1 kg → " + q1.equals(w1));
    }
}