package alzaky.putra.banksampah.response;

import java.util.List;
import alzaky.putra.banksampah.object.TipeProduk;

public class TypeProductResponse {
    public boolean success;
    public  String message;
    public List<TipeProduk> data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<TipeProduk> getTypeProducts() {
        return data;
    }

    public void setTypeProducts(List<TipeProduk> data) {
        this.data = data;
    }
}
