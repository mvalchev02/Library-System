import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyFrame extends JFrame {
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet result = null;
    int id;

    JTabbedPane tab = new JTabbedPane();
    JTable tableR = new JTable();
    JTable tableB = new JTable();
    JPanel readersPanel = new JPanel();
    JPanel booksPanel = new JPanel();
    JPanel takenBooksPanel = new JPanel();
    JPanel readersUp = new JPanel();
    JPanel readersMid = new JPanel();
    JPanel readersDown = new JPanel();
    JPanel booksUp = new JPanel();
    JPanel booksMid = new JPanel();
    JPanel booksDown = new JPanel();

    //READERS
    JLabel fnameL = new JLabel("Име");
    JLabel lnameL = new JLabel("Фамилия");
    JLabel egnL = new JLabel("ЕГН");
    JLabel telL = new JLabel("Телефон");
    JLabel specL = new JLabel("Специалност");

    JTextField fnameTF = new JTextField();
    JTextField lnameTF = new JTextField();
    JTextField egnTF = new JTextField();
    JTextField telTF = new JTextField();
    JTextField specTF = new JTextField();
    //CLOSED

    //BOOKS
    JLabel titleL = new JLabel("Заглавие");
    JLabel authorL = new JLabel("Автор");
    JLabel isbnL = new JLabel("ISBN");
    JLabel publHouseL = new JLabel("Издателство");
    JLabel yearL = new JLabel("Година на издаване");
    JTextField titleTF = new JTextField();
    JTextField authorTF = new JTextField();
    JTextField isbnTF = new JTextField();
    JTextField publHouseTF = new JTextField();
    JTextField yearrTF = new JTextField();
    //CLOSED

    JButton addButtonR = new JButton("Добави");
    JButton deleteButtonR = new JButton("Изтрий");
    JButton editButtonR = new JButton("Промени");
    JButton searchButtonR = new JButton("Търси");
    JButton refreshButtonR = new JButton("Обнови");
    JScrollPane myScrollR = new JScrollPane(tableR);

    JButton addButtonB = new JButton("Добави");
    JButton deleteButtonB = new JButton("Изтрий");
    JButton editButtonB = new JButton("Промени");
    JButton searchButtonB = new JButton("Търси");
    JButton refreshButtonB = new JButton("Обнови");
    JScrollPane myScrollB = new JScrollPane(tableB);

    public MyFrame() {
        this.setSize(400, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        readersPanel.setName("Читатели");
        booksPanel.setName("Книги");
        takenBooksPanel.setName("Взети книги");
        tab.add(readersPanel);
        tab.add(booksPanel);
        tab.add(takenBooksPanel);

        this.add(tab);

        readersPanel.setLayout(new GridLayout(3, 1));
        readersUp.setLayout(new GridLayout(5, 2));
        readersUp.add(fnameL);
        readersUp.add(fnameTF);
        readersUp.add(lnameL);
        readersUp.add(lnameTF);
        readersUp.add(egnL);
        readersUp.add(egnTF);
        readersUp.add(telL);
        readersUp.add(telTF);
        readersUp.add(specL);
        readersUp.add(specTF);

        readersMid.add(addButtonR);
        readersMid.add(deleteButtonR);
        readersMid.add(editButtonR);
        readersMid.add(searchButtonR);
        readersMid.add(refreshButtonR);

        myScrollR.setPreferredSize(new Dimension(350, 150));
        readersDown.add(myScrollR);

        readersPanel.add(readersUp);
        readersPanel.add(readersMid);
        readersPanel.add(readersDown);

        booksPanel.setLayout(new GridLayout(3, 1));
        booksUp.setLayout(new GridLayout(5, 2));
        booksUp.add(titleL);
        booksUp.add(titleTF);
        booksUp.add(authorL);
        booksUp.add(authorTF);
        booksUp.add(isbnL);
        booksUp.add(isbnTF);
        booksUp.add(publHouseL);
        booksUp.add(publHouseTF);
        booksUp.add(yearL);
        booksUp.add(yearrTF);

        booksMid.add(addButtonB);
        booksMid.add(deleteButtonB);
        booksMid.add(editButtonB);
        booksMid.add(searchButtonB);
        booksMid.add(refreshButtonB);

        myScrollB.setPreferredSize(new Dimension(350, 150));
        booksDown.add(myScrollB);

        booksPanel.add(booksUp);
        booksPanel.add(booksMid);
        booksPanel.add(booksDown);

        addButtonR.addActionListener(new AddAction());
        deleteButtonR.addActionListener(new DeleteAction());
        tableR.addMouseListener(new MouseAction());
        searchButtonR.addActionListener(new SearchAction());
        refreshButtonR.addActionListener(new RefreshAction());
        editButtonR.addActionListener(new EditReaders());
        refreshTable();

        addButtonB.addActionListener(new AddActionB());
        deleteButtonB.addActionListener(new DeleteActionB());
        tableB.addMouseListener(new MouseActionB());
        searchButtonB.addActionListener(new SearchActionB());
        refreshButtonB.addActionListener(new RefreshActionB());
        editButtonB.addActionListener(new EditBooks());
        refreshTableB();

        this.setVisible(true);

    }

    public void refreshTable() {

        conn = DBConnection.getConnection();
        try {
            state = conn.prepareStatement("select * from readers");
            result = state.executeQuery();
            tableR.setModel(new MyModel(result));

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    class AddAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            conn = DBConnection.getConnection();
            String sql = "insert into readers(fname, lname, egn, tel, spec) values(?,?,?,?,?)";
            try {
                state = conn.prepareStatement(sql);
                state.setString(1, fnameTF.getText());
                state.setString(2, lnameTF.getText());
                state.setString(3, egnTF.getText());
                state.setString(4, telTF.getText());
                state.setString(5, specTF.getText());

                state.execute();
                refreshTable();
                //refreshCombo();
                clearForm();

            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

    }

    class MouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = tableR.getSelectedRow();
            id = Integer.parseInt(tableR.getValueAt(row, 0).toString());
            fnameTF.setText(tableR.getValueAt(row, 1).toString());
            lnameTF.setText(tableR.getValueAt(row, 2).toString());
            egnTF.setText(tableR.getValueAt(row, 3).toString());
            telTF.setText(tableR.getValueAt(row, 4).toString());
            specTF.setText(tableR.getValueAt(row, 5).toString());


        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }

    }

    class DeleteAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();

            String sql = "delete from readers where id=?";

            try {
                state = conn.prepareStatement(sql);
                state.setInt(1, id);
                state.execute();
                id = -1;
                refreshTable();
                // refreshCombo();
                clearForm();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

    }

    public void clearForm() {
        fnameTF.setText("");
        lnameTF.setText("");
        egnTF.setText("");
        telTF.setText("");
        specTF.setText("");
    }

    class SearchAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            conn = DBConnection.getConnection();
            String sql = "select * from readers where spec=?";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, specTF.getText());
                result = state.executeQuery();
                tableR.setModel(new MyModel(result));
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


        }

    }
    class RefreshAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            refreshTable();

        }

    }
    class EditReaders implements ActionListener {
        public void actionPerformed (ActionEvent arg0) {
			conn=DBConnection.getConnection();
            if(id>0) {
                String sql="update readers set fname=?, lname=?, egn=?, tel=?, spec=? where id=?";

                try {
                    state=conn.prepareStatement(sql);

                    state.setString(1, fnameTF.getText());
                    state.setString(2, lnameTF.getText());
                    state.setString(3, egnTF.getText());
                    state.setString(4, telTF.getText());
                    state.setString(5, specTF.getText());
                    state.setInt(6, id);

                    state.execute();

                    refreshTable();

                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                clearForm();
            }

        }
    }

    //BOOKS

    public void refreshTableB() {

        conn = DBConnection.getConnection();
        try {
            state = conn.prepareStatement("select * from books");
            result = state.executeQuery();
            tableB.setModel(new MyModel(result));

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    class AddActionB implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            conn = DBConnection.getConnection();
            String sql = "insert into books(title, author, isbn, publHouse, yearr) values(?,?,?,?,?)";
            try {
                state = conn.prepareStatement(sql);
                state.setString(1, titleTF.getText());
                state.setString(2, authorTF.getText());
                state.setString(3, isbnTF.getText());
                state.setString(4, publHouseTF.getText());
                state.setString(5, yearrTF.getText());

                state.execute();
                refreshTableB();
                //refreshCombo();
                clearFormB();

            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

    }

    class MouseActionB implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = tableB.getSelectedRow();
            id = Integer.parseInt(tableB.getValueAt(row, 0).toString());
            titleTF.setText(tableB.getValueAt(row, 1).toString());
            authorTF.setText(tableB.getValueAt(row, 2).toString());
            isbnTF.setText(tableB.getValueAt(row, 3).toString());
            publHouseTF.setText(tableB.getValueAt(row, 4).toString());
            yearrTF.setText(tableB.getValueAt(row, 5).toString());


        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }

    }

    class DeleteActionB implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();

            String sql = "delete from books where id=?";

            try {
                state = conn.prepareStatement(sql);
                state.setInt(1, id);
                state.execute();
                id = -1;
                refreshTableB();
                // refreshCombo();
                clearFormB();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

    }

    public void clearFormB() {
        titleTF.setText("");
        authorTF.setText("");
        isbnTF.setText("");
        publHouseTF.setText("");
        yearrTF.setText("");
    }

    class SearchActionB implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            conn = DBConnection.getConnection();
            String sql = "select * from books where isbn=?";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, isbnTF.getText());
                result = state.executeQuery();
                tableB.setModel(new MyModel(result));
                clearFormB();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


        }

    }
    class RefreshActionB implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            refreshTableB();

        }

    }
    class EditBooks implements ActionListener {
        public void actionPerformed (ActionEvent arg0) {
            conn=DBConnection.getConnection();
            if(id>0) {
                String sql="update books set title=?, author=?, isbn=?, publHouse=?, yearr=? where id=?";

                try {
                    state=conn.prepareStatement(sql);

                    state.setString(1, titleTF.getText());
                    state.setString(2, authorTF.getText());
                    state.setString(3, isbnTF.getText());
                    state.setString(4, publHouseTF.getText());
                    state.setString(5, yearrTF.getText());
                    state.setInt(6, id);

                    state.execute();

                    refreshTableB();

                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                clearFormB();
            }

        }
    }
}
