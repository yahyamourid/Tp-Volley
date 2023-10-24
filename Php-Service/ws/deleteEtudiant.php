<?php 
if($_SERVER["REQUEST_METHOD"] == "DELETE"){ 
    include_once("../racine.php"); 
    include_once RACINE.'/service/EtudiantService.php'; 

    // Récupérer l'ID de l'URL
    $id = $_GET['id'];

    delete($id); 
} 

function delete($id){ 
    $es = new EtudiantService(); 
    $es->delete($id); 

    // Chargement de la liste des étudiants sous format json 
    header('Content-type: application/json'); 
    echo json_encode($es->findAllApi()); 
}
