from odoo import api, fields, models


class Produktua(models.Model):
    _name = 'enkan.produktua'
    _description = 'Produktua'
    _order = 'kodea'

    kodea = fields.Char(string='Kodea', required=True)
    izena = fields.Char(string='Izena', required=True)
    deskribapena = fields.Text(string='Deskribapen laburra')
    argazkia = fields.Image(string='Argazkia', max_width=1024, max_height=1024)
    lotea_id = fields.Many2one('enkan.lotea', string='Lotea', ondelete='set null')
    x = fields.Char(string='Lote zenbakia', related='lotea_id.zenbakia')


class Lotea(models.Model):
    _name = 'enkan.lotea'
    _description = 'Lotea'
    _rec_name = 'zenbakia'
    _order = 'zenbakia'

    zenbakia = fields.Char(string='Katalogo-zenbakia', required=True)
    irteera_prezioa = fields.Float(string='Irteera-prezioa', required=True)
    amaiera_data = fields.Datetime(string='Amaiera data/ordua')
    denbora = fields.Float(string='Geratzen den denbora (h)')
    pujamax = fields.Float(string='Puja handiena')

    produktu_ids = fields.One2many('enkan.produktua', 'lotea_id', string='Produktuak')
    puja_ids = fields.One2many('enkan.puja', 'lotea_id', string='Pujak')

    @api.depends('zenbakia', 'irteera_prezioa')
    def _compute_display_name(self):
        for r in self:
            r.display_name = f'{r.zenbakia} ({r.irteera_prezioa} €)'

class ParteHartzailea(models.Model):
    _name = 'enkan.parte_hartzailea'
    _description = 'Parte-hartzailea'
    _rec_name = 'izena'

    erabiltzailea = fields.Char(string='Erabiltzaile-izena', required=True)
    gakoa = fields.Char(string='Pasahitza', required=True)
    izena = fields.Char(string='Izena', required=True)
    posta = fields.Char(string='Helbide elektronikoa', required=True)
    puja_ids = fields.One2many('enkan.puja', 'parte_hartzailea_id', string='Pujak')


class Puja(models.Model):
    _name = 'enkan.puja'
    _description = 'Puja'

    lotea_id = fields.Many2one('enkan.lotea', string='Lotea', required=True, ondelete='cascade')
    parte_hartzailea_id = fields.Many2one('enkan.parte_hartzailea', string='Parte-hartzailea',
                                          required=True, ondelete='cascade')
    kantitatea = fields.Float(string='Kantitatea', required=True)
    eguna = fields.Date(string='Eguna', default=fields.Date.today, required=True)
    ordua = fields.Float(string='Ordua', required=True)
    x = fields.Float(string='Irteera-prezioa', related='lotea_id.irteera_prezioa')