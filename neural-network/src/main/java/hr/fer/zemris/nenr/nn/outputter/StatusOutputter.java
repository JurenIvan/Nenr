package hr.fer.zemris.nenr.nn.outputter;

import hr.fer.zemris.nenr.gui.screens.ParametersScreen;

import java.util.ArrayList;
import java.util.List;

public class StatusOutputter implements Outputter {

    private final Runnable runnable;
    private final List<String> data = new ArrayList<>();

    public StatusOutputter(ParametersScreen parametersScreen) {
        runnable = () -> {
            parametersScreen.pushTrainingData(data);
            data.clear();
        };
    }

    @Override
    public void output(int iteration, double err) {
        if (iteration % 1000 == 0)
            data.add("iter: " + iteration + " err:" + err);
        notifyScreen();
    }

    private void notifyScreen() {
        new Thread(runnable).start();
    }
}
