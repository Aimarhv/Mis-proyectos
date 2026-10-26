using UnityEngine;

public class Diamante : MonoBehaviour
{
    [SerializeField] AudioClip sonido;
    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        FindObjectOfType<GameController>().AnotarItemRecogido();
        AudioSource.PlayClipAtPoint(sonido, Camera.main.transform.position);
        Destroy(gameObject);
    }
}
