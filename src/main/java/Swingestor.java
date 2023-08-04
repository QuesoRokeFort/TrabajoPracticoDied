import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
public class Swingestor {
    JPanel jPanel=new JPanel();
    JFrame jFrame;

    public void setjFrame(JFrame jFrame) {
        this.jFrame = jFrame;
    }

    /*public Sucursal addSucursal(int id){
        System.out.println("here");
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
        actualizarFrame(jPanel);
        System.out.println("here");
        final boolean[] aux = {false};
        CompletableFuture<Void> future =new CompletableFuture<>();
            agregar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (txtNombre.getText().equals("") || txtHoraApertura.getText().equals("") || txtHoraCierre.getText().equals("") || txtEstado.getText().equals("")) {
                        if (!aux[0]) {
                            JLabel aviso = new JLabel("ingrese los datos");
                            jPanel.add(aviso);
                            actualizarFrame(jPanel);
                            aux[0] = true;
                        }
                    } else {
                        s.Modificada();
                        s.setNombre(txtNombre.getText());
                        s.setHoraApertura(Integer.parseInt(txtHoraApertura.getText()));
                        s.setHoraCierre(Integer.parseInt(txtHoraCierre.getText()));
                        s.setEstado(txtEstado.getText().equals("abierto") ? Estado.OPERATIVA : Estado.NO_OPERATIVA);
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
        System.out.println("here");
        try {
            future.get();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return s;
    }*/
    public void actualizarFrame(JPanel jPanel){
        jFrame.getContentPane().removeAll();
        jFrame.revalidate();
        jFrame.repaint();
        jFrame.add(jPanel);
        jFrame.setSize(550, 600);
        jFrame.setVisible(true);
    }
    /*public void modificarSucursal(Sucursal s) {
        JPanel jpanel = new JPanel();
        JLabel labelId = new JLabel("Id: "+s.getId());
        JLabel labelHoraApertura = new JLabel("Hora de apertura: "+s.getHoraApertura());
        JLabel labelHoraCierre = new JLabel("Hora de cierre: "+s.getHoraCierre());
        JLabel labelEstado = new JLabel("Estado: " + (s.getEstado().equals(Estado.OPERATIVA)?"abierto" :"cerrado"));
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
        actualizarFrame(jpanel);
        CompletableFuture <Void> future =new CompletableFuture<>();
        atras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                future.complete(null);
            }
        });
        modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(txtNombre.getText().equals("") && txtHoraApertura.getText().equals("") && txtHoraCierre.getText().equals("") && txtEstado.getText().equals(""))){
                    s.Modificada();
                    if(!txtNombre.getText().equals("")) s.setNombre(txtNombre.getText());
                    if(!txtHoraApertura.getText().equals("")) s.setHoraApertura(Integer.parseInt(txtHoraApertura.getText()));
                    if(!txtHoraCierre.getText().equals("")) s.setHoraCierre(Integer.parseInt(txtHoraCierre.getText()));
                    if(!txtEstado.getText().equals("")) s.setEstado(txtEstado.getText().equals("abierto")?  Estado.OPERATIVA:Estado.NO_OPERATIVA);
                }
                future.complete(null);
            }
        });
        try {
            future.get();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }*/
    public Swingestor() {
    }
    public int swingMenuListaBotones(int cantidadOpciones, List<String>listaOpciones){
        final int[] opcion = {-1};
        JPanel jPanel = new JPanel();
        CompletableFuture<Void> future= new CompletableFuture<>();
        for (int i=1;i<cantidadOpciones+1;i++){
            JButton bt= new JButton(i+"- "+listaOpciones.get(i-1));
            jPanel.add(bt);
            int finalI = i;
            bt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    opcion[0] = finalI;
                    future.complete(null);
                }
            });
        }
        JButton bt0= new JButton("0- Salir.");
        jPanel.add(bt0);
        actualizarFrame(jPanel);
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
    /*public int swingMenu(JFrame jFrame){
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
    }*/
    public void menuBusqueda(){
        SearchBox SearchBox = new SearchBox();
        SearchBox.buscador(jFrame);
    }
}
