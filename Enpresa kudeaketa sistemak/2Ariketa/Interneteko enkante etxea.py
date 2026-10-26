from odoo import models, fields, api

class ProduktuaAdibidea(models.Model):
    _name = 'produktua.adibidea'
    _description = 'Produktua'

    kodea = fields.Char(string="ID", required=True)
    izena = fields.Char(string="Zure izena", required=True)
    deskr = fields.Text(string="Deskribapena")
    argazki = fields.Binari(string="Argazkia")
    
    lotea_id = fields.Many2one('lotea.adibidea', string="Lotea", required=True)


class LoteaAdibidea(models.Model):
    _name = 'lotea.adibidea'
    _description = 'Lotea'
    _rec_name = 'zenbaki'

    zenbaki = fields.Char(string="Zenbakia", required=True)
    irteera_prezioa = fields.Float(string="Irteera prezioa" digits=(8,2))
    pujamax = fields.Float(string="Puja maximoa")
    denbora = fields.Char(string="Denbora")
    
    produktua_ids = fields.One2many('produktua.adibidea', 'lotea_id', string="Produktuak")
    puja_ids = fields.One2many('puja.adibidea', 'lotea_id', string="Pujak")


class ParteHartzaileAdibidea(models.Model):
    _name = 'parte.hartzaile.adibidea'
    _description = 'Parte-hartzailea'

    erabiltzaile = fields.Char(string="Erabiltzailea", required=True)
    posta = fields.Char(string="Posta")
    izena = fields.Char(string="Izena")
    gakoa = fields.Char(string="Gakoa")
    
    puja_ids = fields.One2many('puja.adibidea', 'parte_hartzaile_id', string="Pujak")

    # Display name pertsonalizatua parte-hartzailearentzat (adibidez: izena eta erabiltzailea elkarrekin)
    @api.depends('izena', 'erabiltzaile')
    def _compute_display_name(self):
        for record in self:
            izena = record.izena or ''
            erabil = record.erabiltzaile or ''
            record.display_name = f"{izena} ({erabil})" if izena and erabil else (izena or erabil or 'Parte-hartzailea')


class PujaAdibidea(models.Model):
    _name = 'puja.adibidea'
    _description = 'Puja'

    eguna = fields.Date(string="Eguna")
    ordua = fields.Char(string="Ordua")
    kantitatea = fields.Integer(string="Kantitatea")
    
    lotea_id = fields.Many2one('lotea.adibidea', string="Lotea", required=True, ondelete='cascade')
    parte_hartzaile_id = fields.Many2one('parte.hartzaile.adibidea', string="Parte-hartzailea", required=True, ondelete='cascade')