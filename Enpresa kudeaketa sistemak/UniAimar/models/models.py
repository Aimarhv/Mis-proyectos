from odoo import fields, models


class Persona(models.Model):
    _name = 'uni.persona'
    _description = 'Persona'
    _rec_name = 'nombre'

    pid = fields.Char(string='ID', required=True)
    nombre = fields.Char(string='Izena')
    telefonoa = fields.Char(string='Telefonoa')
    email = fields.Char(string='Posta')
    dir = fields.Char(string='Helbidea')


class Profesor(models.Model):
    _name = 'uni.profesor'
    _description = 'Profesor'
    _rec_name = 'nombre'
    _inherits = {'uni.persona': 'persona_id'}


    ded = fields.Char(string='Dedikazioa')
    dept = fields.Char(string='Saila')


    persona_id = fields.Many2one('uni.persona', required=True, ondelete='cascade')
    centro_ids = fields.Many2many('uni.centro', 'uni_profesor_centro_rel',
                              'profesor_id', 'centro_id', string='Ikastetxeak')

    _sql_constraints = [
        ('persona_unica', 'unique(persona_id)',
         'Pertsona bat irakasle bakarra izan daiteke.'),
    ]


class Alumno(models.Model):
    _name = 'uni.alumno'
    _description = 'Alumno'
    _rec_name = 'nombre'
    _inherits = {'uni.persona': 'persona_id'}

    exp = fields.Char(string='Espedientea')
    titulacion = fields.Char(string='Titulazioa')

    centro_id = fields.Many2one('uni.centro', string='Ikastetxeak', ondelete='set null')
    persona_id = fields.Many2one('uni.persona', required=True, ondelete='cascade')
    
    _sql_constraints = [
            ('persona_unica', 'unique(persona_id)',
             'Pertsona bat ikaslea bakarra izan daiteke.'),
        ]

class Centro(models.Model):
    _name = 'uni.centro'
    _description = 'Centro'
    _rec_name = 'nombrec'

    nombrec = fields.Char(string='Zentroaren izena', required=True)


    profesor_ids = fields.Many2many('uni.profesor', 'uni_profesor_centro_rel',
                                    'centro_id', 'profesor_id', string='Irakaslea')
    alumno_ids = fields.One2many('uni.alumno', 'centro_id', string='Ikaslea')



    
class Personal(models.Model):
    _name = 'uni.personal'
    _description = 'Personal'
    _rec_name = 'nombre'
    _inherits = {'uni.persona': 'persona_id'}

    unidad = fields.Char(string='Unitatea')
    cat = fields.Char(string='Kategoria')


    persona_id = fields.Many2one('uni.persona', required=True, ondelete='cascade')
    
    _sql_constraints = [
            ('persona_unica', 'unique(persona_id)',
             'Pertsona bat langile bakarra izan daiteke.'),
        ]