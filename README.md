# Machine Learning Classifier Framework 

This project is a straightforward machine learning framework built in Java. It provides a platform for training and evaluating several common classification models. The framework features a Graphical User Interface (GUI) built with JavaFX, making it easy to experiment with different algorithms and datasets.

---

## Features 

* **Multiple Classification Models**: The framework includes implementations of three popular classification algorithms:
    * **K-Nearest Neighbors (KNN)**: A simple, instance-based learning algorithm.
    * **Perceptron**: A single-layer neural network for binary classification.
    * **Logistic Regression**: A statistical model for predicting binary outcomes.
* **Comprehensive Model Evaluation**: You can assess the performance of the trained models using a variety of standard metrics:
    * **Accuracy**: The proportion of correctly classified instances.
    * **Precision**: The ability of the classifier not to label as positive a sample that is negative.
    * **Recall (Sensitivity)**: The ability of the classifier to find all the positive samples.
    * **F1-Score**: The harmonic mean of precision and recall.
    * **Confusion Matrix**: A detailed breakdown of True Positives, True Negatives, False Positives, and False Negatives.
* **Interactive GUI**: The JavaFX-based interface allows you to:
    * Load your dataset from a **CSV file**.
    * Choose a classifier from a dropdown menu.
    * Adjust **hyperparameters** such as the value of `k` for KNN, or the `learning rate` and number of `epochs` for Perceptron and Logistic Regression.
    * Use a slider to easily set the **train-test split** ratio.
    * **Train** the model and view the evaluation results in real-time.
    * **Save** your trained models for later use.
    * **Load** pre-trained models to evaluate them on new data.
* **Data Handling Utilities**:
    * Efficiently loads data from CSV files.
    * Automatically shuffles and splits the dataset.
    * Includes feature normalization for Perceptron and Logistic Regression to enhance model performance.