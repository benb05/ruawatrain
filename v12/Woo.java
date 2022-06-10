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
  private BufferedReader in;

  // COLORS BC WHY NOT
  public static final String CLEAR = "\033[H\033[2J[0;0H";
  public static final String YELLOW = "\u001B[33m";
  public static final String CYAN = "\u001B[36m";
  public static final String RESET = "\033[0m";
  public static final String ORANGE = "\u001b[38;5;208m"; // only works on 256 color terminal
  public static final String MAGENTA = "\u001B[35m";
  public static final String RED = "\u001B[31m";
  public static final String BLUE = "\u001b[34m";

  public Woo( String inputFile )
  {
    in = new BufferedReader( new InputStreamReader(System.in) );
    clyde = new Ghost(inputFile, 1);  // orange
    inky = new Ghost(inputFile, 2); // cyan
    pinky = new Ghost(inputFile, 3);  // pink
    blinky = new Ghost(inputFile, 4); // red
    pacman = new Pacman(inputFile);
    _maze = pacman.getMap();
    System.out.println(CLEAR);

  }//end constructor

  public String toString()
  {
    String retStr = "[0;0H" + "Score: " + pacman.getScore(); //JUST IN CASE add back clear screen later "[0;0H"
    int i, j;
    for( i=0; i<_maze[0].length; i++ ) {
      for( j=0; j<_maze.length; j++ ) {
        if (j == clyde.getX() && i == clyde.getY()) {
          retStr += clyde.isEatable() ? BLUE + "G" + RESET : ORANGE + "G" + RESET;
        }
        else if (j == inky.getX() && i == inky.getY()) {
          retStr += inky.isEatable() ? BLUE + "G" + RESET : CYAN + "G" + RESET;
        }
        else if (j == blinky.getX() && i == blinky.getY()) {
          retStr += blinky.isEatable() ? BLUE + "G" + RESET : RED + "G" + RESET;
        }
        else if (j == pinky.getX() && i == pinky.getY()) {
          retStr += pinky.isEatable() ? BLUE + "G" + RESET : MAGENTA + "G" + RESET;
        }
        else if (j == pacman.getX() && i == pacman.getY()) {
          retStr += YELLOW + "P" + RESET;
        }
        else {
          retStr += _maze[j][i];
        }
      }
      retStr += "\n";
    }
    if (Ghost.movesVulnerable > 0) {
        retStr += "Powerup lasts for another " + Ghost.movesVulnerable + " moves.\n";
    }
    else {
      retStr += "                                                                  "; // to clear off powerup message
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
    System.out.println("The four ghosts in order of intelligence are " + RED + "Blinky" + RESET + ", " + MAGENTA + "Pinky" + RESET + ", " + CYAN + "Inky" + RESET + ", and " + ORANGE + "Clyde" + RESET + ".");
    System.out.println("They will hunt you. Try to evade them.");
    System.out.println("You may turn by clicking one of w, a, s, or d AND THEN ENTER to turn along the map.");
    System.out.println("The + signs are powerups that let you eat the ghosts.");
    System.out.println("You are the capital " + YELLOW + "P" + RESET + ". Your goal is to try to eat all the dots, which will turn into hashtags once you pass over them.");
    System.out.println("The game will begin when you enter your first move.");
    System.out.println("Click enter to begin the game.");
    try {
      in.readLine();
    }
    catch (Exception e) {}
    System.out.println(CLEAR);
    System.out.println(this);
    try {
      pacman.turn(in.readLine());
    }
    catch ( Exception e) {}

    while (!clyde.hasWon() && !inky.hasWon() && !blinky.hasWon() && !pinky.hasWon() && !pacman.hasWon()) {
      System.out.println(this);
      TimerTask task = new TimerTask()
      {
        public void run()
        {
          try {
            pacman.turn(in.readLine());
          }
          catch ( Exception e) {}
        }
      };
      Timer timer = new Timer();
      timer.schedule(task, 300);
      delay(300);
      timer.cancel();

      pacman.move();
      clyde.movePacman(pacman.getX(),pacman.getY());
      clyde.move();
      inky.movePacman(pacman.getX(),pacman.getY());
      inky.move();
      blinky.movePacman(pacman.getX(),pacman.getY());
      blinky.move();
      pinky.movePacman(pacman.getX(),pacman.getY());
      pinky.move();
      if (Pacman.ghostsEaten != 4 && Ghost.movesVulnerable > 0) {
          Ghost.movesVulnerable--;
      }
      else {
        Pacman.isInvincible = false;
        Pacman.ghostsEaten = 0;
        Ghost.movesVulnerable = 0;
        Ghost.points = 200;
      }
    }
    System.out.println(this);
    if (pacman.hasWon()) {
      System.out.println("You won!! Congratulations!");
    }
    else {
      System.out.println("A ghost killed you.\nYour final score was " + pacman.getScore() + "\nBetter luck next time!");
    }
    System.exit(0);
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
