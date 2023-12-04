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
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import pe.usat.moviles.rapidisimoapp_empresa.R;
import pe.usat.moviles.rapidisimoapp_empresa.response.ConductorRegistrarResponse;
import pe.usat.moviles.rapidisimoapp_empresa.response.TarifaRegistrarResponse;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.ApiService;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.RetrofitClient;
import pe.usat.moviles.rapidisimoapp_empresa.util.Helper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarConductorFragment extends Fragment implements View.OnClickListener {

    AutoCompleteTextView autoCompleteTextViewTipoDoc;
    AutoCompleteTextView autoCompleteTextViewEstado;
    MaterialButton btnRegistrar;

    TextInputEditText editNumeroDocumento,editApellidos,editNombres,editDireccion,editFechaNacimiento,txtPass,txtUser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agregar_conductor, container, false);
        autoCompleteTextViewEstado=view.findViewById(R.id.autoCompleteTextViewEstado);
        autoCompleteTextViewTipoDoc=view.findViewById(R.id.autoCompleteTextViewTipoDoc);
        editNumeroDocumento=view.findViewById(R.id.editNumeroDocumento);
        editApellidos=view.findViewById(R.id.editApellidos);
        editNombres=view.findViewById(R.id.editNombres);
        editDireccion=view.findViewById(R.id.editDireccion);
        editFechaNacimiento=view.findViewById(R.id.editFechaNacimiento);
        btnRegistrar = view.findViewById(R.id.btnRegistrar);
        txtPass=view.findViewById(R.id.txtPass);
        txtUser=view.findViewById(R.id.txtUser);
        btnRegistrar.setOnClickListener(this);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.opciones_tipo_documento, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(requireContext(),
                R.array.opciones_estado, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoCompleteTextViewTipoDoc.setAdapter(adapter);
        autoCompleteTextViewEstado.setAdapter(adapter1);
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

        final String tipoDoc = autoCompleteTextViewTipoDoc.getText().toString();
        final String numeroDoc = editNumeroDocumento.getText().toString();
        final String apellidos = editApellidos.getText().toString();
        final String nombres = editNombres.getText().toString();
        final String direccion = editDireccion.getText().toString();
        final String fechaNac = editFechaNacimiento.getText().toString();
        final String estado = autoCompleteTextViewEstado.getText().toString();
        final String usuario = txtUser.getText().toString();
        final String contrasena = txtPass.getText().toString();



            ApiService apiService = RetrofitClient.createService();
            final Call<ConductorRegistrarResponse> call = apiService.registrarConductor(tipoDoc,numeroDoc,apellidos,nombres,direccion,fechaNac,estado,usuario,contrasena);
            call.enqueue(new Callback<ConductorRegistrarResponse>() {

                @Override
                public void onResponse(final Call<ConductorRegistrarResponse> call, final Response<ConductorRegistrarResponse> response) {
                    if (response.code() == 200){
                        if (response.body().isStatus()){ //True
                            Helper.mensajeInformacion(getActivity(), "Mensaje", "Conductor registrado correctamente");
                        } else {
                            Helper.mensajeError(getActivity(), "Error al registrar el conductor", "Mensaje de error específico");
                        }
                    } else {
                        Helper.mensajeInformacion(getActivity(), "Mensaje", "No se registro conductor correctamente");
                    }
                }

                @Override
                public void onFailure(final Call<ConductorRegistrarResponse> call, final Throwable t) {
                    Log.e("Error al conectar con el servicio web de tarifa", t.getMessage());
                }
            });

        }
    }
