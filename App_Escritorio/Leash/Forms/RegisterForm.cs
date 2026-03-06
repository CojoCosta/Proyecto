using Leash.Api;
using Leash.Models;
using System;
using System.Drawing;
using System.Windows.Forms;

namespace Leash.Forms
{
    public class RegisterForm : Form
    {
        private readonly ApiClient _apiClient;
        private TextBox _txtNombre;
        private TextBox _txtApellidos;
        private TextBox _txtUsuario;
        private TextBox _txtEmail;
        private TextBox _txtPassword;
        private DateTimePicker _dtpNacimiento;
        private Label _lblEstado;

        public RegisterForm(ApiClient apiClient)
        {
            _apiClient = apiClient;
            InitializeUi();
        }

        private void InitializeUi()
        {
            Text = "Registro de usuario";
            StartPosition = FormStartPosition.CenterParent;
            ClientSize = new Size(460, 360);
            FormBorderStyle = FormBorderStyle.FixedDialog;
            MaximizeBox = false;

            var top = 25;
            _txtNombre = AddField("Nombre", ref top);
            _txtApellidos = AddField("Apellidos", ref top);
            _txtUsuario = AddField("Usuario", ref top);
            _txtEmail = AddField("Email", ref top);
            _txtPassword = AddField("Password", ref top, true);

            Controls.Add(new Label { Text = "Fecha nacimiento", Location = new Point(30, top + 5), AutoSize = true });
            _dtpNacimiento = new DateTimePicker
            {
                Location = new Point(150, top),
                Width = 250,
                Format = DateTimePickerFormat.Short
            };
            Controls.Add(_dtpNacimiento);

            var btnCrear = new Button { Text = "Crear cuenta", Width = 120, Location = new Point(280, top + 45) };
            var btnCancelar = new Button { Text = "Cancelar", Width = 100, Location = new Point(170, top + 45) };
            _lblEstado = new Label { Location = new Point(30, top + 90), AutoSize = true, ForeColor = Color.Firebrick };

            btnCrear.Click += async (s, e) => await RegistrarAsync();
            btnCancelar.Click += (s, e) => Close();

            Controls.Add(btnCrear);
            Controls.Add(btnCancelar);
            Controls.Add(_lblEstado);
        }

        private TextBox AddField(string label, ref int top, bool password = false)
        {
            Controls.Add(new Label { Text = label, Location = new Point(30, top + 5), AutoSize = true });
            var box = new TextBox { Location = new Point(150, top), Width = 250, UseSystemPasswordChar = password };
            Controls.Add(box);
            top += 40;
            return box;
        }

        private async System.Threading.Tasks.Task RegistrarAsync()
        {
            _lblEstado.Text = string.Empty;
            var usuario = new Usuario
            {
                Nombre = _txtNombre.Text.Trim(),
                Apellidos = _txtApellidos.Text.Trim(),
                NombreUsuario = _txtUsuario.Text.Trim(),
                Email = _txtEmail.Text.Trim(),
                Password = _txtPassword.Text,
                FechaNacimiento = _dtpNacimiento.Value.ToString("yyyy-MM-dd")
            };

            if (string.IsNullOrWhiteSpace(usuario.NombreUsuario) || string.IsNullOrWhiteSpace(usuario.Password))
            {
                _lblEstado.Text = "Usuario y password son obligatorios.";
                return;
            }

            try
            {
                var ok = await _apiClient.RegistrarUsuarioAsync(usuario);
                if (ok)
                {
                    MessageBox.Show("Usuario registrado correctamente.", "Registro", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    Close();
                }
                else
                {
                    _lblEstado.Text = "No se pudo registrar el usuario.";
                }
            }
            catch (Exception ex)
            {
                _lblEstado.Text = "Error: " + ex.Message;
            }
        }
    }
}
