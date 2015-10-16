package Test;

import javafx.stage.StageStyle;
import MessageContainer.Request;
import MessageContainer.Update;
import interfaces.Handler;
import interfaces.Mapper;

public class newStageHandler implements Handler{
	
	private Mapper mapper;
	private Request request;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String 	name = (String) request.getResource("name"),
				title = (String) request.getResource("title");
		boolean resizable = (boolean) request.getResource("resizable");
		StageStyle stageStyle = (StageStyle) request.getResource("stageStyle");
		int x = (int) request.getResource("X"),
			y = (int) request.getResource("Y");
		
		Update<String> upDate = new Update<String>("", Update.NEWSTAGE);
		upDate.setNewViewName(name);
		upDate.setTitle(title + request.getResource("serial number"));
		upDate.setResizable(resizable);
		upDate.setStageStyle(stageStyle);
		upDate.setXLocation(x);
		upDate.setYLocation(y);
		upDate.addResource("serial number", request.getResource("serial number"));
		try {
			mapper.mapUpdate(upDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getPorgress() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public void setRequest(Request<?, ?> request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	@Override
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

}
