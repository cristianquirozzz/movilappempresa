package pe.usat.moviles.rapidisimoapp_empresa.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import pe.usat.moviles.rapidisimoapp_empresa.R;
import pe.usat.moviles.rapidisimoapp_empresa.response.TarifaRegistrarResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.VehiculoRegistrarResponse;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.ApiService;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.RetrofitClient;
import pe.usat.moviles.rapidisimoapp_empresa.util.Helper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarVehiculoFragment extends Fragment implements View.OnClickListener {

    TextInputEditText txtPlaca,txtCapacidad,txtModelo,txtMarca;
    MaterialButton btnRegistrar;
    AutoCompleteTextView autoCompleteTextViewTipoCarga;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agregar_vehiculo, container, false);
        txtPlaca = view.findViewById(R.id.editPlaca);
        txtCapacidad = view.findViewById(R.id.editCapacidad);
        txtModelo = view.findViewById(R.id.editModelo);
        txtMarca = view.findViewById(R.id.editMarca);
        autoCompleteTextViewTipoCarga = view.findViewById(R.id.autoCompleteTextViewTipoCarga);
        btnRegistrar = view.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.opciones_carga, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoCompleteTextViewTipoCarga.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegistrar) {
                registrar();
                hideKeyboard(v);
        }
    }
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void registrar() {

            Double capacidadTotal = Double.parseDouble(txtCapacidad.getText().toString());
            String placa = txtPlaca.getText().toString();
            String modelo = txtModelo.getText().toString();
            String marca = txtMarca.getText().toString();
            ApiService apiService = RetrofitClient.createService();
            String textoTipoCarga = autoCompleteTextViewTipoCarga.getText().toString();
            String resultado = textoTipoCarga.equalsIgnoreCase("CONGELADO") ? "C" : "N";

            final Call<VehiculoRegistrarResponse> call = apiService.registrarVehiculo(placa, capacidadTotal, resultado, modelo, marca);
            call.enqueue(new Callback<VehiculoRegistrarResponse>() {
                @Override
                public void onResponse(final Call<VehiculoRegistrarResponse> call, final Response<VehiculoRegistrarResponse> response) {
                    if (response.code() == 200){
                        if (response.body() != null && response.body().isStatus()) {
                            Helper.mensajeInformacion(getActivity(), "Mensaje", "Vehiculo registrado correctamente");
                        } else {
                            Helper.mensajeError(getActivity(), "Error al registrar el vehiculo", "Mensaje de error específico");
                        }
                    } else {
                        Helper.mensajeInformacion(getActivity(), "Mensaje", "No se registró el vehiculo correctamente");
                    }
                }

                @Override
                public void onFailure(final Call<VehiculoRegistrarResponse> call, final Throwable t) {
                    String mensajeError = "Error en la solicitud de registro del vehiculo: " + t.getMessage();
                    Helper.mensajeInformacion(getActivity(), "Mensaje", mensajeError);
                    t.printStackTrace(); // Esto imprimirá los detalles del error en la consola para que puedas ver la causa específica.
                }
            });


    }
    }
