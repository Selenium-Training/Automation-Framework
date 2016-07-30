package dellCore;

import newCore.ExecutionEssentials;

public class Dell {

	public HomePageFixture homePageFixture;
	
	public Dell(ExecutionEssentials executionEssentials) {
		homePageFixture = new HomePageFixture(executionEssentials);
	}
}
