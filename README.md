# Blappy Fird

San Jose State University<br>
Course: Enterprise Software - CMPE 172 / Spring 2020<br>
By Brian Tao, Haasitha Pidaparthi, Syed Sarwar<br>

---

## Project Introduction

This is a three-tier web application inspired by the very popular Flappy Bird mobile app that rose to fame in 2014. This game is very simple in premise: the player controls a bird that travels in short hops, attempting to navigate between and around clouds without touching them as the game stage continuously scrolls sideways. The character hops each time the player presses the up arrow key and naturally falls downwards, mimicking gravity. If the character makes contact with any obstacle or the top or bottom of the screen, the game ends. The score increases with time, with the goal being to survive as long as possible to accumulate a high score. The player is also able to choose between three different character designs.

---

## Sample Demo Screenshots
<br>
<img src="./README_pictures/pause.jpg" alt="Pause screen" height="400">

> The pause screen.

<img src="./README_pictures/game_running.jpg" alt="Game is running with the pink bird." height="400">

> Game is running with the pink bird.

<img src="./README_pictures/end_game.jpg" alt="Game ended, showing score and high score." height="400">

> Game ended, now displaying the score for the current run and the high score.

---

## Prerequisites

JDK 1.8

Spring Boot

Gradle 4+ or Maven 3.2+

You can also import the code straight into your IDE, such as Eclipse or IntelliJ IDEA.

---

## Running The Project Locally

1. Download the project and open it in your favorite IDE that enables you to run Java applications and run the project from there.

 OR

2. Download the files and run from a terminal by navigating to the directory containing the Java programs, compiling, and running.

---

## Diagrams

### Class Diagram

<img src="./README_pictures/ClassDiagram_BlappyFird.jpg" alt="Class Diagram">

### Sequence Diagram

<img src="./README_pictures/sequence_diagram.jpg" alt="Sequence Diagram">

---

## Schema

<img src="./README_pictures/schema.jpg" alt="Database Schema" height="400">

---

## Database Queries

<img src="./README_pictures/query1.jpg" alt="Database Query 1" height="400">

<img src="./README_pictures/query2.jpg" alt="Database Query 2" height="400">

<img src="./README_pictures/query3.jpg" alt="Database Query 3" height="400">

---

## Mid-tier APIs

The application will be hosted on the local server running on port 8080. Using spring boot the application is able to handle its data relay onto an API by pushing the high score of the flappy bird game as well as the current score along with a name. People who update their score will then have it prompted to open up onto a leaderboard which contains all the top scores. Using maven for configurations and then the spring boot layer to handle all the api returns. There is a service component which is handled by the GameRunner class which runs the game and holds the score and high score along with a constructor and getters and setters which are relayed to the class blappy_firdController to handle what will be pushed to the rest api. Using the mapping /play/scoreboard gives you the scoreboard to be handled alongside the app.

---

## UI Data Transport

<img src="./README_pictures/gr_actionPerformed.jpg" alt="GameRunner's actionPerformed method" height="400">

> The game is run in the GameRunner class. This class calls the actionPerformed() method, shown above, every 15 ms. In turn, actionPerformed() calls the repaint() method on the GameBoard gb. Inside the GameBoard, this calls the paintComponent() method.

<img src="./README_pictures/gb_paintComponent.jpg" alt="GameBoard's paintComponent method" height="400">

> The GameBoard holds and draws all the visual elements of the game. The frequent calling of the repaint() method updates all visual changes to the GameBoard to produce the animation effect.
