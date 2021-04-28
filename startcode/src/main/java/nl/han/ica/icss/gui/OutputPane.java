package nl.han.ica.icss.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

@SuppressWarnings("restriction")
public class OutputPane extends BorderPane {

	private Label title;
	private TextArea content;

	public OutputPane () {
		super();

		title = new Label("Output (CSS):");
		title.setPadding(new Insets(5, 5, 5, 5));

		content = new TextArea();
		content.setEditable(false);

		setTop(title);
		setCenter(content);
	}
	public void setText(String text) {
		content.setText(text);
	}
	public String getText() {
		return content.getText();
	}
	public void writeToFile(File file) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream(file));
			out.print(this.getText());
			out.close();
		} catch(Exception exception) {
			System.err.println(exception);
		}
	}
}
