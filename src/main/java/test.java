import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.*;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class test {
    public static void createGraph() throws IOException {

        File imgFile2 = new File("src/test/resources/graph.png");
        imgFile2.createNewFile();

        Graph<String, DefaultEdge> directedGraph =
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        directedGraph.addVertex("a");
        directedGraph.addVertex("b");
        directedGraph.addVertex("c");
        directedGraph.addVertex("d");
        directedGraph.addEdge("a", "b");
        directedGraph.addEdge("a", "c");
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

        assertTrue(imgFile.exists());
    }

    private static void assertTrue(boolean exists) {
    }

    public static void main( String[] args ) throws IOException {

        System.out.println("Hello World!");
        createGraph();
    }
}
