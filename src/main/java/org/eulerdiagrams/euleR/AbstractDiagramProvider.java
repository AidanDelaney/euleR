package org.eulerdiagrams.euleR;

import edu.uic.ncdm.venn.VennDiagram;
import edu.uic.ncdm.venn.data.VennData;

import org.eulerdiagrams.euleR.Contour;
import org.eulerdiagrams.vennom.apCircles.AbstractDiagram;
import org.eulerdiagrams.vennom.apCircles.AreaSpecification;

import java.util.*;
import com.google.common.collect.*;

/**
 * Parses a VennData into an AbstractDiagram and provide access to
 * AreaSpecification and VennDiagram.  In essence, it's a non-generalised
 * adaptor between VennData and AbstractDiagram.
 *
 * We handle all the conversion between the Wilconson syntax and the Rodgers
 * syntax here rather than pushing it into constructors and toString methods
 * of our Contour, Zone and AbstractDiagram objects.  This is because we will
 * want to eventually drop the Rodgers microsyntax.
 */
class AbstractDiagramProvider {
    private org.eulerdiagrams.euleR.AbstractDiagram diagram;

    // The apCircles.AbstractDiagram only likes single letter contour labels.
    // We use this to keep track of the translation between our AbstractDiagram
    // contour labels and the single letter labels.
    private BiMap<Character, Contour> apcirclesBridge = HashBiMap.create();

    /**
     * Parse the VennData structure into an AbstractDiagram.
     *
     * FIXME: Throw IllegalArgumentException when things get sticky.
     */
    public AbstractDiagramProvider(VennData data) {
        String[][] zoneData = data.data;
        double [] weights = data.areas;
        // retain a list of all inzones, which we'll use later to add to the
        // diagram once we've worked out the contour set.
        List<Contour[]> inzs = new Vector<Contour[]>();
        Set<Contour> contours = new HashSet<Contour>();

        // zoneData holds Wilconson's micro-syntax at [i][0]
        for(int i=0; i< zoneData.length; i++) {
            Contour [] inz = parseWilconsonSyntax(zoneData[i][0]);
            inzs.add(inz);
            for(Contour c: inz) {
                contours.add(c);
            }
        }

        diagram = new org.eulerdiagrams.euleR.AbstractDiagram(contours);
        // This is horrible, we're relying on weights and inzs to have the same
        // length.
        for(int i = 0; i < weights.length; i++) {
            diagram.addZone(weights[i], inzs.get(i));
        }
    }

    public AreaSpecification asAreaSpecification() {
        // We have to translate our internal AbstractDiagram into an
        // apCircles.AbstractDiagram, then into an AreaSpecification.
        org.eulerdiagrams.vennom.apCircles.AbstractDiagram pjrDiagram;

        // To create an apCircles.AbstractDiagram we must map each Contour to a
        // single letter label & write out the zones in the apCircles
        // micro-syntax.

        // First map Characters to each Contour

        // Evil...but a result of the design decision of the underlying library
        final char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','o','p','q','r','s','t','u','v','w','x','y','z'};
        Contour [] contours = diagram.getContours().toArray(new Contour[diagram.getContours().size()]);
        for(int i=0; (i < contours.length) && (i < alphabet.length); i++) {
            apcirclesBridge.put(alphabet[i], contours[i]);
        }

        // Then build the Rogers syntax for each zone
        StringBuilder apCirclesSyntax = new StringBuilder();
        Map<Zone, Double> weightmap = diagram.getZones();
        HashMap<String, Double> pjrWeights = new HashMap<String, Double>();
        for(Zone z: weightmap.keySet()) {
            StringBuilder pjrZone = new StringBuilder();
            for (Contour c: z.getInContours()) {
                pjrZone.append(apcirclesBridge.inverse().get(c));
            }
            pjrWeights.put(pjrZone.toString(), weightmap.get(z));
            apCirclesSyntax.append(pjrZone);
            apCirclesSyntax.append(" "); //separate zones with a space
        }

        // Finally, put the diagram together.
        pjrDiagram = new org.eulerdiagrams.vennom.apCircles.AbstractDiagram(apCirclesSyntax.toString());
        AreaSpecification aspec = new AreaSpecification(pjrDiagram, pjrWeights);
        return aspec;
    }

    /**
     * This syntax is too esoteric to warrant a parser generator, hence this
     * hand-rolled excuse.
     */
    public Contour[] parseWilconsonSyntax(String usyntax) {
        // Wilconson syntax is of the form where zones are separated with a space and
        // intersection is denoted with an ampersand.
        List<Contour> contours = new Vector<Contour>();

        String [] contourLabels = usyntax.split("&");
        for(String s: contourLabels) {
            contours.add(new Contour(s));
        }
        return contours.toArray(new Contour[contours.size()]);
    }
}
