package pe.usat.moviles.rapidisimoapp_empresa.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pe.usat.moviles.rapidisimoapp_empresa.R;
import pe.usat.moviles.rapidisimoapp_empresa.adapter.SolicitudAdapter;
import pe.usat.moviles.rapidisimoapp_empresa.model.Conductor;
import pe.usat.moviles.rapidisimoapp_empresa.model.Solicitud;
import pe.usat.moviles.rapidisimoapp_empresa.model.Vehiculo;
import pe.usat.moviles.rapidisimoapp_empresa.response.AsignarVehiculoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.ChoferListadoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.ConductorRegistrarResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.EstadoRegistrarResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.SolicitudListadoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.TarifaRegistrarResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.VehiculoListadoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.ApiService;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.RetrofitClient;
import pe.usat.moviles.rapidisimoapp_empresa.util.Helper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView recyclerViewSolicitudes;
    SolicitudAdapter solicitudAdapter;
    List<Solicitud> solicitudesLista = new ArrayList<>();
    List<Conductor> conductorLista = new ArrayList<>();
    List<Vehiculo> vehiculoLista = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayoutSolicitudes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);

        //Configurar el recyclerview
        recyclerViewSolicitudes = view.findViewById(R.id.recyclerViewSolicitudes);
        recyclerViewSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));
        solicitudAdapter = new SolicitudAdapter(solicitudesLista, false);
        recyclerViewSolicitudes.setAdapter(solicitudAdapter);

        //Configurar el swipeRefreshLayoutClientes
        swipeRefreshLayoutSolicitudes = view.findViewById(R.id.swipeRefreshLayoutSolicitudes);

        //Reconozca el evento onRefresh
        swipeRefreshLayoutSolicitudes.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listarSolicitudes(); //Refrescar el contenido de la lista
                swipeRefreshLayoutSolicitudes.setRefreshing(false); //Desactivar la animación
            }
        });
        swipeRefreshLayoutSolicitudes.setColorSchemeResources(R.color.primaryColor, R.color.primaryColorDark, R.color.colorAccent);

        listarSolicitudes();

        return view;
    }

    private void listarSolicitudes() {
        int conductorId = 1;

        ApiService apiService = RetrofitClient.createService();
        Call<SolicitudListadoResponse> call = apiService.listadoSolicitudes();
        call.enqueue(new Callback<SolicitudListadoResponse>() {
            @Override
            public void onResponse(Call<SolicitudListadoResponse> call, Response<SolicitudListadoResponse> response) {
                if (response.code() == 200) {
                    boolean status = response.body().isStatus();
                    if (status) {
                        solicitudesLista.clear();
                        solicitudesLista.addAll(Arrays.asList(response.body().getData()));
                        solicitudAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("Error al acceder al servicio web", response.message());
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SolicitudListadoResponse> call, Throwable t) {
                Log.e("Falla al conectarse al servicio web", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void registrar(String estado, String observacion, int solicitud_id, Context context) {

        ApiService apiService1 = RetrofitClient.createService();
        final Call<EstadoRegistrarResponse> call = apiService1.registrarEstado(estado, observacion, solicitud_id);
        call.enqueue(new Callback<EstadoRegistrarResponse>() {

            @Override
            public void onResponse(final Call<EstadoRegistrarResponse> call, final Response<EstadoRegistrarResponse> response) {
                if (response.code() == 200) {
                    if (response.body().isStatus()) { //True
                        Helper.mensajeInformacion(context, "Mensaje", "Estado registrado correctamente");
                    } else {
                        Helper.mensajeError(context, "Error al registrar el Estado", "Mensaje de error específico");
                    }
                } else {
                    Helper.mensajeInformacion(context, "Mensaje", "No se registro Estado correctamente");
                }
            }


            @Override
            public void onFailure(final Call<EstadoRegistrarResponse> call, final Throwable t) {
                Log.e("Error al conectar con el servicio web de tarifa", t.getMessage());
            }
        });

    }
    public void asignar(int vehiculo, int conductor, int solicitud_id, Context context) {

        ApiService apiService2 = RetrofitClient.createService();
        final Call<AsignarVehiculoResponse> call = apiService2.asignarVehiculo(vehiculo, conductor, solicitud_id);
        call.enqueue(new Callback<AsignarVehiculoResponse>() {

            @Override
            public void onResponse(final Call<AsignarVehiculoResponse> call, final Response<AsignarVehiculoResponse> response) {
                if (response.code() == 200) {
                    if (response.body().isStatus()) { //True
                        Helper.mensajeInformacion(context, "Mensaje", "Vehiculo asignado correctamente");
                    } else {
                        Helper.mensajeError(context, "Error al asignar el Vehiculo", "Mensaje de error específico");
                    }
                } else {
                    Helper.mensajeInformacion(context, "Mensaje", "No se asigno vehiculo correctamente");
                }
            }


            @Override
            public void onFailure(final Call<AsignarVehiculoResponse> call, final Throwable t) {
                Log.e("Error al conectar con el servicio web de tarifa", t.getMessage());
            }
        });

    }

    public void cargarChofer(AutoCompleteTextView autoCompleteTextViewConductor,Context context) {
        final ApiService apiService = RetrofitClient.createService();
        final Call<ChoferListadoResponse> call = apiService.listadoConductor();
        call.enqueue(new Callback<ChoferListadoResponse>() {
            @Override
            public void onResponse(final Call<ChoferListadoResponse> call, final Response<ChoferListadoResponse> response) {
                if (response.code() == 200) {
                    final boolean status = response.body().isStatus();
                    if (status) { //True
                        conductorLista.clear();
                        conductorLista.addAll(Arrays.asList(response.body().getData()));
                        final String[] nombreConductor = new String[conductorLista.size()];
                        for (final Conductor conductor : conductorLista) {
                            nombreConductor[conductorLista.indexOf(conductor)] = conductor.getId()+"-"+conductor.getNombres();
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<>
                                (
                                        context,
                                        android.R.layout.simple_list_item_1,
                                        nombreConductor
                                );
                        autoCompleteTextViewConductor.setAdapter(adapter);

                    }
                } else {
                    //Status: 500, 401, 400, etc
                    try {
                        Log.e("Error al ejecutar el servicio de listar ciudad", response.errorBody().string());
                    } catch (final IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(final Call<ChoferListadoResponse> call, final Throwable t) {
                Log.e("Error al conectar con el servicio web de ciudades", t.getMessage());
            }
        });

    }
    public void cargarVehiculo(AutoCompleteTextView autoCompleteTextViewVehiculo,Context context) {
        final ApiService apiService = RetrofitClient.createService();
        final Call<VehiculoListadoResponse> call = apiService.listadoVehiculo();
        call.enqueue(new Callback<VehiculoListadoResponse>() {
            @Override
            public void onResponse(final Call<VehiculoListadoResponse> call, final Response<VehiculoListadoResponse> response) {
                if (response.code() == 200) {
                    final boolean status = response.body().isStatus();
                    if (status) { //True
                        vehiculoLista.clear();
                        vehiculoLista.addAll(Arrays.asList(response.body().getData()));
                        final String[] matriculaVehiculo = new String[vehiculoLista.size()];
                        for (final Vehiculo vehiculo : vehiculoLista) {
                            matriculaVehiculo[vehiculoLista.indexOf(vehiculo)] = vehiculo.getId()+"-"+vehiculo.getMatricula();
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<>
                                (
                                        context,
                                        android.R.layout.simple_list_item_1,
                                        matriculaVehiculo
                                );
                        autoCompleteTextViewVehiculo.setAdapter(adapter);

                    }
                } else {
                    //Status: 500, 401, 400, etc
                    try {
                        Log.e("Error al ejecutar el servicio de listar ciudad", response.errorBody().string());
                    } catch (final IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(final Call<VehiculoListadoResponse> call, final Throwable t) {
                Log.e("Error al conectar con el servicio web de ciudades", t.getMessage());
            }
        });

    }
}