import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FormPrincipal extends JFrame{
    /**
     * Variables Form
     */
    private JPanel PanelMain;
    private JTextField textUser;
    private JTextField textPass;
    private JButton submitButton;
    private JLabel idPass;
    private JLabel idUser;

    /**
     * Variables conexion, PrepareStatemen, Statement, Resulset
     */
    Connection cx = null;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    /**
     * Funciones del Panel Login
     * Button Submit valida conexion y ejecuta funcion login
     */
    public FormPrincipal() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    connection();
                    login();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        FormPrincipal f = new FormPrincipal();
        f.setContentPane(new FormPrincipal().PanelMain);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();
    }

    /**
     * Funcion Connection pasando url y parametros.
     * @throws SQLException
     */
    public void connection() throws SQLException {
        try{
            cx = DriverManager.getConnection("jdbc:mysql://localhost/manteniancedb","root","");
            System.out.println("Conexion Exitosa a DB");
        }catch (SQLException e){
            throw  new RuntimeException();
        }

    }

    /**
     * Limpiar TextField user,pass
     */
    public void limpiarTextField(){

        textPass.setText(null);
        textUser.setText(null);
    }
    /**
     * Funcion Login consulta si existe usuario
     * @throws SQLException
     */
    public void login() throws SQLException {

        st = cx.createStatement();
        rs =  st.executeQuery("SELECT * FROM usuario WHERE user = '"+ textUser.getText().toLowerCase() +"' AND pass = '"+ textPass.getText().toLowerCase() +"' ");
        if(rs.next()) {
            JOptionPane.showMessageDialog(null,"Bienvenido");
            limpiarTextField();
        }else{
            JOptionPane.showMessageDialog(null,"El usuario no Existe","WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
            limpiarTextField();
        }
    }

}

