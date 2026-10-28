using System.Collections;
using UnityEngine;
using UnityEngine.SceneManagement;
using Unity.Cinemachine;

public class GameController : MonoBehaviour
{
    private int itemsRestantes;
    private int puntos;
    private int vidas;
    private int nivelActual;
    private GameStatus status;
    private Player player;

    // Partida amaitu den ala ez adierazten du
    private bool partidaTerminada = false;
    // Maila aldatzen ari den ala ez (bitan kargatzea saihesteko)
    private bool cambiandoNivel = false;
    // Meta zeharkatu den ala ez
    private bool juegoGanado = false;
    // Azken kolpearen unea (bizitza bikoitzak galtzea saihesteko)
    private float tiempoUltimoGolpe = -10f;

    [SerializeField] UnityEngine.UI.Text textoPartidaamaituda;
    // Azken mailan bakarrik: Meta objektua eta garaipen testua
    [SerializeField] GameObject meta;
    [SerializeField] UnityEngine.UI.Text textoVictoria;
    // Garaipen mezua zenbat segundotan ikusten den menura itzuli aurretik
    [SerializeField] float segundosVictoria = 4f;
    // Kolpe baten ondoren zenbat segundotan ezin da bizitzarik galdu
    [SerializeField] float tiempoInvulnerable = 1f;
    // Azken diamantea hartu eta mailaz aldatu arteko itxaronaldia
    [SerializeField] float retardoCambioNivel = 0.8f;

    [SerializeField] private UnityEngine.UI.Image[] corazones;

    // Cinemachine kamera (CinemachineCamera hemen arrastatu)
    [SerializeField] CinemachineCamera camaraCinemachine;

    void Start()
    {
        // Aurreko Game Over batetik badator, denbora normalean jarri
        Time.timeScale = 1;
        partidaTerminada = false;
        cambiandoNivel = false;
        juegoGanado = false;

        // Maila bakarra probatzen baduzu eta GameStatus ez badago, sortu egiten da
        status = FindFirstObjectByType<GameStatus>();
        if (status == null)
            status = new GameObject("GameStatus").AddComponent<GameStatus>();

        player = FindFirstObjectByType<Player>();

        // Mailan dauden diamante guztiak zenbatu
        itemsRestantes = FindObjectsByType<Diamante>(FindObjectsSortMode.None).Length;

        // GameStatus-etik puntuak eta bizitzak hartu
        puntos = status.puntos;
        vidas = status.vidas;

        // Hasieran bihotzak sinkronizatu
        ActualizarCorazonesUI();

        // Maila eszenaren izenetik ateratzen da ("Nivel2" -> 2)
        nivelActual = ObtenerNivelDeEscena();
        status.nivelActual = nivelActual;

        Debug.Log("Nivel " + nivelActual + " | Diamantes: " + itemsRestantes);

        // "Game Over" testua eta garaipen testua ezkutatu hasieran
        if (textoPartidaamaituda != null)
            textoPartidaamaituda.enabled = false;
        if (textoVictoria != null)
            textoVictoria.enabled = false;

        // Meta ez da agertzen diamante guztiak hartu arte
        if (meta != null)
            meta.SetActive(false);
    }

    private int ObtenerNivelDeEscena()
    {
        string nombre = SceneManager.GetActiveScene().name; // adib. "Nivel2"
        string numero = nombre.Replace("Nivel", "");
        if (int.TryParse(numero, out int n))
            return n;

        // Eszenaren izena NivelX ez bada, GameStatus-en gordetakoa erabiltzen da
        return status.nivelActual;
    }

    private bool EsUltimoNivel()
    {
        return !Application.CanStreamedLevelBeLoaded("Nivel" + (nivelActual + 1));
    }

    public void AnotarItemRecogido()
    {
        // Diamante bakoitzeko 10 puntu gehitu
        puntos += 10;
        status.puntos = puntos;

        // Falta diren diamanteak gutxitu
        itemsRestantes--;
        Debug.Log("Puntos: " + puntos + " | Items restantes: " + itemsRestantes);

        // Diamante guztiak hartu badira
        if (itemsRestantes <= 0 && !partidaTerminada && !cambiandoNivel)
        {
            if (EsUltimoNivel() && meta != null)
            {
                // Azken maila: meta agertzen da eta zeharkatu behar da
                meta.SetActive(true);
                Debug.Log("Todos los diamantes! Ha aparecido la meta");
            }
            else
            {
                // Gainerako mailak: hurrengora pasa
                Invoke(nameof(AvanzarNivel), retardoCambioNivel);
            }
        }
    }

