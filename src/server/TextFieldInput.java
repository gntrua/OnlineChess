import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class TextFieldInput extends JTextField implements ActionListener{
    JTextArea text;
    ChatClient connect;
    TextFieldInput(ChatClient connect){
	this.connect = connect;
	this.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
	String s = this.getText();
	this.setText("");
	connect.send(s);
    }
}


