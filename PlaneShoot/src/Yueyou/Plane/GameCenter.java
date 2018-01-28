package Yueyou.Plane;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Administrator
 *
 */
public class GameCenter extends JPanel {
	static final int GAME_WIDTH = 480;
	static final int GAME_HEIGHT = 700;
	static final int TIMER_FREQUENCY = 10; 		//定时器间隔出发 ms
	static final int TIMER_ENTER_ACTION = 50;	//敌机出生间隔频率  TIMER_FREQUENCY
	static final int TIMER_MOVE_ACTION = 10;
	static final int TIMER_CLEAR_ACTION = 500;
	static final int TIMER_BULLET_ACTION = 20;	//发射子弹间隔
	static final int TIMER_INDEX_MAX = TIMER_CLEAR_ACTION; //取所有ACTION最大公倍数
	static final int AWARD_PROBALITY = 20;  //奖励物出现几率 百分比
	static final int FLYINGOBJ_MOVESPEED = 10; //飞行物移动速度 
	static final int GAME_START = 1;
	static final int GAME_RUNNING = 2;
	static final int GAME_PAUSE = 3;
	static final int GAME_OVER = 4;
	public static BufferedImage image_background;
	public static BufferedImage image_startBg;
	public static BufferedImage[] image_startPlane;
	public static BufferedImage image_pauseBg;
	public static BufferedImage image_overBg;
	public static BufferedImage[] image_bee;
	public static BufferedImage image_enemy;
	public static BufferedImage[] image_hero;
	public static BufferedImage image_bullet;
	
