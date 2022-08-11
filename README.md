# CPSC 210 Personal Project

## Movie Rater & Organizer

An application that allows you to *add movies to a list*, in addition to their individual *genres* and *ratings*. 
This can be useful for movie critics to keep the movies they watch organized, or for personal use. 
This project is of interest of me, because I love watching movies but tend to forget which movies I have watched, 
and my rating of them!

## User Stories

- As a user, I want to see all the movies in my movie list
- As a user, I want to add a movie to a movie list
- As a user, I want to delete a movie from a movie list
- As a user, I want to change the rating of a movie in the list
- As a user, I want to find all the movies associated with a specific director
- As a user, I want to be able to save my movie list to file
- As a user, I want to be able to load my movie list from file

## Instructions for Grader

- You can generate the first required event by pressing the 'add' button. This will trigger a popup window
that will prompt you to enter information of a movie you wish to add to the list. Click 'add' in the popup window 
to add it to the list. 
- You can generate the second required event by selecting a movie in the list on the main window, and pressing the 'delete'
button underneath.
- My visual elements are located in both the main window and the popup window.
- You can save the state of my application by pressing the 'save' button.
- You can reload the state of my saved application by pressing the 'load' button.

## Phase 4: Task 2

- New movie added
- Movie deleted
- New movie added

## Phase 4: Task 3

In terms of design, if I had more time I would for sure refactor my code. 
For instance, I would probably separate the code related to button-making in my MovieListGUI into 
separate classes, which each implement a higher-level 'button' interface. This would make the 
readability of my code much easier, and would also decrease the repetitive code within the
single MovieListGUI class that handles the graphical interaction.

I would also change the multiplicity of PopoutWindow within MovieListGUI to 
1 (instead of 0...*). Only one window is needed to complete the intended
function, so these would better the design of the project.