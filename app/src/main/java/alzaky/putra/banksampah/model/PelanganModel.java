package alzaky.putra.banksampah.model;

import com.google.gson.annotations.SerializedName;

public class PelanganModel {
    @SerializedName("fullname")
    private String fullname;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("telepon")
    private String telepon;

    @SerializedName("kota")
    private String kota;

    public PelanganModel(String fullname, String alamat, String telepon, String kota) {
        this.fullname = fullname;
        this.alamat = alamat;
        this.telepon = telepon;
        this.kota = kota;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
}
