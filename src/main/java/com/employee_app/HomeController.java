package com.employee_app;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.employee_app.home.EmployeeData;
import com.employee_app.home.HomeModel;

// import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField department;

    @FXML
    private TableView<EmployeeData> employeeDataTableView;
    @FXML
    private TableColumn<EmployeeData, String> idColumn;
    @FXML
    private TableColumn<EmployeeData, String> nameColumn;
    @FXML
    private TableColumn<EmployeeData, String> departmentColumn;

    @FXML
    private Button addEntryBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Label errorMsg;

    public TableView<EmployeeData> getEmployeeDataTableView() {
        return employeeDataTableView;
    }

    // instantiate a model
    HomeModel homeModel = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.homeModel = new HomeModel();
        this.loadEmployeeData();
    }

    // load data
    @FXML
    public void loadEmployeeData() {

        this.idColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("id"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("name"));
        this.departmentColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("department"));

        this.employeeDataTableView.setItems(homeModel.getEmployees());
        errorMsg.setText(null);
    }

    // add employee
    @FXML
    private void addEmployee(ActionEvent event) {
        if (!this.name.getText().equals("") && !this.department.getText().equals("")) {
            homeModel.addEmployee(this.name.getText(), this.department.getText());
            this.loadEmployeeData();
            this.clearFields(null);
        } else {
            errorMsg.setText("*Input name and department");
        }
    }

    // update employee
    @FXML
    private void updateEmployee(ActionEvent event) {
        if(employeeDataTableView.getSelectionModel().getSelectedIndex() != -1) {
            homeModel.updateEmployee(employeeDataTableView.getSelectionModel().getSelectedIndex(), this.name.getText(), this.department.getText());
            this.loadEmployeeData();
            this.clearFields(null);
        } else {
            errorMsg.setText("*Select a row which you want to update");
        }
    }

    // delete employee
    @FXML
    private void deleteEmployee(ActionEvent event) {
        if(employeeDataTableView.getSelectionModel().getSelectedIndex() != -1){
            homeModel.deleteEmployee(employeeDataTableView.getSelectionModel().getSelectedIndex());
            this.loadEmployeeData();
            this.clearFields(null);
        } else {
            errorMsg.setText("*Select employee data which you want to delete");
        }
    }

    // clear fields
    @FXML
    private void clearFields(ActionEvent event) {
        this.name.setText("");
        this.department.setText("");
        errorMsg.setText(null);
    }
}
