# Portfolio project IDATG2003 - 2024
This file uses Mark Down syntax. For more information see [here](https://www.markdownguide.org/basic-syntax/).

Group Members: Theodor Sjetnan Utvik, Sigurd Riseth

## Project description

[//]: # (TODO: Write a short description of your project/product here.)
A system for displaying traindepartures from a single station. Shows planned departure time, delays (if any), line name and destination, train number and what track the train will depart from.
Allows user to create new and edit existing train departures. The system revolves around a "station-clock" and only trains with departure times after it are displayed (unless delayed).

The system is limited by the fact that it does not consider the date or if there are any departures the next day. It only supports one station, and the clock has to be manually updated from the UI.

## Project structure

The project structure is displayed in the directory tree below.

```text
.
├── src
│   ├── main
│   │   └── java
│   │       └── edu.ntnu.stud
│   │           ├── station
│   │           │   └── Station.java
│   │           ├── traindeparture
│   │           │   └── TrainDeparture.java
│   │           ├── userinterface
│   │           │   ├── InputReader.java
│   │           │   ├── Printer.java
│   │           │   └── UserInterface.java
│   │           └── TrainDispatchApp.java
│   └── test
│       └── java
│           └── edu.ntnu.stud
│               ├── station
│               │   └── StationTest.java
│               └── traindeparture
│                   └── TrainDepartureTest.java
├── target
│   └── // Compiled bytecode and generated JAR files
├── .gitignore
├── pom.xml
├── README.md
└── TrainDispatchSystem.iml
```

[//]: # (TODO: Describe the structure of your project here. How have you used packages in your structure. Where are all sourcefiles stored. Where are all JUnit-test classes stored. etc.)

The src-folder is divided into two parts; main containing the main source code of the application, and test containing the test classes for the source code. 

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
  - Once the program is running, you'll be prompted with a menu offering 10 choices.
  - Navigate through the menu using text inputs to interact with different functionalities.

4. **Exit the Program:**
  - To exit the program, choose option 0 in the menu. This will gracefully terminate the program.


## How to run the tests

To execute all tests, right-click on the 'java' folder within the 'test' directory and choose 'Run All Tests'.
Alternatively, you can run individual test classes by right-clicking on the specific test class.
If you wish to test a particular method within a test class, right-click on the desired method.

[//]: # (TODO: Describe how to run the tests here.)
