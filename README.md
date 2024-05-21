# Portfolio project IDATG2003 - 2024

Project Name: ChaosGame

Group Members: Theodor Sjetnan Utvik, Sigurd Riseth

## Project description

[//]: # (TODO: Write a short description of your project/product here.)
ChaosGame is an application for generating and visualizing [fractals](https://en.wikipedia.org/wiki/Fractal) digitally.
The application allows users to create and run their own fractals, as well as run pre-defined fractals.
The application also includes the Mandelbrot set, which allows users to explore it and generate Julia-sets from it.

## Project structure

The project structure is displayed in the directory tree below.

```text
📦 ChaosGame
├─ src
│  ├─ main
│  │  ├─ java.no.ntnu.idatg2003
│  │  │  ├─ controller
│  │  │  │  ├─ FrontPageController.java
│  │  │  │  ├─ PresetGameController.java
│  │  │  │  └─ RunGameController.java
│  │  │  ├─ model
│  │  │  │  ├─ game.engine
│  │  │  │  │  ├─ ChaosCanvas.java
│  │  │  │  │  ├─ ChaosGame.java
│  │  │  │  │  ├─ ChaosGameDescription.java
│  │  │  │  │  ├─ ChaosGameDescriptionFactory.java
│  │  │  │  │  ├─ ChaosGameFileHandler.java
│  │  │  │  │  ├─ ChaosGameObserver.java
│  │  │  │  │  └─ ChaosGameSubject.java
│  │  │  │  ├─ math.datatypes
│  │  │  │  │  ├─ Complex.java
│  │  │  │  │  ├─ Matrix2x2.java
│  │  │  │  │  └─ Vector2D.java
│  │  │  │  └─ transformation
│  │  │  │     ├─ AffineTransform2D.java
│  │  │  │     ├─ JuliaTransform.java
│  │  │  │     └─ Transform2D.java
│  │  │  ├─ view
│  │  │  │  ├─ ui
│  │  │  │  ├─ ChaosGameApp.java
│  │  │  │  ├─ FrontPage.java
│  │  │  │  ├─ FrontPageView.java
│  │  │  │  ├─ PresetGameView.java
│  │  │  │  └─ RunGameView.java
│  │  │  ├─ utility
│  │  │  │  └─ LoggerUtil.java
│  │  │  └─ ChaosGameAppLauncher.java
│  │  └─ resources
│  │     ├─ csv
│  │     │  ├─ preset.games
│  │     │  ├─ Julia.csv
│  │     │  ├─ Sierpinski.csv
│  │     │  └─ barnsley-fern.csv
│  │     ├─ app.log
│  │     └─ log4j2.xml
│  └─ test
│     ├─ java.no.ntnu.idatg2003
│     │  ├─ game.engine
│     │  │  ├─ ChaosCanvasTest.java
│     │  │  └─ ChaosGameFileHandlerTest.java
│     │  ├─ math.datatypes
│     │  │  ├─ ComplexTest.java
│     │  │  ├─ Matrix2x2Test.java
│     │  │  └─ Vector2DTest.java
│     │  └─ transformations
│     │     ├─ AffineTransformation2DTest.java
│     │     └─ JuliaTransformationTest.java
│     └─ resources/csv/preset.games
│        └─ // csv-files used in testing
├─ .gitignore
├─ README.md
└─ pom.xml
```

[//]: # (TODO: Describe the structure of your project here. How have you used packages in your structure. Where are all sourcefiles stored. Where are all JUnit-test classes stored. etc.)

The src-folder is divided into two parts; Main containing the main source code of the application, and test containing the test classes for the source code. These are divided in the same package structure for ease of navigation.

## Link to repository

[//]: # (TODO: Include a link to your repository here.)

https://gitlab.stud.idi.ntnu.no/sigurris/mappeeksamen-idatg2003

## How to run the project

[//]: # (TODO: Describe how to run your project here. What is the main class? What is the main method?
What is the input and output of the program? What is the expected behaviour of the program?)

1. **Open the Project:**
- Ensure you have Java and a compatible IDE (like IntelliJ IDEA, Eclipse, or Visual Studio Code) and Maven installed.
- Open your IDE and import the project by selecting the root directory where the `pom.xml` file is located.

2. **Compile the Code:**
- Compile the code using Maven. This can be done in terminal or IDE.
  - Through Terminal:
```text
mvn compile
```

3. **Interact with the Program:**
  - Navigate the menus using the buttons.
  - **Main Menu:**
    - **Run a Game:**
      - Select if you want to run a pre-defined game or a custom game.
      - Choose an amount of iterations and press the 'Run' button.
    - **Create Custom Game:**
      - Enter the values for the game you want to create
      - Give it a name and press the 'Save' button.
      - The chaosgame is now ready to be played under 'Run a Game' -> 'Custom Games'.
    - **Mandelbrot Set:**
      - Press the 'Open' button to view the Mandelbrot set.
      - Click on the mandelbrot set to generate a julia set.
      - You can zoom in and out by scrolling.
    - **Exit:**
      - Press the 'Exit' button to close the application.
4. **Exit the Program:**
    - Close the application window or return to the main menu and press the exit button.


## How to run the tests

To execute all tests, right-click on the 'java' folder within the 'test' directory and choose 'Run All Tests'.
Alternatively, you can run individual test classes by right-clicking on the specific test class.
If you wish to test a particular method within a test class, right-click on the desired method.

You can also run the tests from the terminal using Maven. This can be done by running the following command:
    
```text
mvn test
```

The application also allows you to generate the JavaDoc documentation by running the following command:

```text
mvn javadoc:javadoc
```

[//]: # (TODO: Describe how to run the tests here.)
