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
    clyde = new Ghost(inputFile, 1);
    inky = new Ghost(inputFile, 2);
    pinky = new Ghost(inputFile, 3);
    blinky = new Ghost(inputFile, 4);
    pacman = new Pacman(inputFile);
    _maze = pacman.getMap();
    // init 2D array to represent maze
    // (80x25 is default terminal window size)
    System.out.println(CLEAR);

  }//end constructor

  public String toString()
  {
    String retStr = "[0;0H" + "Score: " + pacman.getScore();
    int i, j;
    for( i=0; i<_maze[0].length; i++ ) {
      for( j=0; j<_maze.length; j++ ) {
        if (j == pacman.getPX() && i == pacman.getPY()) {
          retStr += YELLOW + "P" + WHITE;
        }
        else if (j == clyde.getGX() && i == clyde.getGY()) {
          retStr += ORANGE + "G" + WHITE;
        }
        else if (j == inky.getGX() && i == inky.getGY()) {
          retStr += CYAN + "G" + WHITE;
        }
        else if (j == blinky.getGX() && i == blinky.getGY()) {
          retStr += RED + "G" + WHITE;
        }
        else if (j == pinky.getGX() && i == pinky.getGY()) {
          retStr += MAGENTA + "G" + WHITE;
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
    pacman.movePacman(20,16);

    clyde.movePacman(20,16);
    inky.movePacman(20,16);
    blinky.movePacman(20,16);
    pinky.movePacman(20,16);

    clyde.setGX(9);
    clyde.setGY(6);

    inky.setGX(24);
    inky.setGY(14);

    blinky.setGX(26);
    blinky.setGY(19);

    pinky.setGX(21);
    pinky.setGY(3);
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
      clyde.movePacman(pacman.getPX(),pacman.getPY());
      clyde.move();
      inky.movePacman(pacman.getPX(),pacman.getPY());
      inky.move();
      blinky.movePacman(pacman.getPX(),pacman.getPY());
      blinky.move();
      pinky.movePacman(pacman.getPX(),pacman.getPY());
      pinky.move();
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
