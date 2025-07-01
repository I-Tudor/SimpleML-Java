package evaluation;

import models.Instance;

import java.util.List;

public class Precision<F, L> implements EvaluationMeasure<F, L> {
    @Override
    public double evaluate(List<Instance<F, L>> instances, List<L> predictions) {
        int truePositives = 0;
        int falsePositives = 0;
        for(int i = 0; i < instances.size(); i++) {
            if(predictions.get(i).equals(1)){
                if(instances.get(i).getOutput().equals(1)){
                    truePositives++;
                }
                else{
                    falsePositives++;
                }
            }
        }
        return (double)truePositives / (double)(truePositives + falsePositives);
    }
}
