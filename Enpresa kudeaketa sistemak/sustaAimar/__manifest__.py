{
    'name': 'SustaAimar',
    'version': '19.0.1.0.0',
    'summary': 'Etxebizitza-sustapenen kudeaketa (promozioak, etxebizitzak, enpresak eta kontratuak)',
    'category': 'Sales',
    'author': 'Aimar',
    'license': 'LGPL-3',
    'depends': ['base'],
    'data': [
        'security/ir.model.access.csv',
        'views/promozioa_views.xml',
        'views/etxebizitza_views.xml',
        'views/enpresa_views.xml',
        'views/kontratua_views.xml',
        'views/menus.xml',
    ],
    'demo': [
        'demo/demo.xml',
    ],
    'application': True,
    'installable': True,
}