package alzaky.putra.banksampah.response;



import alzaky.putra.banksampah.object.Login;

public class LoginResponse {
    boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
    private Login pelanggan;

    public Login getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Login pelanggan) {
        this.pelanggan = pelanggan;
    }




}
