namespace Leash.Models
{
    public class Publicacion
    {
        public int IdPublicacion { get; set; }
        public int IdUsuario { get; set; }
        public string NombreUsuario { get; set; }
        public string Contenido { get; set; }
        public string FechaPublicacion { get; set; }
    }
}
