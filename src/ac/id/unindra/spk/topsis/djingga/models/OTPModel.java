package ac.id.unindra.spk.topsis.djingga.models;

public class OTPModel {
    String idOTP,storedOTP,enteredOTP, idUser;

    public String getIdOTP() {
        return idOTP;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setIdOTP(String idOTP) {
        this.idOTP = idOTP;
    }

    public String getStoredOTP() {
        return storedOTP;
    }

    public void setStoredOTP(String storedOTP) {
        this.storedOTP = storedOTP;
    }

    public String getEnteredOTP() {
        return enteredOTP;
    }

    public void setEnteredOTP(String enteredOTP) {
        this.enteredOTP = enteredOTP;
    }

   
}
