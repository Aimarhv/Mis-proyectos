from odoo import fields, models


class Aireportua(models.Model):
    _name = 'hegazkina.aireportua'
    _description = 'Aireportua'
    _rec_name = 'nombre'

    ald = fields.Char(string='ID', required=True)
    nombre = fields.Char(string='Izena', required=True)
    pais = fields.Char(string='Pais')
    ciudad = fields.Char(string='Ciudad')


    jatorriko_hegaldia_ids = fields.One2many('hegazkina.hegaldia', 'jatorriko_aireportua_id', string='Aireportua')
    helmuga_hegaldia_ids = fields.One2many('hegazkina.hegaldia', 'helmuga_aireportua_id', string='Aireportua')

class Hegaldia(models.Model):
    _name = 'hegazkina.hegaldia'
    _description = 'Hegaldia'
    _rec_name = 'vld'


    vld = fields.Char(string='ID', required=True)


    jatorriko_aireportua_id = fields.Many2one('hegazkina.aireportua', string='Jatorrizkoa aireportua', required=True)
    helmuga_aireportua_id = fields.Many2one('hegazkina.aireportua', string='Helmugako aireportua', required=True)
    tripulazioa_ids = fields.One2many('hegazkina.tripulazioa', 'hegaldia_id', string='Esleipenak')
    tripulatzaile_ids = fields.One2many('hegazkina.tripulatzailea', 'hegaldia_id', string='Tripulatzaileak')


class Tripulazioa(models.Model):
    _name = 'hegazkina.tripulazioa'
    _description = 'Tipulazioa'


    data = fields.Date(string='Data', required=True)

    hegaldia_id = fields.Many2one('hegazkina.hegaldia', string='Hegaldia', required=True, ondelete='cascade')
    hegazkina_id = fields.Many2one('hegazkina.hegazkina', string='Hegazkina', required=True, ondelete='restrict')
    

class Hegzkina(models.Model):
    _name = 'hegazkina.hegazkina'
    _description = 'Hegazkina'
    _rec_name = 'matrikula'

    matrikula = fields.Char(string='Matrikula', required=True)
    fabrikatzailea = fields.Char(string='Fabrikatzailea')
    modeloa = fields.Char(string='Modeloa')
    kapazitatea = fields.Integer(string='Kapazitatea')
    autonomia = fields.Float(string='Orduak')

    tripulazioa_ids = fields.One2many('hegazkina.tripulazioa', 'hegazkina_id', string='Esleitutako hegaldiak')


class Tripulatzailea(models.Model):
    _name = 'hegazkina.tripulatzailea'
    _description = 'Tripulatzailea'

    izena = fields.Char(string='Izena', required=True)
    abizena = fields.Char(string='Abizena', required=True)
    jaiotze_data = fields.Date(string='Jaiotze-data')
    nan = fields.Char(string='NAN', required=True)
    postua = fields.Selection([
        ('pilotua', 'Pilotua'),
        ('kopilotua', 'Kopilotua'),
        ('laguntzailea', 'Hegaldi-laguntzailea'),
    ], string='Postua', required=True)

    hegaldia_id = fields.Many2one('hegazkina.hegaldia', string='Hegaldia', ondelete='set null')