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
import eduardo.com.example.acer.sociappevents.Rest.Photos;

/**
 * Created by ACER on 29/07/2015.
 */
public class AdapterPhotos extends RecyclerView.Adapter<AdapterPhotos.viewHolderPhotos>{

    private ArrayList<Photos> listaPhotos = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public AdapterPhotos(Context context) {

        layoutInflater = LayoutInflater.from(context);

    }

    public void setListaFotos(ArrayList<Photos> listafotos) {
        this.listaPhotos = listafotos;
        notifyItemRangeChanged(0, listafotos.size());

    }

    @Override
    public viewHolderPhotos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.costum_rowdata, parent, false);
        viewHolderPhotos viewHolder = new viewHolderPhotos(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(viewHolderPhotos holder, int position) {

        Photos currentPhoto = listaPhotos.get(position);
        holder.namePhoto.setText(currentPhoto.getTitle());
        holder.datePhoto.setText(currentPhoto.getDatePhoto());
        holder.photo.setImageBitmap(currentPhoto.getPhoto());
    }


    @Override
    public int getItemCount() {
        return listaPhotos.size();
    }

    static class viewHolderPhotos extends RecyclerView.ViewHolder {

        private TextView namePhoto;
        private TextView datePhoto;
        private ImageView photo;

        public viewHolderPhotos(View itemView) {
            super(itemView);

            namePhoto = (TextView) itemView.findViewById(R.id.nombre_foto);
            datePhoto = (TextView) itemView.findViewById(R.id.fecha_foto);
            photo = (ImageView) itemView.findViewById(R.id.photos);
        }
    }
}
