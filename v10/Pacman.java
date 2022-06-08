// Ruawatrain :: Benjamin Belotser, David Deng, Josiah Moltz
// APCS pd6
// FP01

//enable file I/O
import java.io.*;
import java.util.*;

public class Pacman extends Character{
  int score;  // pacman's score
  int ndX, ndY; // storing the next dX,dY
  int dots; // keeps track of remaining dots

  // CONSTRUCTOR
  public Pacman( String inputFile )
  {
    super(inputFile);
    score = 0;
    dottify();
  }//end constructor

  // ACCESSORS
  public int getScore()
  {
    return score;
  }

  public char[][] getMap()
  {
    return _maze;
  }

  // MUTATORS
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
    if (onPath(xPos+ndX, yPos+ndY)) {
      dX=ndX;
      dY=ndY;
    }
  }

  // MOVING
  public void setPos(int x, int y) {  // necessary to redefine becuase we need to update food
    super.setPos(x,y);
    if (_maze[xPos][yPos] == '.') {
      _maze[xPos][yPos] = '#';
      score += 10;
      dots--;
    }
  }

  public void move() {
    if (onPath(xPos + ndX, yPos + ndY)) {  // if the next desired direction makes sense, update to match it
      dX=ndX;
      dY=ndY;
    }

    if (onPath(xPos + dX, yPos + dY)) { // if you can move
      xPos += dX;
      yPos += dY;
      if (_maze[xPos][yPos] == '.') { // if the new square is food
        _maze[xPos][yPos] = '#';
        score += 10;
        dots--;
      }
    }
  }

  // WINNING
  public boolean hasWon() {
    return dots == 0;
  }

}
