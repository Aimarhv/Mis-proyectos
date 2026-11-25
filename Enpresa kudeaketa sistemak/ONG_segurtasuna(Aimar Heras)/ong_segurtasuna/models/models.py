from odoo import fields, models


class Proyecto(models.Model):
    _inherit = 'ong.proyecto'

    # Presupuestoa arduradunek bakarrik ikusi dezakete
    presup = fields.Float(groups='ong_segurtasuna.group_arduradunak')
