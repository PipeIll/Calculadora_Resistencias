package Models;

import java.awt.Color;

public enum ResistorColor {

    BLACK  ("Negro",   0, 1,          new Color(20, 20, 20)),
    BROWN  ("Café",    1, 10,         new Color(139, 69, 19)),
    RED    ("Rojo",    2, 100,        new Color(200, 30, 30)),
    ORANGE ("Naranja", 3, 1_000,      new Color(220, 120, 0)),
    YELLOW ("Amarillo",4, 10_000,     new Color(230, 210, 0)),
    GREEN  ("Verde",   5, 100_000,    new Color(30, 160, 30)),
    BLUE   ("Azul",    6, 1_000_000,  new Color(30, 80, 200)),
    VIOLET ("Violeta", 7, 10_000_000, new Color(148, 0, 211)),
    GREY   ("Gris",    8, 100_000_000,new Color(150, 150, 150)),
    WHITE  ("Blanco",  9, 1_000_000_000, new Color(240, 240, 240)),
    GOLD   ("Dorado",  -1, 0.1,       new Color(212, 175, 55)),
    SILVER ("Plateado",-1, 0.01,      new Color(180, 180, 180));

    private final String name;
    private final int    digit;
    private final double multiplier;
    private final Color  color;

    ResistorColor(String name, int digit, double multiplier, Color color) {
        this.name       = name;
        this.digit      = digit;
        this.multiplier = multiplier;
        this.color      = color;
    }

    public String getName()        { return name; }
    public int    getDigit()       { return digit; }
    public double getMultiplier()  { return multiplier; }
    public Color  getColor()       { return color; }

    // Solo para bandas de dígito (no Gold ni Silver)
    public static ResistorColor[] soloBandas() {
        return new ResistorColor[]{BLACK, BROWN, RED, ORANGE, YELLOW, GREEN, BLUE, VIOLET, GREY, WHITE};
    }

    // Para tolerancia
    public static ResistorColor[] soloTolerancia() {
        return new ResistorColor[]{BROWN, RED, GREEN, BLUE, VIOLET, GREY, GOLD, SILVER};
    }

    // El % de tolerancia según el color
    public double getTolerance() {
        return switch (this) {
            case BROWN  -> 1;
            case RED    -> 2;
            case GREEN  -> 0.5;
            case BLUE   -> 0.25;
            case VIOLET -> 0.10;
            case GREY   -> 0.05;
            case GOLD   -> 5;
            case SILVER -> 10;
            default     -> 0;
        };
    }

    @Override
    public String toString() { return name; }
}
