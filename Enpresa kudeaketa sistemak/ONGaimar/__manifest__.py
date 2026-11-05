{
    'name': 'ONGaimar',
    'version': '19.0.1.0.0',
    'summary': 'GKE proiektuen jarraipena (egoitzak, proiektuak, herriak eta aktuazioak)',
    'category': 'Sales',
    'author': 'Aimar',
    'license': 'LGPL-3',
    'depends': ['base', 'mail'],
    'data': [
        'security/ir.model.access.csv',
        'views/sede_views.xml',
        'views/proyecto_views.xml',
        'views/actuacion_views.xml',
        'views/poblacion_views.xml',
        'views/menus.xml',
        'demo/demo.xml'
    ],
    'application': True,
    'installable': True,
}