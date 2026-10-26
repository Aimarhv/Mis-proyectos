using Microsoft.Extensions.DependencyInjection;

namespace Txandak_ausaz_osatzeko_aplikazioa
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