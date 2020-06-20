package controllers;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Income {
    // Defining the data model for income tableview
    private SimpleStringProperty category;
    private SimpleDoubleProperty amount;
    private SimpleStringProperty date;

    Income(String cat, double amt, String date){
        this.amount = new SimpleDoubleProperty(amt);
        this.category = new SimpleStringProperty(cat);
        this.date = new SimpleStringProperty(date);
    }

    public void setAmount(double amount) {
        this.amount = new SimpleDoubleProperty(amount);
    }
    public void setCategory(String category) {
        this.category = new SimpleStringProperty(category);
    }
    public void setDate(String date) { this.date = new SimpleStringProperty(date); }
    public Double getAmount() {
        return amount.get();
    }
    public String getCategory() {
        return category.get();
    }
    public String getDate(){ return date.get();}


}
