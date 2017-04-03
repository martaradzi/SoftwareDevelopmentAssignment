// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package RootElement.ROVUSystem;

import RootElement.ROVUSystem.Rover;

class DataSamplingRover extends Rover {

	public String name;

	public DataSamplingRover(Subject subject) {
		this.subject = subject;
		subject.attach(this);
	}

	public void takePhoto() {
	}

	@Override
	public void update() {
		if (this.subject.getState() == 0) {
			// WAIT
		} else {
			// SAMPLE
		}
	}
};