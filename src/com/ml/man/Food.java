package com.ml.man;

import java.util.List;
import java.util.Random;

public class Food extends SnakePoint {

	private int x;
	private int y;

	public Food() {
	}


	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setPosition() {
		boolean flag = true;
		Snake snake = Snake.snake;
		List<SnakePoint> points = snake.getSnakePoints();

		Random random = new Random();
		// 设置随机产生的点，十个像素为一个单位(0<=x,y<=340)
		int x = random.nextInt(40) * 10;
		int y = random.nextInt(40) * 10;
		this.x = x;
		this.y = y;
		
	}
}
