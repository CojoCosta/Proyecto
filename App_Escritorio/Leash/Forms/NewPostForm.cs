using Leash.Api;
using Leash.Session;
using System;
using System.Drawing;
using System.Windows.Forms;

namespace Leash.Forms
{
    public class NewPostForm : Form
    {
        private readonly ApiClient _apiClient;
        private readonly TextBox _txtContenido;
        private readonly Label _lblEstado;

        public NewPostForm(ApiClient apiClient)
        {
            _apiClient = apiClient;
            Text = "Nueva publicacion";
            StartPosition = FormStartPosition.CenterParent;
            ClientSize = new Size(520, 280);
            FormBorderStyle = FormBorderStyle.FixedDialog;
            MaximizeBox = false;

            Controls.Add(new Label { Text = "Escribe tu publicacion:", Location = new Point(20, 20), AutoSize = true });
            _txtContenido = new TextBox
            {
                Location = new Point(20, 45),
                Size = new Size(480, 150),
                Multiline = true,
                ScrollBars = ScrollBars.Vertical
            };
            _lblEstado = new Label { Location = new Point(20, 245), AutoSize = true, ForeColor = Color.Firebrick };

            var btnPublicar = new Button { Text = "Publicar", Location = new Point(410, 210), Width = 90 };
            var btnCancelar = new Button { Text = "Cancelar", Location = new Point(310, 210), Width = 90 };

            btnPublicar.Click += async (s, e) => await PublicarAsync();
            btnCancelar.Click += (s, e) => Close();

            Controls.Add(_txtContenido);
            Controls.Add(btnPublicar);
            Controls.Add(btnCancelar);
            Controls.Add(_lblEstado);
        }

        private async System.Threading.Tasks.Task PublicarAsync()
        {
            var usuario = SessionContext.UsuarioActual;
            if (usuario == null)
            {
                _lblEstado.Text = "Debes iniciar sesion.";
                return;
            }

            if (string.IsNullOrWhiteSpace(_txtContenido.Text))
            {
                _lblEstado.Text = "El contenido no puede ir vacio.";
                return;
            }

            try
            {
                var ok = await _apiClient.CrearPublicacionAsync(usuario.IdUsuario, usuario.NombreUsuario, _txtContenido.Text.Trim());
                if (ok)
                {
                    DialogResult = DialogResult.OK;
                    Close();
                }
                else
                {
                    _lblEstado.Text = "No se pudo crear la publicacion.";
                }
            }
            catch (Exception ex)
            {
                _lblEstado.Text = "Error: " + ex.Message;
            }
        }
    }
}
