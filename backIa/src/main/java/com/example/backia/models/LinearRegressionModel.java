package com.example.backia.models;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

public class LinearRegressionModel {

    public static double[] performLinearRegression(double[] xData, double[] yData) {
        WeightedObservedPoints obs = new WeightedObservedPoints();

        for (int i = 0; i < xData.length; i++) {
            obs.add(xData[i], yData[i]);
        }

        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(1);

        return fitter.fit(obs.toList());
    }
}
