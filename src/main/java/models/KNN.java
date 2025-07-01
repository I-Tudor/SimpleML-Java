package models;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class KNN<F extends Serializable, L extends Serializable> implements Model<F, L>, Serializable {
    private int k;
    private List<Instance<F, L>> trainingSet;

    public KNN(int k){
        this.k = k;
    }


    @Override
    public void train(List<Instance<F, L>> instances) {
        trainingSet = instances;
    }

    @Override
    public List<L> test(List<Instance<F, L>> instances) {
        List<L> predictions = new ArrayList<>();
        for (Instance<F, L> instance : instances) {
            L prediction = predict(instance);
            predictions.add(prediction);
        }
        return predictions;
    }

    private L predict(Instance<F, L> instance) {
        List<L> kNearestLabels = trainingSet.stream()
                .sorted(Comparator.comparingDouble(data -> distance(data.getInput(), instance.getInput())))
                .limit(k)
                .map(Instance::getOutput)
                .toList();
        L prediction = kNearestLabels.stream()
                .collect(Collectors.groupingBy(label -> label, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();
        return prediction;
    }

    private double distance(List<F> a, List<F> b) {
        double sum = 0.0;
        for(int i = 0; i < a.size(); i++) {
            double diff = (double)a.get(i) - (double)b.get(i);
            sum += Math.pow(diff, 2);
        }
        return Math.sqrt(sum);
    }

}
