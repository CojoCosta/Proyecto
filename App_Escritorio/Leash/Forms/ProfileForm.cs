using Leash.Api;
using Leash.Models;
using Leash.Session;
using System;
using System.Drawing;
using System.Windows.Forms;

namespace Leash.Forms
{
    public class ProfileForm : Form
    {
        private readonly ApiClient _apiClient;
        private readonly Label _lblUsuarioValue;
        private readonly Label _lblNombreValue;
        private readonly Label _lblApellidosValue;
        private readonly Label _lblEmailValue;
        private readonly Label _lblFechaValue;
        private readonly Label _lblEstado;

        public ProfileForm(ApiClient apiClient)
        {
            _apiClient = apiClient;
            Text = "Mi perfil";
            StartPosition = FormStartPosition.CenterParent;
            ClientSize = new Size(520, 360);
            FormBorderStyle = FormBorderStyle.FixedDialog;
            MaximizeBox = false;
            BackColor = Color.WhiteSmoke;

            var card = new Panel
            {
                Location = new Point(25, 25),
                Size = new Size(470, 250),
                BackColor = Color.White,
                BorderStyle = BorderStyle.FixedSingle
            };

            var lblTitle = new Label
            {
                Text = "Perfil de usuario",
                Font = new Font("Segoe UI", 14F, FontStyle.Bold),
                ForeColor = Color.FromArgb(30, 30, 30),
                Location = new Point(20, 15),
                AutoSize = true
            };

            _lblUsuarioValue = AddInfoRow(card, "Usuario", 55);
            _lblNombreValue = AddInfoRow(card, "Nombre", 90);
            _lblApellidosValue = AddInfoRow(card, "Apellidos", 125);
            _lblEmailValue = AddInfoRow(card, "Email", 160);
            _lblFechaValue = AddInfoRow(card, "Nacimiento", 195);
            card.Controls.Add(lblTitle);

            var btnEditar = new Button
            {
                Text = "Editar perfil",
                Location = new Point(280, 295),
                Width = 110,
                BackColor = Color.FromArgb(46, 125, 50),
                ForeColor = Color.White,
                FlatStyle = FlatStyle.Flat
            };
            btnEditar.FlatAppearance.BorderSize = 0;

            var btnCerrar = new Button
            {
                Text = "Cerrar",
                Location = new Point(400, 295),
                Width = 95
            };

            _lblEstado = new Label
            {
                Location = new Point(25, 302),
                AutoSize = true,
                ForeColor = Color.Firebrick
            };

            btnCerrar.Click += (s, e) => Close();
            btnEditar.Click += async (s, e) =>
            {
                using (var editForm = new EditProfileForm(_apiClient))
                {
                    if (editForm.ShowDialog(this) == DialogResult.OK)
                    {
                        await CargarPerfilAsync();
                        _lblEstado.ForeColor = Color.ForestGreen;
                        _lblEstado.Text = "Perfil actualizado.";
                    }
                }
            };

            Controls.Add(card);
            Controls.Add(btnEditar);
            Controls.Add(btnCerrar);
            Controls.Add(_lblEstado);

            Shown += async (s, e) => await CargarPerfilAsync();
        }

        private static Label AddInfoRow(Control parent, string caption, int top)
        {
            var lblCaption = new Label
            {
                Text = caption + ":",
                Location = new Point(20, top),
                Width = 120,
                Font = new Font("Segoe UI", 9F, FontStyle.Bold),
                ForeColor = Color.FromArgb(55, 71, 79)
            };
            parent.Controls.Add(lblCaption);

            var lblValue = new Label
            {
                Text = "-",
                Location = new Point(145, top),
                Width = 300,
                Font = new Font("Segoe UI", 9F, FontStyle.Regular),
                ForeColor = Color.FromArgb(33, 33, 33)
            };
            parent.Controls.Add(lblValue);
            return lblValue;
        }

        private async System.Threading.Tasks.Task CargarPerfilAsync()
        {
            var actual = SessionContext.UsuarioActual;
            if (actual == null)
            {
                _lblEstado.Text = "No hay usuario en sesion.";
                return;
            }

            try
            {
                var perfil = await _apiClient.ObtenerPerfilAsync(actual.NombreUsuario) ?? actual;
                SessionContext.UsuarioActual = perfil;
                PintarPerfil(perfil);
                _lblEstado.Text = string.Empty;
            }
            catch (Exception ex)
            {
                _lblEstado.ForeColor = Color.Firebrick;
                _lblEstado.Text = "Error: " + ex.Message;
            }
        }

        private void PintarPerfil(Usuario perfil)
        {
            _lblUsuarioValue.Text = Safe(perfil.NombreUsuario);
            _lblNombreValue.Text = Safe(perfil.Nombre);
            _lblApellidosValue.Text = Safe(perfil.Apellidos);
            _lblEmailValue.Text = Safe(perfil.Email);
            _lblFechaValue.Text = string.IsNullOrWhiteSpace(perfil.FechaNacimiento) ? "-" : perfil.FechaNacimiento;
        }

        private static string Safe(string value)
        {
            return string.IsNullOrWhiteSpace(value) ? "-" : value;
        }
    }
}
