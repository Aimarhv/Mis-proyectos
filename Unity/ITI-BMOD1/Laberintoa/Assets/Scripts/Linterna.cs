using UnityEngine;
using UnityEngine.UI;

public class Linterna : MonoBehaviour
{
    [Header("Referencias")]
    public Light luzLinterna;          // Spot Light hija de la cámara
    public AudioSource sonidoClick;    // Click al encender/apagar (opcional)

    [Header("Ambiente oscuro")]
    public bool oscurecerEscena = true;
    public Color luzAmbiente = new Color(0.047f, 0.055f, 0.078f);
    public bool usarNiebla = true;
    public Color colorNiebla = new Color(0.04f, 0.047f, 0.063f);
    public float densidadNiebla = 0.02f;

    [Header("Batería")]
    public float duracionBateria = 60f;        // segundos de luz con la batería llena
    public float recargaApagada = 1.5f;        // segundos de batería que recupera por segundo apagada
    public float minimoParaEncender = 3f;      // batería mínima para poder encenderla
    [Range(0f, 1f)] public float umbralBateriaBaja = 0.2f; // por debajo parpadea
    public Image barraBateria;                 // Image tipo Filled (opcional)

    public static bool Encendida { get; private set; }

    float bateria;
    float intensidadBase;

    void Awake()
    {
        if (oscurecerEscena)
        {
            RenderSettings.ambientMode = UnityEngine.Rendering.AmbientMode.Flat;
            RenderSettings.ambientLight = luzAmbiente;
            RenderSettings.skybox = null;
            RenderSettings.reflectionIntensity = 0f;
            Camera.main.clearFlags = CameraClearFlags.SolidColor;
            Camera.main.backgroundColor = luzAmbiente;

            if (usarNiebla)
            {
                RenderSettings.fog = true;
                RenderSettings.fogMode = FogMode.ExponentialSquared;
                RenderSettings.fogColor = colorNiebla;
                RenderSettings.fogDensity = densidadNiebla;
            }
        }

        bateria = duracionBateria;
        intensidadBase = luzLinterna.intensity;
        Encendida = false;
        luzLinterna.enabled = false;
    }

    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Space))
            Alternar();

        if (Encendida)
        {
            bateria -= Time.deltaTime;

            if (bateria <= 0f)
            {
                bateria = 0f;
                Apagar();
            }
            else if (bateria / duracionBateria < umbralBateriaBaja)
            {
                // Parpadeo cuando queda poca batería
                float ruido = Mathf.PerlinNoise(Time.time * 15f, 0f);
                luzLinterna.intensity = intensidadBase * Mathf.Lerp(0.2f, 1f, ruido);
            }
            else
            {
                luzLinterna.intensity = intensidadBase;
            }
        }
        else
        {
            // Apagada: recupera batería poco a poco
            bateria = Mathf.Min(duracionBateria, bateria + recargaApagada * Time.deltaTime);
        }

        if (barraBateria != null)
            barraBateria.fillAmount = bateria / duracionBateria;
    }

    void Alternar()
    {
        if (Encendida)
        {
            Apagar();
        }
        else if (bateria >= minimoParaEncender)
        {
            Encendida = true;
            luzLinterna.enabled = true;
            luzLinterna.intensity = intensidadBase;
            if (sonidoClick != null) sonidoClick.Play();
        }
    }

    void Apagar()
    {
        Encendida = false;
        luzLinterna.enabled = false;
        if (sonidoClick != null) sonidoClick.Play();
    }

    // Se llama al perder una vida: linterna apagada y batería llena, sin sonido
    public void Reiniciar()
    {
        Encendida = false;
        luzLinterna.enabled = false;
        luzLinterna.intensity = intensidadBase;
        bateria = duracionBateria;
    }

    // Para pilas que se puedan recoger más adelante
    public void Recargar(float segundos)
    {
        bateria = Mathf.Min(duracionBateria, bateria + segundos);
    }
}