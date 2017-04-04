// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package nl.vu.cs.s2.simbadtest;

import java.awt.image.BufferedImage;

import nl.vu.cs.s2.simbadtest.Data;
import nl.vu.cs.s2.simbadtest.Database;
import nl.vu.cs.s2.simbadtest.Subject;

public class CentralStation extends Subject {
	public Database mapDatabase;
	private boolean missionComplete = false;
	public Data[] data;
	
	DataSamplingRover rover;
	int photoCounter = 0;
	double photos;

	CentralStation() {
		photos = 0.7 * ExampleEnvironment.WIDTH * ExampleEnvironment.HEIGHT;
		mapDatabase = new Database(ExampleEnvironment.WIDTH, ExampleEnvironment.HEIGHT);
		rover = new DataSamplingRover(this,0,0,"2");
	}


	private BufferedImage getData() {

		return rover.robot.p;
	}

	private void storeData(int y, int x) {
		mapDatabase.database[y][x].photo = getData();
	}

	public void initiate() {
		while (this.getState() == 0) {
			
			if (rover.robot.getCounter() % 5 == 0) {
				
				int x = (rover.robot.a / 10) + 10;
				int y = (rover.robot.b / 10) + 10;
				
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < 20; j++) {
						if (mapDatabase.database[i][j].isTarget == false) {
							System.out.print("1" + " ");
						} else {
							System.out.print("0" + " ");
						}
					}
					System.out.println();
				}
				
				if (mapDatabase.database[x][y].isTarget == true) {
					rover.robot.SamplingMode = true;
					
					mapDatabase.database[x][y].isTarget = false;
					storeData(x, y);
					photoCounter += 1;
					checkState();

				}

			}

		}
	}

	private void checkState() {
		if (photos == photoCounter) {
			missionComplete = false;
			this.setState(1);
		}

	}

}