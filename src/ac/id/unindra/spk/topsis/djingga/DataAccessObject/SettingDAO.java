package ac.id.unindra.spk.topsis.djingga.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ac.id.unindra.spk.topsis.djingga.controllers.LoginViewController;
import ac.id.unindra.spk.topsis.djingga.models.LoginModel;
import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.SettingModel;
import ac.id.unindra.spk.topsis.djingga.services.OTPService;
import ac.id.unindra.spk.topsis.djingga.services.SettingService;
import ac.id.unindra.spk.topsis.djingga.utilities.DatabaseConnection;
import ac.id.unindra.spk.topsis.djingga.utilities.NotificationManager;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class SettingDAO implements SettingService {
    private Connection conn = new DatabaseConnection().getConnection();

    @Override
    public void getProfile(SettingModel settingModel, OTPModel OTPModel) {
        String sql = "SELECT * FROM pengguna WHERE idPengguna =? ";
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, settingModel.getUserId());
            rs = stat.executeQuery();
            if (rs.next()) {
                settingModel.setFullname(rs.getString("namaLengkap"));
                OTPModel.setIdOTP(rs.getString("idOTP"));
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }

    @Override
    public boolean checkOldPassword(SettingModel settingModel, LoginModel loginModel) {
        String sql = "SELECT * FROM pengguna WHERE idPengguna=?";
        PreparedStatement stat = null;
        ResultSet rs = null;
        boolean valid = false;
        try {
            stat = conn.prepareStatement(sql);
            stat.setString(1, settingModel.getUserId());
            rs = stat.executeQuery();

            if (rs.next()) {
                BCrypt.Result result = BCrypt.verifyer().verify(loginModel.getPassword().toCharArray(),
                        rs.getString("kataSandi"));
                if (result.verified) {
                    valid = true;
                } else {
                    NotificationManager.notification("Kata Sandi Lama tidak sesuai",
                            "Periksa kembali kata sandi lama Anda");
                    stat.close();
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
        return valid;

    }

}
