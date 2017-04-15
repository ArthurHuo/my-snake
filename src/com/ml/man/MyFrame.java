package com.ml.man;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MyFrame extends Frame {
	public static final MyFrame mf = new MyFrame();
	private Snake snake = Snake.snake;
	private Food food = new Food();
	private Thread myThread = new Thread(new DrawSnake());
	int speed = 500;
	private int dir;
	private int score;
	private boolean flag = true; // 是否第一次点击 开始 按钮
	private boolean pause = false; // 是否暂停(默认为 否)
	boolean frameFlag = false;// 是否显示难度调节窗口(初始为否)
	private boolean die = false;// 是否死亡
	// 得到dialog
	private Frame frame;

	private static final int width = 410;
	private static final int height = 450;
	private static final int posX = 300;
	private static final int posY = 80;

	private Panel p1;
	private Panel p2;

	private Button b1;
	private Button b2;
	// 需要改进--用choice 或者 checkbox 或者再弹出一个对话框，窗体
	private Button b3;
	private Button b4;
	private Label l1;
	Label l2;

	private MyFrame() {
		super("贪吃蛇");
	}

	private void init() {
		frame = new LevelDiff("难度调节");

		this.setBounds(posX, posY, width, height);
		this.setBackground(Color.WHITE);
		this.setResizable(false);

		p1 = new Panel();
		p1.setSize(width - 10, height - 50);// 400,400个像素点

		System.out.println(p1.getSize());

		p2 = new Panel(new GridLayout(1, 6));
		p2.setBackground(Color.MAGENTA);
		p2.setSize(width, 20);

		b1 = new Button("开始");
		b2 = new Button("暂停");
		b2.setEnabled(false);
		b3 = new Button("难度调节");
		b4 = new Button("退出");
		l1 = new Label("得分：0");
		l2 = new Label("难度：低级");
		p2.add(b1);
		p2.add(b2);
		p2.add(b3);
		p2.add(b4);
		p2.add(l1);
		p2.add(l2);

		this.add(p1, BorderLayout.CENTER);
		this.add(p2, BorderLayout.SOUTH);

		this.setVisible(true);

		MyButtonListener listener = new MyButtonListener();
		MyKeyListener keyListener = new MyKeyListener();

		b1.addMouseListener(listener);
		// 为b1添加键盘响应事件
		b1.addKeyListener(keyListener);
		p1.addKeyListener(keyListener);
		b2.addMouseListener(listener);
		b3.addMouseListener(listener);
		b4.addMouseListener(listener);

	}

	public void launchFrame() {
		// 初始化窗体
		init();
		// 启动线程
		// new Thread(new DrawSnake()).start();

		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}
		});
	}

	class MyKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			List<SnakePoint> points = snake.getSnakePoints();
			int snake_dir = e.getKeyCode();

			// 右行时 按下左键
			if (snake_dir == KeyEvent.VK_LEFT
					&& snake.getDir() == KeyEvent.VK_RIGHT) {
				snake_dir = KeyEvent.VK_RIGHT;
			}
			// 左行时按下右键
			if (snake_dir == KeyEvent.VK_RIGHT
					&& snake.getDir() == KeyEvent.VK_LEFT) {
				snake_dir = KeyEvent.VK_LEFT;
			}
			// 上行时按下下键
			if (snake_dir == KeyEvent.VK_UP
					&& snake.getDir() == KeyEvent.VK_DOWN) {
				snake_dir = KeyEvent.VK_DOWN;
			}
			// 下行时按下上键
			if (snake_dir == KeyEvent.VK_DOWN
					&& snake.getDir() == KeyEvent.VK_UP) {
				snake_dir = KeyEvent.VK_UP;
			}

			snake.setDir(snake_dir);

		}
	}

	class MyButtonListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			Button b = (Button) e.getSource();
			if (b == b1) {
				System.out.println("b1--开始");
				b1.setEnabled(false);
				b2.setEnabled(true);
				pause = false;// 取消暂停
				if (flag) { // 开始状态 是否第一次点击 开始 按钮
					snake.setDir(snake.getDir());
					flag = false;
				} else {// 从新开始，沿原来的方向(第2、3、4、……点击开始按钮)
					snake.setDir(dir);
				}

				// 吐掉异常
				try {
					myThread.start();
				} catch (Exception ex) {

				}

			}
			if (b == b2) {
				dir = snake.getDir();
				pause = true; // 暂停
				b1.setEnabled(true);
				b2.setEnabled(false);
			}
			if (b == b3) {

				if (frameFlag) {
					frameFlag = false;
					frame.setVisible(false);
				} else {
					frameFlag = true;
					frame.setVisible(true);
				}

			}
			if (b == b4) {
				mf.setVisible(false);
				System.exit(0);

			}
		}
	}

	@Override
	// update画出一整条蛇出来
	public void update(Graphics g) {

		Image offScreenImage = null;
		if (offScreenImage == null)
			offScreenImage = createImage(width, height - 20);
		Graphics gOff = offScreenImage.getGraphics();
		// 调用paint(),将缓冲图象的画笔传入
		paint(gOff);
		// 再将此缓冲图像一次性绘到代表屏幕的Graphics对象，即该方法传入的“g”上
		p1.getGraphics().drawImage(offScreenImage, 0, 0, null);

	}

	@Override
	// paint 每次只画一个点
	public void paint(Graphics g) {

		Color c = g.getColor();
		g.setColor(Color.BLUE);

		if (die) {
			g.setColor(Color.RED);
			g.setFont(new Font(null, Font.BOLD, 30));
			g.drawString("GAME OVER", 120, 160);
		}

		// 画食物
		g.fillOval(food.getX(), food.getY(), 10, 10);

		List<SnakePoint> points = snake.getSnakePoints();

		for (SnakePoint point : points) {
			g.fillOval(point.getX(), point.getY(), 10, 10);
		}

		g.setColor(c);

	}

	// 画蛇——线程
	class DrawSnake implements Runnable {

		// flag为true为下一个位置有食物，反之，则无食物
		boolean falg = false;

		@Override
		public void run() {

			// 初始化食物位置
			food.setPosition();
			while (true) {

				// pause为true时 执行暂停 不再更新点的坐标 ——阻塞
				// 是暂停就停止repaint();
				die = snake.die();
				if (die) {
					repaint();
					break;
				}

				try {

					if (!pause) {
						flag = snake.hasFood(food);
						if (flag) {
							snake.eat(food);
							l1.setText("得分： " + ++score + "");
							food.setPosition();
						}

						snake.snakeMove(snake.getDir());
						repaint();

					}

					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}
	}
}
