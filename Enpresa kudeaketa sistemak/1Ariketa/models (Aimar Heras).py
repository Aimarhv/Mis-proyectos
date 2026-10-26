from odoo import models, fields

class Ikasleak(models.Model):
    _name = 'adibidea.ikasleak'
    

    izena = fields.Char(string = "Zure izena", required = True )
    abizena = fields.Char(string = "Zure abizena", required = True)
    helbidea = fields.Char(string = "Helbidea", required = True)
    herria = fields.Char(string = "Herria")
    jaiotza = fields.Date(string = "Jaiotza", required = True)
    nan = fields.Char(string = "Nan", required = True)
    kotxea = fields.Boolean(string = "Kotxea")
    adinHandia = fields.Boolean(string = "AdinHandia")
    maila = fields.Char(string = "Maila", required = True)
    bestelakoak = fields.Char(string = "Bestelakoak")



    taldea = fields.Many2one(
        'adibidea.taldeak',
        string="Taldea"
    )

    @api.depends('izena', 'abizena', 'nan')
    def _compute_display_name(self):
        for rec in self:
            if rec.izena and rec.abizena:
                rec.display_name = f"{rec.nan} - {rec.izena}, {rec.abizena}"
            else:
                rec.display_name = f"{rec.nan}, {rec.izena}"

    

class Enpresak(models.Model):
    _name = 'adibidea.enpresak'
    _rec_name = 'izena'

    izena = fields.Char(string = "Enpresaren izena", required = True )
    helbidea = fields.Char(string = "Enpresaren Helbidea", required = True)
    auzoa = fields.Char(string="Auzoa", required = True)
    herria = fields.Char(string="Herria", required = True)
    sektorea = fields.Selection(
    [
        ('1', 'Lehen sektorea'),
        ('2', 'Bigarren sektorea'),
        ('3', 'Hirugarren sektorea')
    ],
    string="Sektorea"
)
    tamaina = fields.Char(string="Tamaina")
    telefonoa = fields.Char(string="Telefonoa", required = True)
    instruktorea = fields.Char(string="Instruktorea", required = True)
    instruktorearen_posta = fields.Char(string="Instruktorearen posta", required = True)

class Taldeak(models.Model):
        _name = 'adibidea.taldeak'

        name = fields.Char(string = "Taldearen izena", required = True)
        tutorea = fields.Char(string = "Tutorearen izena", required = True)
        ikasturtea = fields.Char(string = "Ikasturtea")

class Praktikak(models.Model):
        _name = 'adibidea.praktikak'

        ikaslea = fields.Many2one(
                'adibidea.ikasleak',
                string="Ikaslea"
            )

        enpresa = fields.Many2one(
                        'adibidea.enpresak',
                        string="Enpresa"
                    )

        ikasturtea = fields.Char(string = "Ikasturtea")
        hasiera = fields.Date(string ="Hasiera")
        bukaera = fields.Date(string ="Bukaera")
        ordutegia = fields.Char(string = "Ordutegia")
        iraupena = fields.Char(string = "Iraupena")

        @api.depends('ikaslea', 'enpresa')
        def _compute_display_name(self):
            for rec in self:
                rec.display_name = f"{rec.ikaslea.izena or ''} - {rec.enpresa.izena or ''}"


        









