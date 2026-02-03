package clases;

import java.sql.Date;

public class Publicacion {
    private int id_publicacion;
    private int id_usuario;
    private BLob foto_publicacion;
    private Date fecha_publicacion;
    private int num_likes;

    @path("/publicacion")
    //#region SET Y GET
    public void setId_publicacion(int id_publicacion) {
        this.id_publicacion = id_publicacion;
    }
    public int getId_publicacion() {
        return id_publicacion;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    public int getId_usuario() {
        return id_usuario;
    }

    public void setFoto_publicacion(BLob foto_publicacion) {
        this.foto_publicacion = foto_publicacion;
    }
    public BLob getFoto_publicacion() {
        return foto_publicacion;
    }

    public void setFecha_publicacion(Date fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }
    public Date getFecha_publicacion() {
        return fecha_publicacion;
    }
    
    public void setNum_likes(int num_likes) {
        this.num_likes = num_likes;
    }
    public int getNum_likes() {
        return num_likes;
    }
    //#endregion

    //#region CONSTRUCTORES
    public Publicacion(){ }
    public Publicacion(int id_usuario, Blob foto_publicacion, Date fecha_publicacion, int num_likes){
        this.id_usuario = id_usuario;
        this.foto_publicacion = foto_publicacion;
        this.fecha_publicacion = fecha_publicacion;
        this.num_likes = num_likes;        
    }
//#endregion
    
}
