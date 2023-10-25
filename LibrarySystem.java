import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
                JTextField author = new JTextField(1);
                JTextField book = new JTextField();
                JTextField pub = new JTextField();
                Object[] message = {
                        "Book Author:", author,
                        "Book Name :", book,
                        "Publisher : ",pub
                };
                int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    if (author.getText().equals("h") && pub.getText().equals("h")) {
                        System.out.println("Login successful");
                    } else {
                        System.out.println("login failed");
                    }
                } else {
                    System.out.println("Login canceled");
                }
            }
        });

    }
    void createP1()
    {


        String[][] data =lib1.loadFromFile();

        // Column Names
        String[] columnNames = { "Title", "Author", "Publication Year" };
        table1 = new JTable(data, columnNames);
        table1.setBounds(30, 40, 200, 100);
        p1.setSize(500,500);
        // adding it to JScrollPane
        p1.add(label1, BorderLayout.NORTH);
        JScrollPane sp = new JScrollPane(table1);
        p1.add(sp, BorderLayout.CENTER);
        p1.add(buttonPane, BorderLayout.SOUTH);
    }



}
