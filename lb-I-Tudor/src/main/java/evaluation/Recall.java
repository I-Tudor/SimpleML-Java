package evaluation;

import models.Instance;

import java.util.List;

public class Recall<F, L> implements EvaluationMeasure<F, L> {
    @Override
    public double evaluate(List<Instance<F, L>> instances, List<L> predictions) {
        TruePositives truePositives = new TruePositives();
        double tp = truePositives.evaluate(instances, predictions);
        FalseNegatives falseNegatives = new FalseNegatives();
        double fn = falseNegatives.evaluate(instances, predictions);
        return tp / (tp + fn);
    }
}
