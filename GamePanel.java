// ********************************************************
// Name: Branden Yang
// Class: CSE 110
// Title: Snake.java
// Description: Project programmed for CSE 110 Honors Contract.
//	Uses JFrame and JPanel classes to recreate the classic 
//	video game "Snake" in Java.
// Date programmed: 10/31/2021
// ********************************************************

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE; // total number of spaces in game
	static final int DELAY = 75; // controls speed of game; longer delay = slower game pace
	static final String FONT = "Comic Sans MS";
	final int x[] = new int[GAME_UNITS]; // array that holds data of object x-coordinates on board
	final int y[] = new int[GAME_UNITS]; // array that holds data of object y-coordinates on board
	int bodyParts = 6; // number of units snake has (initial is 6, value changes as game goes on)
	int applesEaten, appleX, appleY;
	char direction = 'R'; // available directions: 'L', 'R', 'U' 'D'
	boolean gameIsRunning = false;
	Timer timer;
	Random random;
	
	public GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new CustomKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		gameIsRunning = true;
		generateApple();
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(gameIsRunning) {
			// creates grid to make each unit on screen visible
			g.setColor(Color.darkGray);
			for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
				g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
			}
			
			// creates initial apple
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			// creates snake
			for(int i = 0; i < bodyParts; i++) {
				if(i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(45, 180, 0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			// displays score
			g.setColor(Color.white);
			g.setFont(new Font(FONT, Font.PLAIN, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, ((SCREEN_WIDTH - metrics.stringWidth("Score" + applesEaten)) / 2), g.getFont().getSize());
		} else {
			gameOver(g);
		}
	}
	
	/*
	 * Generates new coordinates for the apple (rather than creating a new object)
	 */
	public void generateApple() {
		appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}
	
	public void move() {
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
			case 'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
			case 'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
			case 'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
		}
	}
	
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			generateApple();
		}
	}
	
	public void checkCollisions() {
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) { // if head of snake touches body
				gameIsRunning = false;
			}
		}
		
		// checks if touches any border
		if(x[0] < 0) {
			gameIsRunning = false;
		}
		if(x[0] > SCREEN_WIDTH) {
			gameIsRunning = false;
		}
		if(y[0] < 0) {
			gameIsRunning = false;
		}
		if(y[0] > SCREEN_HEIGHT) {
			gameIsRunning = false;
		}
		
		if(!gameIsRunning) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		// Game Over screen
		g.setColor(Color.white);
		g.setFont(new Font(FONT, Font.PLAIN, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", ((SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2), SCREEN_HEIGHT / 2);
		
		// displays score
		g.setColor(Color.white);
		g.setFont(new Font(FONT, Font.PLAIN, 40));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, ((SCREEN_WIDTH - metrics2.stringWidth("Score" + applesEaten)) / 2), (SCREEN_HEIGHT / 2) + g.getFont().getSize());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (gameIsRunning) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class CustomKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') { // snake can't go opposite direction of its current direction
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') { // snake can't go opposite direction of its current direction
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') { // snake can't go opposite direction of its current direction
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') { // snake can't go opposite direction of its current direction
					direction = 'D';
				}
				break;
			case KeyEvent.VK_A:
				if(direction != 'R') { // snake can't go opposite direction of its current direction
					direction = 'L';
				}
				break;
			case KeyEvent.VK_D:
				if(direction != 'L') { // snake can't go opposite direction of its current direction
					direction = 'R';
				}
				break;
			case KeyEvent.VK_W:
				if(direction != 'D') { // snake can't go opposite direction of its current direction
					direction = 'U';
				}
				break;
			case KeyEvent.VK_S:
				if(direction != 'U') { // snake can't go opposite direction of its current direction
					direction = 'D';
				}
				break;
			}
		}
	}
	
}
