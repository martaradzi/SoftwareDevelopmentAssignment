package nl.vu.cs.s2.simbadtest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.CameraSensor;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;

public class ExampleRobot extends Agent {

	private String currentMode;
	CameraSensor camera;
	int a =0 ;
	int b =0;
	int x;
	int y;
	RangeSensorBelt sonar;
	int direction = 1;
	RangeSensorBelt bumper;
	boolean bumperHit;
	int number = 1;
	boolean SamplingMode  = false;
	boolean photoCompleted = false;
	BufferedImage p;
	

	public ExampleRobot(Vector3d position, String name, int u, int v) {
		super(position, name);
		
		// Add bumpers
		bumper = RobotFactory.addBumperBeltSensor(this, 12);

		// Add sonars
		sonar = RobotFactory.addSonarBeltSensor(this, 12);

		camera = RobotFactory.addCameraSensor(this);
		camera.setUpdatePerSecond(60);
		
	}

	/** This method is called by the simulator engine on reset. */
	public void initBehavior() {
		System.out.println("I exist and my name is " + this.name);
	}

	void rotationDelay() {
		if (direction == 1) {
			a -= 2;
		} else if (direction == 2) {
			b += 2;
		} else if (direction == 3) {
			a += 2;
		} else if (direction == 4) {
			b -= 2;
		}
	}

	void updateDirection() {
		if (direction == 4) {
			direction = 1;
		} else {
			direction += 1;
		}
	}

	boolean bumperHit() {
		if (bumper.hasHit(0) || bumper.hasHit(1) || bumper.hasHit(2) || bumper.hasHit(10) || bumper.hasHit(11)) {
			return true;
		}
		return false;
	}

	void turnLeft() {
		this.setTranslationalVelocity(0);
		this.rotateY(Math.PI/2);
		updateDirection();
	}

	/**
	 * This method is call cyclically (20 times per second) by the simulator
	 * engine.
	 */
	public void performBehavior() {

		// perform the following actions every 5 virtual seconds
		if (this.getCounter() % 2 == 0) {
			switch (direction) {
			case 1:
				a += 1;
				break;
			case 2:
				b -= 1;
				break;
			case 3:
				a -= 1;
				break;
			case 4:
				b += 1;
				break;
			}
			if (a >= 100 || b >= 100 || a <= -100 || b <= -100) {
				rotationDelay();
			}
		}
		Random rand = new Random();

		int n = (rand.nextInt(10) + 1) * 20;
		if (this.getCounter() % n == 0) {
			turnLeft();
		}

		else if (this.getCounter() % 5 == 0) {
			System.out.println(SamplingMode);
			if (number <= 4 && SamplingMode == true) {

				this.setTranslationalVelocity(0);
				p = camera.createCompatibleImage();
				camera.copyVisionImage(p);
				String name = "/Users/AdhiviraTheodorus/Downloads/" + a/10 + "D" + number + ".png";
				File output = new File(name);
				try {
					ImageIO.write(p, "png", output);

				} catch (IOException e) {
					e.printStackTrace();
				}
				this.rotateY(Math.PI/2);
				//rotationDelay();
				updateDirection();
				if (number == 4) {
					System.out.println(number);
					SamplingMode = false;
					number = 1;

				} else {
					number += 1;
					System.out.println(number);
				}
			} else if (bumperHit()) {
				this.currentMode = "avoidObstacle";
			} else {
				this.currentMode = "goStraight";
			}

			if (this.currentMode == "goStraight") {
				this.setTranslationalVelocity(1);

			} else {
				turnLeft();
			}
		}

	}

}