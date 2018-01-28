package Yueyou.Plane;

import java.awt.image.BufferedImage;

abstract public class FlyingObject {
	protected int width;
	protected int height;
	protected int xCor;
	protected int yCor;
	protected boolean bAlive;
	protected BufferedImage image;
	protected Rect rect = new Rect();
	
	public int getX()
	{
		return xCor;
	}
	
	public int getY()
	{
		return yCor;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public boolean isAlive()
	{
		return bAlive;
	}
	
	public void goDie()
	{
		bAlive = false;
	}
	
	public boolean isCrash(FlyingObject obj)
	{
		rect.SetRect(xCor, yCor, width, height);
		obj.rect.SetRect(obj.xCor, obj.yCor, obj.width, obj.height);
		return rect.isContact(obj.rect);
	}
	
	public abstract boolean isInbound();
	public abstract void step();
}
