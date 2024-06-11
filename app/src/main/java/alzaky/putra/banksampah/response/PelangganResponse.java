package alzaky.putra.banksampah.response;

import com.google.gson.annotations.SerializedName;

public class PelangganResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String username;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("telepon")
    private String telepon;

    @SerializedName("kota")
    private String kota;

    public PelangganResponse(int id, String username, String fullName, String alamat, String telepon, String kota) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.alamat = alamat;
        this.telepon = telepon;
        this.kota = kota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    @Override
    public String toString() {
        return "pelanggan{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", alamat='" + alamat + '\'' +
                ", telepon='" + telepon + '\'' +
                ", kota='" + kota + '\'' +
                '}';
    }
}
