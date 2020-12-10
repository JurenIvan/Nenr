package hr.fer.zemris.nenr.nn.outputter;

import hr.fer.zemris.nenr.gui.screens.ParametersScreen;

import java.util.ArrayList;
import java.util.List;

public class StatusOutputter implements Outputter {

    private final Runnable runnable;
    private final List<String> data = new ArrayList<>();
    private final ParametersScreen parametersScreen;

    public StatusOutputter(ParametersScreen parametersScreen) {
        this.parametersScreen = parametersScreen;
        runnable = () -> {
            parametersScreen.pushTrainingData(data);
            data.clear();
        };
    }

    @Override
    public void output(String message) {
        data.add(message);
        parametersScreen.pushTrainingData(data);
        data.clear();
//        notifyScreen();
    }

    private void notifyScreen() {
        new Thread(runnable).start();
    }
}
