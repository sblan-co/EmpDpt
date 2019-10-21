/*
 * Author: Sara Blanco Muñoz
 * Connectors
 * E-mail: mublasa@gmail.com
 * Date: 19/01/2018
 * Updated: 21/10/2019
 */
package Index;

import Objects.Department;
import Objects.Employee;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *  Usegul panel which we can use to make the interface for both Departments
 *  and Employees, since they both share similar visual elements.
 */
public class ManagementPnl extends JPanel {

    private JTextField txtSearcher;
    private JButton[] buttons;
    private JComboBox<Department> cbDep;
    private JList list;
    private DefaultListModel listModel;

    public ManagementPnl(int option) {

        cbDep = new JComboBox();
        txtSearcher = new JTextField("Search...");
        buttons = new JButton[3];

        // Pestaña        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        if (option == 0) {
            cbDep.setVisible(false);
        } else {
            cbDep.setVisible(true);
        }

        JPanel searchList = new JPanel();
        listModel = new DefaultListModel();
        list = new JList(listModel);

        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        searchList.setLayout(new BoxLayout(searchList, BoxLayout.Y_AXIS));

        searchList.add(txtSearcher, BorderLayout.PAGE_START);
        searchList.add(new JScrollPane(list));

        buttons[0] = new JButton("Add");
        buttons[1] = new JButton("Edit");
        buttons[2] = new JButton("Delete");

        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(1, 3, 2, 10));

        for (JButton b : buttons) {
            pnlButtons.add(b);
        }

        this.add(cbDep);
        this.add(searchList);
        this.add(pnlButtons);

    }

    // Getters
    
    public JTextField getTxtSearcher() {
        return txtSearcher;
    }

    public JButton[] getButtons() {
        return buttons;
    }

    public JComboBox getCbDep() {
        return cbDep;
    }

    public JList getList() {
        return list;
    }
    
    public DefaultListModel getListModel() {
        return listModel;
    }    
    
    // Setters

    public void setList(Department[] data) {
        listModel.removeAllElements();

        for (Department d : data) {
            listModel.addElement(d);
        }
    }

    public void setList(Employee[] data) {
        listModel.removeAllElements();

        for (Employee d : data) {
            listModel.addElement(d);
        }
    }

    public void setCbDep(ArrayList<Department> data) {
        this.cbDep.removeAllItems();
        for (Department d : data) {
            this.cbDep.addItem(d);
        }
    }    
}
