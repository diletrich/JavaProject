package Yueyou.Plane;

public class Bullet extends FlyingObject {
	
	private int ySpeed = -4*GameCenter.FLYINGOBJ_MOVESPEED;
	public Bullet(int x,int y) {
		// TODO Auto-generated constructor stub
		xCor = x;
		yCor = y;
		image = GameCenter.image_bullet;
		width = image.getWidth();
		height = image.getHeight();
		bAlive = true;
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		yCor += ySpeed;
	}

	@Override
	public boolean isInbound()
	{
		return yCor >= 0;
	}
}
