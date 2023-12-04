package pe.usat.moviles.rapidisimoapp_empresa.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
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
import pe.usat.moviles.rapidisimoapp_empresa.adapter.UbicacionAdapter;
import pe.usat.moviles.rapidisimoapp_empresa.model.Solicitud;
import pe.usat.moviles.rapidisimoapp_empresa.model.Ubicacion;
import pe.usat.moviles.rapidisimoapp_empresa.response.SolicitudListadoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.UbicacionListadoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.ApiService;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbicacionFragment extends Fragment {
    RecyclerView recyclerViewSolicitudes;
    UbicacionAdapter solicitudAdapter;
    List<Ubicacion> ubicacionesLista = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayoutSolicitudes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);

        //Configurar el recyclerview
        recyclerViewSolicitudes = view.findViewById(R.id.recyclerViewSolicitudes);
        recyclerViewSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));
        solicitudAdapter = new UbicacionAdapter(ubicacionesLista, false);
        recyclerViewSolicitudes.setAdapter(solicitudAdapter);

        //Configurar el swipeRefreshLayoutClientes
        swipeRefreshLayoutSolicitudes = view.findViewById(R.id.swipeRefreshLayoutSolicitudes);

        //Reconozca el evento onRefresh
        swipeRefreshLayoutSolicitudes.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listarUbicaciones(); //Refrescar el contenido de la lista
                swipeRefreshLayoutSolicitudes.setRefreshing(false); //Desactivar la animación
            }
        });
        swipeRefreshLayoutSolicitudes.setColorSchemeResources(R.color.primaryColor, R.color.primaryColorDark, R.color.colorAccent);



        listarUbicaciones();

        return view;
    }

    private void listarUbicaciones(){
        int solicitud = 0;
        ApiService apiService = RetrofitClient.createService();
        Call<UbicacionListadoResponse> call = apiService.listadoUbicaciones(solicitud);
        call.enqueue(new Callback<UbicacionListadoResponse>() {
            @Override
            public void onResponse(Call<UbicacionListadoResponse> call, Response<UbicacionListadoResponse> response) {
                if (response.code() == 200) {
                    boolean status = response.body().isStatus();
                    if (status) {
                        ubicacionesLista.clear();
                        ubicacionesLista.addAll(Arrays.asList(response.body().getData()));
                        solicitudAdapter.notifyDataSetChanged();
                    }
                } else {
                    String errorMessage = "Error al acceder al servicio web";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("Error al acceder al servicio web", errorMessage);
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UbicacionListadoResponse> call, Throwable t) {
                Log.e("Falla al conectarse al servicio web", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}