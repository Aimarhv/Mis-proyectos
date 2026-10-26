using Microsoft.Extensions.DependencyInjection;

namespace I01._01_Gorputz_masaren_indizea
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