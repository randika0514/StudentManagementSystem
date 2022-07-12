package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Student;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentManageFormController {

    public AnchorPane studentContext;
    public TextField txtStuId;
    public TextField txtStuName;
    public TextField txtStuNic;
    public TextField txtStuAddress;
    public TextField txtStuContact;
    public TextField txtStuEmail;
    public TableView tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colNic;
    public TextField txtSearch;

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Student s = new Student(
                txtStuId.getText(),txtStuName.getText(),txtStuEmail.getText(),txtStuContact.getText(),txtStuAddress.getText(),txtStuNic.getText()
        );

        try {
          boolean isUpdated = CrudUtil.execute("UPDATE ijse.student SET student_name=? , email=? , contact=? , address=? , nic=? WHERE student_id=?",s.getStuName(),s.getStuEmail(),s.getContact(),s.getAddress(),s.getNic(),s.getStuId());
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws IOException {
        Student s = new Student(
                txtStuId.getText(),txtStuName.getText(),txtStuEmail.getText(),txtStuContact.getText(),txtStuAddress.getText(),txtStuNic.getText()
        );

        try {
            if (CrudUtil.execute("INSERT INTO ijse.student VALUES (?,?,?,?,?,?)",s.getStuId(),s.getStuName(),s.getStuEmail(),s.getContact(),s.getAddress(),s.getNic())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        studentContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/StudentManageForm.fxml"));
        studentContext.getChildren().add(parent);

    }

    public void btnSearch(ActionEvent actionEvent) {
        try {
            search();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void search() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM ijse.student WHERE student_id=?",txtSearch.getText());
        if (resultSet.next()){
            txtStuId.setText(resultSet.getString(1));
            txtStuName.setText(resultSet.getString(2));
            txtStuEmail.setText(resultSet.getString(3));
            txtStuContact.setText(resultSet.getString(4));
            txtStuAddress.setText(resultSet.getString(5));
            txtStuNic.setText(resultSet.getString(6));
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty..").show();
        }
    }
}
