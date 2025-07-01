package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Perceptron<F, L> implements Model<F, L>, Serializable {
    private double[] weights;
    private double bias;
    private double learningRate;
    private int maxEpochs;

    public Perceptron(double learningRate, int maxEpochs) {
        this.learningRate = learningRate;
        this.maxEpochs = maxEpochs;
    }


    @Override
    public void train(List<Instance<F, L>> instances) {
        normalize(instances);
        int featureCount = instances.get(0).getInput().size();
        weights = new double[featureCount];
        bias = 0.1;
        for(int epoch = 0; epoch < maxEpochs; epoch++) {
            boolean errors = false;

            for(Instance<F, L> instance : instances) {
                List<F> inputs = instance.getInput();
                int trueLabel = -1;
                if(instance.getOutput().equals(1)) {
                    trueLabel = 1;
                }

                double weightedSum = bias;
                for(int i=0; i<featureCount; i++) {
                    weightedSum += weights[i] * (double)inputs.get(i);
                }
                int predictedLabel = -1;
                if(weightedSum >= 0) {
                    predictedLabel = 1;
                }

                if(predictedLabel != trueLabel) {
                    errors = true;

                    double adjustment = learningRate * (trueLabel - predictedLabel);
                    for(int i=0; i<featureCount; i++) {
                        weights[i] += adjustment * (double) inputs.get(i);
                    }
                    bias += adjustment;
                }
            }
            if(!errors) {
                break;
            }
            learningRate *= 0.99;
        }
    }

    @Override
    public List<L> test(List<Instance<F, L>> instances) {
        normalize(instances);
        List<L> predictedLabels = new ArrayList<>();
        for(Instance<F, L> instance : instances) {
            List<F> inputs = instance.getInput();
            double weightedSum = bias;
            for(int i = 0; i<inputs.size(); i++) {
                weightedSum += weights[i] * (double)inputs.get(i);
            }

            Integer predictedLabel = 0;
            if(weightedSum >= 0) {
                predictedLabel = 1;
            }
            predictedLabels.add((L) predictedLabel);
        }
        return predictedLabels;
    }

    private void normalize(List<Instance<F, L>> instances) {
        int featureCount = instances.get(0).getInput().size();
        double[] featureMin = new double[featureCount];
        double[] featureMax = new double[featureCount];

        for(int i=0; i<featureCount; i++) {
            featureMin[i] = Double.MAX_VALUE;
            featureMax[i] = Double.MIN_VALUE;
        }

        for(Instance<F, L> instance : instances) {
            List<F> inputs = instance.getInput();
            for(int i=0; i<inputs.size(); i++) {
                double value = (double)inputs.get(i);
                featureMin[i] = Math.min(featureMin[i], value);
                featureMax[i] = Math.max(featureMax[i], value);
            }
        }

        for(Instance<F, L> instance : instances) {
            List<F> inputs = instance.getInput();
            for(int i=0; i<inputs.size(); i++) {
                double value = (double)inputs.get(i);
                double normalizedValue = (value - featureMin[i]) / (featureMax[i] - featureMin[i]);
                inputs.set(i, (F) Double.valueOf(normalizedValue));
            }
        }
    }
}
