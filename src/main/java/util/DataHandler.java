package util;

import models.Instance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DataHandler {
    public static List<Instance<Double, Integer>> loadData(String filePath) throws IOException {
        List<Instance<Double, Integer>> instances = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        for(int i = 1; i < lines.size(); i++){
            String line = lines.get(i);
            String[] tokens = line.split(",");

            List<Double> features = new ArrayList<>();
            for(int j = 0; j < tokens.length - 1; j++){
                features.add(Double.parseDouble(tokens[j]));
            }

            Integer label = Integer.parseInt(tokens[tokens.length - 1]);
            instances.add(new Instance<>(features, label));
        }
        return instances;
    }

    public static <F, L> Map<String, List<Instance<F, L>>> splitData(
            List<Instance<F, L>> data, double trainPercentage) {
        Collections.shuffle(data);
        int trainSize = (int) (data.size() * trainPercentage);
        Map<String, List<Instance<F, L>>> split = new HashMap<>();
        split.put("train", data.subList(0, trainSize));
        split.put("test", data.subList(trainSize, data.size()));
        return split;
    }
}
