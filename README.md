# CatType 😺 - A Typing Speed Test Game

CatType is a simple yet fun typing speed test application built in Java. It measures your typing speed in **Words Per Minute (WPM)** and **Characters Per Second (CPS)**, along with your accuracy. The application can be run with a graphical user interface (GUI) built with Java Swing or as a simpler text-based version in the command line.

The GUI version features an interactive cat animation that reacts to your typing\!

-----

## ✨ Features

  - **Dual Mode:** Choose between a full graphical experience (GUI) or a lightweight command-line interface (TUI).
  - **Performance Metrics:** Get detailed feedback on your performance, including:
      - Words Per Minute (WPM)
      - Characters Per Second (CPS)
      - Accuracy (%)
      - Total time taken
  - **Customizable Tests:** Adjust the number of words for each test to match your desired difficulty.
  - **High Score Tracking:** The application keeps a running list of your best scores to track your improvement over time.
  - **Interactive UI:** The GUI features a cat that changes its animation based on which keys you press, adding a bit of fun to the experience.
  - **Detailed Analysis:** After each test, see a color-coded breakdown of your typed text, with correct characters in green and mistakes underlined in red.

-----

## 🚀 Getting Started

### Prerequisites

  - Java Development Kit (JDK) 8 or higher.

### Project Structure

For the application to work correctly, your project should follow this structure. The `res` folder contains all necessary resources like images and the `words.txt` dictionary.

```
cat-type/
├── bin/            <-- Compiled .class files will go here
├── res/            <-- All resource files (images, words.txt)
│   ├── catdown.png
│   ├── catleft.png
│   ├── catright.png
│   ├── catup.png
│   ├── icon.png
│   └── words.txt
└── src/            <-- All .java source files
    ├── CatType.java
    ├── WpmGame.java
    ├── LeaderboardEntry.java
    └── noPasteTextArea.java
```

### How to Compile and Run

All commands should be run from the **root directory** of the project (e.g., the `cat-type/` folder).

**1. Compile the Code**

This command compiles all `.java` files from the `src` folder and places the resulting `.class` files into the `bin` folder.

```bash
javac -d bin src/*.java
```

**2. Run the Application**

This command runs the application, using the `-cp` flag to set the classpath to the `bin` folder, which contains both your compiled code and the necessary resources.

```bash
java -cp bin CatType
```

When the program starts, it will first prompt you in the terminal to choose between the GUI and the TUI:

  - To launch the **Text-based version (TUI)**, enter `t` and press Enter.
  - To launch the **GUI**, enter any other key and press Enter.

-----

## 🕹️ How It Works

The application logic is cleanly separated into multiple classes:

  - **`WpmGame.java`**: This is the core engine for the typing test. It handles generating random words from `words.txt`, measuring time, calculating WPM, CPS, and accuracy, and managing the high scores. This class also contains all the logic for the command-line version of the game.
  - **`CatType.java`**: This class builds the graphical user interface using the **Java Swing** library. It uses the `WpmGame` class as a backend to run the tests and then displays the results in a user-friendly window.
  - **`LeaderboardEntry.java`**: A simple data class that holds the results of a single game for the high-score table.
  - **`noPasteTextArea.java`**: A custom `JTextArea` component that prevents the user from pasting text to cheat.

When a new game starts, `WpmGame` reads the word list and generates a random test. As the user types in the `CatType` GUI, the program tracks the time. Once the user hits "Enter," the input is sent to `WpmGame` to be processed, and the performance metrics are calculated and displayed on the results screen.
