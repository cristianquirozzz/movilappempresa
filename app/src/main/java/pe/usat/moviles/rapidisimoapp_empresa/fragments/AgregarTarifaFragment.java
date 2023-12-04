package pe.usat.moviles.rapidisimoapp_empresa.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import pe.usat.moviles.rapidisimoapp_empresa.R;
import pe.usat.moviles.rapidisimoapp_empresa.response.TarifaRegistrarResponse;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.ApiService;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.RetrofitClient;
import pe.usat.moviles.rapidisimoapp_empresa.util.Helper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarTarifaFragment extends Fragment implements View.OnClickListener {

    TextInputEditText txtTarifa;
    MaterialButton btnRegistrar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agregar_tarifa, container, false);
        txtTarifa = view.findViewById(R.id.txtTarifa);
        btnRegistrar = view.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);

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

            Double tarifa = Double.parseDouble(txtTarifa.getText().toString());
            ApiService apiService = RetrofitClient.createService();
            final Call<TarifaRegistrarResponse> call = apiService.registrarTarifa(tarifa);
            call.enqueue(new Callback<TarifaRegistrarResponse>() {

                @Override
                public void onResponse(final Call<TarifaRegistrarResponse> call, final Response<TarifaRegistrarResponse> response) {
                    if (response.code() == 200){
                        if (response.body().isStatus()){ //True
                            Helper.mensajeInformacion(getActivity(), "Mensaje", "Tarifa registrada correctamente");
                        } else {
                            Helper.mensajeError(getActivity(), "Error al registrar el cliente", "Mensaje de error específico");
                        }
                    } else {
                        Helper.mensajeInformacion(getActivity(), "Mensaje", "No se registro tarifa correctamente");
                    }
                }


                @Override
                public void onFailure(final Call<TarifaRegistrarResponse> call, final Throwable t) {
                    Log.e("Error al conectar con el servicio web de tarifa", t.getMessage());
                }
            });

        }
    }
