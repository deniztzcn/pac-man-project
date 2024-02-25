# Pacman-Style Game Application

This project is an implementation of a Pacman-style game application with specific features and requirements outlined below:

## Features

1. **Gameplay:**
   - Players navigate through a maze, collecting points and avoiding enemies.
   - Every 5 seconds, enemies have a 25% chance to create upgrades.
   - Implement at least 5 different significant complex upgrades.

2. **Main Menu:**
   - Provides options for New Game, High Scores, and Exit.
   - Users can navigate through the menu using mouse.

3. **Graphical Interface:**
   - Includes score, time, life counters, and other necessary elements.
   - Utilizes graphic files for visualizations and animations.
   - Implements animations for game characters and upgrades.

4. **Time Handling:**
   - All time-related actions are managed using the Thread class, ensuring proper synchronization of threads.
   - Timer, Executor, and similar classes are not allowed.

5. **Interrupt Mechanism:**
   - Players can interrupt the game at any time using the compound keyboard shortcut Ctrl+Shift+Q, returning them to the main menu.

6. **High Scores:**
   - Allows players to save their scores under a chosen name.
   - High scores are persisted using the Serializable interface to ensure they are not lost after closing the application.

8. **MVC Pattern:**
   - The application follows the Model-View-Controller (MVC) programming pattern.
   - Complete event handling is implemented using the delegated event handling model.

## Technologies Used
- Java
- Swing (for GUI)
- AbstractTableModel (for managing game board)
- Serializable (for high scores persistence)
