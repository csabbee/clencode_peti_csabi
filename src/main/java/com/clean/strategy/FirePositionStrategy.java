package com.clean.strategy;

import java.util.ArrayList;
import java.util.List;

import com.clean.interfaces.GameStrategy;
import com.clean.ship.Point;
import com.clean.shipgame.Status;

public class FirePositionStrategy implements GameStrategy {
		
	private List<Point> guesses;
	private XYGuessGenerator generator;
	private List<Point> hits = new ArrayList<>();
	private List<Point> guessesFirstHalf = new ArrayList<>();
	private int peter = 0;
	private Point tempPoint;
	
	public void initialise() {
		generator.addMagicElement();
		generator.generateOptimalGuess(0);
		guessesFirstHalf = generator.getGuesses();
		generator.generateOptimalGuess(1);
		guesses = generator.getGuesses();
	}

	@Override
	public String getTarget(Status input) {
		String answer;
		switch (input) {
		case SUNK:
			for (Point point : hits) {
				if (!(guessesFirstHalf.contains(point))){
					hits.remove(point);
				}
			}
			hits.clear();
			answer = getTarget(Status.MISS);
			break;
		case HIT:
			if (tempPoint == null) {
				hits.add(guesses.get(0));
			} else {
				hits.add(tempPoint);
			}
			answer = guessingsmart();
			break;
		case MISS:
			answer = handlingMissCase();
			break;
		default:
			answer = "OOPS! BAD CODE ON MY PART! (But, please blame Csabi!)";
			break;
		}
		tempPoint = null;
		return answer;
	}

	private String handlingMissCase() {
		String answer;
		if (!(hits.isEmpty())){
			answer = guessingsmart();			
		} else {
			answer = converter(guesses.get(1));
			guesses.remove(0);
			peter++;
		}
		return answer;
	}

	private String guessingsmart() {
		Point point = hits.get(0);
		String answer;
		tempPoint = shiftPoint(point, Direction.UP);
		if (guesses.remove((tempPoint))){
			answer = converter(tempPoint);
			peter++;
		} else {
			tempPoint = shiftPoint(point, Direction.DOWN);
			if (guesses.remove((tempPoint))){
				answer = converter(tempPoint);
				peter++;
			} else {
				tempPoint = shiftPoint(point, Direction.LEFT);
				if (guesses.remove((tempPoint))){
					answer = converter(tempPoint);
					peter++;
				} else {
					tempPoint = shiftPoint(point, Direction.RIGHT);
					if (guesses.remove((tempPoint))){
						answer = converter(tempPoint);
						peter++;
					} else {
						hits.remove(0);
						if(hits.isEmpty()){
							answer = getTarget(Status.MISS);
						}
						else {
							answer = guessingsmart();
						}
					}
				}
				
			}
		}
		return answer;
	}
	
	private Point shiftPoint (Point point, Direction direction){
		Point newPoint;
		switch (direction) {
		case UP:
			newPoint = new Point(point.getX(), point.getY()+1);
			break;
		case DOWN:
			newPoint = new Point(point.getX(), point.getY()-1);
			break;
		case LEFT:
			newPoint = new Point(point.getX()-1, point.getY());
			break;
		case RIGHT:
			newPoint = new Point(point.getX()+1, point.getY());
			break;
		default:
			newPoint = point;
		}
		return newPoint;
	}
	
	private String converter(Point point) {
		return "fire " + String.valueOf(point.getX()) + " " +String.valueOf(point.getY());
	}
	
	@Override
	public int getPeter() {
		return peter;
	}

	public void setGenerator(XYGuessGenerator generator) {
		this.generator = generator;
	}
		
}
