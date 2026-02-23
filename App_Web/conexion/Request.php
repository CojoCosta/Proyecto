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
            throw new Exception("Error HTTP: " . $httpCode . " - " . $response);
        }

        $result = json_decode($response);

        if (json_last_error() !== JSON_ERROR_NONE) {
            throw new Exception("Error decodificando JSON: " . json_last_error_msg());
        }

        return $result;
    }
}
