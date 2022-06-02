// Ruawatrain :: Benjamin Belotser, David Deng, Josiah Moltz
// APCS pd6
// FP01

import java.io.*;
import java.util.*;

public class Ghost {
  private char[][] _maze;
  private int gX, gY; // ghost's position
  private boolean _solved;

  // ACCESSORS
  public int getGX() {
    return gX;
  }

  public int getGY() {
    return gY;
  }

  // MUTATORS

  // SOLVER
  // Returns an array of length 2 that holds the x-coordinate and y-coordinate of where to go next
  public Integer[] solve() {
    Queue<Integer[]> pacChildren = new LinkedList<Integer[]>();
    Queue<Integer[]> ghostChildren = new LinkedList<Integer[]>();
    pacChildren.add(new Integer[]{/*pacmanXCoord, pacmanYCoord*/});
    ghostChildren.add(new Integer[]{gX, gY});
    Integer[] pacCoords;
    Integer[] ghostCoords;
    //while the two paths have not met
    while (pacChildren.peek()[0] != ghostChildren.peek()[0] && pacChildren.peek()[1] != ghostChildren.peek()[1]) {
      pacCoords = pacChildren.poll();
      ghostCoords = ghostChildren.poll();
      //checks left coordinate
      if (_maze[pacCoords[0]][pacCoords[1] - 1] != ' ') {
        pacChildren.add(new Integer[]{pacCoords[0], pacCoords[1] - 1});
      }
      if (_maze[ghostCoords[0]][ghostCoords[1] - 1] != ' ') {
        ghostChildren.add(new Integer[]{ghostCoords[0], ghostCoords[1] - 1});
      }
      //checks right coordinate
      if (_maze[pacCoords[0]][pacCoords[1] + 1] != ' ') {
        pacChildren.add(new Integer[]{pacCoords[0], pacCoords[1] + 1});
      }
      if (_maze[ghostCoords[0]][ghostCoords[1] + 1] != ' ') {
        ghostChildren.add(new Integer[]{ghostCoords[0], ghostCoords[1] + 1});
      }
      //checks up coordinate
      if (_maze[pacCoords[0] - 1][pacCoords[1]] != ' ') {
        pacChildren.add(new Integer[]{pacCoords[0] - 1, pacCoords[1]});
      }
      if (_maze[ghostCoords[0] - 1][ghostCoords[1]] != ' ') {
        ghostChildren.add(new Integer[]{ghostCoords[0] - 1, ghostCoords[1]});
      }
      //checks down coordinate
      if (_maze[pacCoords[0] + 1][pacCoords[1]] != ' ') {
        pacChildren.add(new Integer[]{pacCoords[0] + 1, pacCoords[1]});
      }
    }
    
  }

  // MOVER
}
