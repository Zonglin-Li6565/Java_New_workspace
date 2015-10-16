package application;

import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	Stage stage;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		stage = primaryStage;
		MainWindowController controller = (MainWindowController) replaceSceneContent("MainWindow.fxml");
	}
	
	/**
	 * Used to replace SceneContent;
	 * @param fxml
	 * @return
	 * @throws Exception
	 */
	
	private Node replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        stage.setResizable(false);
        Scene scene = new Scene(page);
        double nowWidth = stage.getWidth();
        double nowHight = stage.getHeight();
        double nowX = stage.getX();
        double nowY = stage.getY();
        stage.setScene(scene);        
        stage.sizeToScene();
        double futureWidth = stage.getWidth();
        double futureHight = stage.getHeight();
        double futureX = nowX - (futureWidth - nowWidth) / 2;
        double futureY = nowY - (futureHight - nowHight) / 2;
        if(nowX >=0 && nowY >= 0){
        	stage.setX(futureX);
        	stage.setY(futureY);
        }
        
        Node n = loader.getController();
        System.out.println("login");
        return (Node) loader.getController();
    }

}
