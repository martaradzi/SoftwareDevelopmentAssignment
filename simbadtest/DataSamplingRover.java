// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package nl.vu.cs.s2.simbadtest;

import javax.vecmath.Vector3d;



class DataSamplingRover extends Observer  {

	public String name;
	public ExampleRobot robot;
	
	public DataSamplingRover(Subject subject,int x , int z , String name) {
		this.subject = subject;
		subject.attach(this);
		this.robot = new ExampleRobot(new Vector3d(x, 0, z), name , x , z);		
	}

	public void takePhoto() {
		
	}

	@Override
	public void update() {
		if (this.subject.getState() == 1) {
				System.exit(0);
		} 
		
	}
	
};
