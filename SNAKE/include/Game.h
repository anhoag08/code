#ifndef GAME_H
#define GAME_H

#include "Snake.h"
#include "Position.h"

enum GameStatus {
    GAME_RUNNING = 1,
    GAME_STOP = 2,
    GAME_WON = 4 | GAME_STOP,
    GAME_OVER = 8 | GAME_STOP,
};

enum CellType {
    CELL_EMPTY = 0, CELL_SNAKE, CELL_CHERRY, CELL_OFF_BOARD
};

class Game
{
    const int width;
	const int height;
    std::vector< std::vector<CellType> > squares;
    Snake snake;
    Direction currentDirection;
    GameStatus status;
    int score;
    std::queue<Direction> inputQueue;
    Position cherryPosition;

	void addCherry();
	void setCellType(Position pos, CellType cellType);

public:
    Game(int _width, int _height)
        : width(_width), height(_height),
          squares(_height, vector<CellType>(_width, CELL_EMPTY)),
          snake(*this, Position(_width/2, _height/2)),
          currentDirection(Direction::RIGHT),
          status(GAME_RUNNING),
          score(0)
    {
        addCherry();
    }

    bool isGameRunning() const { return status == GAME_RUNNING; }
    bool isGameOver() const { return status == GAME_OVER; }
    void processUserInput(Direction direction);
    void nextStep();
    const std::vector< std::vector<CellType> >& getSquares() const { return squares; }
    CellType getCellType(Position p) const;
    void setGameStatus(GameStatus status);

    std::vector<Position> getSnakePositions() const;
    Position getCherryPosition() const { return cherryPosition; }
    int getScore() const { return score; }
    bool canChange(Direction current, Direction next) const;

    void snakeMoveTo(Position position);
    void snakeLeave(Position position);
};

#endif