	private FlyingObject[]  enemys  = new FlyingObject[]{};
	private Bullet[]		bullets = new Bullet[]{};
	private static Hero		hero;
	private static int timerIndex = 0;
	private static int specailTimerIndex = 0;
	private static int gameStatus = GAME_START;
	private static int startBg_xCor;
    private static int startBg_yCor;
    private static int pauseBg_xCor;
    private static int pauseBg_yCor;
    private static int overBg_xCor;
    private static int overBg_yCor;
    private static int finalScore_xCor;
    private static int finalScroe_yCor;
    private static int score = 0;
	private Random rd = new Random();
	static
	{
		try
		{
			image_background = ImageIO.read(GameCenter.class.getResource("background.png"));
			image_bee = new BufferedImage[]{ImageIO.read(GameCenter.class.getResource("bee1.png")),ImageIO.read(GameCenter.class.getResource("bee2.png"))};
			image_enemy = ImageIO.read(GameCenter.class.getResource("enemy.png"));
			image_hero = new BufferedImage[]{ImageIO.read(GameCenter.class.getResource("hero1.png")),ImageIO.read(GameCenter.class.getResource("hero2.png"))};
			image_bullet = ImageIO.read(GameCenter.class.getResource("bullet.png"));
			image_startBg = ImageIO.read(GameCenter.class.getResource("shoot_copyright.png"));
			image_startPlane = new BufferedImage[]{ImageIO.read(GameCenter.class.getResource("game_loading1.png")),ImageIO.read(GameCenter.class.getResource("game_loading2.png")),ImageIO.read(GameCenter.class.getResource("game_loading3.png"))};
			image_pauseBg = ImageIO.read(GameCenter.class.getResource("btn_finish.png"));
			image_overBg = ImageIO.read(GameCenter.class.getResource("gameover.png"));
			startBg_xCor = GAME_WIDTH/2-image_startBg.getWidth()/2;
			startBg_yCor = GAME_HEIGHT/2-image_startBg.getHeight()/2;
			pauseBg_xCor = GAME_WIDTH/2-image_pauseBg.getWidth()/2;
			pauseBg_yCor = GAME_HEIGHT/2-image_pauseBg.getHeight()/2;
			finalScore_xCor = GAME_WIDTH/2;
			finalScroe_yCor = GAME_HEIGHT/2+200;
			hero = new Hero();
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}
	public static void main(String[] args)
	{
		GameCenter game = new GameCenter();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.setSize(GAME_WIDTH, GAME_HEIGHT);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e){
				if(gameStatus == GAME_RUNNING)
					hero.move(e.getX(), e.getY());	
			}
		});
		frame.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e)
			{
				
			}
			public void mouseExited(MouseEvent e) 
			{
				if(gameStatus == GAME_RUNNING)
					gameStatus = GAME_PAUSE;
			}
			public void mouseClicked(MouseEvent e) 
			{
				if(gameStatus == GAME_START)
					gameStatus = GAME_RUNNING;
				else if(gameStatus == GAME_PAUSE)
					gameStatus = GAME_RUNNING;
				else if(gameStatus == GAME_OVER)
					gameStatus = GAME_START;
			}
		});
		game.action();
	}
	
	void action()
	{
		Timer tm = new Timer();
		tm.schedule(new TimerTask() {
			@Override
			public void run() {
				if(gameStatus == GAME_RUNNING)
				{
					if(++timerIndex == TIMER_INDEX_MAX) timerIndex = 0;
					enterAction();
					moveAction();
					clearAction();
				}
				repaint();
				// TODO Auto-generated method stub
			}
		}, TIMER_FREQUENCY, TIMER_FREQUENCY);
	}
	
	void enterAction()
	{
		bulletEnterAction();
		if(timerIndex%TIMER_ENTER_ACTION != 0) return ;
		FlyingObject flyObj = nextOne();
		enemys = Arrays.copyOf(enemys, enemys.length+1);
		enemys[enemys.length-1] = flyObj;
	}
	
	void bulletEnterAction()
	{
		if(timerIndex % TIMER_BULLET_ACTION != 0) return ;
		Bullet[] newBullets = newBullet();
		int length = bullets.length;
		bullets = Arrays.copyOf(bullets, bullets.length+newBullets.length);
		for(int i=0,j=newBullets.length;i<j;++i)
		{
			bullets[length+i] = newBullets[i];
		}
		//System.out.println("bulletsBorn:" + bullets.length);
	}
	
	void moveAction()
	{
		if(timerIndex % TIMER_MOVE_ACTION != 0) return ;
		for(int i=0,j=enemys.length;i<j;++i)
		{
			if(enemys[i].isAlive())
				enemys[i].step();
		}	
		for(int i=0,j=bullets.length;i<j;++i)
		{
			if(bullets[i].isAlive())
				bullets[i].step();
		}
		hero.step();
		crashAction();
		
	}
	
	void crashAction()
	{
		for(int i=0,j=enemys.length;i<j;++i)
		{
			FlyingObject flyObj = enemys[i];
			if(!flyObj.isAlive() || !flyObj.isInbound()) continue;
			//hero与其他的碰撞检测
			if(hero.isCrash(flyObj))
			{
				if(!hero.subLife())
				{
					//游戏结束 
					gameStatus = GAME_OVER;
					break;
				}
				flyObj.goDie();
				continue;
			}
			//子弹的碰撞检测
			for(int l=0,m=bullets.length;l<m;++l)
			{
				Bullet bullet = bullets[l];
				if(!bullet.isAlive() || !bullet.isInbound())
					continue;
				if(bullet.isCrash(flyObj))
				{
					if(flyObj instanceof Bee)
					{
						//奖励
						hero.addAward((Bee)flyObj);
					}
					else if(flyObj instanceof AirPlane)
					{
						//加分
						score += ((Enemy)flyObj).getScore();
					}
					flyObj.goDie();
					bullet.goDie();
					break;
				}
			}
		}
		
		
	}
	
	void clearAction()
	{
		//清除无用的敌机和奖励机
		if(timerIndex % TIMER_CLEAR_ACTION != 0) return ;
		int length = enemys.length;
		FlyingObject[] aliveFlyings = new FlyingObject[length];
		int index = 0;
		for(int i =0;i<length;++i)
		{
			FlyingObject obj = enemys[i];
			if(obj.isInbound() && obj.isAlive())
			{
				aliveFlyings[index++] = obj;
			}		
		}
		if(index != length)
			enemys = Arrays.copyOfRange(aliveFlyings, 0, index);
	    //清除无用的子弹
		length = bullets.length;
	    Bullet[] aliveBullets = new Bullet[length];
	    index = 0;
	    for(int i =0;i<length;++i)
	    {
	    	Bullet bullet = bullets[i];
	    	if(bullet.isInbound() && bullet.isAlive())
	    	{
	    		aliveBullets[index++] = bullet;
	    	}
	    }
	    if(index != length)
	    	bullets = Arrays.copyOfRange(aliveBullets, 0, index);
	   // System.out.println("flyings:"+enemys.length + " ------- " + "bullets:"+ bullets.length);
	}
	
	FlyingObject nextOne()
	{
		int r = rd.nextInt(100);
		if(r < AWARD_PROBALITY )
		{
			return new Bee();
		}
		else 
		{
			return new AirPlane();
		}
	}
	
	Bullet[] newBullet()
	{
		if(hero.isDoubleFire())
			return new Bullet[]{new Bullet(hero.getX(), hero.getY()-hero.getHeight()/10),
								new Bullet(hero.getX()+hero.getWidth(),hero.getY()-hero.getHeight()/10)};
		else
			return new Bullet[]{new Bullet(hero.getX()+hero.getWidth()/2, hero.getY() - hero.getHeight()/10)};
	}
	
	public void paint(Graphics g)
	{
			paintBg(g);
			paintEnemys(g);
			paintBullets(g);
			paintHero(g);
			paintOther(g);

	}
	
	private void paintBg(Graphics g)
	{
		g.drawImage(image_background, 0, 0, null);
	}
	
	private void paintOther(Graphics g)
	{
		switch (gameStatus) {
		case GAME_RUNNING:
			g.drawString("当前得分:"+score, 40, 40);
			g.drawString("剩余生命:"+hero.getLife(), 40, 60);
			break;
		case GAME_START:
			g.drawImage(image_startBg, startBg_xCor, startBg_yCor, null);
			int index = (++specailTimerIndex*TIMER_FREQUENCY/300)%image_startPlane.length;
			g.drawImage(image_startPlane[index],GAME_WIDTH*(index+1)/5,GAME_HEIGHT*3/4,null);
			break;
		case GAME_PAUSE:
			g.drawImage(image_pauseBg, pauseBg_xCor, pauseBg_yCor, null);
			g.drawString("当前得分:"+score, 40, 40);
			g.drawString("剩余生命:"+hero.getLife(), 40, 60);
			break;
		case GAME_OVER:
			g.drawImage(image_overBg, 0, 0, null);
			g.drawString(":"+score, finalScore_xCor, finalScroe_yCor);
			break;
		default:
			break;
		}
		
	}
	
	private void paintEnemys(Graphics g)
	{
		FlyingObject enemy = null;
		for(int i=0,j=enemys.length;i<j;++i)
		{
			enemy = enemys[i];
			if(enemy!=null && enemy.isInbound() && enemy.isAlive())
				g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), null);
		}
	}
	
	private void paintBullets(Graphics g)
	{
		Bullet bullet = null;
		for(int  i=0,j=bullets.length;i<j;++i)
		{
			bullet = bullets[i];
			if(bullet!=null && bullet.isAlive() && bullet.isInbound())
				g.drawImage(bullets[i].getImage(), bullets[i].getX(), bullets[i].getY(), null);
		}
	}
	
	private void paintHero(Graphics g)
	{
		g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
	}
}
