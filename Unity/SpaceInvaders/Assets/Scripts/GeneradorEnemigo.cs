using System.Collections;
using UnityEngine;

public class GeneradorEnemigos : MonoBehaviour
{
    [SerializeField] Transform prefabEnemigo;
    [SerializeField] float tiempoEntreOleadas = 7.5f;
    [SerializeField] int enemigosPorOleada = 4;
    [SerializeField] float separacionX = 1.5f;
    [SerializeField] Vector2 posicionInicial = new Vector2(-2.25f, 2f);

    [Header("Dificultad progresiva")]
    [SerializeField] int nivelMaximo = 10;
    [SerializeField] int enemigosExtraPorNivel = 1;
    [SerializeField] UnityEngine.UI.Text textoNivel;

    [Header("Velocidad de movimiento enemigo")]
    [SerializeField] float multiplicadorVelocidadPorNivel = 0.15f;
    [SerializeField] float multiplicadorVelocidadTope = 2.5f;

    [Header("Dificultad de disparo enemigo")]
    [SerializeField] float pausaMinimaInicial = 3f;
    [SerializeField] float pausaMaximaInicial = 7.5f;
    [SerializeField] float reduccionPausaPorNivel = 0.3f;
    [SerializeField] float pausaMinimaTope = 1f;
    [SerializeField] float pausaMaximaTope = 2.5f;

    private int nivelActual = 1;

    void Start()
    {
        ActualizarTextoNivel();
        StartCoroutine(GenerarOleadas());
    }

    IEnumerator GenerarOleadas()
    {
        while (true)
        {
            yield return new WaitForSeconds(tiempoEntreOleadas);

            int cantidadEnemigos = enemigosPorOleada + (enemigosExtraPorNivel * (nivelActual - 1));

            float multiplicadorVelocidad = Mathf.Min(multiplicadorVelocidadTope, 1f + multiplicadorVelocidadPorNivel * (nivelActual - 1));
            float pausaMinima = Mathf.Max(pausaMinimaTope, pausaMinimaInicial - reduccionPausaPorNivel * (nivelActual - 1));
            float pausaMaxima = Mathf.Max(pausaMaximaTope, pausaMaximaInicial - reduccionPausaPorNivel * (nivelActual - 1));

            for (int i = 0; i < cantidadEnemigos; i++)
            {
                Vector2 posicion = posicionInicial + new Vector2(i * separacionX, 0);
                Transform nuevoEnemigo = Instantiate(prefabEnemigo, posicion, Quaternion.identity);
                nuevoEnemigo.GetComponent<Enemigo>().ConfigurarDificultad(multiplicadorVelocidad, pausaMinima, pausaMaxima);
            }

            if (nivelActual < nivelMaximo)
            {
                nivelActual++;
            }

            ActualizarTextoNivel();
        }
    }

    void ActualizarTextoNivel()
    {
        if (textoNivel != null)
            textoNivel.text = "Maila: " + nivelActual;
    }
}