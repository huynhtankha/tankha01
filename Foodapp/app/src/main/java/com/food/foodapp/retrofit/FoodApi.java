package com.food.foodapp.retrofit;

import com.food.foodapp.models.AddProductModel;
import com.food.foodapp.models.AdminModel;
import com.food.foodapp.models.CategoryFoodModel;
import com.food.foodapp.models.KeModel;
import com.food.foodapp.models.OrderModel;
import com.food.foodapp.models.PostsModel;
import com.food.foodapp.models.ProductFoodModel;
import com.food.foodapp.models.RatingModel;
import com.food.foodapp.models.UserModel;

import java.util.Date;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FoodApi {
    @GET("category.php")
    Observable<CategoryFoodModel> getCategory();

    @GET("user.php")
    Observable<UserModel> getUser();
    @GET("qlrating.php")
    Observable<RatingModel> getBl();
    @GET("thongke.php")
    Observable<KeModel> getThongke();
    @GET("products.php")
    Observable<ProductFoodModel> getProduct();
    @GET("baiviet.php")
    Observable<PostsModel> getPosts();
    @POST("details.php")
    @FormUrlEncoded
    Observable<ProductFoodModel> getFood(
        @Field("page") int page,
        @Field("loai") int loai
    );
    @POST("ratingcmt.php")
    @FormUrlEncoded
    Observable<RatingModel> getRating(
            @Field("page") int page,
            @Field("idsp") int idsp
    );
    @POST("del.php")
    @FormUrlEncoded
    Observable<AddProductModel> getDel(
            @Field("id") int id
    );
    @POST("signup.php")
    @FormUrlEncoded
    Observable<UserModel> getSign(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("mobile") String mobile
//            @Field("address") String address,
//            @Field("ward") String ward
    );
    @POST("addproduct.php")
    @FormUrlEncoded
    Observable<AddProductModel> getaddProduct(
            @Field("tensp") String tensp,
            @Field("hinhanh") String hinhanh,
            @Field("gia") String gia,
            @Field("noidung") String noidung,
            @Field("loai") int loai,
            @Field("trangthai") String trangthai
    );
    @POST("addpost.php")
    @FormUrlEncoded
    Observable<AddProductModel> getaddpost(
            @Field("ten_baiviet") String ten_baiviet,
            @Field("hinhanh") String hinhanh,
            @Field("noidung") String noidung
    );
    @POST("updatesp.php")
    @FormUrlEncoded
    Observable<AddProductModel> getsuaProduct(
            @Field("tensp") String tensp,
            @Field("hinhanh") String hinhanh,
            @Field("gia") String gia,
            @Field("noidung") String noidung,
            @Field("id") int id,
            @Field("trangthai") String trangthai
    );
    @POST("camon.php")
    @FormUrlEncoded
    Observable<UserModel> getCamon(
            @Field("updatettemail") String email

    );
    @POST("street.php")
    @FormUrlEncoded
    Observable<UserModel> getStreet(
            @Field("id") int id,
            @Field("street") String street
    );
    @POST("login.php")
    @FormUrlEncoded
    Observable<UserModel> getLogin(
            @Field("email") String email,
            @Field("password") String password

    );
    @POST("loginadmin.php")
    @FormUrlEncoded
    Observable<AdminModel> getLoginAdmin(
            @Field("email") String email,
            @Field("password") String password
    );
    @POST("update.php")
    @FormUrlEncoded
    Observable<UserModel> getUpdate(
            @Field("email") String email,
            @Field("password") String password
    );
    @POST("updateadmin.php")
    @FormUrlEncoded
    Observable<AdminModel> getUpdateadmin(
            @Field("email") String email,
            @Field("password") String password
    );
    @POST("updatett.php")
    @FormUrlEncoded
    Observable<AddProductModel> getUpdatett(
            @Field("hinhanh") String hinhanh,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("ward") String ward,
            @Field("street") String street,
            @Field("id") int id
            );
    @POST("updatemomo.php")
    @FormUrlEncoded
    Observable<AddProductModel  > getMomo(
            @Field("id") int id,
            @Field("token") String token
    );
    @POST("updatetopay.php")
    @FormUrlEncoded
    Observable<UserModel> getUpdatet(
            @Field("id") int id,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("ward") String ward,
            @Field("street") String street
    );
    @POST("rating.php")
    @FormUrlEncoded
    Observable<RatingModel> getcmt(
            @Field("idusername") int idusername,
            @Field("hinhanh") String hinhanh,
            @Field("username") String username,
            @Field("idsp") int idsp,
            @Field("comment") String comment,
            @Field("daterating") Date daterating
    );
    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModel> forgotPassword(
            @Field("email") String email
    );
    @POST("search.php")
    @FormUrlEncoded
    Observable<ProductFoodModel> search(
            @Field("search") String search
    );

    @POST("order.php")
    @FormUrlEncoded
    Observable<AddProductModel> getOrder(
            @Field("username") String username,
            @Field("email") String email,
            @Field("address") String address,
            @Field("ward") String ward,
            @Field("street") String street,
            @Field("mobile") String mobile,
            @Field("id_user") int id_user,
            @Field("total") String total,
            @Field("quantity") int quantity,
            @Field("details") String details,
            @Field("order_date") Date orderDate
    );
    @POST("lsorder.php")
    @FormUrlEncoded
    Observable<OrderModel> gethistory(
            @Field("id_user") int id
    );
    @POST("lsorderadmin.php")
    @FormUrlEncoded
    Observable<OrderModel> gethistoryadmin(
            @Field("id_user") int id
    );
    @POST("deleteorder.php")
    @FormUrlEncoded
    Observable<AddProductModel> deleteorder(
            @Field("id") int id
    );

    @POST("updatestatus.php")
    @FormUrlEncoded
    Observable<OrderModel> getUpdatexn(
            @Field("id") String id,
            @Field("xacnhan") String xacnhan
    );
    @Multipart
    @POST("upload.php")
    Call<AddProductModel> uploadFile(@Part MultipartBody.Part file

    );
    @Multipart
    @POST("uploadimage.php")
    Call<AddProductModel> uploadFileuser(@Part MultipartBody.Part file

    );
    @Multipart
    @POST("uploadpost.php")
    Call<AddProductModel> uploadFilepost(@Part MultipartBody.Part file

    );
}
