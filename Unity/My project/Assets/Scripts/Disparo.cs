using UnityEngine;

public class Disparo : MonoBehaviour
{
    [SerializeField] Transform prefabExplosion;
    [SerializeField] int puntosPorEnemigo = 10;

    void Update()
    {
        if (transform.position.y > 5)
        {
            Destroy(gameObject);
        }
    }

    private void OnTriggerEnter2D(Collider2D otro)
    {
        if (otro.tag == "Enemigo")
        {
            Transform explosion = Instantiate(prefabExplosion,
                otro.transform.position,
                Quaternion.identity);
            Destroy(otro.gameObject);
            Destroy(explosion.gameObject, 1.5f);
            Destroy(gameObject);

            Nave.SumarPuntos(puntosPorEnemigo);
        }
    }
}