using Microsoft.Extensions.DependencyInjection;

namespace I01._13_Ahateari_tiro_egitea
{
    public partial class App : Application
    {
        public App()
        {
            InitializeComponent();
        }

        protected override Window CreateWindow(IActivationState? activationState)
        {
            return new Window(new AppShell());
        }
    }
}