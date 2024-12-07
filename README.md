# 2048
2048 is a well known tile-sliding game where colliding tiles of equal value combine to a doubled value. Each turn, the player can slide all tiles in one of four directions (up, right, left, and down) and collisions are resolved accordingly

## Making the Game Run
To run the game, the following files must be compiled:
 - `Board.java`
 - `Tile.java`
 - `View.java`

Once they are comiled, the game can be played by running the compiled `View.class` file. 
(this can be done from command line with the command `java View`. To compile java files, use the command `javac <filename>`. If java is not installed, go [here](https://www.java.com/download/ie_manual.jsp) to install it)

## The GUI
When running the application, the user is given a few options. They can select a board size between 4 and 8 (inclusive), view the leaderboard, or start the game. If start is selected, the game will start with the selected size (default being 4). If the leaderboard is selected, players will be shown a leaderboard of past players (which starts with some players already on it, but the user is able to add their runs to the leaderboard after completion).

Once the game has been started by the user, they can make turns by selecting an arrow key; the tiles will slide in the direction indicated by the arrow key.
