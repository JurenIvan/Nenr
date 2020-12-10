package hr.fer.zemris.nenr;

import hr.fer.zemris.nenr.nn.NeuralNetwork;
import hr.fer.zemris.nenr.nn.Sample;

import java.util.List;

public class NeuralNetworkRunner {

    public static void main(String[] args) {
        int[] layers = new int[]{2, 3, 2};
        var nn = new NeuralNetwork(1, 0.001, 100000, layers);

        List<Sample> samples = List.of(
                new Sample(new double[]{1, 0.1}, new double[]{1,0})
//                ,
//                new Sample(new double[]{0.6, 0.1}, new double[]{1,0}),
//                new Sample(new double[]{0.3, 0.1}, new double[]{1,0}),
//                new Sample(new double[]{0.1, 1}, new double[]{0,1}),
//                new Sample(new double[]{0.2, 1}, new double[]{0,1}),
//                new Sample(new double[]{0.4, 1}, new double[]{0,1})
        );

        nn.fit(samples, NeuralNetwork.TrainMode.ONLINE);
        var result1 = nn.predict( new Sample(new double[]{1, 0.1},null));
        var result2 = nn.predict(new Sample(new double[]{0.1, 1}, null));
        var result3 = nn.predict(new Sample(new double[]{0.5, 1}, null));

        System.out.println(result1[0]+ " " + result1[1]);
        System.out.println(result2[0]+ " " + result2[1]);
        System.out.println(result3[0]+ " " + result3[1]);
    }
}
