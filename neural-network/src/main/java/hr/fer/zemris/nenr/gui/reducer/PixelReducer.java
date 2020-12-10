package hr.fer.zemris.nenr.gui.reducer;

import hr.fer.zemris.nenr.PairDouble;

import java.util.List;

import static java.lang.Math.*;

public class PixelReducer implements Reducer<PairDouble> {

    private final int representativePointsCount;

    public PixelReducer(int representativePointsCount) {
        this.representativePointsCount = representativePointsCount;
    }

    @Override
    public double[] reduce(List<PairDouble> points) {
        double[] reduced = new double[representativePointsCount * 2];
        int numberOfPoints = points.size();

        //calculate averages
        double xAverage = 0;
        double yAverage = 0;
        for (int i = 0; i < numberOfPoints; i++) {
            var point = points.get(i);
            xAverage += point.getX();
            yAverage += point.getY();
        }
        xAverage /= numberOfPoints;
        yAverage /= numberOfPoints;

        //move all and find maximal absolute value
        double m = -1;
        for (int i = 0; i < numberOfPoints; i++) {
            var point = points.get(i);
            point.setX(point.getX() - xAverage);
            point.setY(point.getY() - yAverage);

            m = max(abs(point.getX()), max(abs(point.getY()), m));
        }

        //scale to -1,1 interval
        for (int i = 0; i < numberOfPoints; i++) {
            var point = points.get(i);
            point.setX(point.getX() / m);
            point.setY(point.getY() / m);
        }

        //calculate length of drawing
        double d = 0;
        PairDouble previous = points.get(0);
        for (int i = 1; i < numberOfPoints; i++) {
            PairDouble current = points.get(i);
            d += distance(previous, current);
            previous = current;
        }

        //add points
        int counter = 0;
        double distance = 0;
        for (int k = 0; k < representativePointsCount; k++) {
            var targetDistance = k * d / (representativePointsCount - 1);

            previous = points.get(counter);
            while (distance < targetDistance && counter + 1 < points.size()) {
                PairDouble current = points.get(++counter);
                distance += distance(previous, current);
                previous = current;
            }
            reduced[2 * k] = previous.getX();
            reduced[2 * k + 1] = previous.getY();
        }

        return reduced;
    }

    private double distance(PairDouble previous, PairDouble current) {
        return sqrt(pow(previous.getX() - current.getX(), 2) + pow(previous.getY() - current.getY(), 2));
    }
}
