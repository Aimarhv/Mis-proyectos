from odoo import fields, models


class Sede(models.Model):
    _name = 'ong.sede'
    _description = 'Sede'
    _rec_name = 'ciudad'

    codigo = fields.Char(string='Kodea', required=True)
    ciudad = fields.Char(string='Herria')
    pais = fields.Char(string='Bizilekua')
    direccion = fields.Char(string='Helbidea')
    telefono = fields.Char(string='Telefonoa')
    direct = fields.Char(string="Direct")

    proyecto_ids = fields.One2many('ong.proyecto', 'sede_id', string='Proiektua')
    

class Proyecto(models.Model):
    _name = 'ong.proyecto'
    _inherit = ['mail.thread']
    _description = 'Proyecto'
    _rec_name = 'titulo'

   
    cod = fields.Char(string='Kodea', required=True)
    titulo = fields.Char(string='Titulua')
    inicio = fields.Date(string='Hasiera')
    fin = fields.Date(string='Bukaera')
    presup = fields.Float(string='Presupuestoa', tracking=True)
    resp = fields.Char(string='Resp')

    sede_id = fields.Many2one('ong.sede', string='Sede',
                                       required=True, ondelete='cascade')
    actuacion_ids = fields.One2many('ong.actuacion', 'proyecto_cod', string='Aktuazioa')


class Actuacion(models.Model):
    _name = 'ong.actuacion'
    _description = 'Actuacion'

    inversion = fields.Float(string='Inbersioak')
    descrp = fields.Text(string='Deskripzioak')

    proyecto_cod = fields.Many2one('ong.proyecto', string='Proiektoa', 
                                   required=True, ondelete='cascade')
    poblacion_idpob = fields.Many2one('ong.poblacion', string='Poblazioa',
                                      required=True, ondelete='cascade')

class Poblacion(models.Model):
    _name = 'ong.poblacion'
    _description = 'Poblacion'
    _rec_name = 'nombre'

    idpob = fields.Char(string='ID', required=True)
    nombre = fields.Char(string='Izena', required=True)
    pais = fields.Char(string='Bizilekua')
    habs = fields.Integer(string='Habs')

    actuacion_ids = fields.One2many('ong.actuacion', 'poblacion_idpob', string='Aktuazioa')

    
