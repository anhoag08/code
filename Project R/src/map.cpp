#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <fstream>

#include "Map.hpp"
#include "RenderWindow.hpp"
#include "Math.hpp"

using namespace std;

Map::Map(RenderWindow p_window)
:window(p_window)
{}

void Map::loadMap(string filePath, Vector2f &p_pos)
{
	int row, col;
	ifstream mapFile (filePath);
	if(mapFile.is_open())
	{
		for(int i=0; i<15; i++)
		{
			for(int j=0; j<24; j++)
			{
				mapFile >> cmap[i][j];
			}
		}
		mapFile >> row;
		mapFile >> col;
		p_pos.x = 32*col;
		p_pos.y = 32*row; 
	}
}

void Map::drawMap()
{
	src.x = src.y = dst.x = dst.y = 0;
	src.w = src.h = dst.w = dst.h = 32;

	path = window.loadTexture("res/gfx/plat.png");
	wall = window.loadTexture("res/gfx/wall.png");
	hole = window.loadTexture("res/gfx/void.png");
	zone = window.loadTexture("res/gfx/zone.png");
	flag = window.loadTexture("res/gfx/flag.png");

	for(int i=0; i<15; i++)
	{
		for(int j=0; j<24; j++)
		{
			int type = cmap[i][j];
			dst.x = j * 32;
			dst.y = i * 32;

			switch(type)
			{
				case 0:
				{
					window.draw(path, src, dst);
					break;
				}
				case 1:
				{
					window.draw(hole, src, dst);
					break;
				}
				case 2:
				{
					window.draw(wall, src, dst);
					break;
				}
				case 3:
				{
					window.draw(zone, src, dst);
					break;
				}
				case 4:
				{
					window.draw(flag, src, dst);
					break;
				}
				default:
				{
					break;
				}
			}
		}
	}
}