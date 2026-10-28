using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class Nave : MonoBehaviour
{
    [SerializeField] float velocidad = 9;
    private float velocidadDisparo = 2;
    [SerializeField] Transform prefabDisparo = null;
    [SerializeField] UnityEngine.UI.Text textoPuntos;
    [SerializeField] string escenaBienvenida = "Bienvenida";
    [SerializeField] float esperaAntesDeVolver = 2f;
    [SerializeField] float limiteX = 4f;

    [Header("Disparo")]
    [SerializeField] float tiempoEntreDisparos = 0.5f;
    private float proximoDisparoPermitido = 0f;

    [Header("Bizitzak")]
    [SerializeField] int vidasIniciales = 3;
    [SerializeField] float tiempoInvulnerabilidad = 1f;
    [SerializeField] Image[] iconosCorazon; 
    private int vidas;
    private bool invulnerable = false;

    private bool juegoTerminado = false;
    private static int puntuacion = 0;
    private static UnityEngine.UI.Text textoPuntosEstatico;
    private static Nave instancia;

    void Awake()
    {
        instancia = this;
        puntuacion = 0;
        vidas = vidasIniciales;
        textoPuntosEstatico = textoPuntos;
        ActualizarTexto();
        ActualizarVidas();
    }

    void Update()
    {
        if (juegoTerminado) return;

        float horizontal = Input.GetAxis("Horizontal");
        transform.Translate(
            horizontal * velocidad * Time.deltaTime,
            0, 0);

        Vector3 posicion = transform.position;
        posicion.x = Mathf.Clamp(posicion.x, -limiteX, limiteX);
        transform.position = posicion;

        if (Input.GetButtonDown("Fire1") && Time.time >= proximoDisparoPermitido)
        {
            proximoDisparoPermitido = Time.time + tiempoEntreDisparos;

            GetComponent<AudioSource>().Play();
            Transform disparo = Instantiate(prefabDisparo, transform.position, Quaternion.identity);
            disparo.gameObject.GetComponent<Rigidbody2D>().linearVelocity = new Vector2(0, velocidadDisparo);
        }
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (juegoTerminado || invulnerable) return;

        if (collision.CompareTag("DisparoEnemigo"))
        {
            RecibirDaño();
        }
    }

    void RecibirDaño()
    {
        vidas--;
        ActualizarVidas();

        if (vidas <= 0)
        {
            GameOver();
        }
        else
        {
            StartCoroutine(Invulnerabilidad());
        }
    }

    IEnumerator Invulnerabilidad()
    {
        invulnerable = true;
        SpriteRenderer sprite = GetComponent<SpriteRenderer>();

        float tiempoTranscurrido = 0f;
        while (tiempoTranscurrido < tiempoInvulnerabilidad)
        {
            sprite.enabled = !sprite.enabled;
            yield return new WaitForSeconds(0.1f);
            tiempoTranscurrido += 0.1f;
        }

        sprite.enabled = true;
        invulnerable = false;
    }

    void GameOver()
    {
        if (juegoTerminado) return;
        juegoTerminado = true;
        textoPuntos.text = "Partida amaitu da";
        Time.timeScale = 0f;

        GetComponent<SpriteRenderer>().enabled = false;
        GetComponent<Collider2D>().enabled = false;

        StartCoroutine(VolverAlMenu());
    }

    public static void Victoria()
    {
        if (instancia != null)
            instancia.MostrarVictoria();
    }

    void MostrarVictoria()
    {
        if (juegoTerminado) return;
        juegoTerminado = true;
        textoPuntos.text = "Partida irabazita";
        Time.timeScale = 0f;

        StartCoroutine(VolverAlMenu());
    }

    IEnumerator VolverAlMenu()
    {
        yield return new WaitForSecondsRealtime(esperaAntesDeVolver);
        IrAlMenu();
    }

    public void IrAlMenu()
    {
        StopAllCoroutines();
        Time.timeScale = 1f;
        SceneManager.LoadScene(escenaBienvenida);
    }

    public static void SumarPuntos(int cantidad)
    {
        puntuacion += cantidad;
        ActualizarTexto();
    }

    static void ActualizarTexto()
    {
        if (textoPuntosEstatico != null)
            textoPuntosEstatico.text = "Puntuak: " + puntuacion;
    }

    void ActualizarVidas()
    {
        if (iconosCorazon == null) return;

        for (int i = 0; i < iconosCorazon.Length; i++)
        {
            if (iconosCorazon[i] != null)
                iconosCorazon[i].enabled = (i < vidas);
        }
    }
}