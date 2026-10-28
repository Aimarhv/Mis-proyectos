using UnityEngine;

// Ponlo en el objeto "Meta" (con un Collider2D marcado como Is Trigger)
public class Meta : MonoBehaviour
{
    private bool usada = false;

    void OnTriggerEnter2D(Collider2D other)
    {
        if (usada) return;

        // Solo reacciona al jugador
        if (other.GetComponentInParent<Player>() == null) return;

        usada = true;
        FindFirstObjectByType<GameController>().MetaAlcanzada();
    }
}