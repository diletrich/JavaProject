package Yueyou.Plane;

//2D¾ØÐÎµÄÅö×²¼ì²â
public class Rect {
	class Point
	{
		private int x;
		private int y;
		Point(int x,int y)
		{
			this.x = x;this.y = y;
		}
		void set(int x,int y)
		{
			this.x = x;this.y = y;
		}
		int RelativePostion(Point pt)
		{
			if(x < pt.x)
			{
				if(y < pt.y)
					return RELATIVE_BOTTOMLEFT;
				else if(y > pt.y)
					return RELATIVE_TOPLEFT;
				else
					return RELATIVE_BORDERLEFT;
			}
			else if( x > pt.x)
			{
				if(y < pt.y)
					return RELATIVE_BOTTOMRIGHT;
				else if(y > pt.y)
					return RELATIVE_TOPRIGHT;
				else 
					return RELATIVE_BORDERRIGHT;
			}
			else 
			{
				if(y < pt.y)
					return RELATIVE_BORDERBOTTOM;
				else if(y > pt.y)
					return RELATIVE_BORDERTOP;
				else 
					return RELATIVE_SAME;
			}
		}
		
	}
	
	private Point topLeft;
	private Point topRight;
	private Point bottomLeft;
	private Point bottomRight;
	final static int RELATIVE_TOPLEFT = 1;
	final static int RELATIVE_TOPRIGHT = 2;
	final static int RELATIVE_BOTTOMLEFT = 3;
	final static int RELATIVE_BOTTOMRIGHT = 4;
	final static int RELATIVE_BORDERLEFT = 5;
	final static int RELATIVE_BORDERRIGHT = 6;
	final static int RELATIVE_BORDERTOP = 7;
	final static int RELATIVE_BORDERBOTTOM = 8;
	final static int RELATIVE_SAME        = 9;
	
	Rect()
	{
		topLeft = new Point(0, 0);
		topRight = new Point(0, 0);
		bottomLeft = new Point(0, 0);
		bottomRight = new Point(0, 0);
	}
	public void SetRect(int topLeftX,int topLeftY,int width,int height)
	{
		topLeft.set(topLeftX, topLeftY);
		topRight.set(topLeftX+width, topLeftY);
		bottomLeft.set(topLeftX, topLeftY+height);
		bottomRight.set(topLeftX+width, topLeftY+height);
	}
	
	public boolean isContact(Rect rt)
	{
		switch (topLeft.RelativePostion(rt.topLeft)) {
		case RELATIVE_TOPLEFT:
		{
			switch(bottomRight.RelativePostion(rt.topLeft))
			{
			case RELATIVE_BOTTOMRIGHT:
			case RELATIVE_BORDERRIGHT:
			case RELATIVE_BORDERBOTTOM:
			case RELATIVE_SAME:
				return true;
			default:
				return false;
			}
		}
		case RELATIVE_BORDERLEFT:
		{
			return bottomRight.x >= rt.topLeft.x;
		}
		case RELATIVE_BOTTOMLEFT:
		{
			switch(topRight.RelativePostion(rt.bottomLeft))
			{
			case RELATIVE_BORDERTOP:
			case RELATIVE_TOPRIGHT:
			case RELATIVE_BORDERRIGHT:
			case RELATIVE_SAME:
				return true;
			default:
				return false;
			}
		}
		case RELATIVE_TOPRIGHT:
		{
			switch(bottomLeft.RelativePostion(rt.topRight))
			{
			case RELATIVE_BORDERLEFT:
			case RELATIVE_BOTTOMLEFT:
			case RELATIVE_BORDERBOTTOM:
			case RELATIVE_SAME:
				return true;
			default:
				return false;
			}
		}
		case RELATIVE_BORDERRIGHT:
		{
			return topLeft.x <= rt.topRight.x;
		}
		case RELATIVE_BOTTOMRIGHT:
		{
			switch(topLeft.RelativePostion(rt.bottomRight))
			{
			case RELATIVE_BORDERLEFT:
			case RELATIVE_TOPLEFT:
			case RELATIVE_BORDERTOP:
			case RELATIVE_SAME:
				return true;
			default:
				return false;
			}
		}
		case RELATIVE_BORDERTOP:
		{
			return bottomLeft.y >= rt.topLeft.y;
		}
		case RELATIVE_BORDERBOTTOM:
		{
			return topLeft.y <= rt.bottomLeft.y;
		}
		default:
			break;
		}
		
		return false;
	}
	
	
}
