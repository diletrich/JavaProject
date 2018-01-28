package Yueyou.Plane;

import java.awt.image.BufferedImage;
import java.util.Random;


public class Hero extends FlyingObject {
	
	public Hero() {
		// TODO Auto-generated constructor stub
		Random rd = new Random();
		images = GameCenter.image_hero;
		image = images[0];
		width = image.getWidth();
		height = image.getHeight();
		xCor = GameCenter.GAME_WIDTH/2 - width/2;
		yCor = GameCenter.GAME_HEIGHT/2 - height/2;
	}
	private BufferedImage[] images;
	private int imageIndex = 0;
	private int doubleFire = 0;
	private int life = 5;
	
	public boolean isDoubleFire()
	{
	   if(doubleFire > 0)
	   {
		   --doubleFire;
		   return true;
	   }
	   else 
	   {
		   return false;
	   }
	}
	
	public int getLife()
	{
		return life;
	}
	
	public boolean subLife()
	{
		if(life > 1)
		{
			--life;
			return true;
		}
		else 
		{	
			life = 0;
			return false;
		}
	}
	
	public void addAward(Award award)
	{
		int type = award.getAward();
		if(type == Award.DOUBLE_FIRE)
		{
			doubleFire += 40;
		}
		else if(type == Award.LIFE)
		{
			life += 1;
		}
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		image = images[(++imageIndex/10)%images.length];
	}
	public void move(int x,int y)
	{
		xCor = x;yCor = y;
	}
	@Override
	public boolean isInbound()
	{
		return yCor <= GameCenter.GAME_HEIGHT;
	}
}
