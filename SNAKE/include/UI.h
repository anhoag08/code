#ifndef UI__H_
#define UI__H_

#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include "SDL_Utils.h"

enum PictureID {
    PIC_CHERRY = 0, PIC_SNAKE_VERTICAL,
    PIC_SNAKE_HORIZONTAL, PIC_SNAKE_HEAD, PIC_COUNT
};

const int CELL_SIZE = 30;

const SDL_Color BOARD_COLOR = {0, 0, 0};
const SDL_Color LINE_COLOR = {128, 128, 128};


class UI {
public:
    const int SCREEN_WIDTH = 900;
    const int SCREEN_HEIGHT = 600;
    const char* WINDOW_TITLE = "Snake Game";

    const int BOARD_WIDTH;
    const int BOARD_HEIGHT;

    SDL_Window* window;
    SDL_Renderer* renderer;

    SDL_Texture* pictures[PIC_COUNT];


    UI( int boardWidth, int boardHeight) :BOARD_WIDTH(boardWidth), BOARD_HEIGHT(boardHeight)
    {
        initSDL(window, renderer, WINDOW_TITLE, SCREEN_WIDTH, SCREEN_HEIGHT);

        pictures[PIC_CHERRY] = loadTexture("res/gfx/cherry.png", renderer);
        pictures[PIC_SNAKE_VERTICAL] = loadTexture("res/gfx/snake_vertical.png", renderer);
        pictures[PIC_SNAKE_HORIZONTAL] = loadTexture("res/gfx/snake_horizontal.png", renderer);
        pictures[PIC_SNAKE_HEAD] = loadTexture("res/gfx/snake_head.png", renderer);
    }

    SDL_Texture* getImage(PictureID id) const { return pictures[id]; }

    void clearScreen() {
        //SDL_SetRenderDrawColor(renderer, BGR_COLOR.r, BGR_COLOR.g, BGR_COLOR.b, 0);
        SDL_RenderClear(renderer);
    }

    void renderGameOver() {
        clearScreen();
        SDL_RenderPresent( renderer );
    }

    void destroy() {
        for (SDL_Texture* texture : pictures)
            SDL_DestroyTexture(texture);
        quitSDL(window, renderer);
    }

    void drawCell(int left, int top, Position pos, SDL_Texture* texture)
    {
        SDL_Rect cell;
        cell.x = left + pos.x * CELL_SIZE + 5;
        cell.y = top + pos.y * CELL_SIZE + 5;
        cell.w = CELL_SIZE-10;
        cell.h = CELL_SIZE-10;
        SDL_RenderCopy(renderer, texture, NULL, &cell);
    }

    void drawCherry(int left, int top, Position pos)
    {
        drawCell(left, top, pos, getImage(PIC_CHERRY));
    }

    void drawSnake(int left, int top, const vector<Position>& pos)
    {
        // snake's head
        drawCell(left, top, pos[pos.size()-1], getImage(PIC_SNAKE_HEAD));

        // snake's body
        for (int i = pos.size() - 2; i >= 0; i--) {
            SDL_Texture* texture = getImage(
                pos[i].y == pos[i+1].y ? PIC_SNAKE_HORIZONTAL : PIC_SNAKE_VERTICAL);
            drawCell(left, top, pos[i], texture);
        }
    }

    void drawVerticalLine(int left, int top, int cells)
    {
        SDL_SetRenderDrawColor(renderer, LINE_COLOR.r, LINE_COLOR.g, LINE_COLOR.b, 0);
        SDL_RenderDrawLine(renderer, left, top, left, top + cells * CELL_SIZE);
    }

    void drawHorizontalLine(int left, int top, int cells)
    {
        SDL_SetRenderDrawColor(renderer, LINE_COLOR.r, LINE_COLOR.g, LINE_COLOR.b, 0);
        SDL_RenderDrawLine(renderer, left, top, left + cells * CELL_SIZE, top);
    }

    void renderGamePlay(const Game& game)
    {
        int top = 0, left = 0;
        SDL_SetRenderDrawColor(renderer, BOARD_COLOR.r, BOARD_COLOR.g, BOARD_COLOR.b, 0);
        SDL_RenderClear(renderer);

        for (int x = 0; x <= BOARD_WIDTH; x++)
            drawVerticalLine(left + x*CELL_SIZE, top, BOARD_HEIGHT);
        for (int y = 0; y <= BOARD_HEIGHT; y++)
            drawHorizontalLine(left, top+y * CELL_SIZE, BOARD_WIDTH);

        drawCherry(left, top, game.getCherryPosition());
        drawSnake(left, top, game.getSnakePositions());

        SDL_RenderPresent(renderer);
    }

    void renderGameOver(const Game& game)
    {

    }
};

#endif
