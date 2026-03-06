using Leash.Api;
using Leash.Models;
using Leash.Session;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Windows.Forms;

namespace Leash.Forms
{
    public class WallForm : Form
    {
        private readonly ApiClient _apiClient;
        private readonly ListBox _lstPublicaciones;
        private readonly Label _lblEstado;

        public WallForm(ApiClient apiClient)
        {
            _apiClient = apiClient;
            Text = "Leash - Muro";
            StartPosition = FormStartPosition.CenterScreen;
            ClientSize = new Size(760, 540);

            var usuario = SessionContext.UsuarioActual;
            var lblBienvenida = new Label
            {
                Text = "Hola, " + (usuario == null ? "invitado" : usuario.NombreUsuario),
                Font = new Font("Segoe UI", 11F, FontStyle.Bold),
                Location = new Point(20, 20),
                AutoSize = true
            };

            var btnRecargar = new Button { Text = "Recargar", Location = new Point(20, 55), Width = 90 };
            var btnPublicar = new Button { Text = "Nueva publicacion", Location = new Point(120, 55), Width = 140 };
            var btnPerfil = new Button { Text = "Perfil", Location = new Point(270, 55), Width = 90 };
            var btnCerrarSesion = new Button { Text = "Cerrar sesion", Location = new Point(620, 20), Width = 120 };

            _lstPublicaciones = new ListBox
            {
                Location = new Point(20, 95),
                Size = new Size(720, 390),
                HorizontalScrollbar = true
            };
            _lblEstado = new Label { Location = new Point(20, 500), AutoSize = true, ForeColor = Color.Firebrick };

            btnRecargar.Click += async (s, e) => await CargarPublicacionesAsync();
            btnPublicar.Click += async (s, e) =>
            {
                using (var form = new NewPostForm(_apiClient))
                {
                    if (form.ShowDialog(this) == DialogResult.OK)
                    {
                        await CargarPublicacionesAsync();
                    }
                }
            };
            btnPerfil.Click += async (s, e) =>
            {
                using (var form = new ProfileForm(_apiClient))
                {
                    form.ShowDialog(this);
                }
                await CargarPublicacionesAsync();
            };
            btnCerrarSesion.Click += (s, e) =>
            {
                SessionContext.Logout();
                Close();
            };

            Controls.Add(lblBienvenida);
            Controls.Add(btnRecargar);
            Controls.Add(btnPublicar);
            Controls.Add(btnPerfil);
            Controls.Add(btnCerrarSesion);
            Controls.Add(_lstPublicaciones);
            Controls.Add(_lblEstado);

            Shown += async (s, e) => await CargarPublicacionesAsync();
        }

        private async System.Threading.Tasks.Task CargarPublicacionesAsync()
        {
            _lblEstado.Text = "Cargando publicaciones...";
            _lstPublicaciones.Items.Clear();

            try
            {
                List<Publicacion> posts = await _apiClient.ObtenerPublicacionesAsync();
                foreach (var post in posts.OrderByDescending(p => p.IdPublicacion))
                {
                    var encabezado = string.Format("[{0}] @{1}", post.FechaPublicacion, post.NombreUsuario);
                    _lstPublicaciones.Items.Add(encabezado);
                    _lstPublicaciones.Items.Add(post.Contenido);
                    _lstPublicaciones.Items.Add(new string('-', 90));
                }

                _lblEstado.Text = posts.Count == 0 ? "No hay publicaciones." : string.Empty;
            }
            catch (Exception ex)
            {
                _lblEstado.Text = "Error cargando muro: " + ex.Message;
            }
        }
    }
}
