package ac.id.unindra.spk.topsis.djingga.services;

import ac.id.unindra.spk.topsis.djingga.models.LoginModel;
import ac.id.unindra.spk.topsis.djingga.models.OTPModel;
import ac.id.unindra.spk.topsis.djingga.models.SettingModel;

public interface SettingService {

    void getProfile(SettingModel settingModel, OTPModel OTPModel);
    boolean checkOldPassword(SettingModel settingModel, LoginModel loginModel);
} 