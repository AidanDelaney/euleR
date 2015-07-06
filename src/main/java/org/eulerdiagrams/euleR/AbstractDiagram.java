package org.eulerdiagrams.euleR;

import java.util.*;

/**
 * An AbstractDiagram is a set of zones defined over a fixed set of contours.
 *
 * We don't use org.eulerdiagrams.vennom.apCircles.AbstractDiagram as that is expected to be hugely refactored.
 *
 */
class AbstractDiagram {
    private Set<Contour> contours;
    private Map<Zone, Double> zones = new HashMap<Zone, Double>();

    public AbstractDiagram(Set<Contour> contours) {
        this.contours = contours;
    }

    public boolean addZone(double weight, Contour ... inset) {
        Set<Contour> outzones = new HashSet<Contour>(contours);

        for(Contour c : inset) {
            // if inset contains a contour not in this diagram, then don't add
            // this zone to the diagram.
            if(!outzones.remove(c)) {
                return false;
            }
        }
        zones.put(new Zone(new HashSet<Contour>(Arrays.asList(inset)), outzones), weight);
        return true;
    }

    public final Set<Contour> getContours() {
        return contours;
    }

    public final Map<Zone, Double> getZones() {
        return zones;
    }
}
