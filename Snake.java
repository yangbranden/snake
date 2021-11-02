// ********************************************************
// Name: Branden Yang
// Class: CSE 110
// Title: Snake.java
// Description: Project programmed for CSE 110 Honors Contract.
//	Uses JFrame and JPanel classes to recreate the classic 
//	video game "Snake" in Java.
// Date programmed: 10/31/2021
// ********************************************************

import javax.swing.JFrame;

public class Snake {
	public static void main(String[] args) {
		JFrame gameFrame = new JFrame();
		gameFrame.add(new GamePanel());
		gameFrame.setTitle("Snake");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.pack();
		gameFrame.setVisible(true);
		gameFrame.setLocationRelativeTo(null);
	}
}
