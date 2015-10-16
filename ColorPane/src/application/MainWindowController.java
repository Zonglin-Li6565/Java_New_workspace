package application;

	import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import MessageContainer.Request;
import MessageContainer.Update;
import interfaces.Mapper;
import interfaces.ViewController;

public class MainWindowController extends AnchorPane implements ViewController{
	
	private Mapper mapper;
	private static Integer serialNumber = 0;
	
	@FXML
	Label label;
	
	@FXML
	Button button_1;
	
	@FXML
	Button button_2;
	
	@FXML
	Button button_3;
	
	@FXML
	Button button_4;
	
	@FXML
	Button button_5;
	
	@FXML
	VBox vBox;
	
	Button button_6;
	
	@Override
	public void initialize(Scene scene, Stage stage){
		URL css = MainWindowController.class.getResource("/application.css");
		System.out.println(css);
		label.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
		button_1.setStyle("-fx-text-fill: #006400;-fx-background-color: #DFB951;-fx-border-radius: 20;-fx-background-radius: 20;-fx-padding: 5;");
		button_2.setStyle("-fx-text-fill: #006464;-fx-background-color: #DFB951;-fx-border-radius: 20;-fx-background-radius: 20;-fx-padding: 5;");
		button_3.setStyle("-fx-text-fill: #006464;-fx-background-color: #DFB951;-fx-border-radius: 20;-fx-background-radius: 20;-fx-padding: 5;");
		button_4.setStyle("-fx-text-fill: #006464;-fx-background-color: #DFB951;-fx-border-radius: 20;-fx-background-radius: 20;-fx-padding: 5;");
		button_5.setStyle("-fx-text-fill: #006464;-fx-background-color: #DFB951;-fx-border-radius: 20;-fx-background-radius: 20;-fx-padding: 5;");
		button_6 = new Button("Hi");
		button_6.setStyle("-fx-text-fill: #006464;-fx-background-color: #DFB951;-fx-border-radius: 20;-fx-background-radius: 20;-fx-padding: 5;");
		button_6.setOnMouseClicked(getOnMouseClicked());
		vBox.getChildren().add(button_6);
	}		

	@Override
	public int handleUpdate(Update<?> update) {
		// TODO Auto-generated method stub
		System.out.println("handling update");
		@SuppressWarnings("unchecked")
		Update<String> upDate = (Update<String>) update;
		String r = upDate.getResource("R") != null?upDate.getResource("R").toString():"0";
		String g = upDate.getResource("G") != null?upDate.getResource("G").toString():"0";
		String b = upDate.getResource("B") != null?upDate.getResource("B").toString():"0";
		String text = "R = " + r +" G = " + g + " B = " + b + "\n" +
				"handler hashcode = " + upDate.getResource("hashCode");
		serialNumber = upDate.getResource("serial number") != null?(Integer)upDate.getResource("serial number"):serialNumber;
		label.setText(text);
		return 0;
	}
	
	public void onClick(){
		System.out.println("Update");
		@SuppressWarnings("rawtypes")
		Request request = new Request("ColorGenerator");
		try {
			mapper.mapRequest(request, this.hashCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showNewStage(){
		Request<?, String> request = new Request<Object, String>("newStageHandler");
		request.addResource("name", "MainWindowController");
		request.addResource("title", "Another Stage :)");
		request.addResource("resizable", true);
		request.addResource("stageStyle", StageStyle.DECORATED);
		request.addResource("X", 800);
		request.addResource("Y", 200);
		request.addResource("serial number", this.serialNumber + 1);
		try {
			mapper.mapRequest(request, this.hashCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void showNewDialog(){
		@SuppressWarnings("rawtypes")
		Request request = new Request<Object, Object>("newDialogHandler");
		request.addResource("new view name", "MainWindowController");
		request.addResource("title", "Dialog");
		request.addResource("stage style", StageStyle.DECORATED);
		try {
			mapper.mapRequest(request, this.hashCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeItself(){
		Update upDate = new Update("MainWindow", Update.CLOSE);
		upDate.setHashCodeofView(this.hashCode());
		serialNumber --;
		try {
			mapper.mapUpdate(upDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeAll(){
		Update upDate = new Update("MainWindow", Update.CLOSE);
		upDate.setDomain(upDate.ALL);
		try {
			mapper.mapUpdate(upDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setMapper(Mapper mapper) {
		// TODO Auto-generated method stub
		this.mapper = mapper;
	}

}
