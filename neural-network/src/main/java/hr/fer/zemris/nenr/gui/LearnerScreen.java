package hr.fer.zemris.nenr.gui;

import hr.fer.zemris.nenr.PairDouble;
import hr.fer.zemris.nenr.reducer.Reducer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class LearnerScreen extends JPanel {

    private final Reducer<PairDouble> reducer;
    private final Model model;
    private final List<PairDouble> points = new ArrayList<>();

    public LearnerScreen(Reducer<PairDouble> reducer, Model model) {
        super();
        this.reducer = reducer;
        this.model = model;
        setFocusable(true);

        addKeyListener();
        addMouseListeners();

        initGUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        getGraphics().clearRect(0, 0, getWidth(), getHeight());
        if (points.size() == 0) return;

        PairDouble previous = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            PairDouble current = points.get(i);
            g.drawLine((int) previous.getX(), (int) previous.getY(), (int) current.getX(), (int) current.getY());
            previous = current;
        }
    }

    private void addMouseListeners() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                points.add(new PairDouble(e.getX(), e.getY()));
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                points.clear();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                model.addData(points);
            }
        });
    }

    private void addKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                model.setKey(e.getKeyChar());
            }
        });
    }

    private void initGUI() {
        drawLine();
    }

    private void drawLine() {

    }

}
