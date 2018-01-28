package Yueyou.Plane;

import java.util.Random;


public class AirPlane extends FlyingObject implements Enemy {

	private int ySpeed ;
	public AirPlane() {
		// TODO Auto-generated constructor stub
		Random rd = new Random();
		image = GameCenter.image_enemy;
		width = image.getWidth();
		height = image.getHeight();
		xCor = rd.nextInt(GameCenter.GAME_WIDTH-width);
		yCor = -height;
		ySpeed = GameCenter.FLYINGOBJ_MOVESPEED;
		bAlive = true;
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		yCor += ySpeed;	
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public boolean isInbound()
	{
		return yCor <= GameCenter.GAME_HEIGHT;
	}
}
