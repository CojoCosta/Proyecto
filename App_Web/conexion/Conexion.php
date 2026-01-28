<?php
class Conexion
{
    public static function conexion()
    {
        try {
            $conexion = new PDO(
                "mysql:host=sql.freedb.tech;dbname=freedb_Proyecto;charset=utf8",
                "freedb_DiegoCosta",
                "2?#T#@qg5S&2sEr"
            );
        } catch (PDOException $ex) {
            die("Error" . $ex->getMessage());
        }
    }
}
