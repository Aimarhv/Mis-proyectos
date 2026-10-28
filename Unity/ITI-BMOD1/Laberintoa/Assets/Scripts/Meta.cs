using System.Collections;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Meta : MonoBehaviour
{
    [SerializeField] GameObject mensaje;       // textua "Irabazi duzu"
    [SerializeField] GameObject mensajeFalta;  // opcional: texto "Te falta la llave"
    [SerializeField] float espera = 4f;

    bool terminado = false;
    Coroutine ocultando;

    void OnTriggerEnter(Collider other)
    {
        if (terminado) return;

        Personaje personaje = other.GetComponent<Personaje>();
        if (personaje == null) return;

        // Sin la llave no se puede ganar
        if (!Llave.Conseguida)
        {
            if (mensajeFalta != null)
            {
                mensajeFalta.SetActive(true);
                if (ocultando != null) StopCoroutine(ocultando);
                ocultando = StartCoroutine(OcultarMensajeFalta());
            }
            return;
        }

        terminado = true;
        personaje.enabled = false;            // Pelota gelditu egiten da
        if (mensajeFalta != null) mensajeFalta.SetActive(false);
        mensaje.SetActive(true);
        StartCoroutine(VolverAlMenu());
    }

    IEnumerator OcultarMensajeFalta()
    {
        yield return new WaitForSeconds(2f);
        mensajeFalta.SetActive(false);
    }

    IEnumerator VolverAlMenu()
    {
        yield return new WaitForSeconds(espera);
        SceneManager.LoadScene(0);            // Menua
    }
}