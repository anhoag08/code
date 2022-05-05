#pragma once
#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>

#include "Entity.hpp"

using namespace std;

class RenderWindow
{
public:
	RenderWindow(const char* p_title, int p_w, int p_h);
	SDL_Texture* loadTexture(const char* p_filePath);

	int getRefreshRate();

	void cleanUp();
	void clear();
	void render(Entity& p_entity);
	void draw(SDL_Texture* p_tex, SDL_Rect &src, SDL_Rect &dst);
	void display();
	SDL_Renderer* getRenderer()
	{
		return renderer;
	}
private:
	SDL_Window* window;
	SDL_Renderer* renderer;
};