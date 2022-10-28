package bomman.entity.enemy;

import bomman.entity.*;
import bomman.manager.EntityManager;
import bomman.manager.GameManager;
import bomman.manager.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public class SuicidalEnemy extends CommonEntity {
	private static int characterVelocity = 2;

	private int count = 0;

	private List<CommonEntity.DIRECTION> pathToBomber;

	private static AStarAlgorithm aStar = new AStarAlgorithm();


	public SuicidalEnemy(int xUnit, int yUnit, Image img) {
		super(xUnit, yUnit, img);
	}

	public static int getCharacterVelocity() {
		return characterVelocity;
	}

	public void setCharacterVelocity(int characterVelocity) {
		SuicidalEnemy.characterVelocity = characterVelocity;
	}

	public void findPath(int[][] gridGame, int startX, int startY, int desX, int desY) {
		aStar.aStarSearch(gridGame, startX, startY, desX, desY);
		pathToBomber = aStar.getPathToDes();
		if (pathToBomber.isEmpty()) {
			aStar.aStarSearch(gridGame, this.getYPosition() / Sprite.SCALED_SIZE,
					this.getXPosition() / Sprite.SCALED_SIZE, 1, 4);
		}
	}

	@Override
	public void update() {
		if (getAlive() == 0) {

			this.findPath(GameManager.map,
					this.getYPosition() / Sprite.SCALED_SIZE,
					this.getXPosition() / Sprite.SCALED_SIZE,
					EntityManager.bomberman.getYPosition() / Sprite.SCALED_SIZE + 2,
					EntityManager.bomberman.getXPosition() / Sprite.SCALED_SIZE);


			if (getXPosition() % Sprite.SCALED_SIZE == 0 && getYPosition() % Sprite.SCALED_SIZE == 0) {
				if (!pathToBomber.isEmpty()) {
					this.setDirect(pathToBomber.get(0));
				} else {
					this.setDirect(DIRECTION.COLLIDE);
					setAlive(1);
					Bomb.bombs.add(new Bomb(this.getXPosition()/Sprite.SCALED_SIZE, this.getYPosition()/Sprite.SCALED_SIZE,
							Sprite.bomb.getFxImage(), 50));
				}
			}

			this.move(this.getDirect(), characterVelocity);
			pathToBomber.clear();

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
		if (getAlive() == 1) {
			if (frame == 0) count++;
			if (count == 10) {
				this.setImg(Sprite.newEnemy5_up2.getFxImage());
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
			if (this.getDirect() == CommonEntity.DIRECTION.LEFT) {
				if (frame == 0) this.setImg(Sprite.newEnemy5_left1.getFxImage());
				if (frame == 1) this.setImg(Sprite.newEnemy5_left2.getFxImage());
				if (frame == 2) this.setImg(Sprite.newEnemy5_left3.getFxImage());
			}
			if (this.getDirect() == CommonEntity.DIRECTION.RIGHT) {
				if (frame == 0) this.setImg(Sprite.newEnemy5_right1.getFxImage());
				if (frame == 1) this.setImg(Sprite.newEnemy5_right2.getFxImage());
				if (frame == 2) this.setImg(Sprite.newEnemy5_right3.getFxImage());
			}
			if (this.getDirect() == CommonEntity.DIRECTION.UP) {
				if (frame == 0) this.setImg(Sprite.newEnemy5_up1.getFxImage());
				if (frame == 1) this.setImg(Sprite.newEnemy5_up2.getFxImage());
				if (frame == 2) this.setImg(Sprite.newEnemy5_up3.getFxImage());
			}
			if (this.getDirect() == CommonEntity.DIRECTION.DOWN) {
				if (frame == 0) this.setImg(Sprite.newEnemy5_up1.getFxImage());
				if (frame == 1) this.setImg(Sprite.newEnemy5_up2.getFxImage());
				if (frame == 2) this.setImg(Sprite.newEnemy5_up3.getFxImage());
			}
		}
		gc.drawImage(getImg(), getXPosition(), getYPosition());
	}
}
