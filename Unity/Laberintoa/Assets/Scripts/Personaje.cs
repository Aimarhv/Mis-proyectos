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

        transform.SetPositionAndRotation(posicionInicial, rotacionInicial);
    }
}