
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;

public class SearchBox {
    private JTextField searchable = new JTextField(30);
    private JButton searchB = new JButton("Search");
    private JTable result = new JTable();
    private JPanel panel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(result);

    private JButton agregarSucursal = new JButton("agregar");
    JLabel aviso = new JLabel("ingrese todos los valores correctamente");

    public void buscador(JFrame jFrame,String tabla) throws HeadlessException {
        addComponents(jFrame);
        setTable(jFrame, tabla);
        CompletableFuture<Void> future = new CompletableFuture<>();
        JLabel actualizado = new JLabel("actualizado correctamente");
        JButton modificar = new JButton("modificar");
        modificar.addActionListener(e -> {
            agregarSucursal.setText("agregar");
            panel.remove(aviso);
            int selectedRow = result.getSelectedRow();
            if (selectedRow != -1) {
                if (tabla.equals("sucursal")) {

                    Sucursal sucursal= (Sucursal) Gestor.datosFilaPorId(tabla,(int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id")));
                    Gestor.actulizarSucursal(sucursal);
                }else{
                    if(tabla.equals("producto")){
                        Producto producto= (Producto) Gestor.datosFilaPorId(tabla,(int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id")));
                       // Gestor.actulizarProducto(producto);
                    }
                }

                buscarTodo(tabla);
                panel.add(actualizado);
            }
            actualizarFrame(jFrame,panel);
        });
        JButton cancelar =new JButton("cancelar");
        cancelar.addActionListener(e ->  {
            future.complete((null));
        });
        JButton borrar = new JButton("borrar");
        borrar.addActionListener(e -> {
            agregarSucursal.setText("agregar");
            panel.remove(actualizado);
            panel.remove(aviso);
            actualizarFrame(jFrame,panel);
            int selectedRow = result.getSelectedRow();
            Gestor.borrarSucursal((int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id")));
            buscarTodo(tabla);
        });
        agregarSucursal.addActionListener(e -> {
            if (agregarSucursal.getText().equals("agregar")) {
                agregarSucursal.setText("guardar");
                panel.remove(actualizado);
                actualizarFrame(jFrame,panel);
                DefaultTableModel model = (DefaultTableModel) result.getModel();
                Vector<Object> emptyRow = new Vector<>();
                emptyRow.add(Gestor.getLastValue("id", "sucursal") + 1);
                for (int i = 1; i < model.getColumnCount(); i++) {
                    emptyRow.add("");
                }
                model.addRow(emptyRow);
            }else {
                int lastRow = result.getRowCount() - 1;
                Sucursal sucursal= getSucursalFromLine(lastRow);
                if(sucursal.tieneValores()) {
                    agregarSucursal.setText("agregar");
                    Gestor.cargarSucursal(sucursal);
                }else {
                    panel.add(aviso);
                    actualizarFrame(jFrame,panel);
                }
            }
        });
        panel.add(agregarSucursal);
        panel.add(modificar);
        panel.add(borrar);
        panel.add(cancelar);
        buscarTodo(tabla);
        jFrame.setVisible(true);
        try {
            future.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void actualizarFrame(JFrame jFrame,JPanel jPanel){
        jFrame.getContentPane().removeAll();
        jFrame.revalidate();
        jFrame.repaint();
        jFrame.add(jPanel);
        jFrame.setSize(550, 600);
        jFrame.setVisible(true);
    }

    private void addComponents(JFrame jFrame) {
        jFrame.getContentPane().removeAll();
        jFrame.revalidate();
        jFrame.repaint();
        panel.add(searchable);
        panel.add(searchB);
        panel.add(scrollPane);
        jFrame.add(panel);
        jFrame.setSize(550, 600);
        jFrame.setVisible(true);
    }
    private void setTable(JFrame jFrame,String tabla) {
        searchB.addActionListener(e -> {
            agregarSucursal.setText("agregar");
            panel.remove(aviso);
            actualizarFrame(jFrame,panel);
            result.setModel(DbUtils.resultSetToTableModel(
                    new DataBase().search(searchable.getText(), panel,tabla)));
        });
    }
    public void buscarTodo(String tabla){
        result.setModel(DbUtils.resultSetToTableModel(
                new DataBase().search("", panel,tabla)));
    }
    /*public void listar(JFrame jFrame) {
        jFrame.getContentPane().removeAll();
        jFrame.revalidate();
        jFrame.repaint();
        setTable();
        result.setModel(DbUtils.resultSetToTableModel(
                new DataBase().search("", panel)));
        panel.add(scrollPane);
        CompletableFuture<Void> future = new CompletableFuture<>();
        JButton cancelar =new JButton("cancelar");
        cancelar.addActionListener(e ->  {
            future.complete((null));
        });
        panel.add(cancelar);
        jFrame.add(panel);
        jFrame.setSize(600, 600);
        jFrame.setVisible(true);
        try {
            future.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }*/
    public Sucursal getSucursalFromLine(int selectedRow){
        Sucursal sucursal= new Sucursal();
        sucursal.setId( (int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id"))); // 0 is the column index for the ID column
        sucursal.setNombre((String) result.getValueAt(selectedRow,result.getColumnModel().getColumnIndex("nombre")));
        Object horaAperturaObj = result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("horaapertura"));
        int horaApertura = 0;
        if(!horaAperturaObj.toString().equals("") && !horaAperturaObj.toString().equals(null)){
            horaApertura = Integer.parseInt(horaAperturaObj.toString());
        }
        sucursal.setHoraApertura(horaApertura);
        Object horaCierreObj = result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("horacierre"));
        int horaCierre = 0;
        if(!horaCierreObj.toString().equals("") && !horaCierreObj.toString().equals(null)){
            horaCierre = Integer.parseInt(horaAperturaObj.toString());
        }
        sucursal.setHoraCierre(horaCierre);
        Object estadoObj = result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("estado"));
        boolean estado = Boolean.parseBoolean(estadoObj.toString());
        sucursal.setEstado(estado ? Estado.OPERATIVA : Estado.NO_OPERATIVA);
        return sucursal;
    }
}
