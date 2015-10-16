package application;

import javafx.stage.StageStyle;
import MessageContainer.Request;
import MessageContainer.Update;
import interfaces.Handler;
import interfaces.Mapper;

public class NewDialogHandler implements Handler{
	
	private Request<Object, Object> request;
	private Mapper mapper;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int hashCodeofView = request.getHashCode();
		System.out.println("New Dialog");
		String newViewName = (String) request.getResource("new view name");
		Update<Object> update = new Update<Object>(newViewName, Update.NEWDIALOG);
		update.setTitle((String)request.getResource("title"));
		update.setStageStyle((StageStyle)request.getResource("stage style"));
		update.setHashCodeofView(hashCodeofView);
		try {
			mapper.mapUpdate(update);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setRequest(Request request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	@Override
	public int getPorgress() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public void setMapper(Mapper mapper) {
		// TODO Auto-generated method stub
		this.mapper = mapper;
	}

}
