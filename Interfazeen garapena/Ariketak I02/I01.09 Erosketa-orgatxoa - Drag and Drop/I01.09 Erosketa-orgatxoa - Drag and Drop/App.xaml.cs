using Microsoft.Extensions.DependencyInjection;

namespace I01._09_Erosketa_orgatxoa___Drag_and_Drop
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