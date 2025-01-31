package pt.pa.view.Components;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

/**
 * Provides an easy-to-use approach to create a table in javafx
 *
 * Creating a table in javafx can be a mess if we use the tableView without middle classes
 * and Observable things, so there's the solution
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class TableComponent extends TableView<ObservableList<String>> {

    public TableComponent() {
        super();
        setEditable(false);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setPrefWidth(Region.USE_COMPUTED_SIZE);
    }

    public void addColumn(String columnName) {
        TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
        int columnIndex = getColumns().size();
        column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(columnIndex)));
        getColumns().add(column);
    }

    public void addRow(String... rowValues) {
        ObservableList<String> row = FXCollections.observableArrayList(rowValues);
        getItems().add(row);
    }

    public void clearRows() {
        getItems().clear();
    }
}