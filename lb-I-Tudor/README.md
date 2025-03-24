# Additional Lab Activity

**Bonus (1p)**

Implement a very simple machine learning library allowing binary classification, in Java, according to the given UML diagram. The library will offer various algorithms for binary classification, allowing the user to choose any of them. All classifiers will provide training and testing capabilities and the library will also offer various evaluation measures to test the performance of the algorithms.

Requirements:
- Use the concepts and tools we studied this semester: object oriented design, interfaces, inheritance, polymorphism, generics, Java collections, exceptions, I/O Streams and serialisation, functional interfaces, Java 8 streams. For example: 
	- the data can be read from a file (e.g. CSV) or a database
	- the trained models can be serialised
	- interfaces with just one function (e.g. Evaluation Measure) can be functional interfaces

- Implement at least three simple classifiers. Some examples below:
	- K-Nearest Neighbors (KNN)
	- The Perceptron
	- Naive Bayes
	- Logistic Regression
	- Decision Tree
	
- The diagram contains two examples of evaluation measure. See some other examples below:
	- True Positives, False Positives, True Negatives, False Negatives
	- Recall
	- F1 Score
	
- Classifier selection (including setting the values for the corresponding hyperparameters) can be made via an external configuration file or at the beginning of the application.
 
- Test the algorithms using the Pima Indians Diabetes dataset (https://www.kaggle.com/datasets/uciml/pima-indians-diabetes-database). Use a training and a test set and report the obtained performances. Which is the best classifier for this dataset?

**Bonus (0.5p)**

Build a GUI using JavaFX for your machine learning framework. This should allow:
- choosing the input file
- choosing a classifier and values for its hyperparameters
- choosing the percentages for the train-test split
- training the classifier
- displaying test set results, including confusion matrix
