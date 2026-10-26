using UnityEngine;

public class Player : MonoBehaviour
{
    float xInicial, yInicial;
    float velocidad = 3f;
    float horizontal;
    Animator anim;
    SpriteRenderer sprite;
    Collider2D col;
    Rigidbody2D rb;

    public float fuerzaSalto = 9f;
    public string estadoReposo = "PersonajeQuieto";

    void Start()
    {
        xInicial = transform.position.x;
        yInicial = transform.position.y;
        anim = GetComponent<Animator>();
        sprite = GetComponentInChildren<SpriteRenderer>();
        col = GetComponent<Collider2D>();
        rb = GetComponent<Rigidbody2D>();

        ConfigurarFisica();
    }

    // Deja el Rigidbody y el collider en un estado correcto y sin rozamiento
    void ConfigurarFisica()
    {
        rb.bodyType = RigidbodyType2D.Dynamic;
        rb.simulated = true;
        rb.constraints = RigidbodyConstraints2D.FreezeRotation;
        rb.interpolation = RigidbodyInterpolation2D.None;
        rb.collisionDetectionMode = CollisionDetectionMode2D.Continuous;
        rb.sleepMode = RigidbodySleepMode2D.NeverSleep;

        PhysicsMaterial2D sinRozamiento = new PhysicsMaterial2D("SinRozamiento");
        sinRozamiento.friction = 0f;
        sinRozamiento.bounciness = 0f;
        col.sharedMaterial = sinRozamiento;
        rb.sharedMaterial = sinRozamiento;
    }

    void Update()
    {
        horizontal = Input.GetAxis("Horizontal");

        if (anim != null)
        {
            if (Mathf.Abs(horizontal) > 0.1f)
                anim.Play("PersonajeCorriendo");
            else if (anim.HasState(0, Animator.StringToHash(estadoReposo)))
                anim.Play(estadoReposo);
        }

        if (horizontal > 0.1f) sprite.flipX = false;
        else if (horizontal < -0.1f) sprite.flipX = true;

        if (Input.GetButtonDown("Jump") && EstaEnSuelo())
            rb.AddForce(new Vector2(0, fuerzaSalto), ForceMode2D.Impulse);
    }

    void FixedUpdate()
    {
        rb.linearVelocity = new Vector2(horizontal * velocidad, rb.linearVelocity.y);
    }

    bool EstaEnSuelo()
    {
        Vector2 origen = new Vector2(col.bounds.center.x, col.bounds.min.y - 0.01f);
        RaycastHit2D impacto = Physics2D.Raycast(origen, Vector2.down, 0.15f);
        return impacto.collider != null && !impacto.collider.isTrigger;
    }

    public void Recolorar()
    {
        // Borrar todo lo que arrastraba al morir
        horizontal = 0;
        rb.linearVelocity = Vector2.zero;
        rb.angularVelocity = 0f;

        // Volver a dejar el Rigidbody en estado correcto
        ConfigurarFisica();

        // Mover el Rigidbody (no solo el transform) y subir un poco
        // para no reaparecer incrustado en la plataforma
        Vector2 inicio = new Vector2(xInicial, yInicial + 0.1f);
        rb.position = inicio;
        transform.position = new Vector3(inicio.x, inicio.y, 0);
        Physics2D.SyncTransforms();

        rb.WakeUp();
    }
}