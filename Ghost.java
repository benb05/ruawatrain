// Ruawatrain :: Benjamin Belotser, David Deng, Josiah Moltz
// APCS pd6
// FP01

//enable file I/O
import java.io.*;
import java.util.*;

public class Ghost {
  private char[][] _maze;
  int h,w;
  private int gX, gY; // ghost's position
  private boolean _solved;
  private boolean _won;
  private ArrayDeque<String> pacmanMoves = new ArrayDeque<String>();

  // CONSTRUCTOR
  public Ghost( String inputFile )
  {
    _won = false;
    // init 2D array to represent maze
    // (80x25 is default terminal window size)
    _maze = new char[80][25];
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

    //at init time, maze has not been solved:
    _solved = false;
  }//end constructor

  // ACCESSORS
  public int getGX()
  {
    return gX;
  }

  public int getGY()
  {
    return gY;
  }

  public boolean hasWon()
  {
    return _won;
  }

  // MUTATORS
  public void addMove(String direction)
  {
    pacmanMoves.add(direction);
  }

  public void setGX(int nX)
  {
    gX = nX;
  }

  public void setGY(int nY)
  {
    gY = nY;
  }

  public void movePacman(int pX, int pY) // updates pacman's positioning
  {
    if (_maze[pX][pY+1] == '$') { // this conditional finds pacman's old position
      _maze[pX][pY+1] = '#';
    }
    else if (_maze[pX][pY-1] == '$') {
      _maze[pX][pY-1] = '#';
    }
    else if (_maze[pX+1][pY] == '$') {
      _maze[pX+1][pY] = '#';
    }
    else if (_maze[pX-1][pY] == '$') {
      _maze[pX-1][pY] = '#';
    }
    _maze[pX][pY] = '$';
  }

  // SOLVER
  public void solve( int x, int y )
  {
    //delay(10);  // to be removed post testing era

    //primary base case
    if ( _solved ) {
      return;
    }
    //other base cases
    else if ( _maze[x][y] == '$' ) {
      _solved = true;
      //System.out.println( this ); // to be removed post testing era
      if (_maze[gX][gY+1] == '@') {
        gX = gX;
        gY = gY+1;
      }
      else if (_maze[gX][gY-1] == '@') {
        gX = gX;
        gY = gY-1;
      }
      else if (_maze[gX+1][gY] == '@') {
        gX = gX+1;
        gY = gY;
      }
      else if (_maze[gX-1][gY] == '@') {
        gX = gX-1;
        gY = gY;
      }
      else {
        gX = x;
        gY = y;
        _won = true;
      }
      return;
    }
    else if ( _maze[x][y] != '#' ) {
      return;
    }
    //otherwise, recursively solve maze from next pos over,
    //after marking current location
    else {
      _maze[x][y] = '@';
      //System.out.println( this ); //refresh screen
      recursion(x,y);
      _maze[x][y] = '.';
      //System.out.println( this ); //refresh screen
      return;
    }
  }

  // RECURSIVE OPTIMIZATION
  public void recursion(int x, int y) // minor optimization based on pacmans last move, generates recursive step in solver
  {
    if (pacmanMoves.size()>1) {
      String lastMove = pacmanMoves.remove();
    }
    String lastMove = pacmanMoves.peek();
    if (lastMove.equals("W")) {
      solve(x, y-1);
      solve(x+1, y);
      solve(x, y+1);
      solve(x-1, y);
    }
    else if (lastMove.equals("A")) {
      solve(x-1, y);
      solve(x+1, y);
      solve(x, y-1);
      solve(x, y+1);
    }
    else if (lastMove.equals("S")) {
      solve(x, y+1);
      solve(x+1, y);
      solve(x-1, y);
      solve(x, y+1);
    }
    else if (lastMove.equals("D")) {
      solve(x+1, y);
      solve(x-1, y);
      solve(x, y-1);
      solve(x, y+1);
    }
  }

  // RESET
  public void reset()
  {
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        if (_maze[j][i] == '.') {
          _maze[j][i] = '#';
        }
      }
    }
    _solved = false;
  }

  // TURN
  public void move()
  {
    solve(gX,gY);
    reset();
  }

  // FOR TESTING

  private void delay( int n ) // removed post testing era
  {
    try {
      Thread.sleep(n);
    } catch( InterruptedException e ) {
      System.exit(0);
    }
  }

  public void play()  // removed post testing era
  {
    while (_maze[gX][gY] != '$')
    {
      move();
    }
  }

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

    Ghost ms = new Ghost( mazeInputFile );

    //clear screen
    System.out.println( "[2J" );

    //display maze
    System.out.println( ms );

    ms.setGX(6);
    ms.setGY(3);
    ms.move();
    ms.movePacman(4,8);
    ms.move();
    ms.movePacman(5,8);
    ms.move();
    ms.movePacman(6,8);
    ms.move();
    ms.movePacman(7,8);
    ms.move();
    ms.movePacman(8,8);
    ms.move();
    ms.movePacman(8,9);
    ms.move();
    ms.movePacman(8,10);
    ms.move();
    ms.movePacman(8,11);
    ms.move();
    ms.movePacman(8,12);
    ms.move();
    ms.movePacman(8,13);
    ms.move();
    ms.movePacman(8,14);
    ms.play();
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
  }//end main()

}
