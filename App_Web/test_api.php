<?php
// Script de prueba para verificar la conexión con la API

echo "<h1>Test de Conexión API</h1>";

// Función para hacer requests sin procesar JSON
function testRequest($url) {
    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_TIMEOUT, 30);
    curl_setopt($ch, CURLOPT_HTTPHEADER, [
        'Content-Type: application/json',
        'Accept: application/json'
    ]);
    
    $response = curl_exec($ch);
    $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    $error = curl_error($ch);
    curl_close($ch);
    
    return [
        'response' => $response,
        'httpCode' => $httpCode,
        'error' => $error
    ];
}

// 1. Probar get usuarios
echo "<h2>1. Obtener todos los usuarios</h2>";
$result = testRequest("http://localhost:8080/apirest/rest/usuarios");
echo "<p>HTTP Code: " . $result['httpCode'] . "</p>";
echo "<pre>Response: " . htmlspecialchars($result['response']) . "</pre>";

// 2. Probar login con datos específicos
echo "<h2>2. Probar inicio de sesión</h2>";

// Reemplaza estos valores con los que uses para probar
$testUser = "test";  // Cambia esto
$testPass = "test";  // Cambia esto

$result2 = testRequest("http://localhost:8080/apirest/rest/usuarios/inicioSesion/" . urlencode($testUser) . "/" . urlencode($testPass));
echo "<p>HTTP Code: " . $result2['httpCode'] . "</p>";
echo "<pre>Response: " . htmlspecialchars($result2['response']) . "</pre>";
?>
