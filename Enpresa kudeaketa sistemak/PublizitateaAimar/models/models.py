from odoo import fields, models


class Autor(models.Model):
    _name = 'publizitatea.autor'
    _description = 'Autor'
    _rec_name = 'izena'

    izena = fields.Char(string='Izena', required=True)
    email = fields.Char(string='Korreoa')
    adscrirp = fields.Text(string="adscrirp")

    articulo_ids = fields.Many2many(
        'publizitatea.articulo',
        'publizitatea_articulo_autor_rel',
        'autor_id',
        'articulo_id',
        string='Artikuluak',
    )
    


class Articulo(models.Model):
    _name = 'publizitatea.articulo'
    _description = 'Artikulua'
    _rec_name = 'titulo'

    titulo = fields.Text(string="Titulua")
    contenido = fields.Char(string="Contenido")
    fecha_pub = fields.Date(string="Data_pub")

    autor_ids = fields.Many2many(
        'publizitatea.autor',
        'publizitatea_articulo_autor_rel',  
        'articulo_id',                     
        'autor_id',
        string='Autores',
    )
    revista_id = fields.Many2one('publizitatea.revista', string="Revista")

class Revista(models.Model):
    _name = 'publizitatea.revista'
    _description = 'revista'
    _rec_name = 'issn'
    


    issn = fields.Char(required=True)
    numero = fields.Integer(required=True)
    titulo = fields.Char()
    año = fields.Integer(string="Año")


    articulo_id = fields.One2many('publizitatea.articulo', 'revista_id', string="Artículos")
    

