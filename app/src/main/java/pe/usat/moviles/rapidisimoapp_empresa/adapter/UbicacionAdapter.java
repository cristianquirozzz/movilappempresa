package pe.usat.moviles.rapidisimoapp_empresa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pe.usat.moviles.rapidisimoapp_empresa.R;
import pe.usat.moviles.rapidisimoapp_empresa.model.Cliente;
import pe.usat.moviles.rapidisimoapp_empresa.model.Ubicacion;

public class UbicacionAdapter extends RecyclerView.Adapter<UbicacionAdapter.SolicitudViewHolder>{

    private List<Ubicacion> solicitudUbicacion;
    private boolean isEditable;
    public int posicionItemSeleccionado;

    public UbicacionAdapter(final List<Ubicacion> solicitudUbicacion, boolean isEditable) {
        this.solicitudUbicacion = solicitudUbicacion;
        this.isEditable = isEditable;
    }

    @NonNull
    @Override
    public SolicitudViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        //Vincular el adapter a la vista (cardview Solicitud). Archivo: Solicitud_cardview.xml
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_ubicacion, parent, false);
        return new SolicitudViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SolicitudViewHolder holder, final int position) {
        //Sirve para imprimir los datos en el cardview
        final Ubicacion ubicacion = solicitudUbicacion.get(position);
        holder.bind(ubicacion);
    }

    @Override
    public int getItemCount() {
        //Determina la cantidad de registros a asignar al recyclerView
        return solicitudUbicacion.size();
    }

    public class SolicitudViewHolder extends RecyclerView.ViewHolder{
        //Declarar los controles que tiene el cardview de Solicitud
        private TextView txtConductor,txtPlaca,txtEstado,txtLatitud,txtLongitud;

        public SolicitudViewHolder(@NonNull final View itemView) {
            super(itemView);
            //Relacionar los controles del cardview con los controles declarados en java
            txtConductor = itemView.findViewById(R.id.txtConductor);
            txtPlaca = itemView.findViewById(R.id.txtPlaca);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            txtLatitud = itemView.findViewById(R.id.txtLatitud);
            txtLongitud = itemView.findViewById(R.id.txtLongitud);

            
        }

        public void bind(final Ubicacion ubicacion){
            //Asignar los datos del Solicitud a cada control del cardview
            txtConductor
                    .setText(ubicacion.getConductor());
            txtPlaca
                    .setText(ubicacion.getMatricula());
            txtEstado
                    .setText(ubicacion.getNombreEstado());
            txtLatitud
                    .setText(String.valueOf(ubicacion.getLatitud()));
            txtLongitud
                    .setText(String.valueOf(ubicacion.getLongitud()));

        }

    }

}
