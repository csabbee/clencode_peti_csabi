package com.clean.strategy;

import java.util.ArrayList;
import java.util.List;

import com.clean.ship.Point;

public class XYGuessGenerator {
	
	public XYGuessGenerator(int boardSize) {
		super();
		this.boardSize = boardSize;
	}
	
	private int boardSize;
	private List<Point> guesses = new ArrayList<>();
	
	public void addMagicElement() {
		Point point = new Point(-1, -1);
		guesses.add(point);
	}

	public void generateOptimalGuess(int shift) {
		int middle = (boardSize-1) /2;
		for (int layer = 0; layer < middle+3; layer++) {
			makeTopGuesses(layer, middle, shift);
			if (!(layer == 0)) {
				makeBottomGuesses(layer, middle, shift);
			}
			makeLeftGuesses(layer, middle, shift);
			makeRightGuesses(layer, middle, shift);
		}
	}

	private void makeTopGuesses(int layer, int middle, int shift) {
		int x, y;
		for (int topsteps = 0; topsteps < layer+1; topsteps++) {
			x = middle - layer + topsteps*2 + shift;
			y = middle + layer;
			storeGuesses(x,y);
		}	
	}
	private void makeBottomGuesses(int layer, int middle, int shift) {
		int x, y;	
		for (int bottomsteps = 0; bottomsteps < layer+1; bottomsteps++) {
			x = middle - layer + bottomsteps*2 + shift;
			y = middle - layer;
			storeGuesses(x,y);
		}
	}
	private void makeLeftGuesses(int layer, int middle, int shift) {
		int x, y;	
		for (int leftsteps = 1; leftsteps < layer; leftsteps++) {
			x = middle - layer + shift;
			y = middle - layer + leftsteps*2;
			storeGuesses(x,y);
		}
	}
	private void makeRightGuesses(int layer, int middle, int shift) {
		int x, y;	
		for (int rightsteps = 1; rightsteps < layer; rightsteps++) {
			x = middle + layer + shift;
			y = middle - layer + rightsteps*2;
			storeGuesses(x,y);
		}
	}
	
	private boolean checkGuesses(int x, int y) {
		return (x < 0 || y < 0 || x >= boardSize || y >= boardSize) ? false : true;
	}
	
	private void storeGuesses(int x, int y) {
		if (checkGuesses(x, y)) {
			Point point = new Point(x, y);
			guesses.add(point);
		}
	}
	public List<Point> getGuesses() {
		return guesses;
	}
}
