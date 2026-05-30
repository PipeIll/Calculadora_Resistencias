package Models;

public class ResistorCalculator {

    public static class Result {
        public final double ohms;
        public final double tolerance;
        public final String formatted;

        public Result(double ohms, double tolerance) {
            this.ohms      = ohms;
            this.tolerance = tolerance;
            this.formatted = formatOhms(ohms) + (tolerance > 0 ? " ±" + tolerance + "%" : "");
        }

        private static String formatOhms(double ohms) {
            if (ohms >= 1_000_000)
                return clean(ohms / 1_000_000) + " MΩ";
            if (ohms >= 1_000)
                return clean(ohms / 1_000) + " kΩ";
            return clean(ohms) + " Ω";
        }

        // Quita decimales innecesarios
        private static String clean(double v) {
            if (v == Math.floor(v)) return String.valueOf((long) v);
            return String.valueOf(v);
        }
    }

    //Calcula resistencia de 4 bandas:

    public static Result calculate4(ResistorColor b1, ResistorColor b2,
                                    ResistorColor multiplier, ResistorColor tolerance) {
        double ohms = (b1.getDigit() * 10 + b2.getDigit()) * multiplier.getMultiplier();
        return new Result(ohms, tolerance.getTolerance());
    }

    //Calcula resistencia de 5 bandas:
    public static Result calculate5(ResistorColor b1, ResistorColor b2, ResistorColor b3,
                                    ResistorColor multiplier, ResistorColor tolerance) {
        double ohms = (b1.getDigit() * 100 + b2.getDigit() * 10 + b3.getDigit())
                * multiplier.getMultiplier();
        return new Result(ohms, tolerance.getTolerance());
    }
}
