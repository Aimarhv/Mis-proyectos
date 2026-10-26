from odoo import api, fields, models


class Promozioa(models.Model):
    _name = 'susta.promozioa'
    _description = 'Promozioa'
    _rec_name = 'izena'
    _order = 'kodea'

    kodea = fields.Char(string='Kodea', required=True)
    izena = fields.Char(string='Izena', required=True)
    herria = fields.Char(string='Herria')
    planoa = fields.Image(string='Planoa', max_width=1024, max_height=1024)

    etxebizitza_ids = fields.One2many('susta.etxebizitza', 'promozioa_id', string='Etxebizitzak')
    kontratu_ids = fields.One2many('susta.kontratua', 'promozioa_id', string='Kontratuak')


class Etxebizitza(models.Model):
    _name = 'susta.etxebizitza'
    _description = 'Etxebizitza'

    promozioa_id = fields.Many2one('susta.promozioa', string='Promozioa',
                                   required=True, ondelete='cascade')
    azalera = fields.Float(string='Azalera (m2)', aggregator='avg')
    gelak = fields.Integer(string='Gelak')
    bainugelak = fields.Integer(string='Bainugelak')
    planoa = fields.Image(string='Planoa', max_width=1024, max_height=1024)
    argazkia = fields.Image(string='Argazkia', max_width=1024, max_height=1024)
    prezioa = fields.Float(string='Prezioa')
    terraza = fields.Boolean(string='Terraza')
    jardina = fields.Boolean(string='Lorategi pribatua')
    piscina = fields.Boolean(string='Igerilekua')
    garajea = fields.Boolean(string='Garajea')


class Enpresa(models.Model):
    _name = 'susta.enpresa'
    _description = 'Enpresa'
    _rec_name = 'izena'

    izena = fields.Char(string='Izena', required=True)
    mota = fields.Char(string='Mota')
    helbidea = fields.Char(string='Helbide osoa')
    telefonoa = fields.Char(string='Telefonoa')
    faxa = fields.Char(string='Faxa')
    posta = fields.Char(string='Helbide elektronikoa')

    kontratu_ids = fields.One2many('susta.kontratua', 'enpresa_id', string='Kontratuak')


class Kontratua(models.Model):
    _name = 'susta.kontratua'
    _description = 'Kontratua'

    promozioa_id = fields.Many2one('susta.promozioa', string='Promozioa',
                                   required=True, ondelete='cascade')
    enpresa_id = fields.Many2one('susta.enpresa', string='Enpresa',
                                 required=True, ondelete='cascade')
    zenbatekoa = fields.Float(string='Zenbatekoa', required=True)
