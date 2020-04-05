package zoo.model;

import java.util.ArrayList;

public class SelectModel {
    private ArrayList<String> selectedColumns;
    private ArrayList<ArrayList<String>> rowData;

    public SelectModel(ArrayList<String> selectedColumns, ArrayList<ArrayList<String>> rowData) {
        this.selectedColumns = selectedColumns;
        this.rowData = rowData;
    }

    public ArrayList<String> getSelectedColumns() {
        return selectedColumns;
    }

    public ArrayList<ArrayList<String>> getRowData() {
        return rowData;
    }
}
