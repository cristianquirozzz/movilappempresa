package pe.usat.moviles.rapidisimoapp_empresa.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import pe.usat.moviles.rapidisimoapp_empresa.R;
import pe.usat.moviles.rapidisimoapp_empresa.fragments.HomeFragment;
import pe.usat.moviles.rapidisimoapp_empresa.model.Cliente;
import pe.usat.moviles.rapidisimoapp_empresa.model.Solicitud;
import pe.usat.moviles.rapidisimoapp_empresa.util.Helper;

public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.SolicitudViewHolder>{

    private List<Solicitud> solicitudLista;
    HomeFragment homeFragment=new HomeFragment();

    private boolean isEditable;
    public int posicionItemSeleccionado;
    private AutoCompleteTextView autoCompleteTextViewEstado;
    public SolicitudAdapter(final List<Solicitud> solicitudLista, boolean isEditable) {
        this.solicitudLista = solicitudLista;
        this.isEditable = isEditable;
    }

    @NonNull
    @Override
    public SolicitudViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        //Vincular el adapter a la vista (cardview Solicitud). Archivo: Solicitud_cardview.xml
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_order, parent, false);
        return new SolicitudViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SolicitudViewHolder holder, final int position) {
        final Solicitud solicitud = solicitudLista.get(position);
        holder.bind(solicitud);
        // Lógica para mostrar el diálogo modal al mantener presionado el cardview
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Solicitud solicitud = solicitudLista.get(position);
                    int idSolicitud = solicitud.getId();
                    mostrarDialogoModal(solicitud, v.getContext(), idSolicitud);
                }
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoModal2(solicitud, view.getContext());
            }
        });

        // Resto del código de onBindViewHolder...
    }

    private void mostrarDialogoModal2(Solicitud solicitud, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflar el diseño del diálogo
        View dialogView = inflater.inflate(R.layout.asignarvehiculoconductor, null);
        AutoCompleteTextView autoCompleteTextViewConductor = dialogView.findViewById(R.id.autoCompleteTextViewConductor);
        AutoCompleteTextView autoCompleteTextViewVehiculo = dialogView.findViewById(R.id.autoCompleteTextViewVehiculo);
        homeFragment.cargarChofer(autoCompleteTextViewConductor,context);
        homeFragment.cargarVehiculo(autoCompleteTextViewVehiculo,context);
        // Configurar adaptadores y otras configuraciones para los AutoCompleteTextView

        builder.setView(dialogView)
                .setTitle("Asignar Conductor/ Vehiculo")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Acciones al hacer clic en Aceptar
                        ;
                        String[] conductore = autoCompleteTextViewConductor.getText().toString().split("-");
                        int conductorid = Integer.parseInt(conductore[0]);
                        String[] vehiculoe = autoCompleteTextViewVehiculo.getText().toString().split("-");
                        int vehiculoid = Integer.parseInt(vehiculoe[0]);
                        int idSolicitud = solicitud.getId();

                        homeFragment.asignar(conductorid,vehiculoid,idSolicitud,context);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Acciones al hacer clic en Cancelar
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void mostrarDialogoModal(Solicitud solicitud, Context context, int idSolicitud) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflar el diseño del diálogo
        View view = inflater.inflate(R.layout.modalestado, null);
        TextInputEditText editTextObservacion = view.findViewById(R.id.editTextObservacion);
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.autoCompleteTextViewEstado);

        // Crear un ArrayAdapter y establecerlo en el AutoCompleteTextView
        String[] opciones = context.getResources().getStringArray(R.array.opciones_estados);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, opciones);
        autoCompleteTextView.setAdapter(adapter);

        builder.setView(view)
                .setTitle("Agregar Estado")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Mover la obtención de texto al evento de clic del botón Aceptar
                        String estadoSeleccionado = autoCompleteTextView.getText().toString();
                        String observacion = editTextObservacion.getText().toString();
                        int idSolicitud = solicitud.getId();
                        // Realizar acciones con los valores obtenidos


                        homeFragment.registrar(estadoSeleccionado,observacion,idSolicitud,context);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Acciones al hacer clic en Cancelar
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        //Determina la cantidad de registros a asignar al recyclerView
        return solicitudLista.size();
    }

    public class SolicitudViewHolder extends RecyclerView.ViewHolder{
        //Declarar los controles que tiene el cardview de Solicitud
        private TextView txtOrigen, txtDestino, txtClase, txtTipo, txtCategoria, txtDescripcion;

        public SolicitudViewHolder(@NonNull final View itemView) {
            super(itemView);
            //Relacionar los controles del cardview con los controles declarados en java
            txtOrigen = itemView.findViewById(R.id.txtOrigen);
            txtDestino = itemView.findViewById(R.id.txtDestino);
            txtClase = itemView.findViewById(R.id.txtClase);
            txtTipo = itemView.findViewById(R.id.txtTipo);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            
        }

        public void bind(final Solicitud solicitud){
            //Asignar los datos del Solicitud a cada control del cardview
            txtOrigen.setText(solicitud.getDireccionOrigen());
            txtDestino.setText(solicitud.getDireccionDestino());
            txtClase.setText(solicitud.getClaseCarga());
            txtTipo.setText(solicitud.getTipoCarga());
            txtCategoria.setText(solicitud.getCategoriaCarga());
            txtDescripcion.setText(solicitud.getDescripcionCarga());

            if (isEditable) {
                //Evento cuando clickean al card view
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(itemView.getContext(), "GO TO DETAILS", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putInt("solicitudId", solicitud.getId());

                        NavController navController = Navigation.findNavController((Activity) itemView.getContext(), R.id.nav_host_fragment_content_navigation);
                        navController.navigate(R.id.detalleSolicitudFragment, bundle);
                    }
                });
            }


        }




    }

}
