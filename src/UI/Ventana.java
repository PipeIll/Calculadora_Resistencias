package UI;

import Models.ResistorColor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class Ventana extends JFrame {

    // dropdowns
    private JComboBox<ResistorColor> comboBanda1;
    private JComboBox<ResistorColor> comboBanda2;
    private JComboBox<ResistorColor> comboMultiplicador;
    private JComboBox<ResistorColor> comboTolerancia;

    // resultado
    private JLabel labelResultado;

    // panel del dibujo
    private PanelResistor panelResistor;

    public Ventana() {
        setTitle("Calculadora de Resistencias - 4 Bandas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(crearPanelCombos(), BorderLayout.WEST);
        add(crearPanelDerecha(), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    // Panel izquierdo: los 4 dropdowns + botón
    private JPanel crearPanelCombos() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Bandas"));

        comboBanda1        = new JComboBox<>(ResistorColor.soloBandas());
        comboBanda2        = new JComboBox<>(ResistorColor.soloBandas());
        comboMultiplicador = new JComboBox<>(ResistorColor.values());
        comboTolerancia    = new JComboBox<>(ResistorColor.soloTolerancia());

        // valores por defecto: Brown Black Red Gold = 1kΩ ±5%
        comboBanda1.setSelectedItem(ResistorColor.BROWN);
        comboBanda2.setSelectedItem(ResistorColor.BLACK);
        comboMultiplicador.setSelectedItem(ResistorColor.RED);
        comboTolerancia.setSelectedItem(ResistorColor.GOLD);

        panel.add(new JLabel("Banda 1:"));
        panel.add(comboBanda1);
        panel.add(new JLabel("Banda 2:"));
        panel.add(comboBanda2);
        panel.add(new JLabel("Multiplicador:"));
        panel.add(comboMultiplicador);
        panel.add(new JLabel("Tolerancia:"));
        panel.add(comboTolerancia);

        // botón calcular
        JButton botonCalcular = new JButton("Calcular");
        botonCalcular.addActionListener(e -> calcular());
        panel.add(new JLabel()); // celda vacía para alinear
        panel.add(botonCalcular);

        return panel;
    }

    // Panel derecho: dibujo arriba, resultado abajo
    private JPanel crearPanelDerecha() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));

        panelResistor = new PanelResistor();
        labelResultado = new JLabel("Presiona Calcular", SwingConstants.CENTER);
        labelResultado.setFont(new Font("SansSerif", Font.BOLD, 18));
        labelResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));

        panel.add(panelResistor,  BorderLayout.CENTER);
        panel.add(labelResultado, BorderLayout.SOUTH);

        return panel;
    }

    // Lógica del cálculo
    private void calcular() {
        ResistorColor b1  = (ResistorColor) comboBanda1.getSelectedItem();
        ResistorColor b2  = (ResistorColor) comboBanda2.getSelectedItem();
        ResistorColor mul = (ResistorColor) comboMultiplicador.getSelectedItem();
        ResistorColor tol = (ResistorColor) comboTolerancia.getSelectedItem();

        double ohms = (b1.getDigit() * 10 + b2.getDigit()) * mul.getMultiplier();
        String valor = formatearOhms(ohms);
        String tolerancia = tol.getTolerance() > 0 ? " ±" + tol.getTolerance() + "%" : "";

        labelResultado.setText(valor + tolerancia);
        panelResistor.setBandas(List.of(b1, b2, mul, tol));
    }

    // Convierte ohms a Ω, kΩ o MΩ
    private String formatearOhms(double ohms) {
        if (ohms >= 1_000_000) return limpiar(ohms / 1_000_000) + " MΩ";
        if (ohms >= 1_000)     return limpiar(ohms / 1_000)     + " kΩ";
        return limpiar(ohms) + " Ω";
    }

    // Quita decimales si no hacen falta
    private String limpiar(double v) {
        if (v == Math.floor(v)) return String.valueOf((long) v);
        return String.valueOf(v);
    }


    private static class PanelResistor extends JPanel {

        private List<ResistorColor> bandas = List.of();

        public PanelResistor() {
            setPreferredSize(new Dimension(340, 120));
            setBackground(new Color(235, 235, 235));
            setBorder(BorderFactory.createTitledBorder("Resistencia"));
        }

        public void setBandas(List<ResistorColor> bandas) {
            this.bandas = bandas;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // cuerpo del resistor
            int bodyW = (int) (w * 0.58);
            int bodyH = 30;
            int bodyX = (w - bodyW) / 2;
            int bodyY = (h - bodyH) / 2;

            // patas
            g2.setStroke(new BasicStroke(2.5f));
            g2.setColor(new Color(170, 170, 170));
            g2.drawLine(0, h / 2, bodyX, h / 2);
            g2.drawLine(bodyX + bodyW, h / 2, w, h / 2);

            // cuerpo
            g2.setColor(new Color(210, 190, 150));
            g2.fill(new RoundRectangle2D.Float(bodyX, bodyY, bodyW, bodyH, 12, 12));
            g2.setColor(new Color(140, 110, 70));
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(new RoundRectangle2D.Float(bodyX, bodyY, bodyW, bodyH, 12, 12));

            // bandas
            if (!bandas.isEmpty()) {
                int n       = bandas.size();
                int bandW   = 12;
                int margen  = 18;
                int spacing = (bodyW - 2 * margen - n * bandW) / (n - 1);

                for (int i = 0; i < n; i++) {
                    int x = bodyX + margen + i * (bandW + spacing);
                    g2.setColor(bandas.get(i).getColor());
                    g2.fillRect(x, bodyY, bandW, bodyH);
                    g2.setColor(new Color(0, 0, 0, 50));
                    g2.setStroke(new BasicStroke(0.8f));
                    g2.drawRect(x, bodyY, bandW, bodyH);
                }
            }
        }
    }
}