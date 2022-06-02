// Ruawatrain :: Benjamin Belotser, David Deng, Josiah Moltz
// APCS pd6
// FP01

//enable file I/O
import java.io.*;
import java.util.*;

public class Woo {
  Ghost inky,blinky,pinky,clyde;  // just using clyde,inky for now (favorite names) (both smart)
  Pacman pacman;
  char[][] _maze;
  int h,w;

  // COLORS BC WHY NOT
  public static final String YELLOW = "\u001B[33m";
  public static final String CYAN = "\u001B[36m";
  public static final String WHITE = "\u001B[37m";
  public static final String ORANGE = "\u001b[31m";

  public Woo( String inputFile )
  {
    clyde = new Ghost(inputFile);
    inky = new Ghost(inputFile);
    pacman = new Pacman(inputFile);
    // init 2D array to represent maze
    // (80x25 is default terminal window size)
    _maze = new char[80][40];
    h = 0;
    w = 0;

    //transcribe maze from file into memory
    try {
      Scanner sc = new Scanner( new File(inputFile) );

      System.out.println( "reading in file..." );

      int row = 0;

      while( sc.hasNext() ) {

        String line = sc.nextLine();

        if ( w < line.length() )
          w = line.length();

        for( int i=0; i<line.length(); i++ )
        {
          _maze[i][row] = line.charAt( i );
        }
        h++;
        row++;
      }

      for( int i=0; i<w; i++ )
      {
        _maze[i][row] = ' ';
      }
      h++;
      row++;

    } catch( Exception e ) { System.out.println( "Error reading file" ); }
  }//end constructor

  public String toString()
  {
    //send ANSI code "ESC[0;0H" to place cursor in upper left
    String retStr = "[0;0H";
    //emacs shortcut: C-q, ESC
    //emacs shortcut: M-x quoted-insert, ESC

    int i, j;
    for( i=0; i<h; i++ ) {
      for( j=0; j<w; j++ ) {
        if (j == clyde.getGX() && i == clyde.getGY()) {
          retStr = retStr + ORANGE + "C" + WHITE;
        }
        else if (j == pacman.getPX() && i == pacman.getPY()) {
          retStr = retStr + YELLOW + "P" + WHITE;
        }
        else if (j == inky.getGX() && i == inky.getGY()) {
          retStr = retStr + CYAN + "I" + WHITE;
        }
        else {
          retStr = retStr + _maze[j][i];
        }
      }
      retStr = retStr + "\n";
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
    pacman.setPX(4);
    pacman.setPY(9);

    clyde.movePacman(4,9);
    inky.movePacman(4,9);

    clyde.setGX(6);
    clyde.setGY(5);

    inky.setGX(26);
    inky.setGY(14);

    pacman.turn("D");
    clyde.addMove("D");
    inky.addMove("D");
  }

  public void play() // RUDIMENTARY turn
  {
    while (!clyde.hasWon() && !inky.hasWon()) {
      System.out.println(this);
      delay(100);
      pacman.move();
      clyde.movePacman(pacman.getPX(),pacman.getPY());
      clyde.move();
      inky.movePacman(pacman.getPX(),pacman.getPY());
      inky.move();
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
