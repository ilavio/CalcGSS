package application;

import javafx.concurrent.Task;

public class Threadtask extends Task {

	@Override
	protected Object call() throws Exception {
		int x = Main.m1;
		int s = 0;
		for(;x>=0;) {
			if(s==0&&x>0) {
				s=60;
				x-=1;
			} 
			if (x>=0&&s>0) {
				s--;
			}
			updateMessage(String.valueOf(x+":"+s));

            Thread.sleep(1000);
		}
		// TODO Auto-generated method stub
		return null;
	}

}
