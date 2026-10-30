{
    'name': 'PublizitateaAimar',
    'version': '19.0.1.0.0',
    'summary': 'Aldizkariak, artikuluak eta egileak',
    'category': 'Tools',
    'author': 'Aimar',
    'license': 'LGPL-3',
    'depends': ['base'],
    'data': [
        'security/ir.model.access.csv',
        'views/articulo_views.xml',
        'views/autor_views.xml',
        'views/revista_views.xml',
        'views/menus.xml',
        'demo/demo.xml',
    ],
    'application': True,
    'installable': True,
}