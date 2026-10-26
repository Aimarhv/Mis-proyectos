using System.Collections.Generic;
using UnityEngine;

public class Enemigo : MonoBehaviour
{
    [SerializeField] List<Transform> wayPoints = null;
    float velocidad = 2;
    float distanciaCambio = 0.2f;
    int siguientePosicion = 0;

    void Update()
    {
        if (wayPoints == null || wayPoints.Count == 0) return;

        transform.position = Vector3.MoveTowards(transform.position, wayPoints[siguientePosicion].position, velocidad * Time.deltaTime);

        if (Vector3.Distance(transform.position, wayPoints[siguientePosicion].position) < distanciaCambio)
        {
            siguientePosicion++;
            if (siguientePosicion >= wayPoints.Count)
            {
                siguientePosicion = 0;
            }
        }
    }

    private void OnTriggerEnter2D(Collider2D other)
    {
        if (other.CompareTag("Player"))
        {
            FindFirstObjectByType<GameController>().PerderVida();
        }
    }
}