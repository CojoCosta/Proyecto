using Leash.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;

namespace Leash.Api
{
    public class ApiClient : IDisposable
    {
        private readonly HttpClient _httpClient;
        private readonly JavaScriptSerializer _json = new JavaScriptSerializer();

        public ApiClient(string baseUrl)
        {
            _httpClient = new HttpClient
            {
                BaseAddress = new Uri(baseUrl.TrimEnd('/') + "/")
            };
        }

        public async Task<Usuario> IniciarSesionAsync(string nombreUsuario, string password)
        {
            var endpoint = "usuarios/inicioSesion";
            var body = new
            {
                nombreUsuario = nombreUsuario,
                password = password
            };

            HttpResponseMessage response;
            using (var request = new HttpRequestMessage(HttpMethod.Post, endpoint))
            {
                request.Headers.Accept.ParseAdd("application/json");
                request.Content = BuildJsonContent(body);
                response = await _httpClient.SendAsync(request);
            }

            if (response.StatusCode == HttpStatusCode.NotFound || response.StatusCode == HttpStatusCode.MethodNotAllowed)
            {
                var legacyEndpoint = string.Format(
                    "usuarios/inicioSesion/{0}/{1}",
                    Uri.EscapeDataString(nombreUsuario),
                    Uri.EscapeDataString(password));
                response = await _httpClient.PostAsync(legacyEndpoint, new StringContent(string.Empty));
            }

            if (!response.IsSuccessStatusCode)
            {
                return await AutenticarConListaUsuariosAsync(nombreUsuario, password);
            }

            var payload = await response.Content.ReadAsStringAsync();
            if (string.IsNullOrWhiteSpace(payload))
            {
                return null;
            }
            var dict = _json.Deserialize<Dictionary<string, object>>(payload);
            if (dict == null)
            {
                return await AutenticarConListaUsuariosAsync(nombreUsuario, password);
            }
            return ParseUsuario(dict);
        }

        private async Task<Usuario> AutenticarConListaUsuariosAsync(string nombreUsuario, string password)
        {
            try
            {
                var response = await _httpClient.GetAsync("usuarios");
                if (!response.IsSuccessStatusCode)
                {
                    return null;
                }

                var payload = await response.Content.ReadAsStringAsync();
                var raw = DeserializeObjectList(payload);
                if (raw == null)
                {
                    return null;
                }

                foreach (var item in raw)
                {
                    var dict = item as Dictionary<string, object>;
                    if (dict == null)
                    {
                        continue;
                    }

                    var usuarioLista = ParseUsuario(dict);
                    if (string.Equals(usuarioLista.NombreUsuario, nombreUsuario, StringComparison.OrdinalIgnoreCase)
                        && string.Equals(usuarioLista.Password, password, StringComparison.Ordinal))
                    {
                        return usuarioLista;
                    }
                }

                return null;
            }
            catch
            {
                return null;
            }
        }

        public async Task<bool> RegistrarUsuarioAsync(Usuario usuario)
        {
            var body = new
            {
                nombreUsuario = usuario.NombreUsuario,
                nombre = usuario.Nombre,
                apellidos = usuario.Apellidos,
                email = usuario.Email,
                fechaNacimiento = usuario.FechaNacimiento,
                password = usuario.Password
            };

            var content = BuildJsonContent(body);
            var response = await _httpClient.PostAsync("usuarios", content);
            return response.IsSuccessStatusCode;
        }

        public async Task<Usuario> ObtenerPerfilAsync(string nombreUsuario)
        {
            var endpoint = "usuarios/perfil/" + Uri.EscapeDataString(nombreUsuario);
            var response = await _httpClient.GetAsync(endpoint);
            if (!response.IsSuccessStatusCode)
            {
                return null;
            }

            var payload = await response.Content.ReadAsStringAsync();
            var dict = _json.Deserialize<Dictionary<string, object>>(payload);
            return ParseUsuario(dict);
        }

        public async Task<bool> ActualizarUsuarioAsync(string nombreUsuario, Usuario usuario)
        {
            var endpoint = "usuarios/modificar/" + Uri.EscapeDataString(nombreUsuario);
            var body = new
            {
                nombre = usuario.Nombre,
                apellidos = usuario.Apellidos,
                email = usuario.Email,
                password = usuario.Password
            };

            var content = BuildJsonContent(body);
            var response = await _httpClient.PutAsync(endpoint, content);
            return response.IsSuccessStatusCode;
        }

        public async Task<List<Publicacion>> ObtenerPublicacionesAsync()
        {
            var response = await _httpClient.GetAsync("publicaciones");
            if (!response.IsSuccessStatusCode)
            {
                return new List<Publicacion>();
            }

            var payload = await response.Content.ReadAsStringAsync();
            var raw = DeserializeObjectList(payload);
            var publicaciones = new List<Publicacion>();
            if (raw == null)
            {
                return publicaciones;
            }

            foreach (var item in raw)
            {
                var dict = item as Dictionary<string, object>;
                if (dict != null)
                {
                    publicaciones.Add(ParsePublicacion(dict));
                }
            }

            return publicaciones;
        }

