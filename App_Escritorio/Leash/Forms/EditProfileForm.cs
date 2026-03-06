using Leash.Api;
using Leash.Models;
using Leash.Session;
using System;
using System.Drawing;
using System.Windows.Forms;

namespace Leash.Forms
{
    public class EditProfileForm : Form
    {
        private readonly ApiClient _apiClient;
        private TextBox _txtNombre;
        private TextBox _txtApellidos;
        private TextBox _txtEmail;
        private TextBox _txtPassword;
        private Label _lblEstado;

        public EditProfileForm(ApiClient apiClient)
        {
            _apiClient = apiClient;
            Text = "Editar perfil";
            StartPosition = FormStartPosition.CenterParent;
            ClientSize = new Size(460, 300);
            FormBorderStyle = FormBorderStyle.FixedDialog;
            MaximizeBox = false;
            BuildUi();
        }

        private void BuildUi()
        {
            var top = 30;
            _txtNombre = AddField("Nombre", ref top);
            _txtApellidos = AddField("Apellidos", ref top);
            _txtEmail = AddField("Email", ref top);
            _txtPassword = AddField("Password", ref top, true);

            var btnGuardar = new Button
            {
                Text = "Guardar cambios",
                Location = new Point(270, 205),
                Width = 130
            };
            var btnCancelar = new Button
            {
                Text = "Cancelar",
                Location = new Point(160, 205),
                Width = 100
            };
            _lblEstado = new Label
            {
                Location = new Point(30, 250),
                AutoSize = true,
                ForeColor = Color.Firebrick
            };

            btnGuardar.Click += async (s, e) => await GuardarAsync();
            btnCancelar.Click += (s, e) => Close();

            Controls.Add(btnGuardar);
            Controls.Add(btnCancelar);
            Controls.Add(_lblEstado);

            Shown += async (s, e) => await CargarPerfilAsync();
        }

        private TextBox AddField(string label, ref int top, bool password = false)
        {
            Controls.Add(new Label { Text = label, Location = new Point(30, top + 5), AutoSize = true });
            var box = new TextBox { Location = new Point(130, top), Width = 270, UseSystemPasswordChar = password };
            Controls.Add(box);
            top += 40;
            return box;
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
                _txtNombre.Text = perfil.Nombre;
                _txtApellidos.Text = perfil.Apellidos;
                _txtEmail.Text = perfil.Email;
                _txtPassword.Text = perfil.Password;
            }
            catch (Exception ex)
            {
                _lblEstado.Text = "Error: " + ex.Message;
            }
        }

        private async System.Threading.Tasks.Task GuardarAsync()
        {
            var actual = SessionContext.UsuarioActual;
            if (actual == null)
            {
                _lblEstado.Text = "No hay usuario en sesion.";
                return;
            }

            var actualizado = new Usuario
            {
                Nombre = _txtNombre.Text.Trim(),
                Apellidos = _txtApellidos.Text.Trim(),
                Email = _txtEmail.Text.Trim(),
                Password = _txtPassword.Text
            };

            try
            {
                var ok = await _apiClient.ActualizarUsuarioAsync(actual.NombreUsuario, actualizado);
                if (!ok)
                {
                    _lblEstado.Text = "No se pudo actualizar el perfil.";
                    return;
                }

                actual.Nombre = actualizado.Nombre;
                actual.Apellidos = actualizado.Apellidos;
                actual.Email = actualizado.Email;
                actual.Password = actualizado.Password;
                DialogResult = DialogResult.OK;
                Close();
            }
            catch (Exception ex)
            {
                _lblEstado.Text = "Error: " + ex.Message;
            }
        }
    }
}
