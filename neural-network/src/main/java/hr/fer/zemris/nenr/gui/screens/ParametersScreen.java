package hr.fer.zemris.nenr.gui.screens;

import hr.fer.zemris.nenr.PairDouble;
import hr.fer.zemris.nenr.gui.Model;
import hr.fer.zemris.nenr.gui.reducer.Reducer;
import hr.fer.zemris.nenr.nn.NeuralNetwork;
import hr.fer.zemris.nenr.nn.outputter.StatusOutputter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ParametersScreen extends JPanel {

    private final JComboBox<String> trainMode;
    private final JTextArea networkStructure;
    private final JTextArea learninRate;
    private final JTextArea iterationLimit;
    private final JTextArea eps;
    private final JTextArea trainingStatus;
    private final JTextArea samplingNumber;
    private final JButton trainButton;
    private final Model model;
    private final Reducer<PairDouble> reducer;
    private NeuralNetwork neuralNetwork;

    public ParametersScreen(Model model, Reducer<PairDouble> reducer) {
        this.model = model;
        this.reducer = reducer;

        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        String[] options = {"ONLINE", "MINI_BATCH", "BATCH"};
        trainMode = new JComboBox<>(options);
        networkStructure = new JTextArea("2x3x5", 1, 10);
        learninRate = new JTextArea("0.33", 1, 10);
        iterationLimit = new JTextArea("10000", 1, 10);
        eps = new JTextArea("0.001", 1, 10);
        trainingStatus = new JTextArea("Training status");
        trainButton = new JButton("Train");
        samplingNumber = new JTextArea("20", 1, 10);

        initGui();
    }

    private void initGui() {
        setLayout(new BorderLayout());

        add(trainButton, BorderLayout.PAGE_END);
        trainButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reducer.setSampleCount(Integer.parseInt(samplingNumber.getText()));
                trainingStatus.setText("Training:");

                neuralNetwork = new NeuralNetwork(
                        Double.parseDouble(learninRate.getText()),
                        Double.parseDouble(eps.getText()),
                        Integer.parseInt(iterationLimit.getText()),
                        new StatusOutputter(ParametersScreen.this),
                        parseStructure(networkStructure.getText()));

                neuralNetwork.fit(model.getSamples(), NeuralNetwork.TrainMode.valueOf((String) trainMode.getSelectedItem()));
            }
        });

        GridLayout layout = new GridLayout(7, 2, 20, 20);
        JPanel centerPanel = new JPanel(layout);
        int counter = 0;

        centerPanel.add(new JLabel("Training mode"), counter++);
        centerPanel.add(trainMode, counter++);

        centerPanel.add(new JLabel("Network structure of hidden layers (eg. \"2x3x5\")"), counter++);
        centerPanel.add(networkStructure, counter++);

        centerPanel.add(new JLabel("Learning rate (eg. \"0.33\""), counter++);
        centerPanel.add(learninRate, counter++);

        centerPanel.add(new JLabel("Iteration limit (eg. \"10000\""), counter++);
        centerPanel.add(iterationLimit, counter++);

        centerPanel.add(new JLabel("Maximal Acceptable error (eg. \"0.001\""), counter++);
        centerPanel.add(eps, counter++);

        centerPanel.add(new JLabel("Sampling number for picture (eg. \"20\""), counter++);
        centerPanel.add(samplingNumber, counter++);

        centerPanel.add(new JScrollPane(trainingStatus), counter++);
        centerPanel.add(new JPanel(), counter);
        trainingStatus.setEnabled(false);

        add(centerPanel, BorderLayout.CENTER);
    }

    public void pushTrainingData(List<String> data) {
        trainingStatus.append("\n" + String.join("\n", data));
    }


    private int[] parseStructure(String text) {
        text = samplingNumber.getText() + "x" + text + "x" + model.getKeys().size();
        var splitted = text.trim().split("x");
        int[] result = new int[splitted.length];
        for (int i = 0; i < splitted.length; i++) {
            result[i] = Integer.parseInt(splitted[i]);
        }
        return result;
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }
}
