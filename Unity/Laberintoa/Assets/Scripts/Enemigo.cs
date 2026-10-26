using UnityEngine;

public class Enemigo : MonoBehaviour
{
    [SerializeField] Transform[] waypoints;
    Vector3 siguientePosicion;

    float distanciaCambio = 0.5f;
    float velocidad = 2.0f;
    int numeroSiguientePosicion = 0;

    void Start()
    {
        siguientePosicion = waypoints[0].position;
    }

    void Update()
    {
        transform.position = Vector3.MoveTowards(transform.position, siguientePosicion, velocidad * Time.deltaTime);
        if (Vector3.Distance(transform.position, siguientePosicion) < distanciaCambio)
        {
            numeroSiguientePosicion++;
            if (numeroSiguientePosicion >= waypoints.Length)
                numeroSiguientePosicion = 0;
            siguientePosicion = waypoints[numeroSiguientePosicion].position;
        }
    }

    void OnCollisionEnter(Collision collision)
    {
        collision.gameObject.SendMessage("PerderVida", SendMessageOptions.DontRequireReceiver);
    }
}