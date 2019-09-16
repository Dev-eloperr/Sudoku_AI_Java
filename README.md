# SUDOKU_AI 💥💥💥
> Solves sudoku using input either from  CSV file or Manually

## Table of contents
* [Screenshots](#screenshots)
* [Working](#working)
* [Setup](#setup)
* [Features](#features)
* [Contact](#contact)


## Screenshots
![Example screenshot](./img/1.jpeg)
![Example screenshot](./img/2.jpeg)
![Example screenshot](./img/3.jpeg)

## Technologies
* Java 8

## Setup
`Clone the repo and run Src/Main.java using any suitable IDE

## Working

    Algorithm Used:

        There are 81 maximum variables in a sudoku
        To solve them, we require 81 equations atleast,
        To avoid that, we'll be using the constraints which are
        1) Every row must have unique numbers ranging from 1 to 9
        2) Every column must have unique numbers ranging from 1 to 9
        3) Every box(3x3) must have unique numbers ranging from 1 to 9

        In addition to solving these condition,this algorithm uses MVR heuristic(Minimum value remaining)
        i.e It starts filling the cells in a box wise manner such that box with the least number of unfilled cells gets filled first and so on
        It allows to reduce the number of recursive loops which occur due to backtracking.
        It could also be said as efficient backtracting using heuristics
        Moreover, multiple optimizations have been done to reduce the time complexity
        1) Program doesn't have to check wheather a cell is empty because a Point arraylist which contains the
            empty points is generated in the starting and is ordered by boxes which have the least number
            of empty cells

        ```
        The algorithm above is the most efficient when the boxes have unequal number of elements present in them

        This algorithm works as pure backtracting if boxes have equal/no elements.
        ```



    ---
    Description of Classes:

    SUDOKU SOLVER

    This program uses Dynamic arrays to save empty points which are later
    used to determine heuristics which are minimum value remaining box;

    Multithreading has also been used for efficiency and anti freezing of GUI

    *Description of various classes used*

    1) Main
        Initializes static array of sudoku, points etc, intializes GUI as well
    2) Solver
        This Class has the algorithm to solve sudoku, It also refreshes GUI after every step
        (Timer delay could be edited in this class)
    3) guiController
        Initializes GUI and provides various functions to retrieve/display/refresh the GUI
    4) sudokuInitializer
        This class allows input through Files
        It also has a commented function which could take array values via console
        (used only for debugging purpose)

To-do list:
* Update User playing functionality
* GUI updation


## Contact
Created by [@devkathuria](https://github.com/Dev-eloperr) - feel free to contact me!
