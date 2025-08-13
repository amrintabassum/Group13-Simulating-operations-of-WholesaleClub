module com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
//    requires com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub;


    opens com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub to javafx.fxml;
    exports com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub;
    opens com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.inventoryManager to javafx.fxml;
    opens com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.storeManager to javafx.fxml;

}