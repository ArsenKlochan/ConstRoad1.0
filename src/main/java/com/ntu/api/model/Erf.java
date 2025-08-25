package com.ntu.api.model;
/**
 * Швидка й достатньо точна реалізація erf(x).
 * Макс. абсолютна похибка ~1e-7 для double.
 */
public final class Erf {

    private Erf() {} // утилітарний клас

    // Коефіцієнти Abramowitz & Stegun 7.1.26
    private static final double P  = 0.3275911;
    private static final double A1 = 0.254829592;
    private static final double A2 = -0.284496736;
    private static final double A3 = 1.421413741;
    private static final double A4 = -1.453152027;
    private static final double A5 = 1.061405429;

    /** erf(x) */
    public static double erf(double x) {
        if (Double.isNaN(x)) return Double.NaN;
        if (Double.isInfinite(x)) return Math.copySign(1.0, x);

        double sign = Math.copySign(1.0, x);
        double ax = Math.abs(x);

        // Для дуже великих |x| значення практично ±1
        if (ax > 6.0) return sign; // exp(-x^2) вже в денормалах/0

        double t = 1.0 / (1.0 + P * ax);
        // Поліном Горнера та множник exp(-x^2)
        double poly = ((((A5 * t + A4) * t + A3) * t + A2) * t + A1) * t;
        double y = 1.0 - poly * Math.exp(-ax * ax);

        return sign * y;
    }

    /** Зручність: стандартна нормальна CDF через erf. */
    public static double phi(double x) {
        return 0.5 * (1.0 + erf(x / Math.sqrt(2.0)));
    }
}

