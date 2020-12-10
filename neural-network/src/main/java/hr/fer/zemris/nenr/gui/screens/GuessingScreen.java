package hr.fer.zemris.nenr.gui.screens;

import hr.fer.zemris.nenr.PairDouble;
import hr.fer.zemris.nenr.gui.Model;
import hr.fer.zemris.nenr.gui.reducer.Reducer;
import hr.fer.zemris.nenr.nn.Sample;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class GuessingScreen extends JPanel {

    private final List<PairDouble> points = new ArrayList<>();
    private final ParametersScreen parametersScreen;
    private final Reducer<PairDouble> reducer;
    private final Model model;

    private final JPanel canvas;
    private final JPanel output;
    private final CategoryChart chart;

    public GuessingScreen(ParametersScreen parametersScreen, Model model, Reducer<PairDouble> reducer) {
        this.parametersScreen = parametersScreen;
        this.reducer = reducer;
        this.model = model;

        this.canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                getGraphics().clearRect(0, 0, getWidth(), getHeight());
                drawPoints(g, points);
            }
        };
        this.output = new JPanel();

        canvas.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setFocusable(true);
        addMouseListeners();
        setLayout(new GridLayout(1, 2));

        add(canvas, 0);
        add(output, 1);


//        chart = new XYChartBuilder().width(800).height(600).title("Task 1").xAxisTitle("h").yAxisTitle("evaluation count").build();
        chart = new CategoryChartBuilder().height(500).width(500).title("title").build();
        chart.addSeries("as", new double[]{1, 2, 3}, new double[]{1, 2, 3});
        output.add(new XChartPanel<>(chart));

    }

    private void addMouseListeners() {
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                points.add(new PairDouble(e.getX(), e.getY()));
                double[] reduced = reducer.reduce(points);

                reduced = parametersScreen.getNeuralNetwork().predict(new Sample(reduced, null));
                List<Double> result = new ArrayList<>();
                for (double v : reduced) {
                    result.add(v);
                }
                chart.removeSeries("as");
                chart.addSeries("as", model.getKeys(), result);
                output.repaint();
                canvas.repaint();
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                points.clear();
            }
        });
    }

    private void drawPoints(Graphics g, List<PairDouble> points) {
        if (points.size() == 0) return;

        PairDouble previous = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            PairDouble current = points.get(i);
            g.drawLine((int) previous.getX(), (int) previous.getY(), (int) current.getX(), (int) current.getY());
            previous = current;
        }
    }
}
