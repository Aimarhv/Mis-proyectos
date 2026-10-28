using System.Collections;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class Personaje : MonoBehaviour
{
    float velocidadAvance = 2.0f;
    float velocidadRotacion = 100.0f;
    Vector3 posicionInicial;
    Quaternion rotacionInicial;
    Rigidbody rb;

    [Header("Vidas")]
    [SerializeField] Image[] corazones;          // Corazon1, Corazon2, Corazon3
    [SerializeField] float tiempoInvulnerable = 1f;

    [Header("Linterna")]
    [SerializeField] Linterna linterna;          // si lo dejas vacío, la busca sola

    [Header("Susto al perder")]
    [SerializeField] GameObject imagenSusto;     // Image a pantalla completa (desactivada)
    [SerializeField] AudioSource sonidoSusto;    // Grito (opcional)
    [SerializeField] float tiempoSusto = 1.5f;

    [Header("Derrota")]
    [SerializeField] GameObject mensajeDerrota;  // texto "Partida galdu duzu"
    [SerializeField] float esperaDerrota = 4f;

    int vidas;
    float ultimoGolpe = -10f;

    void Start()
    {
        vidas = corazones.Length;
        posicionInicial = transform.position;
        rotacionInicial = transform.rotation;
        rb = GetComponent<Rigidbody>();

        if (linterna == null) linterna = FindAnyObjectByType<Linterna>();
    }

    void Update()
    {
        float horizontal = Input.GetAxis("Horizontal");
        float vertical = Input.GetAxis("Vertical");

        float avance = vertical * velocidadAvance * Time.deltaTime;
        float rotacion = horizontal * velocidadRotacion * Time.deltaTime;

        transform.Rotate(Vector3.up, rotacion, Space.Self);
        transform.position += transform.forward * avance;
    }

    public void PerderVida()
    {
        if (Time.time - ultimoGolpe < tiempoInvulnerable) return;
        if (vidas <= 0) return;

        ultimoGolpe = Time.time;
        vidas--;
        corazones[vidas].gameObject.SetActive(false); // Bihotza kentzen dio

        Debug.Log("Jokalariak bizitza galdu du. Vidas: " + vidas);

        if (vidas <= 0)
        {
            StartCoroutine(Derrota());
        }
        else
        {
            VolverAlInicio();
        }
    }

    IEnumerator Derrota()
    {
        enabled = false;                       // la bola deja de moverse

        if (linterna != null)
        {
            linterna.Reiniciar();              // linterna apagada
            linterna.enabled = false;          // y sin poder encenderla mientras sale el susto
        }

        // Susto: imagen del monstruo + grito
        if (imagenSusto != null) imagenSusto.SetActive(true);
        if (sonidoSusto != null) sonidoSusto.Play();
        yield return new WaitForSeconds(tiempoSusto);
        if (imagenSusto != null) imagenSusto.SetActive(false);

        mensajeDerrota.SetActive(true);        // "Partida galdu duzu"
        yield return new WaitForSeconds(esperaDerrota);
        SceneManager.LoadScene(0);             // Menua
    }

    void VolverAlInicio()
    {
        if (rb != null)
        {
            rb.linearVelocity = Vector3.zero;
            rb.angularVelocity = Vector3.zero;
        }

        if (linterna != null) linterna.Reiniciar();   // vuelves con la linterna apagada

        transform.SetPositionAndRotation(posicionInicial, rotacionInicial);
    }
}