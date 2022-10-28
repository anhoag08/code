package bomman.entity.enemy;

import bomman.entity.Bomb;
import bomman.entity.CommonEntity;
import bomman.manager.EntityManager;
import bomman.entity.Flame;
import bomman.manager.GameManager;
import bomman.manager.Sprite;
import bomman.manager.TilesManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class InvisibleEnemy extends CommonEntity {
	private static int characterVelocity = 2;

	private int count = 0;
	private int invisibleTime = 100;

	private List<DIRECTION> canMove;

	public InvisibleEnemy(int xUnit, int yUnit, Image img) {
		super(xUnit, yUnit, img);
		canMove = new ArrayList<>();
	}

	public static int getCharacterVelocity() {
		return characterVelocity;
	}

	@Override
	public void update() {
		if (getAlive() == 0) {
			if (getXPosition() % Sprite.SCALED_SIZE == 0 && getYPosition() % Sprite.SCALED_SIZE == 0) {
				for (int i = 0; i < 4; i++) {
					switch (i) {
						default -> this.setDirect(DIRECTION.LEFT);
						case 1 -> this.setDirect(DIRECTION.RIGHT);
						case 2 -> this.setDirect(DIRECTION.UP);
						case 3 -> this.setDirect(DIRECTION.DOWN);
					}
					int col = (getXPosition() / Sprite.SCALED_SIZE) + this.getDirect().moveX;
					int row = (getYPosition() / Sprite.SCALED_SIZE) + this.getDirect().moveY;
					if (GameManager.map[row][col] == 0 && ! EntityManager.hasBomb(col, row)) {
						canMove.add(this.getDirect());
					}
					if (canMove.size() != 0) {
						int rand_dir = (int) (Math.random() * (canMove.size()));
						this.setDirect(canMove.get(rand_dir));
					}
					if (collideBlocks(this, GameManager.map, TilesManager.gameTiles)) {
						this.setDirect(this.getOppositeDirect());
					}
					for (Bomb b: Bomb.bombs) {
						if (collisionWithEntity(this, b)) {
							this.setDirect(this.getOppositeDirect());
						}
					}
				}
			}

			this.move(this.getDirect(), characterVelocity);
			canMove.clear();


			for (Flame i : Flame.flames) {
				if (collisionWithFlame(this, i)) {
					this.setAlive(1);
				}
			}
		}
	}

	@Override
	public void render(GraphicsContext gc, double t) {
		double frame = (int) (t / 0.083) % 12 % 3;
		invisibleTime--;
		if (getAlive() == 1) {
			if (frame == 0) count++;
			if (count == 10) {
				this.setImg(Sprite.newEnemy3_up2.getFxImage());
			}
			if (count == 20) {
				this.setImg(Sprite.mob_dead1.getFxImage());
			}
			if (count == 30) {
				this.setImg(Sprite.mob_dead2.getFxImage());
			}
			if (count == 40) {
				this.setImg(Sprite.mob_dead3.getFxImage());
			}
			if (count == 50) {
				setAlive(2);
			}
		} else {
			if (this.getDirect() == DIRECTION.LEFT) {
				if (frame == 0) this.setImg(Sprite.newEnemy3_left1.getFxImage());
				if (frame == 1) this.setImg(Sprite.newEnemy3_left2.getFxImage());
				if (frame == 2) this.setImg(Sprite.newEnemy3_left3.getFxImage());
				if (invisibleTime <= 0) {
					this.setImg(Sprite.newFloor.getFxImage());
				}
				if (invisibleTime == -100) {
					invisibleTime = 100;
				}
			}
			if (this.getDirect() == DIRECTION.RIGHT) {
				if (frame == 0) this.setImg(Sprite.newEnemy3_right1.getFxImage());
				if (frame == 1) this.setImg(Sprite.newEnemy3_right2.getFxImage());
				if (frame == 2) this.setImg(Sprite.newEnemy3_right3.getFxImage());
				if (invisibleTime <= 0) {
					this.setImg(Sprite.newFloor.getFxImage());
				}
				if (invisibleTime == -100) {
					invisibleTime = 100;
				}
			}
			if (this.getDirect() == DIRECTION.UP) {
				if (frame == 0) this.setImg(Sprite.newEnemy3_up1.getFxImage());
				if (frame == 1) this.setImg(Sprite.newEnemy3_up2.getFxImage());
				if (frame == 2) this.setImg(Sprite.newEnemy3_up3.getFxImage());
				if (invisibleTime <= 0) {
					this.setImg(Sprite.newFloor.getFxImage());
				}
				if (invisibleTime == -100) {
					invisibleTime = 100;
				}
			}
			if (this.getDirect() == DIRECTION.DOWN) {
				if (frame == 0) this.setImg(Sprite.newEnemy3_up1.getFxImage());
				if (frame == 1) this.setImg(Sprite.newEnemy3_up2.getFxImage());
				if (frame == 2) this.setImg(Sprite.newEnemy3_up3.getFxImage());
				if (invisibleTime <= 0) {
					this.setImg(Sprite.newFloor.getFxImage());
				}
				if (invisibleTime == -100) {
					invisibleTime = 100;
				}
			}
		}
		gc.drawImage(getImg(), getXPosition(), getYPosition());
	}
}
