package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab chartTab;
    @FXML
    private PieChart moneyPie;
    @FXML
    private Tab incomeTab;
    @FXML
    private TableView<Income> incomeTable;
    @FXML
    private TableColumn<Income, String> catIncomeT;
    @FXML
    private TableColumn<Income, Double> amtIncomeT;
    @FXML
    private TableColumn<Income, String> dateIncomeT;
    @FXML
    private Label incomeTableLabel;
    @FXML
    private TextField amountTextIncome;
    @FXML
    private DatePicker datePickIncome;
    @FXML
    private Button addIncome;
    @FXML
    private Button removeExpense;
    @FXML
    private Button removeButton;
    @FXML
    private ImageView addIncomeImage;
    @FXML
    private Label catLabel;
    @FXML
    private Label amtLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Tab expenseTab;
    @FXML
    private AnchorPane amtLabel2;
    @FXML
    private TableView<Expense> expenseTable;
    @FXML
    private TableColumn<Expense, String> catExpenseT;
    @FXML
    private TableColumn<Expense, Double> amtExpenseT;
    @FXML
    private TableColumn<Expense, String> dateExpenseT;
    @FXML
    private ImageView addExpenseImage;
    @FXML
    private ChoiceBox<String> categoryChoiceExpense;
    @FXML
    private ChoiceBox<String> categoryChoiceIncome;
    @FXML
    private TextField amountTextExpense;
    @FXML
    private DatePicker datePickExpense;
    @FXML
    private Label catLabel2;
    @FXML
    private Label dateLabel2;
    @FXML
    private Button addExpense;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label incomeErrorLabel;
    @FXML
    private Label expenseErrorLabel;
    @FXML
    private Label balanceText;
    private ObservableList<String> incomeCategory = FXCollections.observableArrayList("Salary", "Refunds", "Investments", "Grants", "Investments", "Others");
    private ObservableList<String> expenseCategory = FXCollections.observableArrayList("Food", "Entertainment", "Clothing",
            "Tax", "Snacks", "Drinks", "Book", "Beauty", "Bills", "Car",  "Travel", "Office");
    private ObservableList<Income> incomeObservableList = FXCollections.observableArrayList();
    private ObservableList<Expense> expenseObservableList = FXCollections.observableArrayList();

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == addIncome){
            String cat = categoryChoiceIncome.getValue();
            String amountIncomeString = amountTextIncome.getText();
            String date;
            if (datePickIncome.getValue() == null){
                // default date if user does not input the date themselves
                datePickIncome.setValue(LocalDate.now());
                date = datePickIncome.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            if (categoryChoiceIncome.getSelectionModel().isEmpty() || amountIncomeString.isEmpty()){
                incomeErrorLabel.setText("Please fill the empty fields");
            }
            else{
                date = datePickIncome.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Income newData = new Income(cat,Double.parseDouble(amountIncomeString), date);
                incomeTable.getItems().add(newData);
            }
        }
        else if (event.getSource() == addExpense){
            String cat = categoryChoiceExpense.getValue();
            String amountExpenseString = amountTextExpense.getText();
            String date;
            if (datePickExpense.getValue() == null){
                // default date taken
                datePickExpense.setValue(LocalDate.now());
                date = datePickExpense.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            if (categoryChoiceExpense.getSelectionModel().isEmpty() || amountExpenseString.isEmpty()){
                expenseErrorLabel.setText("Please fill the empty fields");
            }
            else{
                date = datePickExpense.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Expense newData = new Expense(cat, Double.parseDouble(amountExpenseString), date);
                expenseTable.getItems().add(newData);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Create options for each category
        categoryChoiceExpense.setItems(expenseCategory);
        categoryChoiceIncome.setItems(incomeCategory);

        // Instantiate the income table view
        catIncomeT.setCellValueFactory(new PropertyValueFactory<>("category"));
        amtIncomeT.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateIncomeT.setCellValueFactory(new PropertyValueFactory<>("date"));
        incomeTable.setItems(incomeObservableList);
        incomeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Instantiate the expense table view
        catExpenseT.setCellValueFactory(new PropertyValueFactory<>("category"));
        amtExpenseT.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateExpenseT.setCellValueFactory(new PropertyValueFactory<>("date"));
        expenseTable.setItems(expenseObservableList);
        expenseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Editable income table view
        incomeTable.setEditable(true);
        catIncomeT.setCellFactory(TextFieldTableCell.forTableColumn());
        amtIncomeT.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter())); // String input -> Double
        dateIncomeT.setCellFactory(TextFieldTableCell.forTableColumn());

        // Editable expense table view
        expenseTable.setEditable(true);
        catExpenseT.setCellFactory(TextFieldTableCell.forTableColumn());
        amtExpenseT.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter())); // String input -> Double
        dateExpenseT.setCellFactory(TextFieldTableCell.forTableColumn());

        // Intialize pie chart
        /*ObservableList<TableColumn<Income, ?>> columns = incomeTable.getColumns();
        ObservableList<Income> data = incomeTable.getItems();
        for (int i = 0; i < data.size(); i++ ){
            for (int j = 0; j < columns.size(); j++){
                TableColumn columnCat = columns.get(1);
                String categoryName = columnCat.getCel(data.get(i));

            }
        }*/
        ObservableList<PieChart.Data> Pie = FXCollections.observableArrayList(
                new PieChart.Data("Clothing", 199),
                new PieChart.Data("Tax", 310),
                new PieChart.Data("Snacks", 50),
                new PieChart.Data("Travel", 48),
                new PieChart.Data("Office", 100),
                new PieChart.Data("Food", 210));
        moneyPie.setTitle("Overview of Income");
        moneyPie.setLegendSide(Side.LEFT);
        moneyPie.setData(Pie);
    }

    public void removeIncomeData(ActionEvent actionEvent) {
        ObservableList<Income> incomeRows, incomeAll;
        incomeAll = incomeTable.getItems();
        incomeRows = incomeTable.getSelectionModel().getSelectedItems(); // gets selected rows
        incomeAll.removeAll(incomeRows);
    }
    public void removeExpenseData(ActionEvent actionEvent) {
        ObservableList<Expense> expenseRows, expenseAll;
        expenseAll = expenseTable.getItems();
        expenseRows = expenseTable.getSelectionModel().getSelectedItems(); // gets selected rows to be removed
        expenseAll.removeAll(expenseRows);
    }
    public void catIncomeEdit(TableColumn.CellEditEvent<Income, String> incomeStringCellEditEvent) {
        Income income = incomeTable.getSelectionModel().getSelectedItem();
        income.setCategory(incomeStringCellEditEvent.getNewValue());
    }
    public void amtIncomeEdit(TableColumn.CellEditEvent<Income, Double> incomeDoubleCellEditEvent) {
        Income income = incomeTable.getSelectionModel().getSelectedItem();
        income.setAmount(incomeDoubleCellEditEvent.getNewValue());
    }
    public void dateIncomeEdit(TableColumn.CellEditEvent<Income, String> incomeStringCellEditEvent) {
        Income income = incomeTable.getSelectionModel().getSelectedItem();
        income.setDate(incomeStringCellEditEvent.getNewValue());
    }
    public void catExpenseEdit(TableColumn.CellEditEvent<Expense, String> expenseStringCellEditEvent) {
        Expense expense = expenseTable.getSelectionModel().getSelectedItem();
        expense.setCategory(expenseStringCellEditEvent.getNewValue());
    }
    public void amtExpenseEdit(TableColumn.CellEditEvent<Expense, Double> expenseDoubleCellEditEvent) {
        Expense expense = expenseTable.getSelectionModel().getSelectedItem();
        expense.setAmount(expenseDoubleCellEditEvent.getNewValue());
    }
    public void dateExpenseEdit(TableColumn.CellEditEvent<Expense, String> expenseStringCellEditEvent) {
        Expense expense = expenseTable.getSelectionModel().getSelectedItem();
        expense.setDate(expenseStringCellEditEvent.getNewValue());
    }
    public void refreshChart(){
    }


}
