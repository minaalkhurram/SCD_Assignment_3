
import javax.swing.*;

public class Jtable {

    private JButton button1;
    private JPanel panel1;
    private JTable table1;

    Jtable()
    {
        panel1.setSize(400,400);
        String[][] data = {
                { "Java Tutorials ", "John", "2010",null },
                { "C++ Tutorials", "Albert ", "2012",null },
                {"Python Tutorials","Albert","2015",null}
        };

        // Column Names
        String[] columnNames = { "Title", "Author", "Publication Year","Read Item" };
        table1 = new JTable(data, columnNames);
        table1.setBounds(30, 40, 200, 300);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(table1);
        panel1.add(sp);
    }
    public static void main(String[] args)
    {
        JFrame f=new JFrame("idk");
        f.setSize(400,400);
        f.setContentPane(new Jtable().panel1);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();
    }

}
