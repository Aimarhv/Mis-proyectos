using UnityEngine;
using UnityEngine.SceneManagement;

public class Botondejugar : MonoBehaviour
{
    public void Jugar()
    {
        GameStatus status = FindFirstObjectByType<GameStatus>();
        if (status != null)
        {
            status.vidas = 3;
            status.puntos = 0;
            status.nivelActual = 1;
        }

        Time.timeScale = 1;
        Debug.Log("Botón Jugar pulsado");
        SceneManager.LoadScene("Nivel1");
    }

    public void Salir()
    {
        Debug.Log("Saliendo del juego");
        Application.Quit();

#if UNITY_EDITOR
        UnityEditor.EditorApplication.isPlaying = false;
#endif
    }
}