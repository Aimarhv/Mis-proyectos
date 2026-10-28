using UnityEngine;

public class GirarLlave : MonoBehaviour
{
    [SerializeField] float velocidadGiro = 120f;   // grados por segundo
    [SerializeField] float amplitudFlote = 0.15f;  // cuánto sube y baja
    [SerializeField] float velocidadFlote = 2f;

    Vector3 posicionInicial;

    void Start()
    {
        posicionInicial = transform.position;
    }

    void Update()
    {
        // Giro sobre su eje vertical
        transform.Rotate(0f, velocidadGiro * Time.deltaTime, 0f, Space.World);

        // Flotación suave
        float y = Mathf.Sin(Time.time * velocidadFlote) * amplitudFlote;
        transform.position = posicionInicial + new Vector3(0f, y, 0f);
    }
}