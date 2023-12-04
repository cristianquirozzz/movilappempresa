package pe.usat.moviles.rapidisimoapp_empresa.fragments;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pe.usat.moviles.rapidisimoapp_empresa.R;
import pe.usat.moviles.rapidisimoapp_empresa.adapter.ClienteAdapter;
import pe.usat.moviles.rapidisimoapp_empresa.adapter.UbicacionAdapter;
import pe.usat.moviles.rapidisimoapp_empresa.model.Cliente;
import pe.usat.moviles.rapidisimoapp_empresa.model.Ubicacion;
import pe.usat.moviles.rapidisimoapp_empresa.response.ClienteListadoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.EstadoClienteResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.TarifaRegistrarResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.UbicacionListadoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.ApiService;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.RetrofitClient;
import pe.usat.moviles.rapidisimoapp_empresa.util.Helper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteFragment extends Fragment implements View.OnClickListener{
    RecyclerView recyclerViewSolicitudes;
    ClienteAdapter clienteAdapter;
    TextInputEditText editTextBusqueda;
    MaterialButton buttonBuscar;
    List<Cliente> clientesLista = new ArrayList<>();
    List<Cliente> clientesListaDoc = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayoutSolicitudes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_clientes, container, false);

        //Configurar el recyclerview
        recyclerViewSolicitudes = view.findViewById(R.id.recyclerViewClientes);
        editTextBusqueda = view.findViewById(R.id.editTextBusqueda);
        buttonBuscar = view.findViewById(R.id.buttonBuscar);
        buttonBuscar.setOnClickListener(this);
        recyclerViewSolicitudes.setLayoutManager(new LinearLayoutManager(getContext()));
        clienteAdapter = new ClienteAdapter(clientesLista, false);
        recyclerViewSolicitudes.setAdapter(clienteAdapter);

        //Configurar el swipeRefreshLayoutClientes
        swipeRefreshLayoutSolicitudes = view.findViewById(R.id.swipeRefreshLayoutClientes);

        //Reconozca el evento onRefresh
        swipeRefreshLayoutSolicitudes.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listarClientes(); //Refrescar el contenido de la lista
                swipeRefreshLayoutSolicitudes.setRefreshing(false); //Desactivar la animación
            }
        });
        swipeRefreshLayoutSolicitudes.setColorSchemeResources(R.color.primaryColor, R.color.primaryColorDark, R.color.colorAccent);
        listarClientes();
        return view;
    }

    private void listarClientes(){

        ApiService apiService = RetrofitClient.createService();
        Call<ClienteListadoResponse> call = apiService.listadoCliente();
        call.enqueue(new Callback<ClienteListadoResponse>() {
            @Override
            public void onResponse(Call<ClienteListadoResponse> call, Response<ClienteListadoResponse> response) {
                if (response.code() == 200) {
                    boolean status = response.body().isStatus();
                    if (status) {
                        clientesLista.clear();
                        clientesLista.addAll(Arrays.asList(response.body().getData()));
                        clienteAdapter.notifyDataSetChanged();
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
            public void onFailure(Call<ClienteListadoResponse> call, Throwable t) {
                Log.e("Falla al conectarse al servicio web", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int posicionItemSeleccionado = clienteAdapter.getPosicionItemSeleccionado();
        if (posicionItemSeleccionado != -1) {
            int clienteId = clientesLista.get(posicionItemSeleccionado).getId(); // Obtener el ID del cliente según la posición seleccionada
            String estadoSeleccionado = "";

            switch (item.getItemId()) {
                case 1:
                    estadoSeleccionado = "A";
                    break;
                case 2:
                    estadoSeleccionado = "B";
                    break;
                case 3:
                    estadoSeleccionado = "R";
                    break;
            }

            if (!estadoSeleccionado.isEmpty()) {
                // Llamar a la API con clienteId y estadoSeleccionado utilizando Retrofit
                llamarApiConClienteIdYEstado(clienteId, estadoSeleccionado);

            } else {
                Log.e("Error", "Estado no seleccionado");
            }


        }else{
            Helper.mensajeError(getActivity(), "Error al registrar el cliente", "Mensaje de error específico");
        }

        return false;
    }
    public void llamarApiConClienteIdYEstado(int clienteId, String estadoSeleccionado) {
        // Llamar a la API con clienteId y estadoSeleccionado utilizando Retrofit
        ApiService apiService = RetrofitClient.createService();
        final Call<EstadoClienteResponse> call = apiService.cambiarEstado(clienteId, estadoSeleccionado);
        call.enqueue(new Callback<EstadoClienteResponse>() {

            @Override
            public void onResponse(final Call<EstadoClienteResponse> call, final Response<EstadoClienteResponse> response) {
                if (response.code() == 200){
                    if (response.body().isStatus()){ //True
                        Helper.mensajeInformacion(getActivity(), "Mensaje", "Estado cambiado correctamente");
                        listarClientes();

                    } else {
                        Helper.mensajeError(getActivity(), "Error al registrar el cliente", "Mensaje de error específico");
                    }
                } else {
                    Helper.mensajeInformacion(getActivity(), "Mensaje", "No se registro estado correctamente");
                }
            }
            @Override
            public void onFailure(final Call<EstadoClienteResponse> call, final Throwable t) {
                Log.e("Error al conectar con el servicio web de tarifa", t.getMessage());
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonBuscar) {
            filtrar();
        }

    }

    private void filtrar() {
        String numDoc = editTextBusqueda.getText().toString();

        ApiService apiService1 = RetrofitClient.createService();
        final Call<ClienteListadoResponse> call = apiService1.listadoClienteDoc(numDoc);
        call.enqueue(new Callback<ClienteListadoResponse>() {
            @Override
            public void onResponse(Call<ClienteListadoResponse> call, Response<ClienteListadoResponse> response) {
                if (response.code() == 200) {
                    boolean status = response.body().isStatus();
                    if (status) {
                        clienteAdapter = new ClienteAdapter(clientesListaDoc, false);
                        recyclerViewSolicitudes.setAdapter(clienteAdapter);
                        clientesListaDoc.addAll(Arrays.asList(response.body().getData()));
                        clienteAdapter.notifyDataSetChanged();
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
                    Toast.makeText(getContext(), "Cliente no encontrado", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ClienteListadoResponse> call, Throwable t) {
                Log.e("Falla al conectarse al servicio web", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}