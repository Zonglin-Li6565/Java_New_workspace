package application;

import java.awt.Color;

import MessageContainer.Request;
import MessageContainer.Update;
import interfaces.Handler;
import interfaces.Mapper;

public class ColorGenerator implements Handler{
	
	private Mapper mapper;
	private Request request;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int r = (int) (Math.random() * 255);
		int g = (int) (Math.random() * 255);
		int b = (int) (Math.random() * 255);
		Color color = new Color(r, g, b);
		Update<String> upDate = new Update<String>("MainWindow", Update.DISPLAY);
		upDate.addResource("Color", color);
		upDate.addResource("R", r);
		upDate.addResource("G", g);
		upDate.addResource("B", b);
		upDate.addResource("hashCode", this.hashCode());
		upDate.setHashCodeofView(request.getHashCode());
		try {
			mapper.mapUpdate(upDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setRequest(Request<?, ?> request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	@Override
	public int getPorgress() {
		// TODO Auto-generated method stub
		return 100;
	}
	
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

}
