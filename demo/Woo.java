// Ruawatrain :: Benjamin Belotser, David Deng, Josiah Moltz
// APCS pd6
// FP01

//enable file I/O
import java.io.*;
import java.util.*;

public class Woo {
  private Ghost inky,blinky,pinky,clyde;  // just using clyde,inky for now (favorite names) (both smart)
  private Pacman pacman;
  private char[][] _maze;
  private int h,w;
  private BufferedReader in;

  // COLORS BC WHY NOT
  public static final String CLEAR = "\033[H\033[2J[0;0H";
  public static final String YELLOW = "\u001B[33m";
  public static final String CYAN = "\u001B[36m";
  public static final String WHITE = "\u001B[37m";
  public static final String ORANGE = "\u001b[38;5;208m"; // only works on 256 color terminal
  public static final String MAGENTA = "\u001B[35m";
  public static final String RED = "\u001B[31m";

  public Woo( String inputFile )
  {
    in = new BufferedReader( new InputStreamReader(System.in) );
    clyde = new Ghost(inputFile, 1);  // orange
    inky = new Ghost(inputFile, 2); // cyan
    pinky = new Ghost(inputFile, 3);  // pink
    blinky = new Ghost(inputFile, 4); // red
    pacman = new Pacman(inputFile);
    _maze = pacman.getMap();
    // init 2D array to represent maze
    // (80x25 is default terminal window size)
    System.out.println(CLEAR);

  }//end constructor

  public String toString()
  {
    String retStr = "[0;0H" + "Score: " + pacman.getScore(); //JUST IN CASE add back clear screen later "[0;0H"
    int i, j;
    for( i=0; i<_maze[0].length; i++ ) {
      for( j=0; j<_maze.length; j++ ) {
        if (j == clyde.getX() && i == clyde.getY()) {
          retStr += ORANGE + "G" + WHITE;
        }
        else if (j == inky.getX() && i == inky.getY()) {
          retStr += CYAN + "G" + WHITE;
        }
        else if (j == blinky.getX() && i == blinky.getY()) {
          retStr += RED + "G" + WHITE;
        }
        else if (j == pinky.getX() && i == pinky.getY()) {
          retStr += MAGENTA + "G" + WHITE;
        }
        else if (j == pacman.getX() && i == pacman.getY()) {
          retStr += YELLOW + "P" + WHITE;
        }
        else {
          retStr += _maze[j][i];
        }
      }
      retStr += "\n";
    }
    return retStr;
  }

  private void delay( int n ) // removed post testing era
  {
    try {
      Thread.sleep(n);
    } catch( InterruptedException e ) {
      System.exit(0);
    }
  }

  public void setup()
  {
    int x = 0;
    int y = 0;
    while (!pacman.onPath(x,y)) {
      x = (int) (80 * Math.random());
      y = (int) (41 * Math.random());
    }
    pacman.setPos(x,y);

    clyde.movePacman(x,y);
    inky.movePacman(x,y);
    blinky.movePacman(x,y);
    pinky.movePacman(x,y);

    x = 0;
    y = 0;
    while (!pacman.onPath(x,y)) {
      x = (int) (80 * Math.random());
      y = (int) (41 * Math.random());
    }

    clyde.setPos(x,y);

    x = 0;
    y = 0;
    while (!pacman.onPath(x,y)) {
      x = (int) (80 * Math.random());
      y = (int) (41 * Math.random());
    }

    inky.setPos(x,y);

    x = 0;
    y = 0;
    while (!pacman.onPath(x,y)) {
      x = (int) (80 * Math.random());
      y = (int) (41 * Math.random());
    }

    blinky.setPos(x,y);

    x = 0;
    y = 0;
    while (!pacman.onPath(x,y)) {
      x = (int) (80 * Math.random());
      y = (int) (41 * Math.random());
    }

    pinky.setPos(x,y);
  }

  public void play() // RUDIMENTARY turn
  {
    while (!clyde.hasWon() && !inky.hasWon() && !blinky.hasWon() && !pinky.hasWon() && !pacman.hasWon()) {
      System.out.println(this);
      //delay(100);
      try {
        pacman.turn(in.readLine());
      }
      catch ( Exception e ) { }
      pacman.move();
      clyde.movePacman(pacman.getX(),pacman.getY());
      clyde.move();
      inky.movePacman(pacman.getX(),pacman.getY());
      inky.move();
      blinky.movePacman(pacman.getX(),pacman.getY());
      blinky.move();
      pinky.movePacman(pacman.getX(),pacman.getY());
      pinky.move();
    }
    System.out.println(this);
    if (pacman.hasWon()) {
      System.out.println("You won!! Congratulations!");
    }
    else {
      System.out.println("A ghost killed you.\nYour final score was " + pacman.getScore() + "\nBetter luck next time!");
    }
  }

  public static void main( String[] args )
  {
    String mazeInputFile = null;

    try {
      mazeInputFile = args[0];
    } catch( Exception e ) {
      System.out.println( "Error reading input file." );
      System.out.println( "USAGE:\n $ java Maze path/to/filename" );
    }

    if (mazeInputFile==null) { System.exit(0); }

    Woo game = new Woo(mazeInputFile);

    game.setup();
    game.play();
  }
}
