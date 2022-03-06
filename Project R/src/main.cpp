#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include <iostream>
#include <vector>


#include "RenderWindow.hpp"
#include "Entity.hpp"
#include "Math.hpp"
#include "Utils.hpp"

using namespace std;

int main(int argc, char* args[])
{
	if (SDL_Init(SDL_INIT_VIDEO) > 0)
	{
		cout << "VIDEO ERROR : " << SDL_GetError() << endl;
	}
	if (!(IMG_Init(IMG_INIT_PNG)))
	{
		cout << "IMG ERROR : " << SDL_GetError() << endl;
	}

	RenderWindow window("Game v1.0", 1280, 720);

	SDL_Texture* grassTexture = window.loadTexture("res/gfx/ground_grass_1.png");

	vector<Entity> entities;

	int s = 0;
	bool px = true;
	bool py = true;

	cin >> s;

	for(int i=0; i<s; i++)
	{
		Vector2f tempPos;
		cin >> tempPos.x >> tempPos.y;
		Entity tempE(tempPos, grassTexture);
		entities.push_back(tempE);
	}

	bool gameRunning = true;

	SDL_Event event;

	const float timeStep = 0.01f;
	float accumulator = 0.0f;
	float currentTime = utils::hireTimeInSeconds();

	while(gameRunning)
	{
		int startTicks = SDL_GetTicks();

		float newTime = utils::hireTimeInSeconds();
		float frameTime = newTime - currentTime;

		currentTime = newTime;

		accumulator += frameTime;

		const Uint8 *state = SDL_GetKeyboardState(NULL);

		while(accumulator >= timeStep)
		{
			while (SDL_PollEvent(&event))
			{
				if (event.type == SDL_QUIT)
				{
					gameRunning = false;
				}
				if(state[SDL_SCANCODE_W])
				{
					cout << "W" << endl;
				}
			}

			accumulator -= timeStep;
		}

		const float alpha = accumulator / timeStep;

		window.clear();

		for(Entity& e : entities)
		{
			window.render(e);

			Vector2f nPos = e.getPos();


			// if(e.getPos().x > 1280 - e.getCurrentFrame().w || e.getPos().x < 0)
			// {
			// 	if(e.getPos().x > 1280 - e.getCurrentFrame().w){px = false;}
			// 	if(e.getPos().x < 0){px = true;}
			// }

			// if(e.getPos().y > 720 - e.getCurrentFrame().h || e.getPos().y < 0)
			// {
			// 	if(e.getPos().y > 720 - e.getCurrentFrame().h){py = false;}
			// 	if(e.getPos().y < 0){py = true;}
			// }

			// if(px == true){nPos.x++;}
			// else{nPos.x--;}

			// if(py == true){nPos.y++;}
			// else{nPos.y--;}

			e.setPos(nPos);
		}

		window.display();

		int frameTicks = SDL_GetTicks() - startTicks;

		if(frameTicks < 1000 / window.getRefreshRate())
		{
			SDL_Delay(1000 / window.getRefreshRate() - frameTicks);
		}

	}

	window.cleanUp();
	SDL_Quit();

	return 0;
}