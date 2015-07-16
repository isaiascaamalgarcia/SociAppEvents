package eduardo.com.example.acer.sociappevents.Rest;

/**
 * Created by ACER on 12/07/2015.
 */
public class EventsData {

    private String nombre;
    private String fecha;
    private int num_invitados;
    private int num_fotos;
    private String tipo_invitado;

    public EventsData() {

    }

    public EventsData(String nombre, String fecha, int numero_invitados, int numero_fotos,String tipo_invitado){
        this.nombre = nombre;
        this.fecha = fecha;
        this.num_invitados = numero_invitados;
        this.num_fotos = numero_fotos;
        this.tipo_invitado = tipo_invitado;
    }

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getFecha() {return fecha;}

    public void setFecha(String fecha) {this.fecha = fecha;}

    public int getNum_invitados() {return num_invitados;}

    public void setNum_invitados(int num_invitados) {this.num_invitados = num_invitados;}

    public int getNum_fotos() {return num_fotos;}

    public void setNum_fotos(int num_fotos) {this.num_fotos = num_fotos;}

    public String getTipo_invitado() {return tipo_invitado;}

    public void setTipo_invitado(String tipo_invitado) {this.tipo_invitado = tipo_invitado;}

    @Override
    public String toString(){
        return "nombre"+nombre+
                "fecha"+fecha+
                "num_invitados"+num_invitados+
                "num_fotos"+num_fotos+
                "tipo_invitado"+tipo_invitado;
    }
}
