package nl.han.ica.icss.gui;

import javafx.scene.control.TextArea;

@SuppressWarnings("restriction")
public class FeedbackPane extends TextArea {
	public FeedbackPane() {
		super();
		
		setEditable(false);
	}
	public void clear() {
		this.setText("");
	}
	public void addLine(String line) {
		this.setText( this.getText() + "\n" + line);
	}
}
