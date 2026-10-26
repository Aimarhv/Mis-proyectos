using Microsoft.Extensions.DependencyInjection;

namespace I01._10_Zenbaki_zozketa__Primitiva_
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