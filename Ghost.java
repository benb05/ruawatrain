// Ruawatrain :: Benjamin Belotser, David Deng, Josiah Moltz
// APCS pd6
// FP01

public class Ghost extends Character{
  private boolean intelligence;
  private int pX, pY;
  private boolean _solved;
  private boolean _won;
  private int difficulty; // from 1 to 4, the higher the number the higher difficulty
  private boolean alreadyEaten; //tracks if current ghost has already been eaten or not
  static int movesVulnerable; //tracks number of turns until ghosts are no longer vulnerable
  static int points; //tracks value of ghosts
  private int respawnTime; //wait this many turns before ghost reenters map

  // CONSTRUCTOR
  public Ghost( String inputFile, int d )
  {
    super(inputFile);
    difficulty = d;
    intelligence = true;
    _won = false;
    dX = 1;
    dY = 0;
    _solved = false;
    alreadyEaten = false;
    movesVulnerable = 0;
    points = 200;
    respawnTime = 4;
  }//end constructor

  // ACCESSORS
  public boolean hasWon()
  {
    return _won;
  }

  public boolean isEatable() {
    return !alreadyEaten && movesVulnerable > 0;
  }

  public void movePacman(int x, int y) // updates pacman's positioning
  {
    if (_maze[pX][pY] == '$') {
      _maze[pX][pY] = '#';
    }
    pX = x;
    pY = y;
    _maze[pX][pY] = '$';
  }

  // SOLVER
  public void solve( int x, int y )
  {
    //delay(20);  // to be removed post testing era

    //primary base case
    if ( _solved ) {
      return;
    }
    //other base cases
    else if ( _maze[x][y] == '$' ) {
      _solved = true;
      // if it is solved, set the first step to be the closest @ symbol from init position
      if (_maze[xPos][yPos+1] == '@') {
        dX = 0;
        dY = 1;
      }
      else if (_maze[xPos][yPos-1] == '@') {
        dX = 0;
        dY = -1;
      }
      else if (_maze[xPos+1][yPos] == '@') {
        dX = 1;
        dY = 0;
      }
      else if (_maze[xPos-1][yPos] == '@') {
        dX = -1;
        dY = 0;
      }
      else {  // this means that we didn't have to move (or we only moved once), so we are ON TOP of pacman
        _won = true;
        xPos = x; // just to ensure that the ghost actually moves on to pacman, for visual effects
        yPos = y;
        return;
      }
      xPos = xPos+dX; // update position
      yPos = yPos+dY;
      return;
    }
    else if ( _maze[x][y] != '#' ) {  // DEADEND!
      return;
    }
    //otherwise, recursively solve maze from next pos over, after marking current location
    else {
      _maze[x][y] = '@';
      distOpt(x,y);
      _maze[x][y] = '.';
      return;
    }
  }

  // OPTIMIZATION
  public int abs(int x) { // helper method
    if (x < 0) {
      return -x;
    }
    else {
      return x;
    }
  }

  public void distOpt(int x, int y) { // optimizes the direction the ghost looks based on its positioning from pacman
    // We try to close the "largest distance" first
    // If the x distance from pacman is larger than the y distance, try to cover x distance first, then y distance
    // looking "towards pacman" for each of these
    if ( x >= pX && y >= pY) {
      if (abs(x-pX) > abs(y-pY)) {
        solve(x-1,y);
        solve(x,y-1);
        solve(x+1,y);
        solve(x,y+1);
      }
      else {
        solve(x,y-1);
        solve(x-1,y);
        solve(x+1,y);
        solve(x,y+1);
      }
    }
    else if ( x >= pX && y < pY) {
      if (abs(x-pX) > abs(y-pY)) {
        solve(x-1,y);
        solve(x,y+1);
        solve(x+1,y);
        solve(x,y-1);
      }
      else {
        solve(x,y+1);
        solve(x-1,y);
        solve(x+1,y);
        solve(x,y-1);
      }
    }
    else if (x < pX && y >= pY) {
      if (abs(x-pX) > abs(y-pY)) {
        solve(x+1,y);
        solve(x,y-1);
        solve(x-1,y);
        solve(x,y+1);
      }
      else {
        solve(x,y-1);
        solve(x+1,y);
        solve(x-1,y);
        solve(x,y+1);
      }
    }
    else {
      if (abs(x-pX) > abs(y-pY)) {
        solve(x+1,y);
        solve(x,y+1);
        solve(x-1,y);
        solve(x,y-1);
      }
      else {
        solve(x,y+1);
        solve(x+1,y);
        solve(x-1,y);
        solve(x,y-1);
      }
    }
  }

  // RESET
  public void reset()
  { // reset the map after a probe
    for (int i = 0; i < _maze[0].length; i++) {
      for (int j = 0; j < _maze.length; j++) {
        if (_maze[j][i] == '.') {
          _maze[j][i] = '#';
        }
      }
    }
    _solved = false;
  }

  // MOVING
  public void step() {  // the motion we will make
    if (xPos == 18 && yPos == 17) {
        if (respawnTime > 0) {
            respawnTime--;
            return;
        }
        else {
            xPos = 19;
            respawnTime = 4;
            return;
        }
    }

    if (_maze[xPos][yPos] == '$') { // ensure pacman hasn't moved on top of us
      if (isEatable()) {
        respawn();
        alreadyEaten = false;
        Pacman.addScore(points);
        Pacman.ghostsEaten += 1;
        points *= 2;
      }
      else {
        _won = true;
      }
    }

    if (intelligence) {
      solve(xPos,yPos);
    }
    else {
      randomMove();
    }

    if (_maze[xPos][yPos] == '$') { // post move check
        if (isEatable()) {
            respawn();
            alreadyEaten = true;
            Pacman.addScore(points);
            Pacman.ghostsEaten += 1;
            points *= 2;
        }
        else {
            _won = true;
        }
    }
  }

  public void updateIntelligence()
  { // update intelligence based on difficulty level, the higher the difficulty the higher the chance for intelligence
    if (isEatable()) {
        intelligence = false;
        return;
    }
    int rint = (int) (8*Math.random());
    if (difficulty == 4) {
      intelligence = rint > 6 ? false : true;
    }
    else if (difficulty == 3) {
      intelligence = rint > 5 ? false : true;
    }
    else if (difficulty == 2) {
      intelligence = rint > 3 ? false : true;
    }
    else {
      intelligence = rint > 1 ? false : true;
    }
  }

  public void move()
  {
    if (atIntersection()) { // ensures movements are "smooth", and we don't randomly go right left right left etc
      updateIntelligence();
    }
    step();
    reset();
  }

  public void randomMove()
  {
    int rint;
    if (atIntersection()) {
      rint = (int) (3*Math.random());
      if (rint == 1) {
        dX = (dX+1)%2;
        dY = (dY+1)%2;
      }
      else if (rint == 2) {
        dX = -( (dX+1)%2 );
        dY = -( (dY+1)%2 );
      }
    }
    while (!onPath(xPos+dX, yPos+dY)) {
      rint = (int) (4*Math.random());
      if (rint == 0) {
        dX = 1;
        dY = 0;
      }
      else if (rint == 1) {
        dX = -1;
        dY = 0;
      }
      else if (rint == 2) {
        dX = 0;
        dY = 1;
      }
      else if (rint == 3) {
        dX = 0;
        dY = -1;
      }
    }
    xPos += dX;
    yPos += dY;
  }

  public void respawn() {
    xPos = 19;
    yPos = 19;
  }
}
