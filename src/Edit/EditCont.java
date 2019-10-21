/*
 * Author: Sara Blanco Muñoz
 * Connectors
 * E-mail: mublasa@gmail.com
 * Date: 19/01/2018
 * Updated: 21/10/2019
 */
package Edit;

import Objects.Department;
import Objects.Employee;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

// Controller of our Edit Panel
public class EditCont implements FocusListener, KeyListener {

    private EditPnl v;
    private EditModel m;

    public EditCont(JFrame jf, int tipo, Object obj, ArrayList<Department> dptList) {
        v = new EditPnl();
        m = new EditModel();

        if (dptList != null) {
            v.setCbDpt(dptList);
        }

        v.getTxtId().addFocusListener(this);
        v.getTxtName().addFocusListener(this);

        v.getTxtId().addKeyListener(this);
        v.getTxtName().addKeyListener(this);

        if (tipo == 0) {
            Object[] inputs = {
                new JLabel("Id"),
                v.getTxtId(),
                new JLabel("Name"),
                v.getTxtName()
            };

            if (obj != null) {
                if (((Department) obj).getDptN() != -1) {
                    v.setDepartment((Department) obj);
                }
            }

            int result;

            do {
                result = JOptionPane.showConfirmDialog(jf, inputs, "Edit", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION && !checkValues(tipo)) {
                    JOptionPane.showMessageDialog(null, "All spaces must be filled.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } while (result == JOptionPane.OK_OPTION && !checkValues(tipo));

            if (result == JOptionPane.OK_OPTION && checkValues(tipo)) {
                m.setDpt(new Department(Integer.parseInt(v.getTxtId().getText()), v.getTxtName().getText()));
            }

        } else {

            Object[] inputs = {
                new JLabel("Id"),
                v.getTxtId(),
                new JLabel("Name"),
                v.getTxtName(),
                new JLabel("Department"),
                v.getCbDpt()
            };

            if (obj != null) {
                v.setEmployee((Employee) obj);
            }

            int result;

            do {
                result = JOptionPane.showConfirmDialog(jf, inputs, "Edit", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION && !checkValues(tipo)) {
                    JOptionPane.showMessageDialog(null, "All spaces must be filled.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } while (result == JOptionPane.OK_OPTION && !checkValues(tipo));

            if (result == JOptionPane.OK_OPTION && checkValues(tipo)) {
                System.out.println("set el empleao");
                m.setEmp(new Employee(v.getTxtName().getText(), v.getTxtId().getText(), ((Department)v.getCbDpt().getSelectedItem()).getDptN()));
            }

        }
    }

    // Getters
    
    public EditModel getM() {
        return m;
    }

    public EditPnl getV() {
        return v;
    }
    
    // Events functions
    
    // Focus Events 
    
    @Override
    public void focusGained(FocusEvent e) {
        JTextField txt = (JTextField) e.getSource();
        txt.setForeground(Color.black);
        
        if(txt.getText().equalsIgnoreCase("e.g.: 0001") || txt.getText().equalsIgnoreCase("e.g.: Logistics"))
            txt.setText("");        
    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField txt = (JTextField) e.getSource();

        if (txt.getText() == null || txt.getText().length() <= 0){
            txt.setForeground(Color.gray);
            if (txt == v.getTxtId()) {
                txt.setText("e.g.: 0001");
            } else if (txt == v.getTxtName()) {
                txt.setText("e.g.: Logistics");
            }
        }
    }
    
    // Key Events

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == this.getV().getTxtId()) {
            if (!isNumeric("" + e.getKeyChar())) {
                e.consume();
            }
        } else if (e.getSource() == this.getV().getTxtName()) {
            char c = e.getKeyChar();
            c = ("" + c).toUpperCase().charAt(0);
            
            if (!((c >= 'A' && c <= 'Z') || c == ' ' || c == '\b')) {
               e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        JTextField txt = (JTextField) e.getSource();

        if (e.getSource() == this.getV().getTxtName()) {
            if (txt.getText() != null) {
                if (txt.getText().length() > 0 && !includesAlphabetic()) {
                    txt.setBackground(Color.red);
                    txt.setForeground(Color.white);
                } else {
                    txt.setBackground(Color.white);
                    txt.setForeground(Color.black);
                }
            }
        }
    }
    
    // Additional functions

    public static boolean isNumeric(String strNum) {
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    private boolean includesAlphabetic() {
        String s = this.getV().getTxtName().getText().toUpperCase();
        boolean b = false;
        char c;
        int i = 0;

        do {
            c = s.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                b = !b;
            }
            i++;
        } while (i < s.length() && b == false);

        return b;
    }       

    private boolean checkValues(int type) {
        boolean b = (v.getTxtId().getText().matches("e.g.: 0001") || v.getTxtName().getText().matches("e.g.: Logistics"));

        if (type == 1) {
            return !(b || v.getCbDpt().getSelectedIndex() == -1);
        } else {
            return !b;
        }
    }

    public void fillUpCB(ArrayList<Department> dptList) {
        this.v.setCbDpt(dptList);
    }

}