    // Meta scriptak deitzen du jokalariak zeharkatzen duenean
    public void MetaAlcanzada()
    {
        if (juegoGanado || partidaTerminada) return;
        juegoGanado = true;
        StartCoroutine(Victoria());
    }

    private IEnumerator Victoria()
    {
        // Jokalaria gelditu
        if (player != null)
        {
            Rigidbody2D rb = player.GetComponent<Rigidbody2D>();
            if (rb != null) rb.linearVelocity = Vector2.zero;
            player.enabled = false;
        }

        // Garaipen mezua erakutsi
        if (textoVictoria != null)
            textoVictoria.enabled = true;

        yield return new WaitForSecondsRealtime(segundosVictoria);

        // Menura itzuli
        SceneManager.LoadScene("Menu");
    }

    public void PerderVida()
    {
        // Partida amaitu bada edo irabazi bada, ez egin ezer
        if (partidaTerminada || juegoGanado) return;

        // Segidako bi ukitu kolpe bakar gisa zenbatzeko
        if (Time.time - tiempoUltimoGolpe < tiempoInvulnerable) return;
        tiempoUltimoGolpe = Time.time;

        // Bizitza bat kendu eta GameStatus-en gorde
        vidas--;
        status.vidas = vidas;

        // Bihotzak eguneratu bizitza galtzean
        ActualizarCorazonesUI();
        Debug.Log("Vidas: " + vidas);

        if (vidas <= 0)
        {
            // Azken bizitza: jokalaria eta kamera geldirik geratzen dira
            partidaTerminada = true;
            CongelarJugadorYCamara();
            StartCoroutine(TerminarPartida());
        }
        else
        {
            // Bizitzak geratzen dira: hasierako posiziora itzuli
            if (player != null)
                player.Recolorar();
        }
    }

    // BERRIA: galtzean, kamerak ez du jokalaria jarraitzen eta jokalaria ez da mugitzen
    private void CongelarJugadorYCamara()
    {
        // Kamerak jokalaria jarraitzeari uzten dio
        if (camaraCinemachine != null)
            camaraCinemachine.Follow = null;

        // Jokalaria geldirik geratzen da
        if (player != null)
        {
            Rigidbody2D rb = player.GetComponent<Rigidbody2D>();
            if (rb != null)
            {
                rb.linearVelocity = Vector2.zero;
                rb.simulated = false;
            }
            player.enabled = false;
        }
    }

    private void ActualizarCorazonesUI()
    {
        if (corazones == null) return;

        for (int i = 0; i < corazones.Length; i++)
        {
            if (corazones[i] != null)
            {
                // Bihotza ikusten da bere indizea oraingo bizitzak baino txikiagoa bada
                corazones[i].gameObject.SetActive(i < vidas);
            }
        }
    }

    private IEnumerator TerminarPartida()
    {
        // "Game Over" testua erakutsi
        if (textoPartidaamaituda != null)
            textoPartidaamaituda.enabled = true;

        // Jokoa moteldu eta 3 segundo itxaron (denbora errealean)
        Time.timeScale = 0.1f;
        yield return new WaitForSecondsRealtime(3);

        // Denbora normalera itzuli eta menura joan
        Time.timeScale = 1;
        SceneManager.LoadScene("Menu");
    }

    public void AvanzarNivel()
    {
        if (cambiandoNivel) return;
        cambiandoNivel = true;

        int siguiente = nivelActual + 1;
        string escenaSiguiente = "Nivel" + siguiente;

        // Hurrengo eszena Build Settings-en ez badago, azken maila zen
        if (!Application.CanStreamedLevelBeLoaded(escenaSiguiente))
        {
            Debug.Log("No existe " + escenaSiguiente + " -> vuelvo al Menu");
            SceneManager.LoadScene("Menu");
            return;
        }

        // Maila berria gorde eta kargatu
        status.nivelActual = siguiente;
        Debug.Log("Cargando " + escenaSiguiente);
        SceneManager.LoadScene(escenaSiguiente);
    }
}