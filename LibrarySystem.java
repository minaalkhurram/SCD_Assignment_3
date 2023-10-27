import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


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
                        String[][] data = getDataFromTable(table1);
                        updateDataInFile(data);
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
                    // Get the current book name, author name, and publication year
                    String currentBookName = (String) table1.getValueAt(selectedRow, 0);
                    String currentAuthorName = (String) table1.getValueAt(selectedRow, 1);
                    String nn=(String) table1.getValueAt(selectedRow, 2);
                    int currentPublicationYear = Integer.parseInt(nn); // Assuming the publication year is in the third column

                    // Create a JPanel to hold input fields
                    JPanel inputPanel = new JPanel(new GridLayout(4, 2));
                    JTextField bookNameField = new JTextField(currentBookName, 15);
                    JTextField authorNameField = new JTextField(currentAuthorName, 15);
                    JTextField publicationYearField = new JTextField(String.valueOf(currentPublicationYear), 15);

                    inputPanel.add(new JLabel("Book Name:"));
                    inputPanel.add(bookNameField);
                    inputPanel.add(new JLabel("Author Name:"));
                    inputPanel.add(authorNameField);
                    inputPanel.add(new JLabel("Publication Year:"));
                    inputPanel.add(publicationYearField);

                    // Show the input dialog
                    int result = JOptionPane.showConfirmDialog(
                            null, inputPanel, "Edit Book Information", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        String newBookName = bookNameField.getText();
                        String newAuthorName = authorNameField.getText();
                        int newPublicationYear = Integer.parseInt(publicationYearField.getText());

                        // Update the JTable with the new book name, author name, and publication year
                        table1.setValueAt(newBookName, selectedRow, 0);
                        table1.setValueAt(newAuthorName, selectedRow, 1);
                        table1.setValueAt(Integer.toString(newPublicationYear), selectedRow, 2);

                        // Update the data in the file
                        String[][] data = getDataFromTable(table1);
                        updateDataInFile(data);
                    }
                }
            }
        });
        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel inputPanel = new JPanel(new GridLayout(3, 2));
                JTextField bookNameField = new JTextField(15);
                JTextField authorNameField = new JTextField(15);
                JTextField pubYear=new JTextField(1);
                inputPanel.add(new JLabel("Enter Book Name:"));
                inputPanel.add(bookNameField);
                inputPanel.add(new JLabel("Enter Author Name:"));
                inputPanel.add(authorNameField);
                inputPanel.add(new JLabel("Enter Publication Year:"));
                inputPanel.add(pubYear);


                // Show the input dialog
                int result = JOptionPane.showConfirmDialog(
                        null, inputPanel, "Enter Book and Author Names", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String bookNameToDelete = bookNameField.getText();
                    String authorNameToDelete = authorNameField.getText();
                    String yearToDelete=pubYear.getText();

                    if (!bookNameToDelete.isEmpty() && !authorNameToDelete.isEmpty()) {
                        DefaultTableModel model = (DefaultTableModel) table1.getModel();

                        // Find and remove the row with the provided book name and author name from the JTable
                        for (int row = 0; row < model.getRowCount(); row++) {
                            String bookName = (String) model.getValueAt(row, 0); // Assuming the book name is in the first column
                            String authorName = (String) model.getValueAt(row, 1); // Assuming the author name is in the second column
                            String year=(String) model.getValueAt(row,2);
                            if (bookNameToDelete.equalsIgnoreCase(bookName) && authorNameToDelete.equalsIgnoreCase(authorName)&&yearToDelete.equals(year)) {
                                model.removeRow(row);
                                break; // Remove the first matching item and exit the loop
                            }
                        }

                        // Update the data in the file
                        String[][] data = getDataFromTable(table1);
                        updateDataInFile(data);}/* int selectedRow = table1.getSelectedRow();

                if (selectedRow >= 0) {
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                    model.removeRow(selectedRow);

                    // Update the data in the file
                    String[][] data = getDataFromTable(table1);
                    updateDataInFile(data);

                }*/
        }}});
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
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                DefaultPieDataset dataset = new DefaultPieDataset();

                int rowCount = model.getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    String bookTitle = (String) model.getValueAt(i, 0); // Assuming book title is in the first column
                   String nn= (String) model.getValueAt(i, 3);
                    int popularityCount = Integer.parseInt(nn); // Assuming popularity count is in the fourth column
                    dataset.setValue(bookTitle, popularityCount);
                }

                JFreeChart pieChart = ChartFactory.createPieChart(
                        "Popularity Chart",
                        dataset,
                        true,  // Include legend
                        true,
                        false
                );

                PiePlot plot = (PiePlot) pieChart.getPlot();
                plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})"));
                Font labelFont = new Font("SansSerif", Font.PLAIN, 12);
                plot.setLabelFont(labelFont);
                ChartPanel chartPanel = new ChartPanel(pieChart);
                JFrame frame = new JFrame("Popularity Pie Chart");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(chartPanel);
                frame.pack();
                frame.setVisible(true);
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
   ;

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

        JTextArea textArea = new JTextArea("Book Title : " + bookTitle+"\n\n\nCHAPTER 1 \nLorem ipsum dolor sit amet, consectetur adipiscing elit,sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n Ut enim ad minim veniam,quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\nDuis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum\nLorem ipsum dolor sit amet, consectetur adipiscing elit,sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n Ut enim ad minim veniam,quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\nDuis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"+"\n\n\nCHAPTER 2\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit,sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n Ut enim ad minim veniam,quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\nDuis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum\nLorem ipsum dolor sit amet, consectetur adipiscing elit,sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n Ut enim ad minim veniam,quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\nDuis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum\n\nTHE END ");
        textArea.setEditable(false);
        textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        textArea.setLineWrap(true); // Enable line wrapping
        textArea.setWrapStyleWord(true);
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
    public static void main(String[] args)
    {
        LibrarySystem nn=new LibrarySystem();
    }

}
