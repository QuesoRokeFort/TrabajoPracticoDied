
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                        Gestor.actulizarProducto(producto);
                    }else{
                        if (tabla.equals("stock")){
                            Stock stock = getStockFromLine(selectedRow);
                            List<Integer> ids= new ArrayList<>();
                            ids.add(getIntFromBlock(selectedRow,"id_producto"));
                            ids.add(getIntFromBlock(selectedRow,"id_sucursal"));
                            Gestor.actulizarStock(stock,ids);
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
                        Gestor.borrarStock((int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id_producto")),(int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id_sucursal")));
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
                if(Gestor.tieneId(tabla))emptyRow.add(Gestor.getLastValue("id", tabla) + 1);
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
        if (tabla.equals("sucursal")) {
            JButton stock = new JButton("ver Stock");
            stock.addActionListener(e -> {
                int selectedRow = result.getSelectedRow();
                if (selectedRow!=-1) {
                    Sucursal s = getSucursalFromLine(selectedRow);
                    panel.remove(aviso);
                    panel.remove(agregar);
                    panel.remove(modificar);
                    panel.remove(borrar);
                    panel.remove(stock);
                    JButton ordendepedido = new JButton("orden de pedido");
                    panel.add(ordendepedido);
                    JButton atras = new JButton("atras");
                    panel.add(atras);
                    ordendepedido.addActionListener(e1 -> {
                        SwingUtilities.invokeLater(() -> {
                            try {
                                panel=ordenDeProvicion(s);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            panel.add(atras);
                            actualizarFrame(jFrame,panel);
                            });

                    });
                    atras.addActionListener(e1 -> {
                        panel.removeAll();
                        addComponents(jFrame);
                        panel.add(stock);
                        panel.add(agregar);
                        panel.add(modificar);
                        panel.add(borrar);
                        panel.add(cancelar);
                        buscarTodo(tabla);
                        actualizarFrame(jFrame, panel);
                    });
                    actualizarFrame(jFrame, panel);
                    result.setModel(DbUtils.resultSetToTableModel(
                            new DataBase().search(String.valueOf(s.getId()), panel, "stock")));
                }else{
                }
            });
            panel.add(stock);
        }
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
        sucursal.setHoraApertura(getIntFromBlock(selectedRow,"horaapertura"));
        sucursal.setHoraCierre(getIntFromBlock(selectedRow,"horacierre"));
        sucursal.setEstado(getBoolFromBlock(selectedRow,"estado") ? Estado.OPERATIVA : Estado.NO_OPERATIVA);
        return sucursal;
    }
    private Producto getProductoFromLine(int selectedRow) {
        Producto producto = new Producto();
        producto.setId( (int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id"))); // 0 is the column index for the ID column
        producto.setNombre((String) result.getValueAt(selectedRow,result.getColumnModel().getColumnIndex("nombre")));
        producto.setDescripcion((String)result.getValueAt(selectedRow,result.getColumnModel().getColumnIndex("descripcion")));
        producto.setPrecioUnitario(getDoubleFromBlock(selectedRow,"preciounitario"));
        producto.setPesoKg(getDoubleFromBlock(selectedRow,"pesokg"));
        return producto;
    }
    private Stock getStockFromLine(int selectedRow) {
        Stock stock = new Stock();
        stock.setCantidad( getIntFromBlock(selectedRow,"cantidad"));
        stock.setId_producto( getIntFromBlock(selectedRow,"id_producto"));
        stock.setId_sucursal( getIntFromBlock(selectedRow,"id_sucursal"));
        return stock;
    }
    private double getDoubleFromBlock(int selectedRow,String columna){
        Object pesoKgObj = result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex(columna));
        double valor = 0;
        if(!pesoKgObj.toString().equals("") && !pesoKgObj.toString().equals(null)){
            valor = Double.parseDouble(pesoKgObj.toString());
        }
        return valor;
    }
    private int getIntFromBlock(int selectedRow,String columna){
        Object intObt = result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex(columna));
        int valor = 0;
        if(!intObt.toString().equals("") && !intObt.toString().equals(null)){
            valor = Integer.parseInt(intObt.toString());
        }
        return valor;
    }
    private boolean getBoolFromBlock(int selectedRow,String columna){
        Object estadoObj = result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex(columna));
        boolean estado = Boolean.parseBoolean(estadoObj.toString());
        return estado;
    }
    private Camino getCaminoFromLine(int selectedRow) {
        Camino camino = new Camino();
        camino.setIdSucursalDestino(getIntFromBlock(selectedRow,"idsucursaldestino"));
        camino.setIdSucursalOrigen(getIntFromBlock(selectedRow,"idsucursalorigen"));
        camino.setId( (int)result.getValueAt(selectedRow, result.getColumnModel().getColumnIndex("id")));
        camino.setTiempoDeViaje(getIntFromBlock(selectedRow,"tiempodeviaje"));
        camino.setCapacidadMaxima(getIntFromBlock(selectedRow,"capacidadmaxima"));
        camino.setEstado(getBoolFromBlock(selectedRow,"estado") ? Estado.OPERATIVA : Estado.NO_OPERATIVA);
        return camino;
    }


    public static JPanel ordenDeProvicion( Sucursal sucursal) throws SQLException {
        JPanel jPanel = new JPanel(new FlowLayout()); // Usar FlowLayout como administrador de diseño
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaHoy = formato.format(fechaActual);
        JLabel fecha = new JLabel(fechaHoy);
        List<String> cosas = new ArrayList<>();
        cosas.add("id");
        cosas.add("nombre");
        ResultSet listaProductos = Gestor.buscarCosas(cosas, "producto", "id");
        String nombre = "";
        List<String> nombres = new ArrayList<>();
        while (listaProductos.next()) {
            try {
                nombre = listaProductos.getInt("id") + "-" + listaProductos.getString("nombre");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            nombres.add(nombre);
        }
        String[] opciones = nombres.toArray(new String[0]);
        List<JComboBox<String>> comboBoxes = new ArrayList<>();
        List<JTextField> textFields = new ArrayList<>();

        // Crear el JComboBox y agregarle las opciones
        JButton agregar = new JButton("agregar");
        agregar.addActionListener(e -> {
            JComboBox<String> comboBox = new JComboBox<>(opciones);
            JTextField textField = Gestor.createPlaceholderTextField("Cantidad");
            jPanel.add(comboBox);
            jPanel.add(textField);
            jPanel.revalidate();
            jPanel.repaint();
            comboBoxes.add(comboBox);
            textFields.add(textField);
        });
        JButton guardar = new JButton("guardar");
        guardar.addActionListener(e -> {
            int id = (Gestor.getLastValue("id","ordenpedido")+1);
            OrdenPedido ordenPedido = new OrdenPedido();
            ordenPedido.setFecha(fechaHoy);
            ordenPedido.setId(id);
            ordenPedido.setIdSucursal(sucursal.getId());
            ordenPedido.setEstado(false);
            Gestor.cargarEnTable(OrdenPedido.getNombreTabla(),OrdenPedido.getCantidadDeColumnas(),OrdenPedido.getNombresColumnas(),ordenPedido.getValores());
            for (int i = 0; i < comboBoxes.size(); i++) {
                JComboBox<String> comboBox = comboBoxes.get(i);
                JTextField textField = textFields.get(i);

                String selectedItem = (String) comboBox.getSelectedItem();
                String cantidad = textField.getText();
                System.out.println("Seleccionado: " + selectedItem + ", Cantidad: " + cantidad);
                int indexOfDash = selectedItem.indexOf('-');
                String idProducto = selectedItem.substring(0, indexOfDash);
                System.out.println(idProducto);
                OrdenPedido.ReglonPedido reglonPedido = new OrdenPedido.ReglonPedido();
                reglonPedido.setId(id);
                reglonPedido.setIdProducto(Integer.parseInt(idProducto));
                reglonPedido.setCantidad(Integer.parseInt(cantidad));
                Gestor.cargarEnTable(OrdenPedido.ReglonPedido.getNombreTabla(),OrdenPedido.ReglonPedido.getCantidadDeColumnas(),OrdenPedido.ReglonPedido.getNombresColumnas(),reglonPedido.getValores());
            }
        });
        jPanel.add(fecha);
        jPanel.add(agregar);
        jPanel.add(guardar);
        return jPanel;
    }
}
