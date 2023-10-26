import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.io.FileWriter;

public class LibrarySystem {

    JFrame frame;

    private JPanel p1;

    private JTable table1;
    private JPanel buttonPane;
    private JButton deleteItemButton;
    private JPanel card;
    private JButton addButton;
    private JButton edItItemButton;

    private JLabel label1;
    private JLabel label2;
    private JPanel textPanel;
    private JLabel l1;

    public Library lib1;

    LibrarySystem()
    {frame = new JFrame();
        lib1=new Library();
        p1 = new JPanel();
        createP1();
        p1.add(addButton);


        frame.add(p1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             /*   CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards,"Panel 2");*/
                JTextField author = new JTextField(2);
                JTextField book = new JTextField();
                JTextField pub = new JTextField(1);
                Object[] message = {
                        "Book Author Name:", author,
                        "Book Name :", book,
                        "Publishing year  : ",pub

                };
                int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    if (author.getText().isEmpty()==false && book.getText().isEmpty()==false&&pub.getText().isEmpty()==false) {
                         lib1.additem(book.getText(),author.getText(),Integer.parseInt(pub.getText()) );
                        String[] columnNames = { book.getText(),author.getText(),pub.getText(),Integer.toString(0)};
                        DefaultTableModel model = (DefaultTableModel) table1.getModel();
                        model.addRow(columnNames);
                        table1.setModel(model);
                        savetofile(columnNames);
                        System.out.println("add successful");
                    } else {
                        System.out.println("failed");
                    }
                } else {
                    System.out.println("canceled");
                }
            }
        });

        edItItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    void createP1()
    {

        String[][] data =lib1.loadFromFile();
        String[] columnNames = { "Title", "Author", "Publication Year", "Popularity Count" };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table1 = new JTable(model);
        table1.setBounds(30, 40, 200, 100);
        p1.setSize(500,500);
        // adding it to JScrollPane
        p1.add(label1, BorderLayout.NORTH);
        JScrollPane sp = new JScrollPane(table1);
        p1.add(sp, BorderLayout.CENTER);
        p1.add(buttonPane, BorderLayout.SOUTH);
    }
    void savetofile(String[] s)
    {
         String nn;
         nn='\n'+s[0]+','+s[1]+','+s[2]+','+s[3];
        File myObj = new File("libinput.txt");
        try {
         /*   FileWriter fw = new FileWriter(myObj);
            fw.append(nn);
            fw.close();*/
            Files.write(Paths.get(myObj.toURI()),nn.getBytes(), StandardOpenOption.APPEND);

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
