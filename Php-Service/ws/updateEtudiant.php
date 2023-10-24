<?php 
if($_SERVER["REQUEST_METHOD"] == "PUT"){ 
    include_once("../racine.php"); 
    include_once RACINE.'/service/EtudiantService.php'; 

    // Récupérer l'ID de l'URL
    $id = $_GET['id'];
    $nom = $_GET['nom'];
    $prenom = $_GET['prenom'];
    $ville = $_GET['ville'];
    $sexe = $_GET['sexe'];

    update($id, $nom, $prenom, $ville, $sexe);
} 

function update($id, $nom, $prenom, $ville, $sexe){ 
    $es = new EtudiantService(); 
    $es->update($id, $nom, $prenom, $ville, $sexe); 

    // Chargement de la liste des étudiants sous format json 
    header('Content-type: application/json'); 
    echo json_encode($es->findAllApi()); 
}