        public async Task<bool> CrearPublicacionAsync(int idUsuario, string nombreUsuario, string contenido)
        {
            if (idUsuario <= 0 && !string.IsNullOrWhiteSpace(nombreUsuario))
            {
                idUsuario = await ResolverIdUsuarioPorNombreAsync(nombreUsuario);
            }
            if (idUsuario <= 0)
            {
                idUsuario = await ResolverIdUsuarioFallbackAsync();
            }
            if (idUsuario <= 0)
            {
                return false;
            }

            var body = new
            {
                id_usuario = idUsuario,
                nombre_usuario = nombreUsuario,
                contenido = contenido
            };

            var content = BuildJsonContent(body);
            var response = await _httpClient.PostAsync("publicaciones", content);
            return response.IsSuccessStatusCode;
        }

        private async Task<int> ResolverIdUsuarioPorNombreAsync(string nombreUsuario)
        {
            try
            {
                var response = await _httpClient.GetAsync("usuarios");
                if (!response.IsSuccessStatusCode)
                {
                    return 0;
                }

                var payload = await response.Content.ReadAsStringAsync();
                var raw = DeserializeObjectList(payload);
                if (raw == null)
                {
                    return 0;
                }

                foreach (var item in raw)
                {
                    var usuario = ParseUsuario(item);
                    if (string.Equals(usuario.NombreUsuario, nombreUsuario, StringComparison.OrdinalIgnoreCase))
                    {
                        return usuario.IdUsuario;
                    }
                }

                return 0;
            }
            catch
            {
                return 0;
            }
        }

        private async Task<int> ResolverIdUsuarioFallbackAsync()
        {
            try
            {
                var response = await _httpClient.GetAsync("publicaciones");
                if (response.IsSuccessStatusCode)
                {
                    var payload = await response.Content.ReadAsStringAsync();
                    var raw = DeserializeObjectList(payload);
                    if (raw != null)
                    {
                        foreach (var item in raw)
                        {
                            var post = ParsePublicacion(item);
                            if (post.IdUsuario > 0)
                            {
                                return post.IdUsuario;
                            }
                        }
                    }
                }
            }
            catch
            {
                // ignore
            }

            // Ultimo fallback para APIs antiguas que no exponen id de usuario.
            return 1;
        }

        private IEnumerable<Dictionary<string, object>> DeserializeObjectList(string payload)
        {
            if (string.IsNullOrWhiteSpace(payload))
            {
                return null;
            }

            try
            {
                var list = _json.Deserialize<List<Dictionary<string, object>>>(payload);
                if (list != null)
                {
                    return list;
                }
            }
            catch
            {
                // Fallback below.
            }

            try
            {
                var raw = _json.DeserializeObject(payload) as object[];
                if (raw == null)
                {
                    return null;
                }

                return raw.OfType<Dictionary<string, object>>().ToList();
            }
            catch
            {
                return null;
            }
        }

        private HttpContent BuildJsonContent(object body)
        {
            return new StringContent(_json.Serialize(body), Encoding.UTF8, "application/json");
        }

        private static Usuario ParseUsuario(Dictionary<string, object> dict)
        {
            return new Usuario
            {
                IdUsuario = GetInt(dict, "id_usuario", "idUsuario", "id"),
                NombreUsuario = GetString(dict, "nombre_usuario", "nombreUsuario"),
                Nombre = GetString(dict, "nombre"),
                Apellidos = GetString(dict, "apellidos"),
                Email = GetString(dict, "email"),
                FechaNacimiento = GetString(dict, "fecha_nacimiento", "fechaNacimiento"),
                Password = GetString(dict, "password")
            };
        }

        private static Publicacion ParsePublicacion(Dictionary<string, object> dict)
        {
            return new Publicacion
            {
                IdPublicacion = GetInt(dict, "id_publicacion", "idPublicacion"),
                IdUsuario = GetInt(dict, "id_usuario", "idUsuario"),
                NombreUsuario = GetString(dict, "nombre_usuario", "nombreUsuario"),
                Contenido = GetString(dict, "contenido"),
                FechaPublicacion = GetString(dict, "fecha_publicacion", "fechaPublicacion")
            };
        }

        private static string GetString(IDictionary<string, object> dict, params string[] keys)
        {
            foreach (var key in keys)
            {
                object value;
                if (dict.TryGetValue(key, out value) && value != null)
                {
                    return value.ToString();
                }
            }

            return string.Empty;
        }

        private static int GetInt(IDictionary<string, object> dict, params string[] keys)
        {
            foreach (var key in keys)
            {
                object value;
                if (dict.TryGetValue(key, out value) && value != null)
                {
                    int parsedInt;
                    if (int.TryParse(value.ToString(), out parsedInt))
                    {
                        return parsedInt;
                    }
                }
            }

            return 0;
        }

        public void Dispose()
        {
            _httpClient.Dispose();
        }
    }
}
