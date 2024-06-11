package alzaky.putra.banksampah.response;

public class RegisterResponse {
    private boolean success;
    private String message;
    private Pelanggan data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Pelanggan getData() {
        return data;
    }

    public class Pelanggan {
        private int id;
        private String username;
        private String fullname;
        private String alamat;
        private String telepon;
        private String kota;

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getfullname() {
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
    }
}
