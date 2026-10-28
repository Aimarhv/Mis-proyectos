using UnityEngine;

public class PersonajeImagen : MonoBehaviour
{
    [SerializeField] float balanceo = 0.08f;
    [SerializeField] float velocidadBalanceo = 6f;
    Vector3 posBase;

    void Start() { posBase = transform.localPosition; }

    void LateUpdate()
    {
        Vector3 dir = Camera.main.transform.position - transform.position;
        dir.y = 0;
        if (dir.sqrMagnitude > 0.001f)
            transform.rotation = Quaternion.LookRotation(-dir);

        float b = Mathf.Sin(Time.time * velocidadBalanceo) * balanceo;
        transform.localPosition = posBase + new Vector3(0, Mathf.Abs(b), 0);
    }
}