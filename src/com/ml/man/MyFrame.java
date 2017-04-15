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
	private boolean flag = true; // �Ƿ��һ�ε�� ��ʼ ��ť
	private boolean pause = false; // �Ƿ���ͣ(Ĭ��Ϊ ��)
	boolean frameFlag = false;// �Ƿ���ʾ�Ѷȵ��ڴ���(��ʼΪ��)
	private boolean die = false;// �Ƿ�����
	// �õ�dialog
	private Frame frame;

	private static final int width = 410;
	private static final int height = 450;
	private static final int posX = 300;
	private static final int posY = 80;

	private Panel p1;
	private Panel p2;

	private Button b1;
	private Button b2;
	// ��Ҫ�Ľ�--��choice ���� checkbox �����ٵ���һ���Ի��򣬴���
	private Button b3;
	private Button b4;
	private Label l1;
	Label l2;

	private MyFrame() {
		super("̰����");
	}

	private void init() {
		frame = new LevelDiff("�Ѷȵ���");

		this.setBounds(posX, posY, width, height);
		this.setBackground(Color.WHITE);
		this.setResizable(false);

		p1 = new Panel();
		p1.setSize(width - 10, height - 50);// 400,400�����ص�

		System.out.println(p1.getSize());

		p2 = new Panel(new GridLayout(1, 6));
		p2.setBackground(Color.MAGENTA);
		p2.setSize(width, 20);

		b1 = new Button("��ʼ");
		b2 = new Button("��ͣ");
		b2.setEnabled(false);
		b3 = new Button("�Ѷȵ���");
		b4 = new Button("�˳�");
		l1 = new Label("�÷֣�0");
		l2 = new Label("�Ѷȣ��ͼ�");
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
		// Ϊb1��Ӽ�����Ӧ�¼�
		b1.addKeyListener(keyListener);
		p1.addKeyListener(keyListener);
		b2.addMouseListener(listener);
		b3.addMouseListener(listener);
		b4.addMouseListener(listener);

	}

	public void launchFrame() {
		// ��ʼ������
		init();
		// �����߳�
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

			// ����ʱ �������
			if (snake_dir == KeyEvent.VK_LEFT
					&& snake.getDir() == KeyEvent.VK_RIGHT) {
				snake_dir = KeyEvent.VK_RIGHT;
			}
			// ����ʱ�����Ҽ�
			if (snake_dir == KeyEvent.VK_RIGHT
					&& snake.getDir() == KeyEvent.VK_LEFT) {
				snake_dir = KeyEvent.VK_LEFT;
			}
			// ����ʱ�����¼�
			if (snake_dir == KeyEvent.VK_UP
					&& snake.getDir() == KeyEvent.VK_DOWN) {
				snake_dir = KeyEvent.VK_DOWN;
			}
			// ����ʱ�����ϼ�
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
				System.out.println("b1--��ʼ");
				b1.setEnabled(false);
				b2.setEnabled(true);
				pause = false;// ȡ����ͣ
				if (flag) { // ��ʼ״̬ �Ƿ��һ�ε�� ��ʼ ��ť
					snake.setDir(snake.getDir());
					flag = false;
				} else {// ���¿�ʼ����ԭ���ķ���(��2��3��4�����������ʼ��ť)
					snake.setDir(dir);
				}

				// �µ��쳣
				try {
					myThread.start();
				} catch (Exception ex) {

				}

			}
			if (b == b2) {
				dir = snake.getDir();
				pause = true; // ��ͣ
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
	// update����һ�����߳���
	public void update(Graphics g) {

		Image offScreenImage = null;
		if (offScreenImage == null)
			offScreenImage = createImage(width, height - 20);
		Graphics gOff = offScreenImage.getGraphics();
		// ����paint(),������ͼ��Ļ��ʴ���
		paint(gOff);
		// �ٽ��˻���ͼ��һ���Ի浽������Ļ��Graphics���󣬼��÷�������ġ�g����
		p1.getGraphics().drawImage(offScreenImage, 0, 0, null);

	}

	@Override
	// paint ÿ��ֻ��һ����
	public void paint(Graphics g) {

		Color c = g.getColor();
		g.setColor(Color.BLUE);

		if (die) {
			g.setColor(Color.RED);
			g.setFont(new Font(null, Font.BOLD, 30));
			g.drawString("GAME OVER", 120, 160);
		}

		// ��ʳ��
		g.fillOval(food.getX(), food.getY(), 10, 10);

		List<SnakePoint> points = snake.getSnakePoints();

		for (SnakePoint point : points) {
			g.fillOval(point.getX(), point.getY(), 10, 10);
		}

		g.setColor(c);

	}

	// ���ߡ����߳�
	class DrawSnake implements Runnable {

		// flagΪtrueΪ��һ��λ����ʳ���֮������ʳ��
		boolean falg = false;

		@Override
		public void run() {

			// ��ʼ��ʳ��λ��
			food.setPosition();
			while (true) {

				// pauseΪtrueʱ ִ����ͣ ���ٸ��µ������ ��������
				// ����ͣ��ֹͣrepaint();
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
							l1.setText("�÷֣� " + ++score + "");
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
