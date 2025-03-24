package evaluation;

import models.Instance;

import java.util.List;

public class TruePositives<F, L> implements EvaluationMeasure<F, L> {
    @Override
    public double evaluate(List<Instance<F, L>> instances, List<L> predictions) {
        int tp = 0;

        for(int i = 0; i < instances.size(); i++) {
            L actual = instances.get(i).getOutput();
            L prediction = predictions.get(i);
            if(prediction.equals(actual)) {
                if(actual.equals(1)){
                    tp++;
                }
            }
        }
        return (double)tp;
    }
}
