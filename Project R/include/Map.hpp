#pragma once
#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <fstream>

#include "RenderWindow.hpp"
#include "Math.hpp"

using namespace std;

class Map
{
public:
	Map(RenderWindow p_window);
	void loadMap(string filePath, Vector2f &p_pos);
	void drawMap();
	void startPos(Vector2f &p_pos);
	int cmap[15][24];

private:
	SDL_Rect src, dst;
	RenderWindow window;
	
	SDL_Texture* path;
	SDL_Texture* hole;
	SDL_Texture* zone;
	SDL_Texture* flag;
	SDL_Texture* wall;
};