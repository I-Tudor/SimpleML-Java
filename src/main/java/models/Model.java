package models;

import java.io.*;
import java.util.List;

public interface Model<F, L> {
    void train(List<Instance<F, L>> instances);
    List<L> test(List<Instance<F, L>> instances);

    default void saveModel(String filename) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }
    static <F, L> Model<F,L> loadModel(String filename) throws IOException, ClassNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Model<F, L> model = (Model<F, L>) ois.readObject();
            return model;
        }
    }
}
