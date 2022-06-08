// Ruawatrain :: Benjamin Belotser, David Deng, Josiah Moltz
// APCS pd6
// FP01

//enable file I/O
import java.io.*;
import java.util.*;

public class Pacman {
  private char[][] _maze;
  int score;
  int pX, pY;
  int dX, dY; // change in pacman's motion (derivative teehee)
  int ndX, ndY; // storing the next dX,dY
  int dots; // keeps track of remaining dots

  // CONSTRUCTOR
  public Pacman( String inputFile )
  {
    score = 0;
    _maze = new char[80][41];
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

  public int getScore()
  {
    return score;
  }

  // MUTATORS
  public void movePacman(int x, int y)
  {
    pX = x;
    pY = y;
    if (_maze[pX][pY] == '.') {
      score += 10;
      _maze[pX][pY] = '#';
    }
  }

  private void dottify() {
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
      ndX = 0;
      ndY = -1;
    }
    else if (direction.equals("A")) {
      ndX = -1;
      ndY = 0;
    }
    else if (direction.equals("S")) {
      ndX = 0;
      ndY = 1;
    }
    else if (direction.equals("D")) {
      ndX = 1;
      ndY = 0;
    }
    if (onPath(pX+ndX, pY+ndY)) {
      dX=ndX;
      dY=ndY;
    }
  }

  // MOVING
  public void move() {
    if (onPath(pX+ndX,pY+ndY)) {  // if the next loaded move is possible, do it
      dX=ndX;
      dY=ndY;
    }
    if (_maze[pX+dX][pY+dY] == '.') { // if it is food
      pX += dX;
      pY += dY;
      _maze[pX][pY] = '#';
      score += 10;
      dots--;
    }
    else if (_maze[pX+dX][pY+dY] == '#') {  // if it isn't food
      pX += dX;
      pY += dY;
    }
  }

  public boolean hasWon() {
    return dots == 0;
  }

  public boolean atIntersection() {
    return onPath(pX + ( (dX+1)%2 ),pY + ( (dY+1)%2 )) || onPath(pX - ( (dX+1)%2 ),pY - ( (dY+1)%2 ));
  }

  public boolean onPath( int x, int y) {
    if (_maze[x][y] != '#' && _maze[x][y] != '.'){
      return false;
    } else {
      return true;
    }
  }

}
