package alzaky.putra.banksampah.model;

public class PickupRequest {
    private String fullname;
    private int tipe;
    private int berat;
    private int harga;
    private String tanggal_penjemputan;
    private String alamat;
    private String catatan;

    public PickupRequest(String fullname, int tipe, int berat, int harga, String tanggal_penjemputan, String alamat, String catatan) {
        this.fullname = fullname;
        this.tipe = tipe;
        this.berat = berat;
        this.harga = harga;
        this.tanggal_penjemputan = tanggal_penjemputan;
        this.alamat = alamat;
        this.catatan = catatan;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }

    public int getBerat() {
        return berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getTanggal_penjemputan() {
        return tanggal_penjemputan;
    }

    public void setTanggal_penjemputan(String tanggal_penjemputan) {
        this.tanggal_penjemputan = tanggal_penjemputan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}