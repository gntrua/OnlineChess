import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

class Entrance extends JPanel {
    JTextField nameField;
    JComboBox roomComboBox;
    JComboBox roleComboBox;
    String name ;
    String room;
    String role;
    public Entrance(){
	this.setLayout(new GridLayout(10,1));
	nameField = new JTextField(10);
	roomComboBox = new JComboBox<String>(new String[]{"","1","2","3","4","5","6","7","8","9","10"});
	roleComboBox = new JComboBox<String>(new String[]{"","player", "watcher"});
	this.add(new JLabel("name"));
	this.add(nameField);
	this.add(new JLabel("room_number"));
	this.add(roomComboBox);
	this.add(new JLabel("mode"));
	this.add(roleComboBox);
	this.setOpaque(true);
	nameField.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    name = nameField.getText();
		    nameField.setText("");
		}
	});
	roomComboBox.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    room = (String)roomComboBox.getSelectedItem();
		    if(room.equals(""))role = null;
		}
	});
	roleComboBox.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    String select = (String)roleComboBox.getSelectedItem();
		    if(select.equals("player")){
			role = "0";
		    }else if(select.equals("watcher")){
			role = "1";
		    }

		}
	});
    }

    String[] getInfo(){
	if(name != null && role != null && room != null){
	    return (new String[]{name, role, room});
	}
	return null;
    }
}
