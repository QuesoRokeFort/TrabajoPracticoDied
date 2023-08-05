
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

    private JButton agregar = new JButton("agregar");
    JLabel aviso = new JLabel("ingrese todos los valores correctamente");

    public void buscador(JFrame jFrame,String tabla) throws HeadlessException {
        addComponents(jFrame);
        setTable(jFrame, tabla);
        CompletableFuture<Void> future = new CompletableFuture<>();
        JLabel actualizado = new JLabel("actualizado correctamente");
        JButton modificar = new JButton("modificar");
        modificar.addActionListener(e -> {
            agregar.setText("agregar");
            panel.remove(aviso);
            int selectedRow = result.getSelectedRow();
            if (selectedRow != -1) {
                if (tabla.equals("sucursal")) {

                    Sucursal sucursal= getSucursalFromLine(selectedRow);
                    Gestor.actulizarSucursal(sucursal);
                }else{
                    if(tabla.equals("producto")){
                        Producto producto= getProductoFromLine(selectedRow);
                        System.out.println(producto.getDescripcion());
                        System.out.println(producto.getNombre());
                        Gestor.actulizarProducto(producto);
                    }else{
                        if (tabla.equals("stock")){
                            Stock stock = getStockFromLine(selectedRow);
                            Gestor.actulizarStock(stock);
                        }else {
                            if (tabla.equals("camino")){
                                Camino camino = getCaminoFromLine(selectedRow);
                                Gestor.actualizarCamino(camino);
                            }
                        }
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
            agregar.setText("agregar");
            panel.remove(actualizado);
            panel.remove(aviso);
            actualizarFrame(jFrame,panel);
            int selectedRow = result.getSelectedRow();
            if (tabla.equals("sucursal")) {
                Gestor.borrarSucursal((int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id")));
            }else{
                if(tabla.equals("producto")){
                    Gestor.borrarProducto((int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id")));
                }else{
                    if (tabla.equals("stock")){
                        Gestor.borrarStock((int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id")));
                    }else {
                        if (tabla.equals("camino")){
                            Gestor.borrarCamino((int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id")));
                        }
                    }
                }
            }
            buscarTodo(tabla);
        });
        agregar.addActionListener(e -> {
            if (agregar.getText().equals("agregar")) {
                agregar.setText("guardar");
                panel.remove(actualizado);
                actualizarFrame(jFrame,panel);
                DefaultTableModel model = (DefaultTableModel) result.getModel();
                Vector<Object> emptyRow = new Vector<>();
                emptyRow.add(Gestor.getLastValue("id", tabla) + 1);
                for (int i = 1; i < model.getColumnCount(); i++) {
                    emptyRow.add("");
                }
                model.addRow(emptyRow);
            }else {
                int lastRow = result.getRowCount() - 1;
                if (tabla.equals("sucursal")) {
                    Sucursal sucursal= getSucursalFromLine(lastRow);
                    if(sucursal.tieneValores()) {
                        agregar.setText("agregar");
                        Gestor.cargarSucursal(sucursal);
                    }else {
                        panel.add(aviso);
                        actualizarFrame(jFrame,panel);
                    }
                }else{
                    if(tabla.equals("producto")){
                        Producto producto = getProductoFromLine(lastRow);
                        if(producto.tieneValores()) {
                            agregar.setText("agregar");
                            Gestor.cargarProducto(producto);
                        }else {
                            panel.add(aviso);
                            actualizarFrame(jFrame,panel);
                        }
                    }else{
                        if (tabla.equals("stock")){
                            Stock stock = getStockFromLine(lastRow);
                            if(stock.tieneValores()) {
                                agregar.setText("agregar");
                                Gestor.cargarStock(stock);
                            }else {
                                panel.add(aviso);
                                actualizarFrame(jFrame,panel);
                            }
                        }else {
                            if (tabla.equals("camino")){
                                Camino camino = getCaminoFromLine(lastRow);
                                if(camino.tieneValores()) {
                                    agregar.setText("agregar");
                                    Gestor.cargarCamino(camino);
                                }else {
                                    panel.add(aviso);
                                    actualizarFrame(jFrame,panel);
                                }
                            }
                        }
                    }
                }
            }
        });
        panel.add(agregar);
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
            agregar.setText("agregar");
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
    private Producto getProductoFromLine(int selectedRow) {
        Producto producto = new Producto();
        producto.setId( (int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id"))); // 0 is the column index for the ID column
        producto.setNombre((String) result.getValueAt(selectedRow,result.getColumnModel().getColumnIndex("nombre")));
        producto.setDescripcion((String)result.getValueAt(selectedRow,result.getColumnModel().getColumnIndex("descripcion")));
        Object precioUnitarioObj = result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("preciounitario"));
        double preciounitario = 0;
        if(!precioUnitarioObj.toString().equals("") && !precioUnitarioObj.toString().equals(null)){
            preciounitario = Double.parseDouble(precioUnitarioObj.toString());
        }
        producto.setPrecioUnitario(preciounitario);
        Object pesoKgObj = result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("pesokg"));
        double pesoKg = 0;
        if(!pesoKgObj.toString().equals("") && !pesoKgObj.toString().equals(null)){
            pesoKg = Double.parseDouble(pesoKgObj.toString());
        }
        producto.setPesoKg(pesoKg);
        return producto;
    }
    private Stock getStockFromLine(int selectedRow) {
        Stock stock = new Stock();
        stock.setCantidad( (int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("cantidad")));
        stock.setId_producto( (int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id_producto")));
        stock.setId_sucursal( (int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id_sucursal")));
        return stock;
    }
    private Camino getCaminoFromLine(int selectedRow) {
        Camino camino = new Camino();
        Object idSucursalDestinoObj = result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("idsucursaldestino"));
        int idsucursaldestino = 0;
        if(!idSucursalDestinoObj.toString().equals("") && !idSucursalDestinoObj.toString().equals(null)){
            idsucursaldestino = Integer.parseInt(idSucursalDestinoObj.toString());
        }
        camino.setIdSucursalDestino(idsucursaldestino);
        Object idSucursalOrigenObj = result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("idsucursalorigen"));
        int idSucursalOrigen = 0;
        if(!idSucursalOrigenObj.toString().equals("") && !idSucursalOrigenObj.toString().equals(null)){
            idSucursalOrigen = Integer.parseInt(idSucursalOrigenObj.toString());
        }
        camino.setIdSucursalOrigen(idSucursalOrigen);
        camino.setId( (int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id")));
        Object capacidadMaximaObj = result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("capacidadmaxima"));
        int capacidadmaxima = 0;
        if(!capacidadMaximaObj.toString().equals("") && !capacidadMaximaObj.toString().equals(null)){
            capacidadmaxima = Integer.parseInt(capacidadMaximaObj.toString());
        }
        camino.setCapacidadMaxima(capacidadmaxima);
        Object estadoObj = result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("estado"));
        boolean estado = Boolean.parseBoolean(estadoObj.toString());
        camino.setEstado(estado ? Estado.OPERATIVA : Estado.NO_OPERATIVA);
        return camino;
    }
}
