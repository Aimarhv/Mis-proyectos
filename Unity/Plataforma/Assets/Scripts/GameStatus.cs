using UnityEngine;

public class GameStatus : MonoBehaviour
{
    public int puntos = 0;
    public int vidas = 3;
    public int nivelActual = 1;
    public int nivelMasAlto = 2;

    private void Awake()
    {
        int gameStatusCount = FindObjectsOfType<GameStatus>().Length;
        if (gameStatusCount > 1)
        {
            Destroy(gameObject);
        }
        else
        {
            DontDestroyOnLoad(gameObject);
        }
    }

    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {
    }
}