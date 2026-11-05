{
    'name': 'UniAimar',
    'version': '19.0.1.0.0',
    'summary': 'Unibertsitateko kideen kudeaketa (pertsonak, irakasleak, ikasleak, langileak eta ikastetxeak)',
    'category': 'Sales',
    'author': 'Aimar',
    'license': 'LGPL-3',
    'depends': ['base'],
    'data': [
        'security/ir.model.access.csv',
        'views/persona_views.xml',
        'views/profesor_views.xml',
        'views/alumno_views.xml',
        'views/centro_views.xml',
        'views/personal_views.xml',
        'views/menus.xml',
        'demo/demo.xml'
    ],
    'application': True,
    'installable': True,
}