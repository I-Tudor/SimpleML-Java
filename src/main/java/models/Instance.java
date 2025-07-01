package models;

import java.io.Serializable;
import java.util.List;

public class Instance<F, L> implements Serializable {
    private List<F> input;
    private L output;

    public Instance(List<F> input, L output) {
        this.input = input;
        this.output = output;
    }
    public List<F> getInput() {
        return input;
    }
    public L getOutput() {
        return output;
    }
}
