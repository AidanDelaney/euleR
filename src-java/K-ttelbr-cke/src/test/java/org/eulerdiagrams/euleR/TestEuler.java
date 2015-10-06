package org.eulerdiagrams.euleR;

import edu.uic.ncdm.venn.VennDiagram;
import edu.uic.ncdm.venn.data.VennData;

import org.eulerdiagrams.AbstractDiagram.*;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;

public class TestEuler {
    @Test
    public void simpleTest() {
        Set<AbstractContour> contours = new HashSet<AbstractContour>();
        AbstractContour a = new AbstractContour("a");
        AbstractContour b = new AbstractContour("b");
        contours.add(a);
        contours.add(b);

        WeightedAbstractDiagram d = new WeightedAbstractDiagram(contours);
        d.addZone(100.0, a);
        d.addZone(100.0, b);
        d.addZone(50.0, a, b);

        EulerDrawer ed = new EulerDrawer(d);
        VennDiagram vd = ed.layout();

        assertThat(vd, is(not(nullValue())));
    }

    @Test
    public void testVenn3() {
        Set<AbstractContour> contours = new HashSet<AbstractContour>();
        AbstractContour a = new AbstractContour("a");
        AbstractContour b = new AbstractContour("b");
        AbstractContour c = new AbstractContour("c");
        contours.add(a);
        contours.add(b);
        contours.add(c);

        WeightedAbstractDiagram d = new WeightedAbstractDiagram(contours);
        d.addZone(100.0, a);
        d.addZone(100.0, b);
        d.addZone(40.0, a, b);
        d.addZone(40.0, a, c);
        d.addZone(40.0, b, c);
        d.addZone(10.0, a, b, c);

        EulerDrawer ed = new EulerDrawer(d);
        VennDiagram vd = ed.layout();

        assertThat(vd, is(not(nullValue())));
    }

    @Test
    public void testVennDataVenn2() {
        String[] data = new String[3];
        data[0] = "A";
        data[1] = "B";
        data[2] = "A~B";

        double [] areas = new double[3];
        areas[0] = 1.0;
        areas[1] = 1.0;
        areas[2] = 0.3;

        VennData d = new VennData(data, areas);
        EulerDrawer ed = new EulerDrawer(d);
        VennDiagram vd = ed.layout();

        assertThat(vd, is(not(nullValue())));
    }
}
