/*
 * Author: Sara Blanco Muñoz
 * Connectors
 * E-mail: mublasa@gmail.com
 * Date: 19/01/2018
 * Updated: 21/10/2019
 */
package Index;


import Objects.Department;
import Edit.EditCont;
import com.db4o.ObjectSet;
import Objects.Employee;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *  Controller of the main frame. Attributes:
 *      - v : View where we can find the JFrame and all its elements.
 *      - m : Model where we can save and keep the data related to the Departments and Employees. 
 * 
 *  Actions related to this class are click, focus on text boxes, keyboard events and selection
 *  of items of a list.
 */

public class IndexCont implements ActionListener, ListSelectionListener, FocusListener, KeyListener {

    private IndexView v;
    private IndexModel m;

    public IndexCont() {
        v = new IndexView();
        m = new IndexModel();

        this.refreshAll();
        this.addALtoButtons();
    }

    // Personal functions
    
    /*
    *   This functions updates and refreshes the elements on the frame with
    *   the last changes that we have executed on the database.
    */
    private void refreshAll() {
        this.refreshEmp();
        this.refreshDpt();
        v.getEmpPnl().setCbDep(m.getDptList());
        v.pack();
    }

    // Retrieves the information related to the Departments from the DDBB
    private void refreshDpt() {
        Department dpAux = (Department) m.getDptList().get(0);

        if (dpAux.getDptN() < 0 || v.getDptPnl().getList().getSelectedValue() == null) {
            v.getDptPnl().getButtons()[1].setEnabled(false);
            v.getDptPnl().getButtons()[2].setEnabled(false);
        }

        m.refreshDpt();

        Department[] arrayAux = new Department[m.getDptList().size()];

        for (int i = 0; i < m.getDptList().size(); i++) {
            arrayAux[i] = m.getDptList().get(i);
        }

        v.getDptPnl().setList(arrayAux);

        v.revalidate();
        v.repaint();
    }
       
    // Retrieves the information related to the Employees from the DDBB
    private void refreshEmp() {
        Employee empAux = (Employee) m.getEmpList().get(0);

        if (empAux.getDpt() < 0) {
            v.getEmpPnl().getButtons()[1].setEnabled(false);
            v.getEmpPnl().getButtons()[2].setEnabled(false);
        }

        m.refreshEmp();

        Employee[] arrayAux = new Employee[m.getEmpList().size()];

        for (int i = 0; i < m.getEmpList().size(); i++) {
            arrayAux[i] = m.getEmpList().get(i);
        }

        v.getEmpPnl().setList(arrayAux);

        
        v.revalidate();
        v.repaint();
        
    }

    // We make the buttons listen to our click events
    private void addALtoButtons() {
        for (JButton b : v.getDptPnl().getButtons()) {
            b.addActionListener(this);
        }
        for (JButton b : v.getEmpPnl().getButtons()) {
            b.addActionListener(this);
        }

        v.getDptPnl().getList().addListSelectionListener(this);
        v.getEmpPnl().getList().addListSelectionListener(this);

        v.getDptPnl().getTxtSearcher().addKeyListener(this);
        v.getEmpPnl().getTxtSearcher().addKeyListener(this);
        
        v.getDptPnl().getTxtSearcher().addFocusListener(this);
        v.getEmpPnl().getTxtSearcher().addFocusListener(this);        
    }    
    
