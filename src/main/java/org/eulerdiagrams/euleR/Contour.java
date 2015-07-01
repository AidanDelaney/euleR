package org.eulerdiagrams.euleR;

/**
 * Contours represent sets in Euler and Venn diagrams.  They differ by having
 * different labels.  We use this implementation, rather than "raw" strings as
 * it makes the API cleaner and less confusing.
 */
class Contour {
    private String label;
    public Contour(String s) {
        this.label = s;
    }

    @Override
    public boolean equals(Object o) {
        return o.equals(label);
    }
}
