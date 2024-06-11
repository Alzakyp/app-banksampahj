package alzaky.putra.banksampah.model;

public class RegisterRequest {
    private String username;
    private String password;
    private String fullname;
    private String alamat;
    private String telepon;
    private String kota;

    public RegisterRequest(String username, String password, String fullname, String alamat, String telepon, String kota) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.alamat = alamat;
        this.telepon = telepon;
        this.kota = kota;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullname() {
        return fullname;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public String getKota() {
        return kota;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }
}