    public static boolean isNumeric(String strNum) {
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    // Events functions
    
    // Actions buttons should follow when clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        EditCont ec;

        // Departments                        
        if (Arrays.asList(v.getDptPnl().getButtons()).contains((JButton) e.getSource())) {
            // If it is not the Delete button
            if (e.getSource() != v.getDptPnl().getButtons()[2]) {
                Department d = (Department) v.getDptPnl().getList().getSelectedValue();

                if (e.getSource() == v.getDptPnl().getButtons()[0]) {
                    // Add
                    ec = new EditCont(v, 0, null, null);
                    
                    if(ec.getM().getDpt() != null)
                        m.getDb().store(ec.getM().getDpt());
                } else if (v.getDptPnl().getList().getSelectedIndex() != -1){
                    // Edit
                    ec = new EditCont(v, 0, d, null);
                    ec.getV().setDepartment(d);

                    ObjectSet<Department> result = m.getDb().queryByExample(d);

                    if (!result.isEmpty() && ec.getM().getDpt()!= null) {
                        Department depAux = (Department) result.next();

                        depAux.setDptN(ec.getM().getDpt().getDptN());
                        depAux.setDptName(ec.getM().getDpt().getDptName());

                        m.getDb().store(depAux);
                    }
                }
            } else {
                // Delete
                ObjectSet<Department> result = m.getDb().queryByExample((Department) v.getDptPnl().getList().getSelectedValue());

                if (!result.isEmpty()) {
                    Department depAux = (Department) result.next();

                    // Delete                    
                    int cfDelete = JOptionPane.showConfirmDialog(null, "Do you want to delete '" + depAux.getDptN() + " - " + depAux.getDptName() + "'?", "Delete", JOptionPane.OK_CANCEL_OPTION);
                    if (cfDelete == JOptionPane.OK_OPTION) {
                        m.getDb().delete(depAux);
                    }
                }
            }
        }

        // Employees
        if (Arrays.asList(v.getEmpPnl().getButtons()).contains((JButton) e.getSource())) {

            // If it is not the Delete button
            if (e.getSource() != v.getEmpPnl().getButtons()[2]) {
                if (m.getDptList().get(0).getDptN() == -1 && e.getSource() == v.getEmpPnl().getButtons()[0]) {
                    JOptionPane.showMessageDialog(null, "It is not possible to add Employees without any Departments.");
                } else {

                    Employee emp = (Employee) v.getEmpPnl().getList().getSelectedValue();

                    if (e.getSource() == v.getEmpPnl().getButtons()[0]) {
                        // Add
                        ec = new EditCont(v, 1, null, m.getDptList());                    

                        if(ec.getM().getEmp() != null){
                            ec.getV().setCbDpt(m.getDptList());                        
                            m.getDb().store(ec.getM().getEmp());
                        }

                    } else if (v.getEmpPnl().getList().getSelectedIndex() != -1){
                        // Edit
                        ec = new EditCont(v, 1, emp, m.getDptList());                    
                        ec.getV().setCbDpt(m.getDptList());
                        ec.getV().setEmployee(emp);

                        ObjectSet<Employee> result = m.getDb().queryByExample(emp);

                        if (!result.isEmpty() && ec.getM().getEmp() != null) {
                            Employee empAux = (Employee) result.next();

                            empAux.setDpt(ec.getM().getEmp().getDpt());
                            empAux.setName(ec.getM().getEmp().getName());
                            empAux.setId(ec.getM().getEmp().getId());

                            m.getDb().store(empAux);
                        }
                    }
                }
            } else {
                // Delete
                ObjectSet<Employee> result = m.getDb().queryByExample((Employee) v.getEmpPnl().getList().getSelectedValue());

                if (!result.isEmpty()) {
                    Employee empAux = (Employee) result.next();

                    int cfDelete = JOptionPane.showConfirmDialog(v, "Do you want to delete '" + empAux.getId() + " - " + empAux.getName() + "'?", "Delete", JOptionPane.OK_CANCEL_OPTION);
                    if (cfDelete == JOptionPane.OK_OPTION) {
                        m.getDb().delete(empAux);
                    }
                }
            }
        }

        this.refreshAll();
    }

    // We will enable some buttons like Edit or Delete only when there's an item selected
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == v.getDptPnl().getList()) {
            Department d = (Department) v.getDptPnl().getList().getSelectedValue();

            if (d != null && d.getDptN() != -1) {
                v.getDptPnl().getButtons()[1].setEnabled(true);
                v.getDptPnl().getButtons()[2].setEnabled(true);
            }
        } else if (e.getSource() == v.getEmpPnl().getList()) {
            Employee emp = (Employee) v.getEmpPnl().getList().getSelectedValue();

            if (emp != null && emp.getDpt() != -1) {
                v.getEmpPnl().getButtons()[1].setEnabled(true);
                v.getEmpPnl().getButtons()[2].setEnabled(true);
            }
        }
    }

    // Key Events
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String filter = ((JTextField) e.getSource()).getText().toUpperCase();
        DefaultListModel model = new DefaultListModel();
        
        if (e.getSource() == v.getDptPnl().getTxtSearcher()) {            
            for (Department dpt : m.getDptList()) {
                if (!isNumeric(filter)) {
                    if (dpt.getDptName().toUpperCase().contains(filter)) {
                        model.addElement(dpt);
                    } else {
                        model.removeElement(dpt);
                    }
                } else {
                    if (("" + dpt.getDptN()).contains(filter)) {
                        model.addElement(dpt);
                    } else {
                        model.removeElement(dpt);
                    }
                }
            }
            
            v.getDptPnl().getList().setModel(model);
            
        } else {
            for (Employee emp : m.getEmpList()) {
                if (!isNumeric(filter)) {
                    if (emp.getName().toUpperCase().contains(filter)) {
                        model.addElement(emp);
                    } else {
                        model.removeElement(emp);
                    }
                } else {
                    if (("" + emp.getId()).contains(filter)) {
                        model.addElement(emp);
                    } else {
                        model.removeElement(emp);
                    }
                }
            }
            v.getEmpPnl().getList().setModel(model);
        }
        
        if (filter == null || filter.length() <= 0){
            this.refreshAll();
        }            
    }

    // Focus events
    
    @Override
    public void focusGained(FocusEvent e) {
        JTextField txt = (JTextField) e.getSource();
        txt.setForeground(Color.black);
        txt.setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField txt = (JTextField) e.getSource();        
            
        if (txt.getText() == null || txt.getText().length() < 1) 
            txt.setText("Search...");
    }
}
