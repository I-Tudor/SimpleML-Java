package GUI;

import evaluation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.*;
import util.DataHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainGUIController {

    @FXML
    private Label fileLabel;
    @FXML
    private ComboBox<String> classifierDropdown;
    @FXML
    private Label kLabel;
    @FXML
    private TextField kInput;
    @FXML
    private Label learningRateLabel;
    @FXML
    private TextField learningRateInput;
    @FXML
    private Label epochsLabel;
    @FXML
    private TextField epochsInput;
    @FXML
    private Slider splitSlider;
    @FXML
    private Label resultLabel;
    @FXML
    private GridPane confusionMatrix;
    @FXML
    private Label tpLabel;
    @FXML
    private Label tnLabel;
    @FXML
    private Label fpLabel;
    @FXML
    private Label fnLabel;

    Model<Double, Integer> model;
    private File selectedFile;
    private String selectedClassifier;
    private double trainPercentage = 0.7;
    private int k = 15;
    private double learningRate = 0.01;
    private int epochs = 10000;

    @FXML
    public void initialize() {
        classifierDropdown.getItems().addAll("KNN", "Perceptron", "Logistic Regression");
    }

    @FXML
    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", "*.csv"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            selectedFile = file;
            fileLabel.setText("Selected file: " + file.getName());
        }
    }

    @FXML
    public void onClassifierSelect() {
        selectedClassifier = classifierDropdown.getValue();
        if ("KNN".equals(selectedClassifier)) {
            kLabel.setVisible(true);
            kInput.setVisible(true);
            learningRateLabel.setVisible(false);
            learningRateInput.setVisible(false);
            epochsLabel.setVisible(false);
            epochsInput.setVisible(false);
        } else if ("Perceptron".equals(selectedClassifier) || "Logistic Regression".equals(selectedClassifier)) {
            kLabel.setVisible(false);
            kInput.setVisible(false);
            learningRateLabel.setVisible(true);
            learningRateInput.setVisible(true);
            epochsLabel.setVisible(true);
            epochsInput.setVisible(true);
        }
    }

    @FXML
    public void train() {
        if (selectedFile == null) {
            resultLabel.setText("No file selected");
            return;
        }
        if (selectedClassifier == null) {
            resultLabel.setText("No classifier selected");
            return;
        }

        try {
            trainPercentage = splitSlider.getValue() / 100.0;
            k = Integer.parseInt(kInput.getText());
            learningRate = Double.parseDouble(learningRateInput.getText());
            epochs = Integer.parseInt(epochsInput.getText());

            String results = evaluate(false);
            resultLabel.setText(results);
            confusionMatrix.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultLabel.setText("Error occurred");
        }
    }

    @FXML
    public void save(){
        if(model == null) {
            System.out.println("No model selected");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Model");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Binary Files", "*.bin"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try{
                model.saveModel(file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    @FXML
    public void load(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Model");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Binary Files", "*.bin"));
        File file = fileChooser.showOpenDialog(new Stage());

        if(file != null) {
            try{
                model = Model.loadModel(file.getAbsolutePath());
                String results = evaluate(true);
                resultLabel.setText(results);
                confusionMatrix.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String evaluate(boolean loadedModel) {
        try {
            List<Instance<Double, Integer>> data = DataHandler.loadData(selectedFile.getAbsolutePath());
            Map<String, List<Instance<Double, Integer>>> split = DataHandler.splitData(data, trainPercentage);
            List<Instance<Double, Integer>> trainData = split.get("train").stream().toList();
            List<Instance<Double, Integer>> testData = split.get("test").stream().toList();
            if(!loadedModel) {
                switch (selectedClassifier) {
                    case "KNN": model = new KNN<>(k); break;
                    case "Perceptron": model = new Perceptron<>(learningRate, epochs); break;
                    case "Logistic Regression": model = new LogisticRegression<>(learningRate, epochs); break;
                    default: return "Unknown classifier";
                }
                model.train(trainData);
            }
            List<Integer> predictions = model.test(testData);
            EvaluationMeasure<Double, Integer> accuracy = new Accuracy<>();
            EvaluationMeasure<Double, Integer> precision = new Precision<>();
            Recall<Double,Integer> recall = new Recall<>();
            F1Score<Double,Integer> f1Score = new F1Score<>();
            EvaluationMeasure<Double, Integer> truePositives = new TruePositives<>();
            EvaluationMeasure<Double, Integer> trueNegatives = new TrueNegatives<>();
            EvaluationMeasure<Double, Integer> falsePositives = new FalsePositives<>();
            EvaluationMeasure<Double, Integer> falseNegatives = new FalseNegatives<>();

            double acc = accuracy.evaluate(testData, predictions) * 100;
            acc = Math.round(acc * 100.0) / 100.0;
            double prec = precision.evaluate(testData, predictions) * 100;
            prec = Math.round(prec * 100.0) / 100.0;
            double rec = recall.evaluate(testData, predictions) * 100;
            rec = Math.round(rec * 100.0) / 100.0;
            double f1 = f1Score.evaluate(testData, predictions) * 100;
            f1 = Math.round(f1 * 100.0) / 100.0;

            double tp = truePositives.evaluate(testData, predictions);
            double tn = trueNegatives.evaluate(testData, predictions);
            double fp = falsePositives.evaluate(testData, predictions);
            double fn = falseNegatives.evaluate(testData, predictions);
            tpLabel.setText(Double.toString(tp));
            tnLabel.setText(Double.toString(tn));
            fpLabel.setText(Double.toString(fp));
            fnLabel.setText(Double.toString(fn));

            return "Accuracy: " + acc + "%, Precision: " + prec + "%, Recall: " + rec + "%, F1: " + f1 + "%";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
