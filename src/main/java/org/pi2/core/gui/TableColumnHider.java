package org.pi2.core.gui;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.HashMap;
import java.util.Map;

public class TableColumnHider {

  private JTable table;
  private TableColumnModel tcm;
  private Map hiddenColumns;

  public TableColumnHider(JTable table) {
    this.table = table;
    tcm = this.table.getColumnModel();
    hiddenColumns = new HashMap();
  }

  public void hide(String columnName) {
    int index = tcm.getColumnIndex(columnName);
    TableColumn column = tcm.getColumn(index);
    hiddenColumns.put(columnName, column);
    hiddenColumns.put(":" + columnName, new Integer(index));
    tcm.removeColumn(column);
  }

  public void show(String columnName) {
    Object o = hiddenColumns.remove(columnName);
    if (o == null) {
      return;
    }
    tcm.addColumn((TableColumn) o);
    o = hiddenColumns.remove(":" + columnName);
    if (o == null) {
      return;
    }
    int column = ((Integer) o).intValue();
    int lastColumn = tcm.getColumnCount() - 1;
    if (column < lastColumn) {
      tcm.moveColumn(lastColumn, column);
    }
  }
}
