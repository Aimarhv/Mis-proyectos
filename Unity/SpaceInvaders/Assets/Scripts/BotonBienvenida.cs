using UnityEngine;
using UnityEngine.SceneManagement;

public class BotonBienvenida : MonoBehaviour
{
    [SerializeField] string escenaJuego = "Nivel1";

    public void Jolastu()
    {
        SceneManager.LoadScene(escenaJuego);
    }

    public void SalirDelJuego()
    {
#if UNITY_EDITOR
        UnityEditor.EditorApplication.isPlaying = false;
#else
        Application.Quit();
#endif
    }
}