package pe.usat.moviles.rapidisimoapp_empresa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import pe.usat.moviles.rapidisimoapp_empresa.response.GenericoResponse;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.ApiService;
import pe.usat.moviles.rapidisimoapp_empresa.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    TextInputEditText txtUser, txtPass;
    MaterialButton btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);

        btnIngresar = findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(view -> {
            String user = txtUser.getText().toString();
            String pass = txtPass.getText().toString();

            if (user.isEmpty() && user.isEmpty()) {
                Toast.makeText(this, "Porfavor ingrese datos", Toast.LENGTH_SHORT).show();
                return;
            } else {
                ApiService apiService = RetrofitClient.createService();
                Call<GenericoResponse> call = apiService.login(user, pass);
                call.enqueue(new Callback<GenericoResponse>() {
                    @Override
                    public void onResponse(Call<GenericoResponse> call, Response<GenericoResponse> response) {
                        if (response.code() == 200) {
                            if (response.body().isStatus()) {
                                JsonObject data = response.body().getData();
                                //Verificamos si conductor
                                if (data.get("tipoUsuario").getAsString().equals("P")) {
                                    JsonObject conductor = data.get("Personal").getAsJsonObject();

                                    if (conductor.get("estado").getAsString().equals("A")) {
                                        //Guardamos los datos de sesion
                                        SharedPreferences sesion = getSharedPreferences("sesion", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sesion.edit();
                                        editor.putString("token", data.get("token").getAsString());
                                        editor.putInt("usuarioId", data.get("id").getAsInt());
                                        editor.putInt("conductorId", conductor.get("id").getAsInt());
                                        editor.putString("estado", conductor.get("estado").getAsString());
                                        editor.commit();

                                        /*Llamar al NavigationActivity*/
                                        final Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                                        startActivity(intent);

                                        /*Cerrar el MainActivity*/
                                        LoginActivity.this.finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "No es un usuario activo", Toast.LENGTH_SHORT).show();
                                    }


                                } else {
                                    Toast.makeText(LoginActivity.this, "Usted no es trabajador", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<GenericoResponse> call, Throwable t) {
                        Log.e("Error Login", t.getMessage());
                        Toast.makeText(LoginActivity.this, "Error al conectarse con el servidor", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }
}