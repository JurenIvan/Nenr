package hr.fer.zemris.nenr.gui;

import hr.fer.zemris.nenr.nn.NeuralNetwork;

import javax.swing.*;

public class ParametersScreen extends JPanel {

    private final NeuralNetwork neuralNetwork;

    public ParametersScreen(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }
}
