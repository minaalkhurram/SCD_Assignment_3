import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


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
    private JButton readButton;
    private JButton VIewPopularityButton;
    private JLabel label2;
    private JPanel textPanel;
    private JLabel l1;

    public Library lib1;

    LibrarySystem() {
        frame = new JFrame();
        lib1 = new Library();
        p1 = new JPanel();
        createP1();

        frame.add(p1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        buttonPane.setBackground(Color.decode("#E6E6FA"));
        p1.setBackground(Color.decode("#E6E6FA"));
        JTableHeader tableHeader = table1.getTableHeader();

// Set the background color of the header row
        tableHeader.setBackground(Color.decode("#4B0082"));

        tableHeader.setForeground(Color.white);
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
                        "Publishing year  : ", pub

                };
                int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    if (author.getText().isEmpty() == false && book.getText().isEmpty() == false && pub.getText().isEmpty() == false) {
                        lib1.additem(book.getText(), author.getText(), Integer.parseInt(pub.getText()));
                        String[] columnNames = {book.getText(), author.getText(), pub.getText(), Integer.toString(0)};
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

                                                 int selectedRow = table1.getSelectedRow();

                                                 if (selectedRow >= 0) {
                                                     // Get the current book name
                                                     String currentBookName = (String) table1.getValueAt(selectedRow, 0);

                                                     // Prompt the user for a new book name
                                                     String newBookName = JOptionPane.showInputDialog("Enter the new book name:", currentBookName);

                                                     if (newBookName != null) {
                                                         // Update the JTable with the new book name
                                                         table1.setValueAt(newBookName, selectedRow, 0);

                                                         // Update the data in the file
                                                         String[][] data = getDataFromTable(table1);
                                                         updateDataInFile(data);
                                                     }
                                                 }
                                             }
                                         }
        );
        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow >= 0) {
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                    model.removeRow(selectedRow);

                    // Update the data in the file
                    String[][] data = getDataFromTable(table1);
                    updateDataInFile(data);

                }
        }});
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow >= 0) {
                    // Increment the popularity count
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                    int currentPopularityCount = Integer.parseInt((String) model.getValueAt(selectedRow, 3));
                    currentPopularityCount++;
                    model.setValueAt(String.valueOf(currentPopularityCount), selectedRow, 3);

                    // Update the data in the file
                    String[][] data = getDataFromTable(table1);
                    updateDataInFile(data);

                    // Open a new window to display text
                    String bookTitle = (String) model.getValueAt(selectedRow, 0);
                    openBookWindow(bookTitle);
                }
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isMouseEventInsideTable(e)) {
                    int row = table1.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        table1.setRowSelectionInterval(row, row);
                        table1.setSelectionBackground(Color.decode("#DA70D6")); // You can use any color you prefer
                    }
                    else
                        table1.clearSelection();
                }
            }
             @Override
          public void mouseExited(MouseEvent e) {
                if (isMouseEventInsideTable(e)) {
                    table1.clearSelection();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (isMouseEventInsideTable(e) && e.getClickCount() == 1) {
                    int row = table1.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        // Handle the click on the row, e.g., display its data or perform other actions
                        String title = (String) table1.getValueAt(row, 0);
                        String author = (String) table1.getValueAt(row, 1);
                        int publicationYear = Integer.parseInt((String) table1.getValueAt(row, 2));
                        int popularityCount = Integer.parseInt((String) table1.getValueAt(row, 3));
                    }
                }
            }


        });
        table1.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (isMouseEventInsideTable(e)) {
                    int row = table1.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        table1.setRowSelectionInterval(row, row);
                        table1.setSelectionBackground(Color.decode("#DA70D6"));
                    } else {
                        table1.clearSelection();
                    }
                }
            }
        });

        VIewPopularityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame pieFrame=new JFrame("Pie");
                pieFrame.setSize(300,300);
                pieFrame.setVisible(true);
                DefaultPieDataset dataset = new DefaultPieDataset();

                // Populate the dataset with data from your JTable
                dataset.setValue("Item 1", 50); // Replace with your data
                dataset.setValue("Item 2", 30); // Replace with your data
                dataset.setValue("Item 3", 20);
            }
        });
    }
    private boolean isMouseEventInsideTable(MouseEvent e) {
        int row = table1.rowAtPoint(e.getPoint());
        int column = table1.columnAtPoint(e.getPoint());
        return row >= 0 && column >= 0;

    }
    void createP1()
    {

        String[][] data =lib1.loadFromFile();
        String[] columnNames = { "Title", "Author", "Publication Year", "Popularity Count" };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table1 = new JTable(model);
        table1.setBounds(30, 40, 200, 50);
        p1.setSize(500,300);
        // adding it to JScrollPane
        p1.add(label1, BorderLayout.NORTH);
        label1.setBackground(Color.decode("#DA70D6"));
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


    private String[][] getDataFromTable(JTable table) {
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        String[][] data = new String[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                data[i][j] = (String) table.getValueAt(i, j);
            }
        }

        return data;
    }

    // A method to update the data in the file
    private void updateDataInFile(String[][] data) {
        String fileName = "libinput.txt"; // Make sure the file name is correct

        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (String[] row : data) {
                String line = String.join(",", row);
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while updating the file.");
        }
    }
    private void openBookWindow(String bookTitle) {
        JFrame bookFrame = new JFrame(bookTitle);

        JTextArea textArea = new JTextArea("Book Title : " + bookTitle+"\n\n\nCHAPTER 1\n\nLorem ipsum dolor sit amet,\n consectetur adipiscing elit,\n sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n Ut enim ad minim veniam,\n quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n\n Duis aute irure dolor in reprehenderit in voluptate velit \nesse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident,\n sunt in culpa qui officia deserunt mollit anim id est laborum\n"+"\n\n\nCHAPTER 2\n\nLorem ipsum dolor sit amet,\n consectetur adipiscing elit,\n sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n Ut enim ad minim veniam,\n quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n\n Duis aute irure dolor in reprehenderit in voluptate velit \nesse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident,\n sunt in culpa qui officia deserunt mollit anim id est laborum\n\nTHE END ");
        textArea.setEditable(false);

        bookFrame.add(new JScrollPane(textArea));
        bookFrame.setSize(600, 300);
        bookFrame.setVisible(true);

        bookFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(bookFrame, "Do you want to exit reading?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    bookFrame.dispose(); // Close the window
                }
                else{
                    openBookWindow(bookTitle);
                }
                // If the user selects "No," do nothing, and the window will remain open.
            }
        });

    }
}
