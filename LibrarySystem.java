import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibrarySystem {

    JFrame frame;
    JPanel card;
    private JPanel p1;
    private  JPanel p3;
    private JPanel p2;
    private JTable table1;
    private JPanel buttonPane;
    private JButton deleteItemButton;
    private JButton addButton;
    private JButton edItItemButton;

    private JLabel label1;
    private JLabel label2;
    private JPanel textPanel;
    private JTextField enterBookNameTextField;
    private JTextField enterAuthorNameTextField;
    private JTextField enterPublishingYearTextField;
    private JLabel l1;

    public Library lib1;

    LibrarySystem()
    {frame = new JFrame();
        lib1=new Library();
        p1 = new JPanel();
        createP1();

        p1.add(addButton);
        p2 = new JPanel();
        p2.add(label2);
        textPanel.setSize(300,300);
        p2.add(textPanel);
        p2.setVisible(true);
        p3 = new JPanel();
        p3.setBackground(Color.BLUE);

//Create the panel that contains the "cards".
        JPanel cards = new JPanel(new CardLayout());
        cards.add(p1, "Panel 1");
        cards.add(p2, "Panel 2");
        cards.add(p3, "Panel 3");

// Add your card container to the frame
        Container pane = frame.getContentPane();
        pane.add(cards, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards,"Panel 2");
            }
        });
    }
    void createP1()
    {

        card.setSize(600,600);
        String[][] data =lib1.loadFromFile();

        // Column Names
        String[] columnNames = { "Title", "Author", "Publication Year" };
        table1 = new JTable(data, columnNames);
        table1.setBounds(30, 40, 200, 100);

        // adding it to JScrollPane
        p1.add(label1, BorderLayout.NORTH);
        JScrollPane sp = new JScrollPane(table1);
        p1.add(sp, BorderLayout.CENTER);
        p1.add(buttonPane, BorderLayout.SOUTH);
    }



}
