
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Jtable {

    JPanel panel1;
    private JTable table1;
    private JPanel Jpanel2;
    private JButton addIItemButton;
    private JButton editItemButton;
    private JButton deleteItemButton;
    public Library lib1;

    Jtable()
    {
      lib1=new Library();
        panel1.setSize(400,400);
        String[][] data =lib1.loadFromFile();

        // Column Names
        String[] columnNames = { "Title", "Author", "Publication Year" };
        table1 = new JTable(data, columnNames);
        table1.setBounds(30, 40, 200, 300);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(table1);
        panel1.add(sp);


        addIItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lib1.additem();
            }
        });
    }



}
