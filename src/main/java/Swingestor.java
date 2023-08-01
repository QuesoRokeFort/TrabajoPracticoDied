import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Swingestor {

    public Sucursal addSucursal(JFrame jFrame){
        Sucursal s= new Sucursal();
        JPanel jPanel = new JPanel();
        // el panel es donde se ponen los elementos q luego cargas al frame
        JLabel labelEt1 = new JLabel("introduzca id:");
        JLabel labelEt2 = new JLabel("introduzca hora de apertura:(4 digitos sin ':' , ej: 1400):");
        JLabel labelEt3 = new JLabel("introduzca hora de cierre:(4 digitos sin ':' , ej: 2200):");
        JLabel labelEt4 = new JLabel("introduzca estado(cerrado ,abierto):");
        JLabel labelEt5 = new JLabel("introduzca el nombre:");
        JTextField txtTexto1 = new JTextField();
        txtTexto1.setColumns(20);
        JTextField txtTexto2 = new JTextField();
        txtTexto2.setColumns(20);
        JTextField txtTexto3 = new JTextField();
        txtTexto3.setColumns(20);
        JTextField txtTexto4 = new JTextField();
        txtTexto4.setColumns(20);
        JTextField txtTexto5 = new JTextField();
        txtTexto5.setColumns(20);
        jPanel.add(labelEt5);
        jPanel.add(txtTexto5);
        jPanel.add(labelEt1);
        jPanel.add(txtTexto1);
        jPanel.add(labelEt2);
        jPanel.add(txtTexto2);
        jPanel.add(labelEt3);
        jPanel.add(txtTexto3);
        jPanel.add(labelEt4);
        jPanel.add(txtTexto4);
        JButton boton1 = new JButton("UN BOTON ! ");
        jPanel.add(boton1);
        JButton botonSalida = new JButton("cancelar");
        jPanel.add(botonSalida);
        actualizarFrame(jFrame,jPanel);
        // funcion para q pasen cosas al tocar un boton
        final boolean[] aux = {false};
        CompletableFuture<Void> future =new CompletableFuture<>();
            boton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (txtTexto5.getText().equals("") || txtTexto1.getText().equals("") || txtTexto2.getText().equals("") || txtTexto3.getText().equals("") || txtTexto4.getText().equals("")) {
                        if (!aux[0]) {
                            JLabel aviso = new JLabel("ingrese los datos");
                            jPanel.add(aviso);
                            actualizarFrame(jFrame, jPanel);
                            aux[0] = true;
                        }
                        boton1.removeActionListener((ActionListener) e);
                    } else {
                        s.setNombre(txtTexto5.getText());
                        s.setId(Integer.parseInt(txtTexto1.getText()));
                        s.setHoraApertura(Integer.parseInt(txtTexto2.getText()));
                        s.setHoraCierre(Integer.parseInt(txtTexto3.getText()));
                        s.setEstado(txtTexto4.getText().equals("abierto") ? true : false);
                        future.complete(null);
                    }
                }
            });
            botonSalida.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    s.setNombre("");
                    future.complete(null);
                }
            });
        try {
            future.get();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return s;
    }
    private void actualizarFrame(JFrame jFrame,JPanel jPanel){
        jFrame.getContentPane().removeAll();
        jFrame.revalidate();
        jFrame.repaint();
        jFrame.add(jPanel);
        jFrame.setSize(400, 300);
        jFrame.setVisible(true);
    }
    public void showSucursal(JFrame jFrame,Sucursal s) {
        JPanel jpanel = new JPanel();
        // el panel es donde se ponen los elementos q luego cargas al frame
        JLabel labelEt1 = new JLabel("Id: "+s.getId());
        JLabel labelEt2 = new JLabel("Hora de apertura: "+s.getHoraApertura());
        JLabel labelEt3 = new JLabel("Hora de cierre: "+s.getHoraCierre());
        JLabel labelEt4 = new JLabel("Estado: " + (s.getEstado()?"abierto" :"cerrado"));
        JLabel labelEt5 = new JLabel("Nombre: "+ s.getNombre());
        JTextField txtTexto1 = new JTextField();
        txtTexto1.setColumns(20);
        JTextField txtTexto2 = new JTextField();
        txtTexto2.setColumns(20);
        JTextField txtTexto3 = new JTextField();
        txtTexto3.setColumns(20);
        JTextField txtTexto4 = new JTextField();
        txtTexto4.setColumns(20);
        JTextField txtTexto5 = new JTextField();
        txtTexto5.setColumns(20);
        jpanel.add(labelEt5);
        jpanel.add(txtTexto5);
        jpanel.add(labelEt1);
        jpanel.add(txtTexto1);
        jpanel.add(labelEt2);
        jpanel.add(txtTexto2);
        jpanel.add(labelEt3);
        jpanel.add(txtTexto3);
        jpanel.add(labelEt4);
        jpanel.add(txtTexto4);
        JButton boton1 = new JButton("UN BOTON ! ");
        jpanel.add(boton1);
        actualizarFrame(jFrame, jpanel);
        CompletableFuture <Void> future =new CompletableFuture<>();
        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s.setNombre(txtTexto5.getText().equals("")?s.getNombre():txtTexto5.getText());
                s.setId(txtTexto1.getText().equals("")? s.getId():Integer.parseInt(txtTexto1.getText()));
                s.setHoraApertura(txtTexto2.getText().equals("")?s.getHoraApertura():Integer.parseInt(txtTexto2.getText()));
                s.setHoraCierre(txtTexto3.getText().equals("")?s.getHoraCierre():Integer.parseInt(txtTexto3.getText()));
                s.setEstado(txtTexto4.getText().equals("")? s.getEstado():txtTexto4.getText().equals("abierto")?  true:false );
                future.complete(null);
            }
        });
        try {
            future.get();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public Swingestor() {
    }
    public int swingMenu(JFrame jFrame){
        final int[] valor = {-1};
        JPanel jPanel = new JPanel();
        JButton bt1= new JButton("1-añadir sucursal.");
        JButton bt2= new JButton("2- Mostrar sucursal.");
        JButton bt3= new JButton("3- Editar Sucursal.");
        JButton bt4= new JButton("4- Borrar sucursal.");
        JButton bt5= new JButton("5- Busacr sucursal.");
        JButton bt0= new JButton("0- Salir.");
        jPanel.add(bt1);
        jPanel.add(bt2);
        jPanel.add(bt3);
        jPanel.add(bt4);
        jPanel.add(bt5);
        jPanel.add(bt0);
        actualizarFrame(jFrame,jPanel);
        CompletableFuture<Void> future= new CompletableFuture<>();
            bt1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    valor[0] = 1;
                    future.complete(null);
                }
            });
            bt2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    valor[0] = 2;
                    future.complete(null);
                }
            });
            bt3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    valor[0] = 3;
                    future.complete(null);
                }
            });
            bt4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    valor[0] = 4;
                    future.complete(null);
                }
            });
            bt5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    valor[0] = 5;
                    future.complete(null);
                }
            });
            bt0.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    valor[0] = 0;
                    future.complete(null);
                }
            });
        try {
            future.get();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return valor[0];
    }
    public String menuBusqueda(JFrame jFrame, List<String> nombres){
        JPanel jPanel=new JPanel();
        final String[] valor = new String[1];
        final int[] aux={0};

        CompletableFuture<Void> future = new CompletableFuture<>();
        for (String s : nombres){
            JLabel jLabel = new JLabel(s);
            JButton bt = new JButton("modificar");
            jPanel.add(jLabel);
            jPanel.add(bt);
            bt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    valor[0] = s;
                    future.complete(null);
                }
            });
        }
        JButton button =new JButton("cancelar");
        jPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valor[0]="";
                future.complete(null);
            }
        });
        actualizarFrame(jFrame,jPanel);

        try {
            future.get();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return valor[0];
    }
}
