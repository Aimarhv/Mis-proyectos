{
    'name': 'EnkanAimar',
    'version': '17.0.1.0.0',
    'summary': 'Interneteko enkante-etxearen kudeaketa (produktuak, loteak, parte-hartzaileak eta pujak)',
    'category': 'Sales',
    'author': 'Aimar',
    'license': 'LGPL-3',
    'depends': ['base'],
    'data': [
        'security/ir.model.access.csv',
        'views/produktua_views.xml',
        'views/lotea_views.xml',
        'views/parte_hartzailea_views.xml',
        'views/puja_views.xml',
        'views/menus.xml',
        'demo/demo.xml'
    ],
    'application': True,
    'installable': True,
}
