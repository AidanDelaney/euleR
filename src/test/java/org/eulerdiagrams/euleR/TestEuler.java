package org.eulerdiagrams.euleR;

import edu.uic.ncdm.venn.VennDiagram;

import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;

public class TestEuler {
    @Test
    public void simpleTest() {
        Set<Contour> contours = new HashSet<Contour>();
        Contour a = new Contour("a");
        Contour b = new Contour("b");
        contours.add(a);
        contours.add(b);

        AbstractDiagram d = new AbstractDiagram(contours);
        d.addZone(100.0, a);
        d.addZone(100.0, b);
        d.addZone(50.0, a, b);

        //FIXME
        EulerDrawer ed = new EulerDrawer(d);
        VennDiagram vd = ed.layout();

        assertThat(vd, is(not(nullValue())));
    }
}
