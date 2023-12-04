package pe.usat.moviles.rapidisimoapp_empresa.retrofit;

import pe.usat.moviles.rapidisimoapp_empresa.response.AsignarVehiculoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.ChoferListadoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.ClienteListadoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.ConductorRegistrarResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.EstadoClienteResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.EstadoRegistrarResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.GenericoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.SolicitudListadoActivasResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.SolicitudListadoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.TarifaRegistrarResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.UbicacionListadoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.VehiculoListadoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.VehiculoRegistrarResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    /*
    @FormUrlEncoded
    @POST("/login")
    Call<LoginResponse> login(@Field("email") String email, @Field("clave") String clave);

    @GET("/ciudad/listar")
    Call<CiudadListadoResponse> listarCiudad();

    @FormUrlEncoded
    @POST("/cliente/insertar")
    Call<ClienteInsertarReponse> insertarCliente(@Field("nombre") String nombre, @Field("direccion") String direccion, @Field("email") String email, @Field("ciudad_id") String ciudadId);


    */
    @FormUrlEncoded
    @POST("/login")
    Call<GenericoResponse> login(@Field("usuario") String usuario, @Field("clave") String clave);
    @FormUrlEncoded
    @POST("/vehiculo/insertar")
    Call<VehiculoRegistrarResponse> registrarVehiculo(@Field("matricula") String matricula,@Field("capacidad_total") Double capacidad,@Field("tipo_carga") String tipoCarga,@Field("modelo") String modelo,@Field("marca") String marca);
    @FormUrlEncoded
    @POST("/conductor/insertar")
    Call<ConductorRegistrarResponse> registrarConductor(@Field("tipoDoc") String tipoDoc,@Field("numeroDoc") String numeroDoc,@Field("apellidos") String apellidos,@Field("nombres") String nombres,@Field("direccion") String direccion,@Field("fechaNac") String fechaNac,@Field("estado") String estado,@Field("usuario") String usuario,@Field("contrasena") String contrasena);
    @FormUrlEncoded
    @POST("/tarifa/insertar")
    Call<TarifaRegistrarResponse> registrarTarifa(@Field("tarifa") double tarifa);
    @FormUrlEncoded
    @POST("/solicitud/conductor")
    Call<SolicitudListadoActivasResponse> listadoSolicitudesAtendidas(@Field("conductor_id") int conductorId);

    @GET("/solicitud/listar/0")
    Call<SolicitudListadoResponse> listadoSolicitudes();
    @FormUrlEncoded
    @POST("/solicitud/ubicacion")
    Call<UbicacionListadoResponse> listadoUbicaciones(@Field("solicitud_id") int solicitud_id);
    @FormUrlEncoded
    @POST("/cliente/numeroDoc")
    Call<ClienteListadoResponse> listadoClienteDoc(@Field("numDoc") String numDoc);
    @GET("/cliente")
    Call<ClienteListadoResponse> listadoCliente();
    @GET("/vehiculo")
    Call<VehiculoListadoResponse> listadoVehiculo();
    @GET("/conductor")
    Call<ChoferListadoResponse> listadoConductor();
    @FormUrlEncoded
    @POST("/cliente/estado")
    Call<EstadoClienteResponse> cambiarEstado(@Field("cliente_id") int cliente_id,@Field("estado") String estado);
    @FormUrlEncoded
    @POST("/solicitud/asignarVehiculo")
    Call<AsignarVehiculoResponse> asignarVehiculo(@Field("solicitud_servicio_id") int solicitudid, @Field("vehiculo_id") int vehiculo_id, @Field("conductor_id") int conductor_id);
    @FormUrlEncoded
    @POST("/estado/registrar")
    Call<EstadoRegistrarResponse> registrarEstado(@Field("nombreEstado") String estado, @Field("observacion") String observacion, @Field("solicitudid") int solicitud_id);

    @GET("/pagosolicitud/{id}")
    Call<GenericoResponse> confirmarPago(@Path("id") int estado);
}
