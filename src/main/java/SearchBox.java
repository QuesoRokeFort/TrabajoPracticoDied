
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class SearchBox {
    private JTextField searchable = new JTextField(30);
    private JButton searchB = new JButton("Search");
    private JTable result = new JTable();
    private JPanel panel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(result);




    public int buscador(JFrame jFrame) throws HeadlessException {
        final int[] id = new int[1];
        addComponents(jFrame);
        setTable();
        CompletableFuture<Void> future = new CompletableFuture<>();
        JButton modificar = new JButton("selecionar");
        modificar.addActionListener(e -> {
            int selectedRow = result.getSelectedRow();
            if (selectedRow != -1) {
                id[0] = (int) result.getValueAt(selectedRow, 0); // 0 is the column index for the ID column
            }
            future.complete(null);
        });
        JButton cancelar =new JButton("cancelar");
        cancelar.addActionListener(e ->  {
            id[0]=0;
            future.complete((null));
        });
        JButton borrar = new JButton("borrar");
        borrar.addActionListener(e -> {
            int selectedRow = result.getSelectedRow();
            new Gestor().borrarSucursal(new Sucursal((int)result.getValueAt(selectedRow, 0),
                    (int)result.getValueAt(selectedRow, 1),
                    (int)result.getValueAt(selectedRow, 2),
                    (boolean)result.getValueAt(selectedRow, 3),
                    (String)result.getValueAt(selectedRow, 4)
                    ));
            future.complete(null);
        });
        panel.add(modificar);
        panel.add(borrar);
        panel.add(cancelar);
        jFrame.setVisible(true);
        try {
            future.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id[0];
    }

    private void addComponents(JFrame jFrame) {
        jFrame.getContentPane().removeAll();
        jFrame.revalidate();
        jFrame.repaint();
        panel.add(searchable);
        panel.add(searchB);
        panel.add(scrollPane);
        jFrame.add(panel);
        jFrame.setSize(600, 600);
        jFrame.setVisible(true);
    }
    private void setTable() {
        searchB.addActionListener(e -> result.setModel(DbUtils.resultSetToTableModel(
                new DataBase().search(searchable.getText(), panel))));
    }

    public void listar(JFrame jFrame) {
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

    }
}