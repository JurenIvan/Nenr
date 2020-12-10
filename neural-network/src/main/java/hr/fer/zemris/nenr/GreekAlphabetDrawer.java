package hr.fer.zemris.nenr;

import hr.fer.zemris.nenr.gui.Model;
import hr.fer.zemris.nenr.gui.actions.ClearDocument;
import hr.fer.zemris.nenr.gui.actions.OpenDocument;
import hr.fer.zemris.nenr.gui.actions.SaveAsDocument;
import hr.fer.zemris.nenr.gui.reducer.PixelReducer;
import hr.fer.zemris.nenr.gui.reducer.Reducer;
import hr.fer.zemris.nenr.gui.screens.GuessingScreen;
import hr.fer.zemris.nenr.gui.screens.LearnerScreen;
import hr.fer.zemris.nenr.gui.screens.ParametersScreen;

import javax.swing.*;
import java.awt.*;

public class GreekAlphabetDrawer extends JFrame {

    private static final int DEFAULT_HEIGHT = 800;
    private static final int DEFAULT_WIDTH = 1500;
    private static final int M = 20;

    private final Reducer<PairDouble> reducer = new PixelReducer(M);
    private final Model model = new Model('a', reducer);

    private final GuessingScreen guessingScreen;
    private final LearnerScreen learnerScreen;
    private final ParametersScreen parametersScreen;

    public GreekAlphabetDrawer() {
        Reducer<PairDouble> reducer = new PixelReducer(M);

        learnerScreen = new LearnerScreen(model);
        parametersScreen = new ParametersScreen(model);
        guessingScreen = new GuessingScreen(parametersScreen, model, reducer);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setLocationRelativeTo(null);

        initGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GreekAlphabetDrawer().setVisible(true));
    }

    private void initGUI() {
        printHowToUse();
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        initializeScreens();
        createMenus();
    }

    private void initializeScreens() {
        JTabbedPane screens = new JTabbedPane();
        screens.add("Ingest", learnerScreen);
        screens.add("Parameters", parametersScreen);
        screens.add("Guess", guessingScreen);
        getContentPane().add(screens);
    }

    private void createMenus() {
        JMenuBar mb = new JMenuBar();
        setJMenuBar(mb);
        JMenu file = new JMenu("file");
        mb.add(file);

        file.add(new JMenuItem(new OpenDocument("Open document", this, model)));
        file.add(new JMenuItem(new SaveAsDocument("Save as document", this, model)));
        file.add(new JMenuItem(new ClearDocument("Clear document", model)));
    }

    private void printHowToUse() {
        System.err.println("sign    letter    class");
        System.err.println("alpha  => a =>  1,0,0,0,0");
        System.err.println("beta   => b =>  0,1,0,0,0");
        System.err.println("gamma  => c =>  0,0,1,0,0");
        System.err.println("delta  => d =>  0,0,0,1,0");
        System.err.println("eta    => e =>  0,0,0,0,1");
    }
}
