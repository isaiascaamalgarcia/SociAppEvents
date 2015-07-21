package eduardo.com.example.acer.sociappevents;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import eduardo.com.example.acer.sociappevents.Rest.EventsData;

/**
 * Created by ACER on 12/07/2015.
 */
public class AdapterDashboard extends RecyclerView.Adapter<AdapterDashboard.viewHolderMainActivity> {
    private ArrayList<EventsData> listaEventos = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public AdapterDashboard(Context context) {

        layoutInflater = LayoutInflater.from(context);

    }

    public void setListaEventos(ArrayList<EventsData> listaeventos) {
        this.listaEventos = listaeventos;
        notifyItemRangeChanged(0, listaeventos.size());

    }

    @Override
    public viewHolderMainActivity onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.costum_rowdata, parent, false);
        viewHolderMainActivity viewHolder = new viewHolderMainActivity(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(viewHolderMainActivity holder, int position) {
        EventsData currentEvent = listaEventos.get(position);
        holder.nameEvent.setText(currentEvent.getName());
        holder.dateEvent.setText(currentEvent.getDay());
        holder.num_invitados.setText(currentEvent.getDescription());
    }

    @Override
    public int getItemCount() {
        return listaEventos.size();
    }

    static class viewHolderMainActivity extends RecyclerView.ViewHolder {

        private TextView nameEvent;
        private TextView dateEvent;
        private TextView num_invitados;
        //  private TextView num_fotos;
        //  private TextView tipo_invitado;

        public viewHolderMainActivity(View itemView) {
            super(itemView);

            nameEvent = (TextView) itemView.findViewById(R.id.nombre_evento);
            dateEvent = (TextView) itemView.findViewById(R.id.fecha_evento);
            num_invitados = (TextView) itemView.findViewById(R.id.num_invitados);
            // num_fotos = (TextView)itemView.findViewById(R.id.num_fotos);
            // tipo_invitado = (TextView)itemView.findViewById(R.id.tipo_invitado);
        }
    }
}
