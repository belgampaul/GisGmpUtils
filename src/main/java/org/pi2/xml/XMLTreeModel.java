package org.pi2.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.TreeWalker;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * User: ikka
 * Date: 27.09.13
 * Time: 1:46
 */
public class XMLTreeModel implements TreeModel {
  TreeWalker walker;

  public XMLTreeModel(Document document) {
    if (document instanceof DocumentTraversal) {
      walker = ((DocumentTraversal) document).createTreeWalker(document, NodeFilter.SHOW_ELEMENT, null, false);
    }
  }

  @Override
  public Object getRoot() {
    return walker.getRoot();
  }

  @Override
  public Object getChild(Object parent, int index) {
    walker.setCurrentNode((Node) parent); // Set the current node
    // TreeWalker provides sequential access to children, not random
    // access, so we've got to loop through the kids one by one
    Node child = walker.firstChild();
    while (index-- > 0)
      child = walker.nextSibling();
    return child;
  }

  @Override
  public int getChildCount(Object parent) {
    walker.setCurrentNode((Node) parent); // Set the current node
    // TreeWalker doesn't count children for us, so we count ourselves
    int numkids = 0;
    Node child = walker.firstChild(); // Start with the first child
    while (child != null) { // Loop 'till there are no more
      numkids++; // Update the count
      child = walker.nextSibling(); // Get next child
    }
    return numkids; // This is the number of children
  }

  @Override
  public boolean isLeaf(Object node) {
    walker.setCurrentNode((Node) node); // Set current node
    Node child = walker.firstChild(); // Ask for a child
    return (child == null); // Does it have any?
  }

  @Override
  public void valueForPathChanged(TreePath path, Object newValue) {
  }

  @Override
  public int getIndexOfChild(Object parent, Object child) {
    walker.setCurrentNode((Node) parent); // Set current node
    int index = 0;
    Node c = walker.firstChild(); // Start with first child
    while ((c != child) && (c != null)) { // Loop 'till we find a match
      index++;
      c = walker.nextSibling(); // Get the next child
    }
    return index; // Return matching position
  }

  @Override
  public void addTreeModelListener(TreeModelListener l) {
  }

  @Override
  public void removeTreeModelListener(TreeModelListener l) {
  }
}
