using UnityEngine;

public class DisparoEnemigo : MonoBehaviour
{
    void Update()
    {
        if (transform.position.y < -5)
        {
            Destroy(gameObject);
        }
    }
}