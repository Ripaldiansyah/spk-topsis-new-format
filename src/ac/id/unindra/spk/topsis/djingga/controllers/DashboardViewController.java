package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ac.id.unindra.spk.topsis.djingga.DataAccessObject.AlternativeDAO;
import ac.id.unindra.spk.topsis.djingga.DataAccessObject.CriteriaDAO;
import ac.id.unindra.spk.topsis.djingga.DataAccessObject.TopsisDAO;
import ac.id.unindra.spk.topsis.djingga.models.AlternativeModel;
import ac.id.unindra.spk.topsis.djingga.models.CriteriaModel;
import ac.id.unindra.spk.topsis.djingga.models.TopsisModel;
import ac.id.unindra.spk.topsis.djingga.services.AlternativeService;
import ac.id.unindra.spk.topsis.djingga.services.CriteriaService;
import ac.id.unindra.spk.topsis.djingga.services.TopsisService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class DashboardViewController implements Initializable {

    CriteriaService criteriaService = new CriteriaDAO();
    CriteriaModel criteriaModel = new CriteriaModel();

    AlternativeModel alternativeModel = new AlternativeModel();
    AlternativeService alternativeService = new AlternativeDAO();

    TopsisModel topsisModel = new TopsisModel();
    TopsisService topsisService = new TopsisDAO();

    @FXML
    private Text alternativeCount;

    @FXML
    private Text calculateCount;

    @FXML
    private Text criteriaCount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCriteriaCount();
        setAlternativeCount();
        setCalculateCount();
    }

    private void setCriteriaCount() {
        criteriaService.countCriteria(criteriaModel);
        criteriaCount.setText(String.valueOf(criteriaModel.getTotalCriteria()));
    }

    private void setAlternativeCount() {
        alternativeService.countAlternative(alternativeModel);
        alternativeCount.setText(String.valueOf(alternativeModel.getTotalAlternatif()));
    }

    private void setCalculateCount() {
        topsisService.countTopsis(topsisModel);
        calculateCount.setText(String.valueOf(topsisModel.getTotalTopsis()));
    }

}
