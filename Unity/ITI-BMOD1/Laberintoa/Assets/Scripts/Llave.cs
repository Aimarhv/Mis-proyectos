using UnityEngine;

public class Llave : MonoBehaviour
{
    // Lo lee la Meta para saber si puedes ganar
    public static bool Conseguida;

    [SerializeField] AudioSource sonidoRecoger;   // opcional
    [SerializeField] GameObject mensajeRecogida;  // opcional: un texto del Canvas

    void Awake()
    {
        Conseguida = false; // al reiniciar la escena vuelve a empezar sin llave
    }

    void OnTriggerEnter(Collider other)
    {
        if (!other.CompareTag("Player")) return;

        Conseguida = true;

        if (sonidoRecoger != null) sonidoRecoger.Play();
        if (mensajeRecogida != null) mensajeRecogida.SetActive(true);

        // Oculta el objeto pero deja vivo el sonido unos instantes
        foreach (var r in GetComponentsInChildren<Renderer>()) r.enabled = false;
        GetComponent<Collider>().enabled = false;
        Destroy(gameObject, 2f);
    }
}