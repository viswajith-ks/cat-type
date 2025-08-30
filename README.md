-----

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

  - Java Development Kit (JDK) installed on your system.

### How to Compile and Run

1.  **Navigate to the Source Directory:** Open your terminal or command prompt and navigate to the directory where you have saved the `.java` files.

2.  **Compile the Code:**

    ```bash
    javac CatType.java wpmgame.java
    ```

3.  **Run the Application:**
    When you run the program, it will first prompt you in the terminal to choose between the GUI and the TUI.

    ```bash
    java CatType
    ```

      - To launch the **GUI**, enter `g` (or any key other than 't') and press Enter.
      - To launch the **Text-based version (TUI)**, enter `t` and press Enter.

-----

## 🕹️ How It Works

The application logic is split between two main classes:

  - `wpmgame.java`: This class contains the core logic for the typing test. It generates the random words for the test, measures the time, calculates the WPM, CPS, and accuracy, and manages the high scores. This class can run independently as the command-line version of the game.

  - `CatType.java`: This class builds the graphical user interface using the **Java Swing** library. It uses the `wpmgame` class as a backend to run the tests and then displays the results in a user-friendly window with buttons, text areas, and tables.

When a new game starts, a random set of words is selected from a predefined collection. As the user types, the program tracks the time and keystrokes. Once the user hits "Enter," the input is compared against the generated text, and the performance metrics are calculated and displayed on the results screen.
