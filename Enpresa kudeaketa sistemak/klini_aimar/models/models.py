from odoo import models, fields


class Doctor(models.Model):
    _name = 'klini.doctor'
    _description = 'Doctor'
    _rec_name = 'nombre'

    codigo = fields.Char(string='Código', required=True)
    nombre = fields.Char(string='Nombre', required=True)
    espec = fields.Char(string='Especialidad')
    unidad_ids = fields.One2many('klini.unidad', 'doctor_id', string='Unidades a cargo')
    atencion_ids = fields.One2many('klini.atencion', 'doctor_id', string='Atenciones')

   


class Unidad(models.Model):
    _name = 'klini.unidad'
    _description = 'Unidad'
    _rec_name = 'nombre'

    identificador = fields.Char(string='Id', required=True)
    nombre = fields.Char(string='Nombre', required=True)
    planta = fields.Integer(string='Planta')
    doctor_id = fields.Many2one('klini.doctor', string='Doctor responsable', required=True)
    doctor_espec = fields.Char(related='doctor_id.espec', string='Especialidad', readonly=True)
    ingreso_ids = fields.One2many('klini.ingreso', 'unidad_id', string='Ingresos')

   

class Paciente(models.Model):
    _name = 'klini.paciente'
    _description = 'Paciente'
    _rec_name = 'nombre'

    ss = fields.Char(string='Nº Seguridad Social', required=True)
    nombre = fields.Char(string='Nombre', required=True)
    edad = fields.Integer(string='Edad')
    ingreso_ids = fields.One2many('klini.ingreso', 'paciente_id', string='Ingresos')
    atencion_ids = fields.One2many('klini.atencion', 'paciente_id', string='Atenciones')

   


class Ingreso(models.Model):
    _name = 'klini.ingreso'
    _description = 'Ingreso (relación N:M paciente-unidad)'
    _rec_name = 'paciente_id'

    paciente_id = fields.Many2one('klini.paciente', string='Paciente', required=True, ondelete='cascade')
    unidad_id = fields.Many2one('klini.unidad', string='Unidad', required=True, ondelete='cascade')
    fecha = fields.Date(string='Fecha de ingreso', required=True, default=fields.Date.context_today)
    paciente_ss = fields.Char(related='paciente_id.ss', string='Nº SS', readonly=True)
    paciente_edad = fields.Integer(related='paciente_id.edad', string='Edad', readonly=True)
    unidad_planta = fields.Integer(related='unidad_id.planta', string='Planta', readonly=True)
    doctor_resp = fields.Char(related='unidad_id.doctor_id.nombre', string='Doctor responsable', readonly=True)


class Atencion(models.Model):
    _name = 'klini.atencion'
    _description = 'Atención (relación N:M doctor-paciente)'
    _rec_name = 'sintoma'

    doctor_id = fields.Many2one('klini.doctor', string='Doctor', required=True, ondelete='cascade')
    paciente_id = fields.Many2one('klini.paciente', string='Paciente', required=True, ondelete='cascade')
    fecha = fields.Date(string='Fecha', required=True, default=fields.Date.context_today)
    sintoma = fields.Char(string='Síntoma')
    trat = fields.Text(string='Tratamiento')
    doctor_espec = fields.Char(related='doctor_id.espec', string='Especialidad', readonly=True)
    paciente_ss = fields.Char(related='paciente_id.ss', string='Nº SS', readonly=True)