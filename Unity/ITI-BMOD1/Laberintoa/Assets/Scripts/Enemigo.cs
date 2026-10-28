using UnityEngine;
using UnityEngine.AI;

[RequireComponent(typeof(NavMeshAgent))]
public class Enemigo : MonoBehaviour
{
    [Header("Patrulla")]
    [SerializeField] Transform[] waypoints;
    [SerializeField] float velocidad = 2f;

    [Header("Persecución (linterna encendida + el enemigo te ve)")]
    [SerializeField] Transform jugador;
    [SerializeField] float radioVision = 12f;        // hasta qué distancia te ve
    [SerializeField] float velocidadPersecucion = 3.5f;
    [SerializeField] LayerMask capasParedes;         // capa de las paredes

    [Header("Daño")]
    [SerializeField] float distanciaGolpe = 1.5f;    // a esta distancia te toca
    [SerializeField] float tiempoEntreGolpes = 1.5f; // segundos entre golpes

    [Header("Sonido (AudioSource en bucle)")]
    [SerializeField] AudioSource sonidoEnemigo;
    [SerializeField] float distanciaSonido = 15f;
    [SerializeField] float volumenMaximo = 1f;
    [SerializeField] float pitchPatrulla = 1f;
    [SerializeField] float pitchPersecucion = 1.3f;

    NavMeshAgent agente;
    int indiceWaypoint = 0;
    bool persiguiendo;
    float proximoGolpe;

    void Start()
    {
        agente = GetComponent<NavMeshAgent>();
        agente.stoppingDistance = 0.1f;
        agente.speed = velocidad;

        if (jugador == null)
        {
            GameObject p = GameObject.FindGameObjectWithTag("Player");
            if (p != null) jugador = p.transform;
            else Debug.LogWarning("Enemigo: no encuentro al jugador. Arrástralo al campo Jugador.");
        }

        // Si no marcaste la capa de las paredes, usa la capa "Paredes"
        if (capasParedes.value == 0) capasParedes = LayerMask.GetMask("Paredes");

        if (waypoints.Length > 0) agente.SetDestination(waypoints[0].position);

        if (sonidoEnemigo != null)
        {
            sonidoEnemigo.loop = true;
            sonidoEnemigo.volume = 0f;
            sonidoEnemigo.Play();
        }
    }

    void Update()
    {
        if (jugador == null) return;

        bool antes = persiguiendo;
        persiguiendo = Linterna.Encendida && MeVes();

        if (persiguiendo)
        {
            agente.speed = velocidadPersecucion;
            agente.SetDestination(jugador.position);
        }
        else
        {
            agente.speed = velocidad;

            // Al perderte, vuelve a su ruta
            if (antes && waypoints.Length > 0)
                agente.SetDestination(waypoints[indiceWaypoint].position);

            Patrullar();
        }

        ComprobarGolpe();
        ActualizarSonido();
    }

    // true si estás cerca y no hay ninguna pared entre el enemigo y tú
    bool MeVes()
    {
        if (Vector3.Distance(transform.position, jugador.position) > radioVision) return false;

        Vector3 desde = transform.position + Vector3.up * 0.5f;
        Vector3 hasta = jugador.position + Vector3.up * 0.5f;
        return !Physics.Linecast(desde, hasta, capasParedes);
    }

    void Patrullar()
    {
        if (waypoints.Length == 0) return;

        if (!agente.pathPending && agente.remainingDistance <= 0.5f)
        {
            indiceWaypoint = (indiceWaypoint + 1) % waypoints.Length;
            agente.SetDestination(waypoints[indiceWaypoint].position);
        }
    }

    void ComprobarGolpe()
    {
        if (Time.time < proximoGolpe) return;

        Vector3 a = transform.position; a.y = 0;
        Vector3 b = jugador.position; b.y = 0;

        if (Vector3.Distance(a, b) <= distanciaGolpe)
        {
            proximoGolpe = Time.time + tiempoEntreGolpes;
            jugador.gameObject.SendMessage("PerderVida", SendMessageOptions.DontRequireReceiver);
        }
    }

    void ActualizarSonido()
    {
        if (sonidoEnemigo == null) return;

        float distancia = Vector3.Distance(transform.position, jugador.position);
        float cercania = 1f - Mathf.Clamp01(distancia / distanciaSonido);

        sonidoEnemigo.volume = Mathf.Lerp(sonidoEnemigo.volume, cercania * volumenMaximo, Time.deltaTime * 5f);
        sonidoEnemigo.pitch = Mathf.Lerp(sonidoEnemigo.pitch,
            persiguiendo ? pitchPersecucion : pitchPatrulla, Time.deltaTime * 3f);
    }

    void OnDrawGizmosSelected()
    {
        Gizmos.color = Color.red;
        Gizmos.DrawWireSphere(transform.position, distanciaGolpe);
        Gizmos.color = Color.yellow;
        Gizmos.DrawWireSphere(transform.position, radioVision);
    }
}