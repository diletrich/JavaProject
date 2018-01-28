package Yueyou.Plane;

import java.util.Random;

public final class Bee extends FlyingObject implements Award{
	
	private int xSpeed;
	private int ySpeed;
	
	Bee()
	{
		Random rd = new Random();
		super.image = GameCenter.image_bee[rd.nextInt(GameCenter.image_bee.length)];
		super.width = super.image.getWidth();
		super.height = super.image.getHeight();
		super.xCor = rd.nextInt(GameCenter.GAME_WIDTH-width);
		super.yCor = -height;
		super.bAlive = true;
		xSpeed = GameCenter.FLYINGOBJ_MOVESPEED+ rd.nextInt(GameCenter.FLYINGOBJ_MOVESPEED);
		ySpeed = GameCenter.FLYINGOBJ_MOVESPEED+ rd.nextInt(GameCenter.FLYINGOBJ_MOVESPEED);
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		 xCor += xSpeed;
		 yCor += ySpeed;
		 if(xCor <=0)
		 {
			 xCor = 0;xSpeed = Math.abs(xSpeed);
		 }
		 if(xCor >= (GameCenter.GAME_WIDTH-width))
		 {
			 xCor = GameCenter.GAME_WIDTH-width;xSpeed = -Math.abs(xSpeed);
		 }
	}

	@Override
	public int getAward() {
		// TODO Auto-generated method stub
		Random rd = new Random();
		return rd.nextInt(Award.AWARD_END);
	}
	
	@Override
	public boolean isInbound()
	{
		return yCor <= GameCenter.GAME_HEIGHT;
	}
	
}
