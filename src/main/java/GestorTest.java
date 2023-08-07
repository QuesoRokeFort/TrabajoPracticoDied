import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GestorTest {
  public static void createGraph() throws IOException {
    System.out.println("here");
    File imgFile2 = new File("src/test/resources/graph.png");
    imgFile2.createNewFile();

    Graph<String, DefaultEdge> directedGraph =
            new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    List<String> cosas = new ArrayList<>();
    cosas.add("id");
    cosas.add("nombre");
    List<Object> listaVerices = Gestor.buscarCosas(cosas,"sucursal","id");
    String nombre="";
    List<String> nombres= new ArrayList<>();
    for(int i=1;i<listaVerices.size();i+=2) {
        nombre=listaVerices.get(i-1)+"-"+listaVerices.get(i);
        directedGraph.addVertex(nombre);
        nombres.add(nombre);
        System.out.println(nombre);
    }
    cosas = new ArrayList<>();
    cosas.add("id");
    cosas.add("idSucursalOrigen");
    cosas.add("idSucursalDestino");
    List<Object> listaCaminos= Gestor.buscarCosas(cosas,"camino","");
    System.out.println(listaCaminos.toString());
    for(int i=0;i<listaCaminos.size();i+=3) {
      int finalI = i;
      System.out.println(listaCaminos.get(finalI + 1).getClass());
      Optional<String> firstFilteredNombre = nombres.stream()
              .filter(n -> n.startsWith(String.valueOf(listaCaminos.get(finalI + 1))))
              .findFirst();
      Optional<String> firstFilteredNombre2 = nombres.stream()
              .filter(n -> n.startsWith(String.valueOf(listaCaminos.get(finalI + 2))))
              .findFirst();
      directedGraph.addEdge(firstFilteredNombre.get(),firstFilteredNombre2.get());
    }
    givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist((DefaultDirectedGraph) directedGraph);
  }
    static void givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(DefaultDirectedGraph g) throws IOException {

      JGraphXAdapter<String, DefaultEdge> graphAdapter =
              new JGraphXAdapter<String, DefaultEdge>(g);
      mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
      layout.execute(graphAdapter.getDefaultParent());

      BufferedImage image =
              mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
      File imgFile = new File("src/test/resources/graph.png");
      ImageIO.write(image, "PNG", imgFile);


      assertFileExists(imgFile);
    }

  private static void assertFileExists(File file) {
    if (!file.exists()) {
      throw new AssertionError("File does not exist: " + file.getAbsolutePath());
    }
  }
  }