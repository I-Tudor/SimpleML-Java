package evaluation;

import models.Instance;

import java.util.List;

public class TrueNegatives<F,L> implements EvaluationMeasure<F,L>{
    @Override
    public double evaluate(List<Instance<F, L>> instances, List<L> predictions) {
        int tn = 0;

        for(int i = 0; i < instances.size(); i++) {
            L actual = instances.get(i).getOutput();
            L prediction = predictions.get(i);
            if(prediction.equals(actual)) {
                if(actual.equals(0)){
                    tn++;
                }
            }
        }
        return (double)tn;
    }
}
