namespace I01._11_Harria__papera__guraize;

public partial class App : Application
{
    public App()
    {
        InitializeComponent();
    }

    protected override Window CreateWindow(IActivationState? activationState)
    {
        return new Window(new MainPage())
        {
            Width = 1000,
            Height = 750,
            MinimumWidth = 1000,
            MaximumWidth = 1000,
            MinimumHeight = 750,
            MaximumHeight = 750
        };
    }
}