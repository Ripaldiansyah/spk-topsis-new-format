package ac.id.unindra.spk.topsis.djingga.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import ac.id.unindra.spk.topsis.djingga.DataAccessObject.ForgotPasswordDAO;
import ac.id.unindra.spk.topsis.djingga.DataAccessObject.OtpDAO;
import ac.id.unindra.spk.topsis.djingga.DataAccessObject.SettingDAO;
import ac.id.unindra.spk.topsis.djingga.models.ForgotPasswordModel;
import ac.id.unindra.spk.topsis.djingga.models.LoginModel;
import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.SettingModel;
import ac.id.unindra.spk.topsis.djingga.services.ForgotPasswordService;
import ac.id.unindra.spk.topsis.djingga.services.OTPService;
import ac.id.unindra.spk.topsis.djingga.services.SettingService;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import ac.id.unindra.spk.topsis.djingga.utilities.OTPGenerator;
import at.favre.lib.crypto.bcrypt.BCrypt;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class SettingViewController implements Initializable {
    int countdownSeconds = 90;
    static String idUser;

    @FXML
    private MFXTextField OTPEmail;

    @FXML
    private MFXTextField OTPPassword;

    @FXML
    private MFXTextField OTPProfile;

    @FXML
    private Text OTPTimeEmail;

    @FXML
    private Text OTPTimePassword;

    @FXML
    private Text OTPTimeProfile;

    @FXML
    private AnchorPane SettingMenu;

    @FXML
    private MFXButton changeEmailButton;

    @FXML
    private MFXButton changePasswordButton;

    @FXML
    private MFXButton changeProfileButton;

    @FXML
    private Pane editEmail;

    @FXML
    private MFXButton editPageButton;

    @FXML
    private Pane editPassword;

    @FXML
    private Pane editProfile;

    @FXML
    private MFXButton emailMenu;

    @FXML
    private MFXTextField fullname;

    @FXML
    private Text idText;

    @FXML
    private MFXTextField newEmail;

    @FXML
    private MFXPasswordField newPassword;

    @FXML
    private MFXPasswordField newPasswordConfirm;

    @FXML
    private MFXTextField oldEmail;

    @FXML
    private MFXPasswordField oldPassword;

    @FXML
    private MFXButton passwordMenu;

    @FXML
    private MFXButton profileMenu;

    @FXML
    private MFXButton reqOTPEmail;

    @FXML
    private MFXButton reqOTPPassword;

    @FXML
    private MFXButton reqOTPProfile;

    @FXML
    private AnchorPane userEditPage;

    @FXML
    private Text userNameText;

    @FXML
    private MFXTextField username;

    @FXML
    private MFXButton validasiOTPEmail;

    @FXML
    private MFXButton validasiOTPPassword;

    @FXML
    private MFXButton validasiOTPProfile;

    @FXML
    private Text warningEmail;

    Timer timer = new Timer();
    SettingService settingService = new SettingDAO();
    SettingModel settingModel = new SettingModel();
    OTPModel OTPModel = new OTPModel();
    OTPService OTPService = new OtpDAO();

    ForgotPasswordService forgotPasswordService = new ForgotPasswordDAO();
    ForgotPasswordModel forgotPasswordModel = new ForgotPasswordModel();
    LoginModel loginModel = new LoginModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAccountInfomation();
        enableAnchorPane(SettingMenu);
        enableButtonPassword(reqOTPPassword);
        enableButtonEmail(reqOTPEmail);
        enableButtonProfile(reqOTPProfile);
    }

    private void setAccountInfomation() {
        idText.setText(idUser);
        settingModel.setUserId(idUser);
        settingService.getProfile(settingModel, OTPModel);
        userNameText.setText(settingModel.getFullname());
    }

    private void enableAnchorPane(AnchorPane AnchorPane) {
        SettingMenu.setVisible(false);
        userEditPage.setVisible(false);

        SettingMenu.setDisable(true);
        userEditPage.setDisable(true);

        if (true) {
            AnchorPane.setVisible(true);
            AnchorPane.setDisable(false);
        }
    }

    private void enableUserEditPane(Pane pane) {
        editPassword.setVisible(false);
        editProfile.setVisible(false);
        editEmail.setVisible(false);

        editPassword.setDisable(true);
        editProfile.setDisable(true);
        editEmail.setDisable(true);

        if (true) {
            pane.setVisible(true);
            pane.setDisable(false);
        }
    }

    @FXML
    void editAccount(MouseEvent event) {
        enableAnchorPane(userEditPage);
        userEditPassword(null);
    }

    @FXML
    void userEditPassword(MouseEvent event) {
        enableUserEditPane(editPassword);
    }

    @FXML
    void userEditEmail(MouseEvent event) {
        enableUserEditPane(editEmail);
    }

    @FXML
    void userEditProfile(MouseEvent event) {
        enableUserEditPane(editProfile);
    }

    @FXML
    void requestOTPPassword(MouseEvent event) {
        OTPTimePassword.setVisible(true);
        enableButtonPassword(validasiOTPPassword);
        SettingViewController settingViewController = new SettingViewController();
        settingViewController.countDownOTPRequest(OTPTimePassword, reqOTPPassword);
        reqOTPPassword.setVisible(true);
        OTPTimePassword.setVisible(true);
        sendOTP();

    }

    private void sendOTP() {
        String storedOTP = OTPGenerator.generateOTP(6);
        OTPModel.setStoredOTP(storedOTP);
        OTPModel.setIdUser(idUser);

        OTPService.setOTP(OTPModel, true);
        OTPService.sendOTP(OTPModel);
    }

    @FXML
    void requestOTPEmail(MouseEvent event) {
        OTPTimeEmail.setVisible(true);
        warningEmail.setVisible(false);
        warningEmail.setDisable(true);
        enableButtonEmail(validasiOTPEmail);
        SettingViewController settingViewController = new SettingViewController();
        settingViewController.countDownOTPRequest(OTPTimeEmail, reqOTPEmail);
        reqOTPEmail.setVisible(true);
        OTPTimeEmail.setVisible(true);
       
        sendOTP();
    }

    @FXML
    void requestOTPProfile(MouseEvent event) {
        OTPTimeProfile.setVisible(true);
        enableButtonProfile(validasiOTPProfile);
        SettingViewController settingViewController = new SettingViewController();
        settingViewController.countDownOTPRequest(OTPTimeProfile, reqOTPProfile);
        reqOTPProfile.setVisible(true);
        OTPTimeProfile.setVisible(true);
        sendOTP();
    }


    @FXML
    void validationOTPPassword(MouseEvent event) {
        String OTPEntered = OTPPassword.getText();
        OTPModel.setEnteredOTP(OTPEntered);
        if (OTPService.checkOTPSetting(OTPModel)) {
           enableButtonPassword(changePasswordButton);
           OTPPassword.setVisible(false);
           OTPPassword.setDisable(true);
        }
        
    }

    @FXML
    void validationOTPEmail(MouseEvent event) {
        String OTPEntered = OTPEmail.getText();
        OTPModel.setEnteredOTP(OTPEntered);
        if (OTPService.checkOTPSetting(OTPModel)) {
           enableButtonEmail(changeEmailButton);
           OTPEmail.setVisible(false);
           OTPEmail.setDisable(true);
        }
        
    }
    @FXML
    void validationOTPProfile(MouseEvent event) {
        String OTPEntered = OTPProfile.getText();
        OTPModel.setEnteredOTP(OTPEntered);
        if (OTPService.checkOTPSetting(OTPModel)) {
           enableButtonProfile(changeProfileButton);
           OTPProfile.setVisible(false);
           OTPProfile.setDisable(true);
        }
        
    }


    @FXML
    void changePassword(MouseEvent event){
        loginModel.setPassword(oldPassword.getText());
        boolean valid = settingService.checkOldPassword(settingModel, loginModel);

        if (valid) {
            if (passwordValidation(newPassword,newPasswordConfirm)) {
                forgotPasswordModel.setIdUser(idUser);
                forgotPasswordModel.setPassword(BCrypt.withDefaults().hashToString(12, newPassword.getText().toCharArray()));
                forgotPasswordService.resetPassword(forgotPasswordModel);
            }else{
                NotificationManager.notification("Kata Sandi Baru tidak sesuai", "Periksa kembali kata sandi baru Anda");
            }
        }
        
    }


    private boolean passwordValidation(MFXPasswordField newPassword,
            MFXPasswordField newPasswordConfirm) {
        boolean valid = false;
        if (newPassword.getText().equals(newPasswordConfirm.getText()) && (!newPassword.getText().trim().isEmpty() && !newPasswordConfirm.getText().trim().isEmpty()) ) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    private void enableButtonPassword(MFXButton MFXButton) {
        reqOTPPassword.setVisible(false);
        validasiOTPPassword.setVisible(false);
        changePasswordButton.setVisible(false);

        reqOTPPassword.setDisable(true);
        validasiOTPPassword.setDisable(true);
        changePasswordButton.setDisable(true);
        OTPTimePassword.setVisible(false);

        if (true) {
            MFXButton.setVisible(true);
            MFXButton.setDisable(false);
        }
    }

    private void enableButtonEmail(MFXButton MFXButton) {
        reqOTPEmail.setVisible(false);
        validasiOTPEmail.setVisible(false);
        changeEmailButton.setVisible(false);

        reqOTPEmail.setDisable(true);
        validasiOTPEmail.setDisable(true);
        changeEmailButton.setDisable(true);
        OTPTimeEmail.setVisible(false);
        if (true) {
            MFXButton.setVisible(true);
            MFXButton.setDisable(false);
        }
    }

    private void enableButtonProfile(MFXButton MFXButton) {
        reqOTPProfile.setVisible(false);
        validasiOTPProfile.setVisible(false);
        changeProfileButton.setVisible(false);

        reqOTPProfile.setDisable(true);
        validasiOTPProfile.setDisable(true);
        changeProfileButton.setDisable(true);
        OTPTimeProfile.setVisible(false);
        if (true) {
            MFXButton.setVisible(true);
            MFXButton.setDisable(false);
        }
    }

    

    private void countDownOTPRequest(Text text, MFXButton MFXButton) {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    text.setText("Kirim Ulang Kode Verifikasi (Dalam " + countdownSeconds + " Detik)");
                    MFXButton.setDisable(true);
                    countdownSeconds--;

                    if (countdownSeconds < 0) {
                        MFXButton.setDisable(false);
                        MFXButton.setText("Resend OTP");
                        timer.cancel();
                        text.setVisible(false);
                    }
                });
            }
        }, 0, 1000);
    }

}
