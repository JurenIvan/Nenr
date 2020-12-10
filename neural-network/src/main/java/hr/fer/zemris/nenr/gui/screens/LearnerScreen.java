package hr.fer.zemris.nenr.gui.screens;

import hr.fer.zemris.nenr.PairDouble;
import hr.fer.zemris.nenr.gui.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class LearnerScreen extends JPanel {

    private final Model model;
    private final List<PairDouble> points = new ArrayList<>();
    private boolean specialState = false;

    public LearnerScreen(Model model) {
        super();
        this.model = model;

        setFocusable(true);
        addKeyListener();
        addMouseListeners();

        initGUI();
    }

    private void initGUI() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        getGraphics().clearRect(0, 0, getWidth(), getHeight());
        if (specialState) {
            model.getData().forEach((key, value) -> value.forEach(e -> drawPoints(g, e)));
            return;
        }
        drawPoints(g, points);
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
                if (specialState) specialState = false;
                points.clear();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                model.addData(new ArrayList<>(points));
            }
        });
    }

    private void addKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'x') {
                    repaint();
                    showMessageDialog(LearnerScreen.this, getMessage(), "Entered Special state for viewing inserted file. Press x to exit", INFORMATION_MESSAGE);
                    specialState = !specialState;
                } else {
                    System.err.println("Key set to :" + e.getKeyChar());
                    model.setKey(e.getKeyChar());
                }
            }
        });
    }

    private String getMessage() {
        StringBuilder sb = new StringBuilder();
        model.getData().forEach((k, v) -> sb.append(k).append(" : ").append(v.size()).append("\n"));
        return sb.toString();
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
