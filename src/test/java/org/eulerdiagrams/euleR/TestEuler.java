package org.eulerdiagrams.euleR;

import edu.uic.ncdm.venn.VennDiagram;

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

        AbstractDiagram d = new AbstractDiagram(contours);
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

        AbstractDiagram d = new AbstractDiagram(contours);
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
}
