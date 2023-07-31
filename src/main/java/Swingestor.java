import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Swingestor {
    public void showSucursal(Sucursal s) {
        JFrame jframe = new JFrame("Sucursal: "+s.getNombre());
        JPanel jpanel = new JPanel(); // agregamos todos los elementos a un panel.

        JLabel labelEt1 = new JLabel("Id: "+s.getId());
        JLabel labelEt2 = new JLabel("Hora de apertura: "+s.getHoraApertura());
        JLabel labelEt3 = new JLabel("Hora de cierre: "+s.getHoraCierre());
        JLabel labelEt4 = new JLabel("Estado: " + (s.getEstado()?"abierto" :"cerrado"));
        JTextField txtTexto1 = new JTextField();
        txtTexto1.setColumns(20);
        JTextField txtTexto2 = new JTextField();
        txtTexto2.setColumns(20);
        JTextField txtTexto3 = new JTextField();
        txtTexto3.setColumns(20);
        JTextField txtTexto4 = new JTextField();
        txtTexto4.setColumns(20);
        jpanel.add(labelEt1);
        jpanel.add(txtTexto1);
        jpanel.add(labelEt2);
        jpanel.add(txtTexto2);
        jpanel.add(labelEt3);
        jpanel.add(txtTexto3);
        jpanel.add(labelEt4);
        jpanel.add(txtTexto4);
        JButton boton1 = new JButton("UN BOTON ! ");
        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s.setId(txtTexto1.getText().equals("")? s.getId():Integer.parseInt(txtTexto1.getText()));
                s.setHoraApertura(txtTexto2.getText().equals("")?s.getHoraApertura():Integer.parseInt(txtTexto2.getText()));
                s.setHoraCierre(txtTexto3.getText().equals("")?s.getHoraCierre():Integer.parseInt(txtTexto3.getText()));
                s.setEstado(txtTexto4.getText().equals("")? s.getEstado():txtTexto4.getText().equals("abierto")?  true:false );
            }
        });
        jpanel.add(boton1);
        jframe.add(jpanel); // lo agrega en el centro
        //jframe.setContentPane(jpanel);
        jframe.pack(); //SIN ESTE METODO NO se ve la ventana.
        jframe.setSize(400, 250);
        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jframe.setVisible(true);
    }
    private JPanel crearpanelSucursal(Sucursal s){
        return new JPanel();
    }
    public Swingestor() {
    }
}
