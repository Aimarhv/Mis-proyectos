using System.Collections;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Meta : MonoBehaviour
{
    [SerializeField] GameObject mensaje;      // textua "Irabazi duzu"
    [SerializeField] float espera = 4f;

    bool terminado = false;

    void OnTriggerEnter(Collider other)
    {
        if (terminado) return;

        Personaje personaje = other.GetComponent<Personaje>();
        if (personaje == null) return;

        terminado = true;
        personaje.enabled = false;            // Pelota gelditu egiten da
        mensaje.SetActive(true);
        StartCoroutine(VolverAlMenu());
    }

    IEnumerator VolverAlMenu()
    {
        yield return new WaitForSeconds(espera);
        SceneManager.LoadScene(0);            // Menua
    }
}