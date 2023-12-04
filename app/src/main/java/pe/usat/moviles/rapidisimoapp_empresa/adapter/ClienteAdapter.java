package pe.usat.moviles.rapidisimoapp_empresa.adapter;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import pe.usat.moviles.rapidisimoapp_empresa.R;
import pe.usat.moviles.rapidisimoapp_empresa.fragments.ClienteFragment;
import pe.usat.moviles.rapidisimoapp_empresa.model.Cliente;
import pe.usat.moviles.rapidisimoapp_empresa.model.Solicitud;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>{
    private ClienteFragment cliente;
    private List<Cliente> clienteLista;
    private boolean isEditable;
    public int posicionItemSeleccionado;
    public int getPosicionItemSeleccionado() {
        return posicionItemSeleccionado;
    }

    public void setPosicionItemSeleccionado(int posicion) {
        this.posicionItemSeleccionado = posicion;
    }

    public ClienteAdapter(final List<Cliente> clienteLista, boolean isEditable) {
        this.clienteLista = clienteLista;
        this.isEditable = isEditable;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        //Vincular el adapter a la vista (cardview cliente). Archivo: cliente_cardview.xml
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cliente_cardview, parent, false);
        return new ClienteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ClienteViewHolder holder, final int position) {
        //Sirve para imprimir los datos en el cardview
        final Cliente cliente = clienteLista.get(position);
        holder.bind(cliente);
    }

    @Override
    public int getItemCount() {
        //Determina la cantidad de registros a asignar al recyclerView
        return clienteLista.size();
    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnLongClickListener{
        //Declarar los controles que tiene el cardview de cliente
        private TextView nombreTextView, direccionTextView, telefonoTextView, emailTextView,estadoTextView;

        public ClienteViewHolder(@NonNull final View itemView) {
            super(itemView);
            //Relacionar los controles del cardview con los controles declarados en java
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            direccionTextView = itemView.findViewById(R.id.direccionTextView);
            telefonoTextView = itemView.findViewById(R.id.telefonoTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            estadoTextView=itemView.findViewById(R.id.estadoTextView);
            //Reconocer los eventos OnCreateContextMenuListener y OnLongClickListener
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(final Cliente cliente) {
            String nombre = "";
            String nombres = cliente.getNombres();
            String razonSocial = cliente.getRazonSocial();
            if (nombres != null && !nombres.isEmpty()) {
                nombre = nombres;
            } else if (razonSocial != null && !razonSocial.isEmpty()) {
                nombre = razonSocial;
            }
            // Asignar los datos del cliente a cada control del cardview
            nombreTextView.setText(nombre.isEmpty() ? "Nombre no disponible" : nombre);
            direccionTextView.setText(cliente.getDireccion());
            telefonoTextView.setText(cliente.getTelefono());
            emailTextView.setText(cliente.getEmail());
            String estado = cliente.getEstado();
            String estadoMostrado;

            switch (estado) {
                case "A":
                    estadoMostrado = "Alta";
                    break;
                case "B":
                    estadoMostrado = "Baja";
                    break;
                case "R":
                    estadoMostrado = "Rechazado";
                    break;
                default:
                    estadoMostrado = "Estado desconocido";
                    break;
            }

            estadoTextView.setText(estadoMostrado);
        }


        @Override
        public void onCreateContextMenu(final ContextMenu contextMenu, final View view, final ContextMenu.ContextMenuInfo contextMenuInfo) {
            //Crear las opciones del menú. Se visualizará cuando el usuario haga un clic prolongado sobre el cardview
            contextMenu.setHeaderTitle("Elija un estado");
            contextMenu.add(0, 1, 0, "ALTA");
            contextMenu.add(0, 2, 0, "BAJA");
            contextMenu.add(0, 3, 0, "RECHAZADO");
        }

        @Override
        public boolean onLongClick(final View view) {
            posicionItemSeleccionado = this.getAdapterPosition();
            Log.e("posicionItemSeleccionado", String.valueOf(posicionItemSeleccionado));
            return false;
        }


    }






}
