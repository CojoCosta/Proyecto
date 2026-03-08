<?php

class Request
{
    private $basePath;

    public function __construct($basePath)
    {
        $this->basePath = $basePath;
    }

    public function request($method, $url, $data = null)
    {
        if ($method === 'GET' && $data !== null) {
            $url .= '?' . http_build_query($data);
        }

        $cURLHandle = curl_init($this->basePath . $url);

        curl_setopt($cURLHandle, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($cURLHandle, CURLOPT_CUSTOMREQUEST, $method);
        curl_setopt($cURLHandle, CURLOPT_TIMEOUT, 30);
        curl_setopt($cURLHandle, CURLOPT_HTTPHEADER, [
            'Content-Type: application/json',
            'Accept: application/json'
        ]);

        if ($method !== 'GET' && $data !== null) {
            curl_setopt($cURLHandle, CURLOPT_POSTFIELDS, json_encode($data));
        }

        $response = curl_exec($cURLHandle);

        if (curl_errno($cURLHandle)) {
            throw new Exception("Error en la API: " . curl_error($cURLHandle));
        }

        $httpCode = curl_getinfo($cURLHandle, CURLINFO_HTTP_CODE);
        curl_close($cURLHandle);

        if ($httpCode >= 400) {
            // Si es error 500, podría ser un login fallido - retornamos null para manejarlo en el código
            if ($httpCode === 500) {
                return null;
            }
            throw new Exception("Error HTTP: " . $httpCode . " - " . $response);
        }

        if ($response === '' || $response === null) {
            return null;
        }

        $result = json_decode($response);
        if (json_last_error() === JSON_ERROR_NONE) {
            return $result;
        }

        // Algunas rutas exitosas devuelven texto plano.
        return $response;
    }
}
