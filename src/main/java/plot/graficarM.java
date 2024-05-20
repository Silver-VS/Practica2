package plot;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class graficarM extends JPanel {
    private double[][] data;

    public graficarM() {
        this.data = new double[0][0];
    }

    public void grafico(double[][] data) {
        this.data = data;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw axes
        g2.draw(new Line2D.Double(50, getHeight() - 50, getWidth() - 50, getHeight() - 50)); // X axis
        g2.draw(new Line2D.Double(50, 50, 50, getHeight() - 50)); // Y axis

        // Draw data
        if (data.length > 0) {
            double xScale = (getWidth() - 100) / (data[data.length - 1][0] - data[0][0]);
            double yMax = Double.MIN_VALUE;
            double yMin = Double.MAX_VALUE;

            for (double[] point : data) {
                if (point[1] > yMax) yMax = point[1];
                if (point[1] < yMin) yMin = point[1];
            }

            double yScale = (getHeight() - 100) / (yMax - yMin);

            // Draw X axis labels and ticks
            for (int i = 0; i <= 10; i++) {
                double xValue = data[0][0] + i * (data[data.length - 1][0] - data[0][0]) / 10;
                int x = 50 + (int) ((xValue - data[0][0]) * xScale);
                g2.draw(new Line2D.Double(x, getHeight() - 55, x, getHeight() - 45));
                g2.drawString(String.format("%.2f", xValue), x - 10, getHeight() - 30);
            }

            // Draw Y axis labels and ticks
            for (int i = 0; i <= 10; i++) {
                double yValue = yMin + i * (yMax - yMin) / 10;
                int y = getHeight() - 50 - (int) ((yValue - yMin) * yScale);
                g2.draw(new Line2D.Double(45, y, 55, y));
                g2.drawString(String.format("%.2f", yValue), 20, y + 5);
            }

            // Draw data points
            for (int i = 0; i < data.length - 1; i++) {
                double x1 = 50 + (data[i][0] - data[0][0]) * xScale;
                double y1 = getHeight() - 50 - (data[i][1] - yMin) * yScale;
                double x2 = 50 + (data[i + 1][0] - data[0][0]) * xScale;
                double y2 = getHeight() - 50 - (data[i + 1][1] - yMin) * yScale;

                g2.draw(new Line2D.Double(x1, y1, x2, y2));
            }
        }
    }
}
