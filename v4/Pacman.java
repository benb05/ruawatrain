// Ruawatrain :: Benjamin Belotser, David Deng, Josiah Moltz
// APCS pd6
// FP01

//enable file I/O
import java.io.*;
import java.util.*;

public class Pacman {
  private char[][] _maze;
  int h,w;
  int pX, pY;
  int dX, dY; // change in pacman's motion (derivative teehee)

  // CONSTRUCTOR
  public Pacman( String inputFile )
  {
    // init 2D array to represent maze
    // (80x25 is default terminal window size)
    _maze = new char[80][25];
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

    //at init time, maze has not been solved:
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

  // MUTATORS
  public void setPX(int x)
  {
    pX = x;
  }

  public void setPY(int y)
  {
    pY = y;
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
    if (_maze[pX+dX][pY+dY] == '#') {
      pX = pX+dX;
      pY = pY+dY;
    }
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
