package uk.ac.kent.pjr.apCircles.utilities;import java.awt.event.KeyEvent;import java.io.*;import pjr.apCircles.display.*;import pjr.graph.utilities.*;/** * Just for quick testing of methods. */public class EnterSpecification extends GraphUtility implements Serializable {/**	 * 	 */	private static final long serialVersionUID = 1L;/** Trivial constructor. */	public EnterSpecification() {		super(KeyEvent.VK_S,"Enter Specification",KeyEvent.VK_S);	}	public void apply() {		APEntry ape = new APEntry(getGraphPanel());	}		}