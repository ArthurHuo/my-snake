package com.ml.man;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Snake {
	private Snake() {
		init();
	}

	public static final Snake snake = new Snake();

	private SnakePoint head;
	private int dir;

	private List<SnakePoint> snakePoints;

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	// 得到蛇链
	public List<SnakePoint> getSnakePoints() {
		return snakePoints;
	}

	private void init() {
		head = new SnakePoint(20, 0);
		dir = KeyEvent.VK_RIGHT;

		snakePoints = new ArrayList<SnakePoint>();
		snakePoints.add(head);
		snakePoints.add(new SnakePoint(10, 0));
		snakePoints.add(new SnakePoint(0, 0));
	}

	public void snakeMove(int dir) {
		// 得到蛇链
		List<SnakePoint> points = snakePoints;
		// 得到head
		SnakePoint head = points.get(0);
		// point为中间变量
		SnakePoint point = new SnakePoint();
		point.setX(head.getX());
		point.setY(head.getY());
		int x = point.getX();
		int y = point.getY();

		switch (dir) {
		case KeyEvent.VK_LEFT:
			x = x - 10;
			if (x < 0) {
				x = x + 400;
			}
			point.setX(x);
			break;

		case KeyEvent.VK_RIGHT:
			x = x + 10;
			if (x >= 400) {
				x = 400 - x;
			}
			point.setX(x);
			break;

		case KeyEvent.VK_UP:
			y = y - 10;
			if (y < 0) {
				y = y + 400;
			}
			point.setY(y);
			System.out.println("[][][][]" + point.getX() + "[][][][]"
					+ point.getY());
			break;

		case KeyEvent.VK_DOWN:
			y = y + 10;
			if (y >= 400) {
				y = 400 - y;
			}
			point.setY(y);
			break;

		}

		// 身体的每个部位坐标向前移 身体的下一个点 为上一个点的坐标 头坐标为食物坐标或者keylistener后的坐标

		for (int i = points.size() - 1; i >= 0; i--) {
			if (i == 0) {
				points.get(0).setX(point.getX());
				points.get(0).setY(point.getY());
			} else {
				points.get(i).setX(points.get(i - 1).getX());
				points.get(i).setY(points.get(i - 1).getY());
			}

		}

	}

	public boolean die() {
		List<SnakePoint> points = snakePoints;
		SnakePoint head = points.get(0);

		for (int i = points.size() - 1; i >0; i--) {
			int x = points.get(i).getX();
			int y = points.get(i).getY();
			if (x == head.getX() && y == head.getY()) {
				return true;
			}
		}
		
		return false;

	}

	public void eat(SnakePoint food) {

		List<SnakePoint> points = snakePoints;
		SnakePoint point = new SnakePoint();
		point.setX(food.getX());
		point.setY(food.getY());
		points.add(0, point);
	}

	public boolean hasFood(SnakePoint food) {
		boolean flag = false;
		List<SnakePoint> points = snakePoints;
		SnakePoint head = points.get(0);
		int x = head.getX();
		int y = head.getY();
		switch (dir) {
		case KeyEvent.VK_LEFT:
			x = x - 10;
			if (x == food.getX() && y == food.getY()) {
				flag = true;
			}

			break;

		case KeyEvent.VK_RIGHT:
			x = x + 10;
			if (x == food.getX() && y == food.getY()) {
				flag = true;
			}
			break;

		case KeyEvent.VK_UP:
			y = y - 10;
			if (x == food.getX() && y == food.getY()) {
				flag = true;
			}
			break;

		case KeyEvent.VK_DOWN:
			y = y + 10;
			if (x == food.getX() && y == food.getY()) {
				flag = true;
			}
			break;

		}

		return flag;
	}
}
