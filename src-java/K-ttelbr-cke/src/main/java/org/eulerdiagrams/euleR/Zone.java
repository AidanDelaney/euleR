package org.eulerdiagrams.euleR;

import java.util.Set;

/**
 * A Zone is modelled as a pair of contour sets.  The disjoint union of this
 * pair must be the contour set of the AbstractDiagram to which the zone
 * belongs.
 */
public class Zone {
    private Set<Contour> in, out;

    public Zone (Set<Contour> inset, Set<Contour> outset) {
        this.in = inset;
        this.out = outset;
    }

    public final Set<Contour> getInContours() {
        return in;
    }

    public boolean equals(Object o) {
        if(o instanceof Zone) {
            Zone z = (Zone) o;
            // Zones that don't have the same contour set are incomparable and
            // this is modelled as false.
            return (in.equals(z.in) && out.equals(z.out));
        }
        return false;
    }
}
