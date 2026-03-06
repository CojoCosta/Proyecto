using Leash.Api;
using Leash.Session;
using System;
using System.Configuration;
using System.Drawing;
using System.Windows.Forms;

namespace Leash.Forms
{
    public class LoginForm : Form
    {
        private readonly ApiClient _apiClient;
        private TextBox _txtUsuario;
        private TextBox _txtPassword;
        private Button _btnLogin;
        private Button _btnRegistro;
        private Label _lblEstado;

        public LoginForm()
        {
            var apiBaseUrl = ConfigurationManager.AppSettings["ApiBaseUrl"] ?? "http://localhost:8080/apirest/rest";
            _apiClient = new ApiClient(apiBaseUrl);
            InitializeUi();
        }

        private void InitializeUi()
        {
            Text = "Leash - Inicio de sesion";
            StartPosition = FormStartPosition.CenterScreen;
            ClientSize = new Size(420, 270);
            FormBorderStyle = FormBorderStyle.FixedDialog;
            MaximizeBox = false;

            var lblTitulo = new Label
            {
                Text = "Leash",
                Font = new Font("Segoe UI", 18F, FontStyle.Bold),
                Location = new Point(165, 20),
                AutoSize = true
            };

            var lblUsuario = new Label { Text = "Usuario", Location = new Point(40, 80), AutoSize = true };
            var lblPassword = new Label { Text = "Password", Location = new Point(40, 125), AutoSize = true };

            _txtUsuario = new TextBox { Location = new Point(130, 77), Width = 240 };
            _txtPassword = new TextBox { Location = new Point(130, 122), Width = 240, UseSystemPasswordChar = true };

            _btnLogin = new Button { Text = "Entrar", Location = new Point(130, 170), Width = 110 };
            _btnRegistro = new Button { Text = "Registrarse", Location = new Point(260, 170), Width = 110 };
            _lblEstado = new Label { ForeColor = Color.Firebrick, Location = new Point(40, 215), AutoSize = true };

            _btnLogin.Click += async (s, e) => await LoginAsync();
            _btnRegistro.Click += (s, e) =>
            {
                using (var registro = new RegisterForm(_apiClient))
                {
                    registro.ShowDialog(this);
                }
            };

            Controls.Add(lblTitulo);
            Controls.Add(lblUsuario);
            Controls.Add(lblPassword);
            Controls.Add(_txtUsuario);
            Controls.Add(_txtPassword);
            Controls.Add(_btnLogin);
            Controls.Add(_btnRegistro);
            Controls.Add(_lblEstado);
        }

        private async System.Threading.Tasks.Task LoginAsync()
        {
            _lblEstado.Text = string.Empty;
            _btnLogin.Enabled = false;

            try
            {
                var usuario = await _apiClient.IniciarSesionAsync(_txtUsuario.Text.Trim(), _txtPassword.Text);
                if (usuario == null)
                {
                    _lblEstado.Text = "Usuario o contraseña incorrectos o API no disponible";
                    return;
                }

                SessionContext.UsuarioActual = usuario;

                var muro = new WallForm(_apiClient);
                muro.FormClosed += (s, e) =>
                {
                    if (!IsDisposed)
                    {
                        _txtPassword.Text = string.Empty;
                        _lblEstado.Text = string.Empty;
                        Show();
                    }
                };

                Hide();
                muro.Show();
            }
            catch (Exception ex)
            {
                _lblEstado.Text = "Error: " + ex.Message;
            }
            finally
            {
                _btnLogin.Enabled = true;
            }
        }

        private void InitializeComponent()
        {
            this.SuspendLayout();
            // 
            // LoginForm
            // 
            this.ClientSize = new System.Drawing.Size(521, 395);
            this.Name = "LoginForm";
            this.ResumeLayout(false);

        }
    }
}
