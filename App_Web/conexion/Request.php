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
        $cURLHandle = curl_init($this->basePath . $url);

        curl_setopt($cURLHandle, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($cURLHandle, CURLOPT_CUSTOMREQUEST, $method);

        if ($data !== null) {
            curl_setopt($cURLHandle, CURLOPT_HTTPHEADER, [
                'Content-Type: application/json'
            ]);
            curl_setopt($cURLHandle, CURLOPT_POSTFIELDS, json_encode($data));
        }

        $response = curl_exec($cURLHandle);

        // if (curl_errno($cURLHandle)) {
        //     throw new Exception("Error en la API: " . curl_error($cURLHandle));
        // }

        curl_close($cURLHandle);

        return json_decode($response);
    }
}
