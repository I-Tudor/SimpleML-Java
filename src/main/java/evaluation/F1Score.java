package evaluation;

import models.Instance;

import java.util.List;

public class F1Score<F, L> implements EvaluationMeasure<F, L> {
    @Override
    public double evaluate(List<Instance<F, L>> instances, List<L> predictions) {
        Precision precision = new Precision();
        double prec = precision.evaluate(instances, predictions);
        Recall recall = new Recall();
        double rec = recall.evaluate(instances, predictions);
        return 2 * (prec * rec) / (prec + rec);
    }
}
