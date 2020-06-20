package controllers;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Expense {
    // Defining the data model for expense tableview
    private SimpleStringProperty category;
    private SimpleDoubleProperty amount;
    private SimpleStringProperty date;

    Expense(String category, double amount, String date){
        this.category = new SimpleStringProperty(category);
        this.amount = new SimpleDoubleProperty(amount);
        this.date = new SimpleStringProperty(date);
    }

    public void setCategory(String category) {
        this.category = new SimpleStringProperty(category);
    }
    public void setAmount(Double amount){this.amount = new SimpleDoubleProperty(amount); }
    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }
    public String getCategory() {
        return category.get();
    }
    public Double getAmount() { return amount.get();}
    public String getDate() { return date.get(); }
}
