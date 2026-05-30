package Models;

import java.awt.Color;

public enum ResistorColor {
    BLACK  ("Negro",    0,  1,            0,       new Color(20, 20, 20)),
    BROWN  ("Marron",    1,  10,           1,       new Color(139, 69, 19)),
    RED    ("Rojo",      2,  100,          2,       new Color(200, 30, 30)),
    ORANGE ("Naranja",   3,  1_000,        0,       new Color(220, 120, 0)),
    YELLOW ("Amarillo",   4,  10_000,       0,       new Color(230, 210, 0)),
    GREEN  ("Verde",    5,  100_000,      0.5,     new Color(30, 160, 30)),
    BLUE   ("Azul",     6,  1_000_000,    0.25,    new Color(30, 80, 200)),
    VIOLET ("Morado",   7,  10_000_000,   0.10,    new Color(148, 0, 211)),
    GREY   ("Gris",     8,  100_000_000,  0.05,    new Color(150, 150, 150)),
    WHITE  ("Blanco",    9,  1_000_000_000,0,       new Color(240, 240, 240)),
    GOLD   ("Dorado",     -1, 0.1,          5,       new Color(212, 175, 55)),
    SILVER ("Plateado",   -1, 0.01,         10,      new Color(180, 180, 180));

    private final String name;
    private final int digit;
    private final double multiplier;
    private final double tolerance;
    private final Color color;

    ResistorColor(String name, int digit, double multiplier, double tolerance, Color color) {
        this.name       = name;
        this.digit      = digit;
        this.multiplier = multiplier;
        this.tolerance  = tolerance;
        this.color      = color;
    }

    public String  getName()       { return name; }
    public int     getDigit()      { return digit; }
    public double  getMultiplier() { return multiplier; }
    public double  getTolerance()  { return tolerance; }
    public Color   getColor()      { return color; }

    public static ResistorColor[] BandColors() {
        return new ResistorColor[]{BLACK, BROWN, RED, ORANGE, YELLOW, GREEN, BLUE, VIOLET, GREY, WHITE};
    }

    public static ResistorColor[] MultiplierColor() {
        return values();
    }

    public static ResistorColor[] ToleranceColor() {
        return new ResistorColor[]{BROWN, RED, GREEN, BLUE, VIOLET, GREY, GOLD, SILVER};
    }

    @Override
    public String toString() { return name; }
}
