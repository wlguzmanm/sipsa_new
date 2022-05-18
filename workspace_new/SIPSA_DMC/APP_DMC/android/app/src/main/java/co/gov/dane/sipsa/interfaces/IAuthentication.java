package co.gov.dane.sipsa.interfaces;

import co.gov.dane.sipsa.backend.model.EnvioFormularioViewModel;
import co.gov.dane.sipsa.backend.model.ResponseFile;
import co.gov.dane.sipsa.backend.model.ResponseToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IAuthentication {

    /**Componente Authentication**/
    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Accept: */*",
            "Connection: keep-alive "
    })
    @POST("auth/realms/AppMonitoreo/protocol/openid-connect/token")
    Call<ResponseToken> login(
            @Field("grant_type") String grant_type,
            @Field("client_id") String clientId,
            @Field("username") String username,
            @Field("password") String password
    );



    @POST("ceexperimental/send-formulario/reporte")
    Call<ResponseFile> descargarReporte(
            @Header("Content-Type") String content_type,
            @Header("Authorization") String token,
            @Body EnvioFormularioViewModel body
    );
}
