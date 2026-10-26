using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class MenuPrincipal : MonoBehaviour
{
    [Header("Botones")]
    public Button botonJolastu;
    public Button botonIrten;

    void Start()
    {
        if (botonJolastu != null) botonJolastu.onClick.AddListener(Jolastu);
        if (botonIrten != null) botonIrten.onClick.AddListener(Irten);
    }

    public void Jolastu()
    {
        int siguiente = SceneManager.GetActiveScene().buildIndex + 1;

        if (siguiente < SceneManager.sceneCountInBuildSettings)
            SceneManager.LoadScene(siguiente);
        else
            Debug.LogError("No hay otra escena en Build Profiles. Añade la escena del juego.");
    }

    public void Irten()
    {
#if UNITY_EDITOR
        UnityEditor.EditorApplication.isPlaying = false;
#else
        Application.Quit();
#endif
    }
}