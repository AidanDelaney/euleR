package org.eulerdiagrams.euleR;

import org.eulerdiagrams.AbstractDiagram.AbstractDiagram;
import org.eulerdiagrams.vennom.apCircles.drawers.APForceModel;
import org.eulerdiagrams.vennom.graph.*;
import org.eulerdiagrams.vennom.apCircles.*;

import static org.eulerdiagrams.vennom.apCircles.display.APCircleDisplay.*;
import edu.uic.ncdm.venn.VennDiagram;
import edu.uic.ncdm.venn.data.VennData;

import org.eulerdiagrams.vennom.graph.drawers.GraphDrawer;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class EulerDrawer {
    static {
        /*
         * The lines until the <<< marker are junk that is necessary to set before you can use the VenNom graph drawer.
         * It's not pretty, but without setting properties of the EdgeTypes you get NullPointerExceptions from the
         * VenNom code.
         */
        FIXED = new EdgeType("fixed");
        FIXED.setLineColor(Color.black);
        FIXED.setTextColor(Color.black);
        FIXED.setSelectedLineColor(Color.gray);
        FIXED.setPriority(1020);

        ATTRACTOR = new EdgeType("attractor");
        ATTRACTOR.setLineColor(Color.green);
        ATTRACTOR.setTextColor(Color.green);
        ATTRACTOR.setSelectedLineColor(Color.gray);
        ATTRACTOR.setPriority(1019);

        REPULSOR = new EdgeType("repulsor");
        REPULSOR.setLineColor(Color.red);
        REPULSOR.setTextColor(Color.red);
        REPULSOR.setSelectedLineColor(Color.gray);
        REPULSOR.setPriority(1018);

        Graph.DEFAULT_NODE_TYPE.setHeight(20);
        Graph.DEFAULT_NODE_TYPE.setWidth(20);
        Graph.DEFAULT_NODE_TYPE.setBorderColor(Color.WHITE);
        Graph.DEFAULT_NODE_TYPE.setTextColor(Color.BLUE);
        Graph.DEFAULT_EDGE_TYPE = FIXED;
        // <<<
    }

    private AbstractDiagramProvider adp;

    public EulerDrawer(AbstractDiagram diagram) {
        adp = new AbstractDiagramProvider(diagram);
    }

    public EulerDrawer(VennData diagram) {
        adp = new AbstractDiagramProvider(diagram);
    }

    public VennDiagram layout() {
        AreaSpecification as = adp.asAreaSpecification();;
        JFrame frame = new JFrame();
        GraphDrawer gd = new APForceModel();
        APCirclePanel apc = new APCirclePanel(frame);
        apc.setSpecification(as);
        apc.addGraphDrawer(gd);

        apc.setGraph(as.generateAugmentedIntersectionGraph());
        gd.layout();

        return graphToVennDiagram(gd.getGraph());
    }

    private VennDiagram graphToVennDiagram(Graph graph) {
        /*
         * We need the node centre and the label.  The label is a double valued radius packed into a string.
         */
        final int XCOORD=0, YCOORD=1;

        int numNodes = graph.getNodes().size();
        double [][] centres  = new double[numNodes][2];
        double [] diameters = new double[numNodes];
        String [] labels = new String[numNodes];


        // You can't pass back a null value to the venneuler R code and have it deal with it in a sane manner.
        // Therefore, we're going to pass back an empty array in place of null.
        double [] emptyDoubles = new double[numNodes];
        String [] emptyStrings = new String[numNodes];

        List<Node> nodes = graph.getNodes();
        for(int i = 0; i<numNodes; i++) {
            Node node = nodes.get(i);

            // Get centre
            centres[i][XCOORD] = node.getCentre().getX();
            centres[i][YCOORD] = node.getCentre().getY();

            // Get diameter
            double radius = 0.0;
            try {
                radius = new Double(node.getLabel());
            } catch (NumberFormatException nfe) {
                // TODO: What to do here?
            }
            diameters[i] = radius*2;

            // Get label string
            labels[i] = node.getLabel();
        }

        // centers, diameters, areas,residuals, circleLabels, residualLabels, double[] colors, double stress, double stress01, double stress05) {
        return new VennDiagram(centres, diameters, emptyDoubles, emptyDoubles, labels, emptyStrings, emptyDoubles, 0.0, 0.0, 0.0);
    }
}
