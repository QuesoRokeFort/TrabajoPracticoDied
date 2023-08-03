import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Swingestor {

    public Sucursal addSucursal(JFrame jFrame,int id){
        Sucursal s= new Sucursal();
        s.setId(id);
        JPanel jPanel = new JPanel();
        // el panel es donde se ponen los elementos q luego cargas al frame
        JLabel labelId = new JLabel("id: "+id);
        JLabel labelHoraApertura = new JLabel("introduzca hora de apertura:(4 digitos sin ':' , ej: 1400):");
        JLabel labelHoraCierre = new JLabel("introduzca hora de cierre:(4 digitos sin ':' , ej: 2200):");
        JLabel labelEstado = new JLabel("introduzca estado(cerrado ,abierto):");
        JLabel labelNombre = new JLabel("introduzca el nombre:");
        JTextField txtHoraApertura = new JTextField();
        txtHoraApertura.setColumns(20);
        JTextField txtHoraCierre = new JTextField();
        txtHoraCierre.setColumns(20);
        JTextField txtEstado = new JTextField();
        txtEstado.setColumns(20);
        JTextField txtNombre = new JTextField();
        txtNombre.setColumns(20);
        jPanel.add(labelNombre);
        jPanel.add(txtNombre);
        jPanel.add(labelId);
        jPanel.add(labelHoraApertura);
        jPanel.add(txtHoraApertura);
        jPanel.add(labelHoraCierre);
        jPanel.add(txtHoraCierre);
        jPanel.add(labelEstado);
        jPanel.add(txtEstado);
        JButton agregar = new JButton("Agregar");
        jPanel.add(agregar);
        JButton cancelar = new JButton("Cancelar");
        jPanel.add(cancelar);
        actualizarFrame(jFrame,jPanel);
        // funcion para q pasen cosas al tocar un boton
        final boolean[] aux = {false};
        CompletableFuture<Void> future =new CompletableFuture<>();
            agregar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (txtNombre.getText().equals("") || txtHoraApertura.getText().equals("") || txtHoraCierre.getText().equals("") || txtEstado.getText().equals("")) {
                        if (!aux[0]) {
                            JLabel aviso = new JLabel("ingrese los datos");
                            jPanel.add(aviso);
                            actualizarFrame(jFrame, jPanel);
                            aux[0] = true;
                        }
                    } else {
                        s.setNombre(txtNombre.getText());
                        s.setHoraApertura(Integer.parseInt(txtHoraApertura.getText()));
                        s.setHoraCierre(Integer.parseInt(txtHoraCierre.getText()));
                        s.setEstado(txtEstado.getText().equals("abierto") ? true : false);
                        future.complete(null);
                    }
                }
            });
            cancelar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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
    public void showSucursal(JFrame jFrame, ArrayList<Sucursal> sucusales) {
        JPanel jpanel = new JPanel();
        for (Sucursal s : sucusales) {  
            // el panel es donde se ponen los elementos q luego cargas al frame
            JLabel labelId = new JLabel("Id: " + s.getId());
            JLabel labelHoraApertura = new JLabel("Hora de apertura: " + s.getHoraApertura());
            JLabel labelHoraCierre = new JLabel("Hora de cierre: " + s.getHoraCierre());
            JLabel labelEstado = new JLabel("Estado: " + (s.getEstado() ? "abierto" : "cerrado"));
            JLabel labelNombre = new JLabel("Nombre: " + s.getNombre());
            jpanel.add(labelNombre);
            jpanel.add(labelId);
            jpanel.add(labelHoraApertura);
            jpanel.add(labelHoraCierre);
            jpanel.add(labelEstado);
        }
        JButton volver = new JButton("Volver");
        jpanel.add(volver);
        actualizarFrame(jFrame, jpanel);
        CompletableFuture<Void> future = new CompletableFuture<>();
        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                future.complete(null);
            }
        });
        try {
            future.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public Sucursal modificarSucursal(JFrame jFrame,Sucursal s) {
        Sucursal sucursal= new Sucursal();
        sucursal.setId(s.getId());
        JPanel jpanel = new JPanel();
        // el panel es donde se ponen los elementos q luego cargas al frame
        JLabel labelId = new JLabel("Id: "+s.getId());
        JLabel labelHoraApertura = new JLabel("Hora de apertura: "+s.getHoraApertura());
        JLabel labelHoraCierre = new JLabel("Hora de cierre: "+s.getHoraCierre());
        JLabel labelEstado = new JLabel("Estado: " + (s.getEstado()?"abierto" :"cerrado"));
        JLabel labelNombre = new JLabel("Nombre: "+ s.getNombre());
        JTextField txtHoraApertura = new JTextField();
        txtHoraApertura.setColumns(20);
        JTextField txtHoraCierre = new JTextField();
        txtHoraCierre.setColumns(20);
        JTextField txtEstado = new JTextField();
        txtEstado.setColumns(20);
        JTextField txtNombre = new JTextField();
        txtNombre.setColumns(20);
        jpanel.add(labelNombre);
        jpanel.add(txtNombre);
        jpanel.add(labelId);
        jpanel.add(labelHoraApertura);
        jpanel.add(txtHoraApertura);
        jpanel.add(labelHoraCierre);
        jpanel.add(txtHoraCierre);
        jpanel.add(labelEstado);
        jpanel.add(txtEstado);
        JButton modificar = new JButton("Modificar");
        jpanel.add(modificar);
        JButton atras = new JButton("Atras");
        jpanel.add(atras);
        JButton borrar = new JButton("BORRAR");
        jpanel.add(borrar);
        actualizarFrame(jFrame, jpanel);
        CompletableFuture <Void> future =new CompletableFuture<>();
        atras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                future.complete(null);
            }
        });
        borrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sucursal.setNombre(txtNombre.getText().equals("")?s.getNombre():txtNombre.getText());
                sucursal.borrarSucursal();
                future.complete(null);
            }
        });
        modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(txtNombre.getText().equals("") && txtHoraApertura.getText().equals("") && txtHoraCierre.getText().equals("") && txtEstado.getText().equals(""))){
                    sucursal.setNombre(txtNombre.getText().equals("")?s.getNombre():txtNombre.getText());
                    sucursal.setHoraApertura(txtHoraApertura.getText().equals("")?s.getHoraApertura():Integer.parseInt(txtHoraApertura.getText()));
                    sucursal.setHoraCierre(txtHoraCierre.getText().equals("")?s.getHoraCierre():Integer.parseInt(txtHoraCierre.getText()));
                    sucursal.setEstado(txtEstado.getText().equals("")? s.getEstado():txtEstado.getText().equals("abierto")?  true:false );
                }
                future.complete(null);
            }
        });
        try {
            future.get();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return sucursal;
    }
    public Swingestor() {
    }
    public int swingMenu(JFrame jFrame){
        final int[] opcion = {-1};
        JPanel jPanel = new JPanel();
        JButton bt1= new JButton("1-añadir sucursal.");
        JButton bt2= new JButton("2- Mostrar sucursal.");
        JButton bt3= new JButton("3- Buscar sucursal.");
        JButton bt0= new JButton("0- Salir.");
        jPanel.add(bt1);
        jPanel.add(bt2);
        jPanel.add(bt3);
        jPanel.add(bt0);
        actualizarFrame(jFrame,jPanel);
        CompletableFuture<Void> future= new CompletableFuture<>();
            bt1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    opcion[0] = 1;
                    future.complete(null);
                }
            });
            bt2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    opcion[0] = 2;
                    future.complete(null);
                }
            });
            bt3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    opcion[0] = 3;
                    future.complete(null);
                }
            });
            bt0.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    opcion[0] = 0;
                    future.complete(null);
                }
            });
        try {
            future.get();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return opcion[0];
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
