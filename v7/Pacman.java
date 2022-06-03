// Ruawatrain :: Benjamin Belotser, David Deng, Josiah Moltz
// APCS pd6
// FP01

//enable file I/O
import java.io.*;
import java.util.*;

public class Pacman {
  private char[][] _maze;
  int score;
  int h,w;
  int pX, pY;
  int dX, dY; // change in pacman's motion (derivative teehee)
  int dots;

  // CONSTRUCTOR
  public Pacman( String inputFile )
  {
    score = 0;
    // init 2D array to represent maze
    // (80x25 is default terminal window size)
    _maze = new char[80][41];
    h = 0;
    w = 0;

    //transcribe maze from file into memory
    try {
      Scanner sc = new Scanner( new File(inputFile) );

      //System.out.println( "reading in file..." );

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

    dottify();
  }//end constructor

  // ACCESSORS
  public int getPX()
  {
    return pX;
  }

  public int getPY()
  {
    return pY;
  }

  public char[][] getMap() {
    return _maze;
  }

  // MUTATORS
  public void setPX(int x)
  {
    pX = x;
  }

  public void setPY(int y)
  {
    pY = y;
  }

  public void dottify() {
    for (int i = 0; i < _maze.length; i++) {
      for (int j = 0; j < _maze[0].length; j++) {
        if (_maze[i][j] == '#') {
          _maze[i][j] = '.';
          dots++;
        }
      }
    }
  }

  public void turn(String direction) {
    direction = direction.toUpperCase();
    if (direction.equals("W")) {
      dX = 0;
      dY = -1;
    }
    else if (direction.equals("A")) {
      dX = -1;
      dY = 0;
    }
    else if (direction.equals("S")) {
      dX = 0;
      dY = 1;
    }
    else if (direction.equals("D")) {
      dX = 1;
      dY = 0;
    }
  }

  // MOVING
  public void move() {
    if (_maze[pX+dX][pY+dY] == '.') {
      pX += dX;
      pY += dY;
      _maze[pX][pY] = '#';
      score += 10;
      dots--;
    }
    else if (_maze[pX+dX][pY+dY] == '#') {
      pX += dX;
      pY += dY;
    }
  }

  public boolean hasWon() {
    return dots == 0;
  }

  // FOR TESTING

  public String toString()
  {
    //send ANSI code "ESC[0;0H" to place cursor in upper left
    String retStr = "[0;0H";
    //emacs shortcut: C-q, ESC
    //emacs shortcut: M-x quoted-insert, ESC

    int i, j;
    for( i=0; i<h; i++ ) {
      for( j=0; j<w; j++ )
        retStr = retStr + _maze[j][i];
      retStr = retStr + "\n";
    }
    return retStr;
  }

  // MAIN
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
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
  }//end main()

}
