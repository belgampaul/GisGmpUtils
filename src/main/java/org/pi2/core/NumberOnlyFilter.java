package org.pi2.core;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumberOnlyFilter extends DocumentFilter {

  public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
    StringBuilder sb = new StringBuilder();
    sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
    sb.insert(offset, text);
    if (!containsOnlyNumbers(sb.toString())) {
      return;
    }
    fb.insertString(offset, text, attr);
  }

  public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
    StringBuilder sb = new StringBuilder();
    sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
    sb.replace(offset, offset + length, text);
    if (!containsOnlyNumbers(sb.toString())) {
      return;
    }
    fb.replace(offset, length, text, attr);
  }

  /**
   * This method checks if a String contains only numbers
   */
  public boolean containsOnlyNumbers(String text) {
    //Pattern pattern = Pattern.compile("([+-]{0,1})?[\\d]*");
    final String regExp = "[0-9]+[.]?[0-9]*";
    return text.matches(regExp);
  }

}
