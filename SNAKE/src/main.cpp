/*
 * A simple snake game
 */
#include <iostream>
#include <SDL2/SDL.h>
#include <cstdlib>
#include <ctime>
#include "SDL_Utils.h"
#include "Game.h"
#include "UI.h"

#include <cmath>
#include <chrono>

using namespace std;

const double STEP_DELAY = 0.2;
#define CLOCK_NOW chrono::system_clock::now
typedef chrono::duration<double> ElapsedTime;

const int BOARD_WIDTH = 30;
const int BOARD_HEIGHT = 20;

void start()
{
    cout << "Press any key to start game" << endl;
    waitUntilKeyPressed();
}

int main(int argc, char *argv[])
{
    srand(time(0));
    UI ui(BOARD_WIDTH, BOARD_HEIGHT);
    Game game(BOARD_WIDTH, BOARD_HEIGHT);

    start();

    auto start = CLOCK_NOW();
    ui.renderGamePlay(game);
    SDL_Event e;
    while (game.isGameRunning()) {
        if (SDL_PollEvent(&e)) {
            if (e.type == SDL_KEYUP) {
                switch (e.key.keysym.sym) {
                    case SDLK_UP: game.processUserInput(UP); break;
                    case SDLK_DOWN: game.processUserInput(DOWN); break;
                    case SDLK_LEFT: game.processUserInput(LEFT); break;
                    case SDLK_RIGHT: game.processUserInput(RIGHT); break;
                }
            }
        }

        auto end = CLOCK_NOW();
        ElapsedTime elapsed = end-start;
        if (elapsed.count() > STEP_DELAY) {
            game.nextStep();
            ui.renderGamePlay(game);
            start = end;
        }
        SDL_Delay(1);
    }

    ui.renderGameOver();
    waitUntilKeyPressed();

    ui.destroy();
    return 0;
}
