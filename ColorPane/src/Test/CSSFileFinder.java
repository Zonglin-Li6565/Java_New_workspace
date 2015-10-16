package Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CSSFileFinder {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		URL location = new URL("jar:file:D:\\Test\\Plugins\\Colorful.jar!/application.css");
		InputStream is = location.openStream();
		while(true){
			int read = is.read();
			if(read == -1){
				break;
			}
			System.out.println(read);
		}
	}

}
