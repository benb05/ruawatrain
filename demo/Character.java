// Ruawatrain :: Benjamin Belotser, David Deng, Josiah Moltz
// APCS pd6
// FP01

//enable file I/O
import java.io.*;
import java.util.*;

public class Character {
  protected char[][] _maze;
  protected int dX,dY,xPos,yPos;

  public Character( String inputFile )
  {
    _maze = new char[80][41]; // 80 X 41 for the sizing of out maps
    int h = 0;
    int w = 0;

    //transcribe maze from file into memory
    try {
      Scanner sc = new Scanner( new File(inputFile) );

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

  // ACCESSORS
  public int getX() {
    return xPos;
  }
  public int getY() {
    return yPos;
  }

  // MUTATORS
  public void setPos(int x, int y) {
    xPos = x;
    yPos = y;
  }

  // LOCATION FUNCTIONS - give information about location on the map
  protected boolean atIntersection() {
    return onPath(xPos + ( (dX+1)%2 ),yPos + ( (dY+1)%2 )) || onPath(xPos - ( (dX+1)%2 ),yPos - ( (dY+1)%2 ));
  }

  public boolean onPath( int x, int y) {
    if (_maze[x][y] != '#' && _maze[x][y] != '.'){
      return false;
    } else {
      return true;
    }
  }

  // DISPLAY FUNCTIONS - aid in the display of information
  protected void delay( int n ) // removed post testing era
  {
    try {
      Thread.sleep(n);
    } catch( InterruptedException e ) {
      System.exit(0);
    }
  }

  public String toString()
  {
    //"ESC[0;0H" to place cursor in upper left
    String retStr = "[0;0H";

    for( int i=0; i<_maze[0].length; i++ ) {
      for( int j=0; j<_maze.length; j++ ) {
        if (_maze[j][i] == '@') {
          retStr = retStr + "\u001b[32m" + _maze[j][i] + "\u001b[0m"; // for demo purposes, probes show up green
        }
        else {
          retStr = retStr + _maze[j][i];
        }
      }
      retStr = retStr + "\n";
    }
    return retStr;
  }
}
