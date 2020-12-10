package hr.fer.zemris.nenr.nn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NeuralNetworkTest  {

    @Test
    public void testFit_online() {
        var nn = new NeuralNetwork(0.5, 0.001, 100000, null, 1, 6, 1);

        List<Sample> samples = List.of(
                new Sample(new double[]{-1.0}, new double[]{1.00}),
                new Sample(new double[]{-0.8}, new double[]{0.64}),
                new Sample(new double[]{-0.6}, new double[]{0.36}),
                new Sample(new double[]{-0.4}, new double[]{0.16}),
                new Sample(new double[]{-0.2}, new double[]{0.04}),
                new Sample(new double[]{0.0}, new double[]{0.00}),
                new Sample(new double[]{0.2}, new double[]{0.04}),
                new Sample(new double[]{0.4}, new double[]{0.16}),
                new Sample(new double[]{0.6}, new double[]{0.36}),
                new Sample(new double[]{0.8}, new double[]{0.64}),
                new Sample(new double[]{1.0}, new double[]{1.00}));

        nn.fit(samples, NeuralNetwork.TrainMode.ONLINE);
        for (double i = -1; i < 1; i=i+0.2) {
            var result1 = nn.predict(new Sample(new double[]{i}, null));
            System.out.println(result1[0]);
            Assertions.assertEquals(i * i, result1[0], 0.2);
        }
    }

    @Test
    public void testFit_batch() {
        var nn = new NeuralNetwork(0.5, 0.001, 10000, null, 1, 6, 1);

        List<Sample> samples = List.of(
                new Sample(new double[]{-1.0}, new double[]{1.00}),
                new Sample(new double[]{-0.8}, new double[]{0.64}),
                new Sample(new double[]{-0.6}, new double[]{0.36}),
                new Sample(new double[]{-0.4}, new double[]{0.16}),
                new Sample(new double[]{-0.2}, new double[]{0.04}),
                new Sample(new double[]{0.0}, new double[]{0.00}),
                new Sample(new double[]{0.2}, new double[]{0.04}),
                new Sample(new double[]{0.4}, new double[]{0.16}),
                new Sample(new double[]{0.6}, new double[]{0.36}),
                new Sample(new double[]{0.8}, new double[]{0.64}),
                new Sample(new double[]{1.0}, new double[]{1.00}));

        nn.fit(samples, NeuralNetwork.TrainMode.BATCH);
        for (double i = -1; i < 1; i=i+0.2) {
            var result1 = nn.predict(new Sample(new double[]{i}, null));
            Assertions.assertEquals(i*i,result1[0],0.2);
        }
    }

    @Test
    public void testFit() {
        var nn = new NeuralNetwork(0.5, 0.001, 10000, null, 1, 6, 1);

        List<Sample> samples = List.of(
                new Sample(new double[]{-1.0}, new double[]{1.00}),
                new Sample(new double[]{-0.8}, new double[]{0.64}),
                new Sample(new double[]{-0.6}, new double[]{0.36}),
                new Sample(new double[]{-0.4}, new double[]{0.16}),
                new Sample(new double[]{-0.2}, new double[]{0.04}),
                new Sample(new double[]{0.0}, new double[]{0.00}),
                new Sample(new double[]{0.2}, new double[]{0.04}),
                new Sample(new double[]{0.4}, new double[]{0.16}),
                new Sample(new double[]{0.6}, new double[]{0.36}),
                new Sample(new double[]{0.8}, new double[]{0.64}),
                new Sample(new double[]{1.0}, new double[]{1.00}));

        nn.fit(samples, NeuralNetwork.TrainMode.MINI_BATCH);
        for (double i = -1; i < 1; i=i+0.2) {
            var result1 = nn.predict(new Sample(new double[]{i}, null));
            Assertions.assertEquals(i*i,result1[0],0.2);
        }
    }
}
