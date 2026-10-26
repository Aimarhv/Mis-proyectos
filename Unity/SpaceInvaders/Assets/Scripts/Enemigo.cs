using System.Collections;
using UnityEngine;

public class Enemigo : MonoBehaviour
{
    private float velocidadX = 2f;
    private float velocidadY = 1.2f;
    private float velocidadDisparo = -2;
    [SerializeField] Transform prefabDisparo = null;

    [Header("Límites de la zona")]
    [SerializeField] float limiteXMin = -4f;
    [SerializeField] float limiteXMax = 4f;
    [SerializeField] float limiteYMax = 2.5f;
    [SerializeField] float margenSobreNave = 0.5f;

    private float limiteYMin;

    [Header("Disparo")]
    [SerializeField] float pausaMinima = 3f;
    [SerializeField] float pausaMaxima = 9f;

    void Awake()
    {
        GameObject nave = GameObject.Find("Nave");
        if (nave != null)
        {
            limiteYMin = nave.transform.position.y + margenSobreNave;
        }
        else
        {
            limiteYMin = -0.5f;
        }
    }

    void Start()
    {
        StartCoroutine(Disparar());
    }

    void Update()
    {
        transform.Translate(
           velocidadX * Time.deltaTime,
           velocidadY * Time.deltaTime,
           0);

        Vector3 posicion = transform.position;

        if (posicion.x < limiteXMin)
        {
            posicion.x = limiteXMin;
            velocidadX = -velocidadX;
        }
        else if (posicion.x > limiteXMax)
        {
            posicion.x = limiteXMax;
            velocidadX = -velocidadX;
        }

        if (posicion.y < limiteYMin)
        {
            posicion.y = limiteYMin;
            velocidadY = -velocidadY;
        }
        else if (posicion.y > limiteYMax)
        {
            posicion.y = limiteYMax;
            velocidadY = -velocidadY;
        }

        transform.position = posicion;
    }

    IEnumerator Disparar()
    {
        while (true)
        {
            float pausa = Random.Range(pausaMinima, pausaMaxima);
            yield return new WaitForSeconds(pausa);
            Transform disparo = Instantiate(prefabDisparo, transform.position, Quaternion.identity);
            disparo.gameObject.GetComponent<Rigidbody2D>().linearVelocity = new Vector2(0, velocidadDisparo);
        }
    }

    public void ConfigurarDificultad(float multiplicadorVelocidad, float minimaDisparo, float maximaDisparo)
    {
        velocidadX *= multiplicadorVelocidad;
        velocidadY *= multiplicadorVelocidad;
        pausaMinima = minimaDisparo;
        pausaMaxima = maximaDisparo;
    }
}