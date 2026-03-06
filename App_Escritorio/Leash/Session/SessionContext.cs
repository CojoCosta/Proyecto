using Leash.Models;

namespace Leash.Session
{
    public static class SessionContext
    {
        public static Usuario UsuarioActual { get; set; }

        public static bool IsLoggedIn
        {
            get { return UsuarioActual != null; }
        }

        public static void Logout()
        {
            UsuarioActual = null;
        }
    }
}
