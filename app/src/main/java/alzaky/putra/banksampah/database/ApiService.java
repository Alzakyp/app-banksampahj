package alzaky.putra.banksampah.database;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import alzaky.putra.banksampah.model.LoginRequest;
import alzaky.putra.banksampah.model.PelanganModel;
import alzaky.putra.banksampah.model.PickupRequest;
import alzaky.putra.banksampah.model.RegisterRequest;
import alzaky.putra.banksampah.response.LoginResponse;
import alzaky.putra.banksampah.response.PelangganModel;
import alzaky.putra.banksampah.response.PelangganResponse;
import alzaky.putra.banksampah.response.PickupResponse;
import alzaky.putra.banksampah.response.ProdukResponse;
import alzaky.putra.banksampah.response.RegisterResponse;
import alzaky.putra.banksampah.response.SumHargaResponse;
import alzaky.putra.banksampah.response.TypeProductResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("pelanggan/login")
    Call<LoginResponse> login(@Body LoginRequest  loginRequest);

    @POST("pelanggan/register")
    Call<RegisterResponse> register(@Body RegisterRequest RegisterRequest);

    @POST("pickup/submit")
    Call<PickupResponse> createPickup(@Body PickupRequest PickupRequest);

    @GET("tipeproduk")
    Call<TypeProductResponse> getTypeProducts();

    @GET("getpickupdata")
    Call<PickupResponse> getPickupData();

    @GET("sumharga")
    Call<SumHargaResponse> getSumHarga();

    @PUT("/api/pelanggan/{id}")
    Call<PelangganResponse> updatePelanggan(@Path("id") int id, @Body PelanganModel pelanganModel);

    @NotNull
    Object getPelanggan(@NotNull String id);
}