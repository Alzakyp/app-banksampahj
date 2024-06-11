package alzaky.putra.banksampah.response;

import java.util.ArrayList;

import alzaky.putra.banksampah.object.Pickup;

public class PickupResponse {
    private boolean success;
    private String message;

    private ArrayList<Pickup> data;

    public ArrayList<Pickup> getData() {
        return data;
    }
    public void setData(ArrayList<Pickup> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}