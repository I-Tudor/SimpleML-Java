package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LogisticRegression<F,L> implements Model<F,L>, Serializable {
    private double[] weights;
    private double bias;
    private double learningRate;
    private int maxEpochs;

    public LogisticRegression(double learningRate, int maxEpochs) {
        this.learningRate = learningRate;
        this.maxEpochs = maxEpochs;
    }

    @Override
    public void train(List<Instance<F, L>> instances) {
        normalize(instances);
        int featureCount = instances.get(0).getInput().size();
        weights = new double[featureCount];
        bias = 0.1;
        double previousLoss = 0;

        for(int epoch = 0; epoch < maxEpochs; epoch++) {
            double totalLoss = 0;

            for(Instance<F, L> instance : instances) {
                List<F> inputs = instance.getInput();
                int trueLabel = (Integer) instance.getOutput();

                double weightedSum = bias;
                for(int i = 0; i < featureCount; i++) {
                    weightedSum += weights[i] * (double) inputs.get(i);
                }
                double predictedLabel = sigmoid(weightedSum);

                totalLoss += -trueLabel * Math.log(predictedLabel + 1e-9) - (1 - trueLabel) * Math.log(1 - predictedLabel + 1e-9);

                double error = predictedLabel - trueLabel;
                for(int i = 0; i < featureCount; i++) {
                    weights[i] -= error * (double) inputs.get(i) * learningRate;
                }
                bias -= learningRate * error;
            }

            if(Math.abs(previousLoss - totalLoss) < 1e-5) {
                break;
            }

            if(epoch > maxEpochs/4 && previousLoss < totalLoss) {
                learningRate *= 0.5;
            }
            previousLoss = totalLoss;
        }
    }

    @Override
    public List<L> test(List<Instance<F, L>> instances) {
        normalize(instances);
        List<L> predictedLabels = new ArrayList<>();

        for(Instance<F, L> instance : instances) {
            List<F> inputs = instance.getInput();
            double weightedSum = bias;
            for(int i = 0; i < inputs.size(); i++) {
                weightedSum += weights[i] * (double) inputs.get(i);
            }

            double predictedLabel = sigmoid(weightedSum);
            Integer predictedLabelInt = 0;
            if(predictedLabel >= 0.5) {
                predictedLabelInt = 1;
            }
            predictedLabels.add((L) predictedLabelInt);
        }
        return predictedLabels;
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
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
